package com.byschoo.apirest_pro_clientesdto.Logs;

import java.io.IOException;
import java.io.StringWriter;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.boot.logging.structured.StructuredLogFormatter;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import ch.qos.logback.classic.spi.ILoggingEvent;

public class FormatJsonLogger implements StructuredLogFormatter<ILoggingEvent> {

    private final SimpleDateFormat sdf = new SimpleDateFormat("dd MM yyyy HH:mm:ss.SSS");
    private final String applicationName;
    private final String applicationVersion;
    private String hostname;
    private String ipAddress;

    public FormatJsonLogger() {
        // Obtener el nombre y la versión de la aplicación desde las propiedades o MANIFEST.MF
        this.applicationName = "My_Aplicacion"; // Reemplaza con tu lógica
        this.applicationVersion = "My_Version_Aplicacion"; // Reemplaza con tu lógica

        try {
            InetAddress inetAddress = InetAddress.getLocalHost();
            this.hostname = inetAddress.getHostName();
            this.ipAddress = inetAddress.getHostAddress();
        } catch (UnknownHostException e) {
            this.hostname = "unknown";
            this.ipAddress = "unknown";
        }
    }

    @Override
    public String format(ILoggingEvent event) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false); // use the configure method
        StringWriter sw = new StringWriter();
        JsonGenerator jsonGenerator;
        try {
            jsonGenerator = new JsonFactory().createGenerator(sw);
            jsonGenerator.writeStartObject();

            jsonGenerator.writeStringField("Timestand", sdf.format(new Date(event.getTimeStamp())));
            jsonGenerator.writeStringField("Level", event.getLevel().toString());
            jsonGenerator.writeStringField("Thread", event.getThreadName());
            jsonGenerator.writeStringField("Message", event.getFormattedMessage());

            jsonGenerator.writeObjectFieldStart("application");
            jsonGenerator.writeStringField("name", applicationName);
            jsonGenerator.writeStringField("version", applicationVersion);
            jsonGenerator.writeEndObject();

            jsonGenerator.writeObjectFieldStart("node");
            jsonGenerator.writeStringField("hostname", hostname);
            jsonGenerator.writeStringField("ip", ipAddress);
            jsonGenerator.writeEndObject();

            jsonGenerator.writeEndObject();
            jsonGenerator.close();
        } catch (IOException e) {
            return "Error formatting log event: " + e.getMessage();
        }
        return sw.toString() + "\n";
    }
}