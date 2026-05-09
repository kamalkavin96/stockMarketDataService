package com.kamalkavin96.stockMarketDataProvider.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "nse_sectors_v1")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NSESector {

    @Id
    private Integer id;

    @Column(name = "name", nullable = false, unique = true, length = 225)
    private String name;

    @Column(name = "description", length = 255)
    private String description;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "logo_name", length = 255)
    private String logoName;

}
