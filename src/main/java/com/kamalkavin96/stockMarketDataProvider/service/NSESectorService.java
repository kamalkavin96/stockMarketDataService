package com.kamalkavin96.stockMarketDataProvider.service;

import com.kamalkavin96.stockMarketDataProvider.dto.NSEEquityClassificationView;
import com.kamalkavin96.stockMarketDataProvider.dto.NSEEquitySectorMetaResponse;
import com.kamalkavin96.stockMarketDataProvider.exception.NotFoundException;
import com.kamalkavin96.stockMarketDataProvider.model.NSEIndustries;
import com.kamalkavin96.stockMarketDataProvider.model.NSESector;
import com.kamalkavin96.stockMarketDataProvider.repository.NSEIndustriesRepo;
import com.kamalkavin96.stockMarketDataProvider.repository.NSESectorRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class NSESectorService {
    @Autowired
    NSESectorRepo nseSectorRepo;

    @Autowired
    NSEIndustriesRepo nseIndustriesRepo;

    @Autowired
    NSEEquityClassificationService nseEquityClassificationService;

    public List<NSEEquitySectorMetaResponse> getAllSectors() {

        List<NSESector> sectors = nseSectorRepo.findAll();
        List<NSEEquityClassificationView> classifications = nseEquityClassificationService.getStockClassificationData();
        Map<String, Long> stockCountBySector = classifications.stream()
                .collect(Collectors.groupingBy(
                        NSEEquityClassificationView::getSector,
                        Collectors.counting()));

        List<NSEEquitySectorMetaResponse> nseEquitySectorMetaResponses = sectors.stream().map(sector -> {
            Long stockCount = stockCountBySector.get(sector.getName());

            Long industryCount = classifications.stream()
                    .filter(each -> each.getSector().equals(sector.getName()))
                    .map(NSEEquityClassificationView::getIndustry)
                    .distinct()
                    .count();

            NSEEquitySectorMetaResponse nseEquitySectorMetaResponse = new NSEEquitySectorMetaResponse(
                    sector.getId(),
                    sector.getName(),
                    sector.getDescription(),
                    sector.getLogoName(),
                    industryCount,
                    stockCount);

            return nseEquitySectorMetaResponse;
        }).toList();

        return nseEquitySectorMetaResponses;
    }

    public List<NSEIndustries> getAllIndustriesForSector(Integer sectorId) {
        nseSectorRepo.findById(sectorId)
                .orElseThrow(() -> new NotFoundException("Sector not found with ID: " + sectorId));
        return nseIndustriesRepo.findIndustriesBySectorId(sectorId);
    }
}
