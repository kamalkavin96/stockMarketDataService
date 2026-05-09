package com.kamalkavin96.stockMarketDataProvider.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.kamalkavin96.stockMarketDataProvider.dto.NSEEquityClassificationView;
import com.kamalkavin96.stockMarketDataProvider.repository.NSEEquityClassificationRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NSEEquityClassificationService {

    private final NSEEquityClassificationRepo nseEquityClassificationRepo;

    public List<NSEEquityClassificationView> getStockClassificationData() {
        return nseEquityClassificationRepo.getCompleteClassification();
    }

}
