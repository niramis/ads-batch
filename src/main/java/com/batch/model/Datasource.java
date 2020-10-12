package com.batch.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Datasource {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String datasource;
    private String campaign;
    private Date daily;
    private Long clicks;
    private Long impressions;

    public Datasource(String datasource, String campaign, Date daily, Long clicks, Long impressions) {
        this.datasource = datasource;
        this.campaign = campaign;
        this.daily = daily;
        this.clicks = clicks;
        this.impressions = impressions;
    }
}
