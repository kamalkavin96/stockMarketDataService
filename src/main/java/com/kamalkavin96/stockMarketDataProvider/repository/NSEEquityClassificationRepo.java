package com.kamalkavin96.stockMarketDataProvider.repository;

import com.kamalkavin96.stockMarketDataProvider.dto.NSEEquityClassificationView;
import com.kamalkavin96.stockMarketDataProvider.model.NSEEquityClassification;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface NSEEquityClassificationRepo extends JpaRepository<NSEEquityClassification, Integer> {

    @Query(value = """
            SELECT
                ne.symbol AS symbol,
                nms.name AS macroSector,
                ns.name AS sector,
                ni.name AS industry,
                nbi.name AS basicIndustry
            FROM nse_equity_classification_v1 nec
            JOIN nse_macro_sectors_v1 nms
                ON nec.macro_sector_id = nms.id
            JOIN nse_sectors_v1 ns
                ON nec.sector_id = ns.id
            JOIN nse_industries_v1 ni
                ON nec.industry_id = ni.id
            JOIN nse_basic_industries_v1 nbi
                ON nec.basic_industry = nbi.id
            JOIN nse_equity_v1 ne
                ON nec.symbol_id = ne.id
            """, nativeQuery = true)
    List<NSEEquityClassificationView> getCompleteClassification();

}
