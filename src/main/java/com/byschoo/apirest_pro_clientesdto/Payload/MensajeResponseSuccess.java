package com.byschoo.apirest_pro_clientesdto.Payload;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL) // Indica al serializador JSON que ignore los campos con valores null
public class MensajeResponseSuccess implements Serializable{ // Sin el atributo url y tiempo para Respuestas satisfactorias
    
    private String mensaje;
    private Object object;
}
