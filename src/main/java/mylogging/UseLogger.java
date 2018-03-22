package mylogging;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import mylogging.MyLogger;

public class UseLogger {
    private final static Logger LOGGER = Logger.getLogger(MyLogger.class.getName());

    private static int tabCount = 0;
    public void log(String message) {

        LOGGER.setLevel(Level.ALL);
        String tabs = "";
        for(int i = 0; i < tabCount; i++){
            tabs += "\t";
        }
        LOGGER.info(tabs + message);
    }

    public void enterSection(){
        UseLogger.tabCount++;
    }

    public void exitSection(){
        UseLogger.tabCount--;
    }

}