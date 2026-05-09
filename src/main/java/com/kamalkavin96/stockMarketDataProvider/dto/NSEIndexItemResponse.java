package com.kamalkavin96.stockMarketDataProvider.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NSEIndexItemResponse {

    private String index_symbol;
    private String index_name;
    private Float last;
    private Double change;
    private Double pChange;

}