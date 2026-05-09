package com.kamalkavin96.stockMarketDataProvider.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kamalkavin96.stockMarketDataProvider.service.GrowwClient;

@RestController
@RequestMapping("/history")
public class NSEEquityHistoryController {

    @Autowired
    private GrowwClient growwClient;

    @GetMapping("/indices/{symbol}/{range}/{interval}")
    public List<GrowwClient.Candle> getIndicesChartData(
            @PathVariable String symbol,
            @PathVariable String range,
            @PathVariable String interval) {

        System.out.println(symbol);
        System.out.println(range);
        System.out.println(interval);

        return growwClient.historyPrice(symbol, range, interval);
    }
}