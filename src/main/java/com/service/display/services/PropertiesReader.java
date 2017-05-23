package com.service.display.services;

import com.service.display.config.Configuration;
import org.apache.log4j.Logger;


import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class PropertiesReader {

    final static Logger logger = Logger.getLogger(LogReader.class);

    private static PropertiesReader instance;
    private static Properties prop;

    public static PropertiesReader init(){
        if(instance == null){
            instance = new PropertiesReader();
            instance.initialProp();
        }
        return instance;
    }

    private  void initialProp() {
        if(prop == null) {
            prop = new Properties();
        }
        InputStream input = null;
        try {
            String filename = "config.properties";
            input = LogReader.class.getClassLoader().getResourceAsStream(filename);
            if (input == null) {
                logger.info("Sorry, unable to find " + filename);
                return;
            }
            prop.load(input);
            Configuration.PATH = prop.getProperty("log.file.path");
            Configuration.INTERVAL = Integer.parseInt(prop.getProperty("log.default.interval"));
        } catch (IOException ex) {
            logger.error("can't read properties file " + ex.getMessage());
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    logger.error("can't close input stream " + e.getMessage());
                }
            }
        }
    }
}
