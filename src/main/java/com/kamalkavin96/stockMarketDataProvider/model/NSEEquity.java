package com.kamalkavin96.stockMarketDataProvider.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name="nse_equity_v1")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NSEEquity {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(name="symbol")
    private String symbol;

    @Column(name="name_of_company")
    private String nameOfCompany;

    @Column(name="series")
    private String series;

    @Column(name="date_of_listing")
    private LocalDate dateOfListing;

    @Column(name="paid_up_value")
    private Integer paidUpValue;

    @Column(name="market_lot")
    private Integer marketLot;

    @Column(name="isin_number")
    private String isinNumber;

    @Column(name="face_value")
    private Integer faceValue;

    @Column(name="nse_chart_script_id")
    private Integer nseChartScriptId;

    @Column(name="nse_chart_script_symbol")
    private String nseChartScriptSymbol;
}