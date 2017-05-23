package com.service.display;

import com.service.display.config.Configuration;
import com.service.display.model.Severity;
import com.service.display.repository.LogOperations;
import com.service.display.repository.LogOperationsImpl;
import com.service.display.services.LogReader;
import com.service.display.services.MemoryCache;
import com.service.display.services.PropertiesReader;
import org.json.simple.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.nio.file.Path;

import static org.junit.Assert.assertEquals;


public class LogOperationTest {

    LogOperations logOperations;
    LogReader logReader;

    @Mock
    Path path;

    @Before
    public void initial(){
        MockitoAnnotations.initMocks(this);
        logReader = new LogReader(path.toString());
        logOperations = new LogOperationsImpl();
        PropertiesReader.init();
    }



    @Test
    public void setIncorrectInterval(){
        assertEquals(logOperations.updateInterval("-20"), false);
    }


    @Test
    public void setEmptyInterval(){
        assertEquals(logOperations.updateInterval(""), false);
    }

    @Test
    public void propertiesReaderCheck(){
        assertEquals(Configuration.INTERVAL,25); // default interval in config.properties
    }


    @Test
    public void readerCheck(){
        String currentTime = logOperations.getCurrentTime();
        String args[] = {currentTime + " INFO  some text 1",
                currentTime + " INFO  some text 2",
                currentTime + " WARNING  some text 3"};
        MemoryCache.getInstance().putResult(logReader.readEachLine(args[0]));
        MemoryCache.getInstance().putResult(logReader.readEachLine(args[1]));
        MemoryCache.getInstance().putResult(logReader.readEachLine(args[2])); // simulate reading text
        // check if calculate correctly
        JSONObject allResult = logOperations.getAllResult(Configuration.INTERVAL);
        assertEquals(2, allResult.get(Severity.INFO));
        assertEquals(1, allResult.get(Severity.WARNING));
        assertEquals(0, allResult.get(Severity.ERROR));
    }







}
