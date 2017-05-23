package com.service.display.controller;

import com.service.display.config.Configuration;
import com.service.display.services.LogReader;
import com.service.display.services.PropertiesReader;
import org.apache.log4j.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class AppInitializer implements ServletContextListener {

    final static Logger logger = Logger.getLogger(AppInitializer.class);

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        logger.info("initial properties file");
        PropertiesReader.init();
        new LogReader(Configuration.PATH).readLog();
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {}
}
