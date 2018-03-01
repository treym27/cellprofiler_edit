package mylogging;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;

class MyFormatter extends Formatter {
    public String format(LogRecord rec) {
        StringBuffer buf = new StringBuffer(1000);

        buf.append("\n");

        buf.append(calcDate(rec.getMillis()));

        buf.append(rec.getMessage());

        return buf.toString();
    }

    private String calcDate(long millisecs) {
        SimpleDateFormat date_format = new SimpleDateFormat("MMM dd,yyyy HH:mm");
        Date resultdate = new Date(millisecs);
        return date_format.format(resultdate);
    }

}