package com.service.display.services;

import com.service.display.model.Result;

import java.util.ArrayList;
import java.util.List;

/**
 * because read once use memory cache, singleton design pattern
 */


public class MemoryCache {

 //   final static Logger logger = Logger.getLogger(MemoryCache.class);
    private static final MemoryCache instance = new MemoryCache();
    private static final List<Result> results = new ArrayList<>();
    private static long skipLine = 0;

    private MemoryCache () {}

    public static MemoryCache getInstance() {
        return instance;
    }

    public Result getResult(Integer index) {
        return results.get(index);
    }

    public void putResult(Result result) {
        results.add(result);
    }

    public List<Result> getAllResults(){
        return results;
    }

    public long getSkipLine() {
        return skipLine;
    }

    public void setSkipLine(long skipLine) {
        MemoryCache.skipLine = skipLine;
    }
}
