package com.byschoo.apirest_pro_clientesdto.Logs;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import org.springframework.boot.logging.structured.StructuredLogFormatter;

import ch.qos.logback.classic.spi.ILoggingEvent;

public class FormatKeyValueLogger implements StructuredLogFormatter<ILoggingEvent>{

    private final SimpleDateFormat sdf;

    public FormatKeyValueLogger() {
        sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        // Establece la zona horaria a la del sistema
        sdf.setTimeZone(TimeZone.getDefault());
    }

    @Override
    public String format(ILoggingEvent event) {
        long timestamp = event.getTimeStamp();
        Date date = new Date(timestamp);
        String formattedTime = sdf.format(date);

        return formattedTime + " | " + event.getLevel() + " | " + event.getMessage() + "\n";
    }
}
