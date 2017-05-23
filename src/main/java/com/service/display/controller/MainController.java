package com.service.display.controller;

import com.service.display.config.Configuration;
import com.service.display.repository.LogOperations;
import com.service.display.services.LogReader;
import org.json.simple.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;


@Controller
@RequestMapping(value = "/")
public class MainController{

    @Autowired
    LogOperations logOperations;

    @Autowired
    HttpSession session;

    //return static home page when deploy on Tomcat
    @RequestMapping(value = {"/", "/home"})
    public String render() {
        return "views/main-page.html";
    }

    @RequestMapping(value = "/getResult",  method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    @ResponseBody
    public JSONArray getResult(){
        new LogReader(Configuration.PATH).readLog(); // read logs every time if something new add in log file
        int interval = session.getAttribute("interval") != null ?  // if session is empty get Configuration interval
                       Integer.parseInt(session.getAttribute("interval").toString()) : Configuration.INTERVAL;
        JSONArray jsonArray = new JSONArray();
        jsonArray.add(logOperations.getAllResult(interval));
        jsonArray.add(logOperations.getCurrentTime());
        jsonArray.add(interval);
        return jsonArray;
    }


    @RequestMapping(value = "/updateInterval/{second}",  method = RequestMethod.POST)
    @ResponseBody
    public boolean getResultWithInterval(@PathVariable String second){
        boolean isUpdated = logOperations.updateInterval(second);
        if(isUpdated)
            session.setAttribute("interval",second);
        return isUpdated;
    }

}
