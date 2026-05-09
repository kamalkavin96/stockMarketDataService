package com.kamalkavin96.stockMarketDataProvider.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/market-home")
public class MarketHomeController {

    @GetMapping("/market-overview")
    public Map<String, String> marketOverview(@RequestParam String param) {
        Map<String, String> response = Map.of("message", param);
        return response;
    }

}
