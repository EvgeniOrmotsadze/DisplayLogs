package com.service.display.repository;

import com.service.display.config.Configuration;
import com.service.display.model.Severity;
import com.service.display.services.MemoryCache;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;



@Repository
public class LogOperationsImpl implements LogOperations {

    final static Logger logger = Logger.getLogger(LogOperationsImpl.class);

    @Override
    public JSONObject getAllResult(int interval) {
        JSONObject jsonObject = new JSONObject();
        int[] severityNumbs = getListNumberOfSeverity(interval);
        jsonObject.put(Severity.INFO, severityNumbs[0]);
        jsonObject.put(Severity.WARNING, severityNumbs[1]);
        jsonObject.put(Severity.ERROR, severityNumbs[2]);
        return jsonObject;
    }

    @Override
    public synchronized boolean updateInterval(String second) {
        try {
            int sec = Integer.parseInt(second);
            if(sec < 0)
                throw new IllegalArgumentException("negative number");
        }catch (NumberFormatException ex){
            logger.error("Number format exception " + ex.getMessage());
            return false;
        }catch (IllegalArgumentException ex){
            logger.error("Illegal argument exception " + ex.getMessage());
            return false;
        }
        return true;
    }

    private int[] getListNumberOfSeverity(int interval) {
        int[] severityNumbs = new int[3];
        LocalDateTime now = LocalDateTime.now();
        MemoryCache.getInstance().getAllResults().forEach(result -> {
            LocalDateTime resultTime = result.getLocalDateTime();
            if(now.minusSeconds(interval).until(resultTime, ChronoUnit.SECONDS) > 0) {
                switch (result.getSeverity()) {
                    case INFO:
                        severityNumbs[0]++;
                        break;
                    case WARNING:
                        severityNumbs[1]++;
                        break;
                    case ERROR:
                        severityNumbs[2]++;
                        break;
                }
            }
        });
        return severityNumbs;
    }


    @Override
    public String getCurrentTime() {
        LocalDateTime now = LocalDateTime.now();
        return Configuration.formatter.format(now);
    }


}
