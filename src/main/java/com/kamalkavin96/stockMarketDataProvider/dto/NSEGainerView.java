package com.kamalkavin96.stockMarketDataProvider.dto;

public interface NSEGainerView {
    
    Long getId();

    String getSymbol();

    String getNameOfCompany();

    Float getLtp();

    Float getDayChg();

    Float getDayChgPct();

    Long getVolume();

    Float getHighPrice();

    Float getLowPrice();
}
