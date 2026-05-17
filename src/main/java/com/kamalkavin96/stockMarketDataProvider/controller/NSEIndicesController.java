package com.kamalkavin96.stockMarketDataProvider.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kamalkavin96.stockMarketDataProvider.dto.NSEIndicesCategoryResponse;
import com.kamalkavin96.stockMarketDataProvider.model.NSEIndices;
import com.kamalkavin96.stockMarketDataProvider.service.NSEIndicesServices;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/indices")
@RequiredArgsConstructor
public class NSEIndicesController {

    private final NSEIndicesServices nseIndicesServices;

    @GetMapping("/nse/info")
    public  List<NSEIndicesCategoryResponse> getNSEIndicesInfo() {
        return nseIndicesServices.getIndicesPerformance();
    }

    @GetMapping("/list")
    public List<NSEIndices> getIndicesList() {
        return nseIndicesServices.getAllIndicesList();
    }
    
}