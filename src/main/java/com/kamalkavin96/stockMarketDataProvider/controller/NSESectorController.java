package com.kamalkavin96.stockMarketDataProvider.controller;

import com.kamalkavin96.stockMarketDataProvider.dto.NSEEquitySectorMetaResponse;
import com.kamalkavin96.stockMarketDataProvider.model.NSEIndustries;
import com.kamalkavin96.stockMarketDataProvider.model.NSESector;
import com.kamalkavin96.stockMarketDataProvider.service.NSESectorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/sector")
public class NSESectorController {

    @Autowired
    NSESectorService nseSectorService;

    @GetMapping("/get-all")
    public ResponseEntity<List<NSEEquitySectorMetaResponse>> getAllSectorMapping(){
        return ResponseEntity.status(HttpStatus.OK).body(nseSectorService.getAllSectors());
    }

    @GetMapping("/get-industries/{sectorId}")
    public ResponseEntity<List<NSEIndustries>> getIndustriesForSectorMapping(@PathVariable Integer sectorId){
        return ResponseEntity.status(HttpStatus.OK).body( nseSectorService.getAllIndustriesForSector(sectorId));
    }
}
