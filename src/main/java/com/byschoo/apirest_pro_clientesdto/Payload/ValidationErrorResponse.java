package com.byschoo.apirest_pro_clientesdto.Payload;

import java.util.Date;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ValidationErrorResponse {

    private String mensaje; // Mensaje general de error (opcional)
    private Map<String, String> error; // Mapa con los errores de validación específicos
    private Object object;
    private String code;
    private String severity;
    private String url;
    private final Date tiempo = new Date();


}
