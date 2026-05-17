package com.kamalkavin96.stockMarketDataProvider.service;

import com.kamalkavin96.stockMarketDataProvider.dto.NSEGainerView;
import com.kamalkavin96.stockMarketDataProvider.exception.NotFoundException;
import com.kamalkavin96.stockMarketDataProvider.model.NSEEquity;
import com.kamalkavin96.stockMarketDataProvider.repository.NSEEquityRepo;
import com.kamalkavin96.stockMarketDataProvider.repository.NSEMacroSectorRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NSEEquityService {
    @Autowired
    NSEEquityRepo nseEquityRepo;

    @Autowired
    NSEMacroSectorRepo nseMacroSectorRepo;

    public List<NSEEquity> getNseEquityList() {
        return nseEquityRepo.findAll();
    }

    public NSEEquity getNseEquityMeta(String symbol) {
        return nseEquityRepo.findBySymbol(symbol).orElse(null);
    }

    public List<NSEEquity> searchEquity(String symbol) {
        return nseEquityRepo.searchBySymbolPrefix(symbol);
    }

    public List<String> searchSymbol(String search) {

        String query = search.toLowerCase();

        return nseEquityRepo.searchByEquityOnlySymbolPrefix(search)
                .stream()
                .sorted((a, b) -> {
                    int scoreA = calculateScore(a, query);
                    int scoreB = calculateScore(b, query);
                    return Integer.compare(scoreB, scoreA);
                })
                .limit(15)
                .toList();
    }

    private int calculateScore(String symbol, String query) {
        String s = symbol.toLowerCase();
        int score = 0;
        // exact match
        if (s.equals(query))
            score += 1000;
        // starts with
        if (s.startsWith(query))
            score += 500;
        // contains
        if (s.contains(query))
            score += 100;
        // shorter symbols first
        score += Math.max(0, 20 - s.length());
        return score;
    }

    public List<NSEEquity> findAllEquityUNderMacroSector(Integer macroSectorId) {
        nseMacroSectorRepo.findById(macroSectorId)
                .orElseThrow(() -> new NotFoundException("Macro-Sector not found with ID: " + macroSectorId));
        return nseEquityRepo.findEquityForMacroSector(macroSectorId);
    }

    public List<NSEGainerView> getEquityGainersByIndex(String indexSymbol) {

        List<NSEGainerView> equities = nseEquityRepo.findGainerByIndex(indexSymbol);
        return equities;
    }

    public List<NSEGainerView> getEquityLosersByIndex(String indexSymbol) {

        List<NSEGainerView> equities = nseEquityRepo.findLoserByIndex(indexSymbol);
        return equities;
    }

}
