package com.kamalkavin96.stockMarketDataProvider.service;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

@Component
public class GrowwClient {

    private static final String BASE_URL = "https://groww.in/v1/api";

    private final RestTemplate restTemplate = new RestTemplate();

    /**
     * GET QUOTES
     */
    public Object getQuotes(String symbol) {

        symbol = symbol.toUpperCase();

        String url = BASE_URL +
                "/stocks_data/v1/accord_points/exchange/NSE/segment/CASH/latest_prices_ohlc/" +
                symbol;

        ResponseEntity<Object> response = restTemplate.getForEntity(
                url,
                Object.class);

        return response.getBody();
    }

    /**
     * HISTORY PRICE
     *
     * Example:
     * startingPeriod -> 30day
     * interval -> 1day
     */
    public List<Candle> historyPrice(
            String symbol,
            String startingPeriod,
            String interval) {

        symbol = symbol.toUpperCase();

        // PERIOD CONVERSION
        Map<String, Integer> units = new HashMap<>();

        units.put("day", 1);
        units.put("week", 7);
        units.put("month", 30);
        units.put("year", 365);

        ParsedPeriod parsedPeriod = parsePeriod(startingPeriod, units);

        int daysToSubtract = parsedPeriod.value *
                units.get(parsedPeriod.unit);

        // DATE CALCULATION
        LocalDateTime presentDate = LocalDateTime.now();

        LocalDateTime startDate = presentDate.minusDays(daysToSubtract);

        long startTimeInMillis = startDate
                .atZone(ZoneId.systemDefault())
                .toInstant()
                .toEpochMilli();

        long endTimeInMillis = presentDate
                .atZone(ZoneId.systemDefault())
                .toInstant()
                .toEpochMilli();

        // INTERVAL
        int intervalMinutes = parseInterval(interval);

        // URL
        String historyUrl = BASE_URL +
                "/charting_service/v2/chart/exchange/NSE/segment/CASH/" +
                symbol;

        String url = UriComponentsBuilder
                .fromHttpUrl(historyUrl)
                .queryParam(
                        "startTimeInMillis",
                        startTimeInMillis)
                .queryParam(
                        "endTimeInMillis",
                        endTimeInMillis)
                .queryParam(
                        "intervalInMinutes",
                        intervalMinutes)
                .toUriString();

        // API CALL
        ResponseEntity<Map> response = restTemplate.getForEntity(
                url,
                Map.class);

        Map<String, Object> body = response.getBody();

        List<List<Object>> candles = (List<List<Object>>) body.get("candles");

        List<Candle> candleList = new ArrayList<>();

        for (List<Object> candle : candles) {

            candleList.add(
                    Candle.fromRaw(candle));
        }

        return candleList;
    }

    /**
     * PARSE PERIOD
     */
    private ParsedPeriod parsePeriod(
            String period,
            Map<String, Integer> units) {

        for (String unit : units.keySet()) {

            if (period.contains(unit)) {

                int value = Integer.parseInt(
                        period.replace(unit, ""));

                return new ParsedPeriod(
                        value,
                        unit);
            }
        }

        throw new RuntimeException(
                "Invalid period format: " + period);
    }

    /**
     * PARSE INTERVAL
     */
    private int parseInterval(
            String interval) {

        Map<String, Integer> intervalUnits = new HashMap<>();

        intervalUnits.put("min", 1);
        intervalUnits.put("hour", 60);
        intervalUnits.put("day", 1440);
        intervalUnits.put("week", 10080);
        intervalUnits.put("month", 43200);
        intervalUnits.put("year", 525600);

        for (String unit : intervalUnits.keySet()) {

            if (interval.contains(unit)) {

                int value = Integer.parseInt(
                        interval.replace(unit, ""));

                return value *
                        intervalUnits.get(unit);
            }
        }

        throw new RuntimeException(
                "Invalid interval format: " + interval);
    }

    /**
     * PARSED PERIOD
     */
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    private static class ParsedPeriod {

        private int value;

        private String unit;
    }

    /**
     * CANDLE DTO
     */
    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Candle {

        private LocalDateTime timestamp;

        private Double open;

        private Double high;

        private Double low;

        private Double close;

        private Long volume;

        public static Candle fromRaw(
                List<Object> raw) {

            long unixTimestamp = ((Number) raw.get(0))
                    .longValue();

            LocalDateTime timestamp = Instant.ofEpochSecond(
                    unixTimestamp)
                    .atZone(
                            ZoneId.of("Asia/Kolkata"))
                    .toLocalDateTime();

            return Candle.builder()
                    .timestamp(timestamp)
                    .open(toDouble(raw.get(1)))
                    .high(toDouble(raw.get(2)))
                    .low(toDouble(raw.get(3)))
                    .close(toDouble(raw.get(4)))
                    .volume(toLong(raw.get(5)))
                    .build();
        }

        private static Double toDouble(
                Object value) {

            if (value == null) {
                return null;
            }

            return ((Number) value)
                    .doubleValue();
        }

        private static Long toLong(
                Object value) {

            if (value == null) {
                return null;
            }

            return ((Number) value)
                    .longValue();
        }
    }
}