package com.gopher.system;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class App {
    private static final Logger logger = LoggerFactory.getLogger(App.class);
    public static void main( String[] args ){
        System.out.println("----> logback start");
        logger.trace("--> Hello trace.");
        logger.debug("--> Hello debug.");
        logger.info("--> Hello info.");
        logger.warn("--> Goodbye warn.");
        logger.error("--> Goodbye error.");
        System.out.println("----> logback end");
    }
}