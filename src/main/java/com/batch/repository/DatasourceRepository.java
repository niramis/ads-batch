package com.batch.repository;

import com.batch.model.Datasource;
import com.batch.stats.*;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Date;

public interface DatasourceRepository extends CrudRepository<Datasource, Long> {

    @Query("SELECT " +
            "    new com.batch.stats.ClicksStats(d.datasource, SUM(d.clicks)) " +
            "FROM " +
            "    Datasource d " +
            "WHERE d.datasource=:datasource AND d.daily BETWEEN :startDate AND :endDate " +
            "GROUP BY " +
            "    d.datasource")
    ClicksStats totalClicksForDatasource(@Param("datasource") String datasource, @Param("startDate") Date startDate, @Param("endDate") Date endDate);


    @Query("SELECT " +
            "new com.batch.stats.CtrStats(d.datasource, d.campaign, (sum(d.clicks)*1.0/sum(d.impressions))) " +
            "FROM " +
            " Datasource d " +
            "WHERE d.datasource=:datasource AND d.campaign=:campaign " +
            "GROUP BY " +
            "d.datasource")
    CtrStats getCtrStatistics(@Param("datasource") String datasource, @Param("campaign") String campaign);


    @Query("SELECT "+
            "new com.batch.stats.ImpressionsStats(d.datasource, d.campaign, d.impressions) " +
            "FROM  Datasource d " + "" +
            "WHERE d.datasource=:datasource AND d.campaign=:campaign AND d.daily BETWEEN :startDate AND :endDate")
    ImpressionsStats getImpressionsForCampaign(@Param("datasource") String datasource, @Param("campaign") String campaign, @Param("startDate") Date startDate, @Param("endDate") Date endDate);

}
