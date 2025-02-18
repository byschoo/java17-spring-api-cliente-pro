package com.byschoo.apirest_pro_clientesdto.Payload;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Builder;
import lombok.Data;


@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL) // Indica al serializador JSON que ignore los campos con valores null
public class MensajeResponseFailure {
    
    private String mensaje;
    private Object object;
    private String code;
    private String severity;
    private String url;
    private final Date tiempo = new Date();

}
