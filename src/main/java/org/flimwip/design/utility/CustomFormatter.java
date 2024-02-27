package org.flimwip.design.utility;

import java.util.logging.Formatter;
import java.util.logging.LogRecord;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CustomFormatter extends Formatter {

    // Here you can configure your formatter to provide any look you want to your logs
    @Override
    public String format(LogRecord record) {
        return String.format("%1$tF %1$tT [%2$-7s] %3$s %n",
                LocalDateTime.now(),
                record.getLevel().getName(),
                formatMessage(record));
    }
}