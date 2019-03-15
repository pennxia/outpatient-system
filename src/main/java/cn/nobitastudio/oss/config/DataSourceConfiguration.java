package cn.nobitastudio.oss.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/03/15 18:08
 * @description
 */
@Configuration
public class DataSourceConfiguration {

    @Primary
    @Bean("dataSourceProperties")
    @ConfigurationProperties("spring.datasource.oss")
    public DataSourceProperties primaryDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Primary
    @Bean(name = "dataSource")
    public DataSource dbDataSource(@Autowired @Qualifier("dataSourceProperties") DataSourceProperties props) {
        return props.initializeDataSourceBuilder().build();
    }

    @Primary
    @Bean(name = "jdbcTemplate")
    public JdbcTemplate dbJdbcTemplate(
            @Qualifier("dataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}
