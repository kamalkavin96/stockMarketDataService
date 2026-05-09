package com.kamalkavin96.stockMarketDataProvider.dto;

public interface NSEIndicesPerformanceView {

    String getSymbol();
    String getIndexName();
    String getCategory();
    Float getLtp();
    Double getPriceChange();
    Double getPChange();

}