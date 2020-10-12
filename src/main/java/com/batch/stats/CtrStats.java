package com.batch.stats;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CtrStats {
    String datasource;
    String campaign;
    Double ctr;
}
