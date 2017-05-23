package com.service.display.config;

import java.time.format.DateTimeFormatter;


public class Configuration {

    public static int INTERVAL;
    public static String PATH;
    public static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss,SSS");

}
