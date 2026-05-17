package com.kamalkavin96.stockMarketDataProvider.repository;

import com.kamalkavin96.stockMarketDataProvider.dto.NSEGainerView;
import com.kamalkavin96.stockMarketDataProvider.model.NSEEquity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NSEEquityRepo extends JpaRepository<NSEEquity, Long> {
    Optional<NSEEquity> findBySymbol(String symbol);

    @Query(value = "SELECT * FROM nse_equity_v1 e " +
            "WHERE LOWER(e.symbol) LIKE LOWER(CONCAT(:prefix, '%')) " +
            "   OR LOWER(e.name_of_company) LIKE LOWER(CONCAT('%', :prefix, '%'))", nativeQuery = true)
    List<NSEEquity> searchBySymbolPrefix(@Param("prefix") String prefix);


    @Query(value = "SELECT e.symbol FROM nse_equity_v1 e " +
            "WHERE LOWER(e.symbol) LIKE LOWER(CONCAT(:prefix, '%')) " +
            "   OR LOWER(e.name_of_company) LIKE LOWER(CONCAT('%', :prefix, '%'))", nativeQuery = true)
    List<String> searchByEquityOnlySymbolPrefix(@Param("prefix") String prefix);

    @Query(value = "SELECT e.* " +
            "FROM nse_equity_v1 AS e " +
            "INNER JOIN nse_equity_classification_v1 AS nec " +
            "ON nec.equity_id = e.id " +
            "INNER JOIN nse_macro_sectors_v1 AS nms " +
            "ON nms.id = nec.macro_sector_id " +
            "WHERE nec.macro_sector_id = :macroSectorId", nativeQuery = true)
    List<NSEEquity> findEquityForMacroSector(Integer macroSectorId);

    @Query(value = """
            SELECT
                e.id, e.symbol, e.name_of_company, eq.ltp, eq.day_chg, eq.day_chg_pct, eq.volume, eq.high_price, eq.low_price
            FROM nse_equity_v1 e
            JOIN nse_equity_indices_list_v1 il
                ON il.equity_id = e.id
            JOIN nse_equity_quote_v1 eq
                ON eq.symbol_id = e.id
            WHERE il.main_index = :indexSymbol
            AND eq.day_chg_pct > 0
            ORDER BY eq.day_chg_pct DESC
            """, nativeQuery = true)
    List<NSEGainerView> findGainerByIndex(
            @Param("indexSymbol") String indexSymbol);

      @Query(value = """
            SELECT
                e.id, e.symbol, e.name_of_company, eq.ltp, eq.day_chg, eq.day_chg_pct, eq.volume, eq.high_price, eq.low_price
            FROM nse_equity_v1 e
            JOIN nse_equity_indices_list_v1 il
                ON il.equity_id = e.id
            JOIN nse_equity_quote_v1 eq
                ON eq.symbol_id = e.id
            WHERE il.main_index = :indexSymbol
            AND eq.day_chg_pct < 0
            ORDER BY eq.day_chg_pct ASC
            """, nativeQuery = true)
    List<NSEGainerView> findLoserByIndex(
            @Param("indexSymbol") String indexSymbol);

}
