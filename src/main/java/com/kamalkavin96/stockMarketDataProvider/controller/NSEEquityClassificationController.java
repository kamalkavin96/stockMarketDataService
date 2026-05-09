package com.kamalkavin96.stockMarketDataProvider.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kamalkavin96.stockMarketDataProvider.dto.NSEEquityClassificationView;
import com.kamalkavin96.stockMarketDataProvider.service.NSEEquityClassificationService;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/nse-classification")
@RequiredArgsConstructor
public class NSEEquityClassificationController {

    private final NSEEquityClassificationService nseEquityClassificationService;


    @GetMapping("/stock")
    public List<NSEEquityClassificationView> stockClassification() {
        return nseEquityClassificationService.getStockClassificationData();
    }
    
}
