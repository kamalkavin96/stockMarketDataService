package com.kamalkavin96.stockMarketDataProvider.controller;

import com.kamalkavin96.stockMarketDataProvider.dto.NSEGainerView;
import com.kamalkavin96.stockMarketDataProvider.model.NSEEquity;
import com.kamalkavin96.stockMarketDataProvider.service.NSEEquityService;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("api/v1/nse")
public class NSEEquityController {

    @Autowired
    NSEEquityService nseEquityService;

    private final Logger logger = LoggerFactory.getLogger(NSEEquityController.class);

    @GetMapping("/equity-list")
    public ResponseEntity<List<NSEEquity>> getNSEEquityListMapping(HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.OK).body(nseEquityService.getNseEquityList());

    }

    @GetMapping("/get-equity/{symbol}")
    public ResponseEntity<NSEEquity> getNSEEquityBySymbolMapping(@PathVariable String symbol) {
        return ResponseEntity.status(HttpStatus.OK).body(nseEquityService.getNseEquityMeta(symbol));
    }

    @GetMapping("/search-equity/{symbol}")
    public ResponseEntity<List<NSEEquity>> getSearchNSEEquityBySymbolMapping(@PathVariable String symbol) {
        return ResponseEntity.status(HttpStatus.OK).body(nseEquityService.searchEquity(symbol));
    }


    @GetMapping("/search-symbol/{symbol}")
    public ResponseEntity<List<String>> getSearchNSESymbol(@PathVariable String symbol) {
        return ResponseEntity.status(HttpStatus.OK).body(nseEquityService.searchSymbol(symbol));
    }

    // @GetMapping("/get-equity-macro-sector/{macroSectorId}")
    // public ResponseEntity<List<NSEEquity>>
    // getNSEEquityForMacroSector(@PathVariable Integer macroSectorId){
    // return
    // ResponseEntity.status(HttpStatus.OK).body(nseEquityService.findAllEquityUNderMacroSector(macroSectorId));
    // }

    @GetMapping("/equity/{index_symbol}/gainers")
    public ResponseEntity<List<NSEGainerView>> getNseGainers(@PathVariable("index_symbol") String indexSymbol) {
        return ResponseEntity.ok(nseEquityService.getEquityGainersByIndex(indexSymbol));
    }

    @GetMapping("/equity/{index_symbol}/losers")
    public ResponseEntity<List<NSEGainerView>> getNseLosers(@PathVariable("index_symbol") String indexSymbol) {
        return ResponseEntity.ok(nseEquityService.getEquityLosersByIndex(indexSymbol));
    }

}
