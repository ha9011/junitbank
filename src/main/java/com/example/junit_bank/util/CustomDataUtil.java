package com.example.junit_bank.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CustomDataUtil {
    public static String toStringFormat(LocalDateTime localDateTime) {
        return localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}
