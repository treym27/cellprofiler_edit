package mylogging;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;


public class MyLogger {
    static private FileHandler fileTxt;
    static private Formatter formatterTxt;

    static public void setup() throws IOException {

        Logger logger = Logger.getLogger(MyLogger.class.getName());

        logger.setLevel(Level.INFO);
        fileTxt = new FileHandler("Logging.txt");

        formatterTxt = new MyFormatter();
        fileTxt.setFormatter(formatterTxt);
        logger.addHandler(fileTxt);
    }
}