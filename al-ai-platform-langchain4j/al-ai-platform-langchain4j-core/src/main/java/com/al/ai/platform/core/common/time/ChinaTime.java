package com.al.ai.platform.core.common.time;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.TimeZone;

public final class ChinaTime {
    public static final ZoneId ZONE = ZoneId.of("Asia/Shanghai");
    public static final String JDBC_SESSION_TIME_ZONE = "+08:00";

    private ChinaTime() {}

    public static void installAsDefault() {
        TimeZone.setDefault(TimeZone.getTimeZone(ZONE));
    }

    public static LocalDateTime now() {
        return LocalDateTime.now(ZONE);
    }
}
