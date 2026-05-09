package com.kamalkavin96.stockMarketDataProvider.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(
        name = "nse_equity_classification_v1",
        indexes = {
                @Index(
                        name = "ix_nse_equity_classification_v1_symbol_id",
                        columnList = "symbol_id"
                )
        }
)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NSEEquityClassification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Column(name = "symbol_id", nullable = false)
    Long symbolId;

    @Column(name = "macro_sector_id", nullable = false)
    Integer macroSectorId;

    @Column(name = "sector_id", nullable = false)
    Integer sectorId;

    @Column(name = "industry_id", nullable = false)
    Integer industryId;

    @Column(name = "basic_industry", nullable = false)
    Integer basicIndustry;

    @Column(name = "updated_at", nullable = false)
    Integer updatedAt;

}
