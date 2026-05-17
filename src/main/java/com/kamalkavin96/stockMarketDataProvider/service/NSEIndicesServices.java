package com.kamalkavin96.stockMarketDataProvider.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.kamalkavin96.stockMarketDataProvider.dto.NSEIndexItemResponse;
import com.kamalkavin96.stockMarketDataProvider.dto.NSEIndicesCategoryResponse;
import com.kamalkavin96.stockMarketDataProvider.dto.NSEIndicesPerformanceView;
import com.kamalkavin96.stockMarketDataProvider.model.NSEIndices;
import com.kamalkavin96.stockMarketDataProvider.repository.NSEIndicesRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NSEIndicesServices {

    private final NSEIndicesRepo nseIndicesRepo;

    public List<NSEIndicesCategoryResponse> getIndicesPerformance() {

        List<NSEIndicesPerformanceView> data = nseIndicesRepo.getIndicesPerformance();

        Map<String, List<NSEIndexItemResponse>> groupedData = data.stream()
                .collect(Collectors.groupingBy(
                        NSEIndicesPerformanceView::getCategory,
                        Collectors.mapping(
                                item -> new NSEIndexItemResponse(
                                        item.getSymbol(),
                                        item.getIndexName(),
                                        item.getLtp(),
                                        item.getPriceChange(),
                                        item.getPChange()),
                                Collectors.toList())));

        return groupedData.entrySet()
                .stream()
                .map(entry -> new NSEIndicesCategoryResponse(
                        entry.getKey(),
                        entry.getValue()))
                .toList();
    }

    public List<NSEIndices> getAllIndicesList(){
        return nseIndicesRepo.findAll();
    }
}