package com.kamalkavin96.stockMarketDataProvider.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(
    name = "nse_indices_v1",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = "symbol")
    }
)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NSEIndices {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "symbol", nullable = false, unique = true, length = 100)
    private String symbol;

    @Column(name = "name", length = 255)
    private String name;

    @Column(name = "category", length = 255)
    private String category;
}