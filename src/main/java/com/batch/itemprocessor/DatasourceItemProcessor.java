package com.batch.itemprocessor;

import com.batch.model.Datasource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

public class DatasourceItemProcessor implements ItemProcessor<Datasource, Datasource> {
    private static final Logger log = LoggerFactory.getLogger(DatasourceItemProcessor.class);

    @Override
    public Datasource process(final Datasource datasource) {
        final String name = datasource.getDatasource().toUpperCase();
        final String campaign = datasource.getCampaign().toUpperCase();

        final Datasource transformedDatasource = new Datasource(name, campaign, datasource.getDaily(), datasource.getClicks(), datasource.getImpressions());
        log.info("Converting (" + datasource + ") into (" + transformedDatasource + ")");

        return transformedDatasource;
    }
}
