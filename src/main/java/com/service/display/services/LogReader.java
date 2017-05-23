package com.service.display.services;


import com.service.display.config.Configuration;
import com.service.display.model.Result;
import com.service.display.model.Severity;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

/**
 * this read logs  file and save in Memory cache
 */


public class LogReader {

    final static Logger logger = Logger.getLogger(LogReader.class);
    private String path;
    private String pattern = "(\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2},\\d{3})" // date
            + "[ ]+(WARNING|INFO|ERROR|info|warning|error)" // level
            + "\\s*([^\\n\\r]*)"; // last text with space

    public LogReader(String path) {
        this.path = path;
    }

    //synchronized because multiple session try reading
    //memory cache reading for everyone
    //save last line in singleton and every time when start reading start from remember line
    public synchronized void readLog() {
        MemoryCache instance = MemoryCache.getInstance();
        try (Stream<String> stream = Files.lines(Paths.get(path)).skip(instance.getSkipLine())) {
            stream.forEach(s -> {
                Result result = readEachLine(s);
                instance.putResult(result);
                instance.setSkipLine(instance.getSkipLine() + 1);
            });
        } catch (IOException e) {
            logger.error("error during reading file " + e.getMessage());
        }
    }

    public Result readEachLine(String line) {
        Pattern regex = Pattern.compile(pattern);
        Matcher matcher = regex.matcher(line);
        matcher.matches();
        Result result = new Result();
        LocalDateTime date = LocalDateTime.parse(matcher.group(1), Configuration.formatter);
        result.setLocalDateTime(date);
        result.setSeverity(Severity.valueOf(matcher.group(2).toUpperCase()));
        result.setMessage(matcher.group(3));
        return result;
    }


}
