package mylogging;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import mylogging.MyLogger;

public class UseLogger {
    private final static Logger LOGGER = Logger.getLogger(MyLogger.class.getName());


    public void log(String message) {

        LOGGER.setLevel(Level.ALL);
        LOGGER.info(message);
    }

}