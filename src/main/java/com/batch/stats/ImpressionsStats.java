package com.batch.stats;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ImpressionsStats {
    private String datasource;
    private String campaign;
    private Long impressions;
}
