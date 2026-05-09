package com.kamalkavin96.stockMarketDataProvider.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.kamalkavin96.stockMarketDataProvider.dto.NSEIndicesPerformanceView;
import com.kamalkavin96.stockMarketDataProvider.model.NSEIndices;

@Repository
public interface NSEIndicesRepo extends JpaRepository<NSEIndices, Long> {

    @Query(value = """
            SELECT
                ni.symbol AS symbol,
                ni.name AS indexName,
                ni.category AS category,
                ROUND(niq.last, 2) AS ltp,
                ROUND(niq.variation, 2) AS priceChange,
                ROUND(niq.percent_change, 2) AS pChange
            FROM nse_indices_v1 ni
            JOIN nse_index_quote_v1 niq
                ON niq.index_id = ni.id
            """, nativeQuery = true)
    List<NSEIndicesPerformanceView> getIndicesPerformance();
}