package com.al.ai.platform.admin;

import com.al.ai.platform.core.common.time.ChinaTime;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(
        scanBasePackages = "com.al.ai.platform",
        exclude = {DataSourceAutoConfiguration.class}
)
public class AdminApplication {
    public static void main(String[] args) {
        ChinaTime.installAsDefault();
        SpringApplication.run(AdminApplication.class, args);
    }
}
