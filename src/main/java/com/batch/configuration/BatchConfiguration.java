package com.batch.configuration;

import javax.sql.DataSource;

import com.batch.itemprocessor.DatasourceItemProcessor;
import com.batch.model.Datasource;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    private final String fileName = "test-data.csv";
    private final String insertSql = "INSERT INTO datasource (datasource, campaign, daily, clicks, impressions) VALUES (:datasource, :campaign, :daily, :clicks, :impressions)";
    private final String[] headers = new String[]{"Datasource", "Campaign", "Daily", "Clicks", "Impressions"};

    @Bean
    public FlatFileItemReader<Datasource> reader() {
        return new FlatFileItemReaderBuilder<Datasource>()
                .linesToSkip(1)
                .name("datasourceItemReader")
                .resource(new ClassPathResource(fileName))
                .delimited()
                .names(headers)
                .fieldSetMapper(new BeanWrapperFieldSetMapper<Datasource>() {{
                    setTargetType(Datasource.class);
                }})
                .build();
    }

    @Bean
    public DatasourceItemProcessor processor() {
        return new DatasourceItemProcessor();
    }

    @Bean
    public JdbcBatchItemWriter<Datasource> writer(DataSource dataSource) {
        return new JdbcBatchItemWriterBuilder<Datasource>()
                .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
                .sql(insertSql)
                .dataSource(dataSource)
                .build();
    }

    @Bean
    public Job importUserJob(Step step1) {
        return jobBuilderFactory.get("importDatasourceJob")
                .incrementer(new RunIdIncrementer())
                .flow(step1)
                .end()
                .build();
    }

    @Bean
    public Step step1(JdbcBatchItemWriter<Datasource> writer) {
        return stepBuilderFactory.get("step1")
                .<Datasource, Datasource>chunk(10)
                .reader(reader())
                .processor(processor())
                .writer(writer)
                .build();
    }
}
