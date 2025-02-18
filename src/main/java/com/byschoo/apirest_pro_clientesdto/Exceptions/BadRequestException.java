package com.byschoo.apirest_pro_clientesdto.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true) // Este parámetro indica que la generación de los métodos equals() y hashCode() debe incluir los campos de la superclase (si la hay).
@ResponseStatus(value=HttpStatus.BAD_REQUEST) // Manejar excepciones consultadas en URLs que no se encuentran, Null o vacía
public class BadRequestException extends RuntimeException{

    public BadRequestException(String mensaje) {
        super(mensaje);

    }

}
