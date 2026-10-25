package com.al.ai.platform.core.datasource.common;

import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;

/**
 * 平台通用库 {@code al-ai-platform-common}：
 * 模型 / 对话 / 知识库 / Prompt / 二进制文件等通用能力。
 * 业务域数据请使用后续的 biz 数据源（{@code al-ai-platform-biz}）。
 */
@Configuration
@MapperScan(
        basePackages = {
                "com.al.ai.platform.core.biz.model.mapper",
                "com.al.ai.platform.core.biz.chat.mapper",
                "com.al.ai.platform.core.biz.knowledge.mapper",
                "com.al.ai.platform.core.biz.prompt.mapper"
        },
        sqlSessionTemplateRef = "commonSqlSessionTemplate"
)
public class CommonDataSourceConfig {

    @Bean
    @Primary
    @ConfigurationProperties("spring.datasource.common")
    public DataSourceProperties commonDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @Primary
    @ConfigurationProperties("spring.datasource.common.hikari")
    public DataSource commonDataSource(@Qualifier("commonDataSourceProperties") DataSourceProperties properties) {
        assertResolved(properties.getUsername(), "spring.datasource.common.username / ALAI_DB_USER");
        assertResolved(properties.getPassword(), "spring.datasource.common.password / ALAI_DB_PASSWORD");
        return properties.initializeDataSourceBuilder()
                .type(HikariDataSource.class)
                .build();
    }

    @Bean
    @Primary
    public SqlSessionFactory commonSqlSessionFactory(@Qualifier("commonDataSource") DataSource dataSource) throws Exception {
        MybatisSqlSessionFactoryBean bean = new MybatisSqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        MybatisConfiguration configuration = new MybatisConfiguration();
        configuration.setMapUnderscoreToCamelCase(true);
        bean.setConfiguration(configuration);
        return bean.getObject();
    }

    @Bean
    @Primary
    public SqlSessionTemplate commonSqlSessionTemplate(@Qualifier("commonSqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    @Bean
    @Primary
    public DataSourceTransactionManager commonTransactionManager(@Qualifier("commonDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    private static void assertResolved(String value, String hint) {
        if (!StringUtils.hasText(value) || value.contains("${")) {
            throw new IllegalStateException(
                    "数据源配置未解析: " + hint
                            + "。IDE 不会加载 ~/.zshrc，请确认 application-local.yml 存在且 spring.profiles.active=local，"
                            + "或在运行配置中设置环境变量 ALAI_DB_USER / ALAI_DB_PASSWORD。");
        }
    }
}
