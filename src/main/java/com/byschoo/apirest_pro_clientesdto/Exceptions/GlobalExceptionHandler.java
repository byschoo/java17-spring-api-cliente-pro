package com.byschoo.apirest_pro_clientesdto.Exceptions;

import java.util.HashMap;
import java.util.Map;

import org.apache.coyote.BadRequestException;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;

import com.byschoo.apirest_pro_clientesdto.Payload.MensajeResponseFailure;
import com.byschoo.apirest_pro_clientesdto.Payload.ValidationErrorResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

    
    @ExceptionHandler(MethodArgumentNotValidException.class) // 
    public ResponseEntity<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, WebRequest webRequest) {
    
        Map<String, String> mapErrors = new HashMap<>();
            ex.getBindingResult().getAllErrors().forEach((error) -> {
                String clave = ((FieldError) error).getField();
                String valor = error.getDefaultMessage();
            mapErrors.put(clave, valor);
        });

        ValidationErrorResponse errorResponse = ValidationErrorResponse.builder()
            .mensaje("Error de validación en los datos de entrada")
            .error(mapErrors) // Asigna el mapa de errores al mensaje
            .code("Exc-400-01")
            .object(ex.getClass().getSimpleName())
            .severity("Minor")
            .url(webRequest.getDescription(false).replace("uri=", "")) // Asigna la URL
            .build(); // Crea la instancia

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST); // Devuelve 400 Bad Request. Se lanza cuando fallan las validaciones de los datos de entrada de una solicitud (por ejemplo, datos de un formulario o JSON).
    }
    

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<MensajeResponseFailure> handleResourceNotFoundException(ResourceNotFoundException ex, WebRequest webRequest) {
        return new ResponseEntity<>(
            MensajeResponseFailure.builder()
                .mensaje(ex.getMessage())
                .code(ex.getCode()) // Generalmente devuelve Exc-404-00. Código dinámico para cada excepción.
                .object(ex.getClass().getSimpleName()) // Opcional: Puedes incluir detalles adicionales en el objeto
                .severity("Warning")
                .url(webRequest.getDescription(false).replace("uri=", "")) // Asigna la URL
                .build(),
            ex.getStatus() // Generalmente devuelve 400 Bad Requesst o 404 Not Found. HttpStatus dinámico para cada excepción.
        );
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<MensajeResponseFailure> handleBusinessException(BusinessException ex, WebRequest webRequest) {
        return new ResponseEntity<>(
            MensajeResponseFailure.builder()
                .mensaje(ex.getMessage())
                .code(ex.getCode()) // Código dinámico para cada excepción.
                .object(ex.getClass().getSimpleName()) // Opcional: Puedes incluir detalles adicionales en el objeto
                .severity("warning")
                .url(webRequest.getDescription(false).replace("uri=", "")) // Asigna la URL
                .build(),
            ex.getStatus() // HttpStatus dinámico para cada excepción. Define excepciones específicas para errores de lógica de negocio (por ejemplo, "Saldo insuficiente", "Producto no disponible").
        );
    }

    
    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<MensajeResponseFailure> handleNoHandlerFoundException(NoHandlerFoundException ex, WebRequest webRequest) {
        return new ResponseEntity<>(
            MensajeResponseFailure.builder()
                .mensaje(ex.getMessage())
                .code("Exc-404-05")
                .severity("Warning")
                .object(null) // Opcional: Puedes incluir detalles adicionales en el objeto
                .url(webRequest.getDescription(false).replace("uri=", "")) // Asigna la URL
                .build(),
            HttpStatus.NOT_FOUND // Devuelve 404 Not Found. Se lanza cuando no se encuentra un controlador para una solicitud. Devuelve 404 Not Found.
        );
    }


    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<MensajeResponseFailure> handleBadRequestException(BadRequestException ex, WebRequest webRequest) {
        return new ResponseEntity<>(
            MensajeResponseFailure.builder()
                .mensaje(ex.getMessage())
                .code("Exc-400-02")
                .severity("Minor")
                .object(null) // Opcional: Puedes incluir detalles adicionales en el objeto
                .url(webRequest.getDescription(false).replace("uri=", "")) // Asigna la URL
                .build(),
            HttpStatus.BAD_REQUEST // Devuelve 400 Bad Request. Se lanza cuando fallan las validaciones de los datos de entrada de una solicitud (por ejemplo, datos de un formulario o JSON).
        );
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<MensajeResponseFailure> handleDataIntegrityViolationException(DataIntegrityViolationException ex, WebRequest webRequest) {
        return new ResponseEntity<>(
            MensajeResponseFailure.builder()
                .mensaje(ex.getMessage())
                .code("Exc-409-01")
                .severity("Danger")
                .object(null) // Opcional: Puedes incluir detalles adicionales en el objeto
                .url(webRequest.getDescription(false).replace("uri=", "")) // Asigna la URL
                .build(),
            HttpStatus.CONFLICT // Devuelve 409 Conflict. Se lanza cuando se viola una restricción de integridad de la base de datos (por ejemplo, clave primaria duplicada, violación de clave externa).
        );
    }

    
    @ExceptionHandler(EmptyResultDataAccessException.class)
    public ResponseEntity<MensajeResponseFailure> handleEmptyResultDataAccessException(EmptyResultDataAccessException ex, WebRequest webRequest) {
        return new ResponseEntity<>(
            MensajeResponseFailure.builder()
                .mensaje(ex.getMessage())
                .code("Exc-404-06")
                .severity("Danger")
                .object(null) // Opcional: Puedes incluir detalles adicionales en el objeto
                .url(webRequest.getDescription(false).replace("uri=", "")) // Asigna la URL
                .build(),
            HttpStatus.NOT_FOUND // Devuelve 404 Not Found. Se lanza cuando una consulta no devuelve ningún resultado y se esperaba al menos uno. Indica que el recurso no fue encontrado.
        );
    }

    
    @ExceptionHandler(DataAccessResourceFailureException.class)
    public ResponseEntity<MensajeResponseFailure> handleDataAccessResourceFailureException(DataAccessResourceFailureException ex, WebRequest webRequest) {
        return new ResponseEntity<>(
            MensajeResponseFailure.builder()
                .mensaje(ex.getMessage())
                .code("Exc-503-01")
                .severity("Fatal")
                .object(null) // Opcional: Puedes incluir detalles adicionales en el objeto
                .url(webRequest.getDescription(false).replace("uri=", "")) // Asigna la URL
                .build(),
            HttpStatus.SERVICE_UNAVAILABLE // Devuelve 503 Service Unavailable. Se lanza cuando hay un problema al acceder a un recurso de datos (por ejemplo, la base de datos). Indica que el servicio no está disponible temporalmente.
        );
    }


    @ExceptionHandler({Exception.class, RuntimeException.class})
    public ResponseEntity<MensajeResponseFailure> handleException(Exception ex, WebRequest webRequest) {
        return new ResponseEntity<>(
            MensajeResponseFailure.builder()
                .mensaje(ex.getMessage())
                .code("Exc-500-01")
                .severity("Fatal")
                .object(ex.getClass()) // Opcional: Puedes incluir detalles adicionales en el objeto
                .url(webRequest.getDescription(false).replace("uri=", "")) // Asigna la URL
                .build(),
            HttpStatus.INTERNAL_SERVER_ERROR // Devuelve 500 Internal Server Error. Se utiliza para capturar cualquier excepción no manejada específicamente.
        );
    }

}
