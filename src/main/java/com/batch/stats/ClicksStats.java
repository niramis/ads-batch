package com.batch.stats;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ClicksStats {
    private String datasource;
    private Long clicks;
}
