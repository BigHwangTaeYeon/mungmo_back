package mungmo.adminService.admin.com.config.dateSerialize;

import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CustomLocalDateTimeDeserializer extends JsonDeserializer<LocalDateTime> {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public LocalDateTime deserialize(com.fasterxml.jackson.core.JsonParser p, DeserializationContext deserializationContext) throws IOException, com.fasterxml.jackson.core.JacksonException {
        return LocalDateTime.parse(p.getText(), formatter);
    }
}
