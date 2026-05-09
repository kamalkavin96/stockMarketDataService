package com.kamalkavin96.stockMarketDataProvider.dto;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NSEIndicesCategoryResponse {

    private String category;
    private List<NSEIndexItemResponse> indices;

}