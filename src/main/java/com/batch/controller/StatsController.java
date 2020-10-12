package com.batch.controller;

import com.batch.repository.DatasourceRepository;
import com.batch.stats.*;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
public class StatsController {

    @Autowired
    private DatasourceRepository datasourceRepository;

    @ApiOperation(value = "Total Clicks for a given Datasource for a given Date range")
    @GetMapping(path = "/clicks")
    public ClicksStats clicksPerDataSource(@RequestParam("datasource") String datasource, @RequestParam("startDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate, @RequestParam("endDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) {
        return datasourceRepository.totalClicksForDatasource(datasource, startDate, endDate);
    }

    @ApiOperation(value = "Click-Through Rate (CTR) per Datasource and Campaign")
    @GetMapping(path = "/ctr")
    public CtrStats getCtrStatistics(@RequestParam("datasource") String datasource, @RequestParam("campaign") String campaign) {
        return datasourceRepository.getCtrStatistics(datasource, campaign);
    }

    @ApiOperation(value = "Impressions over time for campaign from datasource")
    @GetMapping(path = "/impressions")
    public ImpressionsStats getImpressionsForCampaign(@RequestParam("datasource") String datasource, @RequestParam("campaign") String campaign, @RequestParam("startDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate, @RequestParam("endDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) {
        return datasourceRepository.getImpressionsForCampaign(datasource, campaign, startDate, endDate);
    }
}
