package com.service.display.repository;

import org.json.simple.JSONObject;



public interface LogOperations {

    JSONObject getAllResult(int interval);

    boolean updateInterval(String second);

    String getCurrentTime();


}
