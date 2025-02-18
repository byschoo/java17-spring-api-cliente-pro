package com.byschoo.apirest_pro_clientesdto.DTO;

import java.io.Serializable;
import java.util.Date;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

// @Data de Lombok: Genera métodos getters y setters para todos los campos, equals(), toString() y hashCode().
// @Builder de Lombok: Genera un constructor estático interno (llamado "builder") que te permite crear instancias de la clase.
// @NotNull de la especificación Bean Validation (JSR 380): Esta anotación verifica que el valor de un campo no sea null. Es decir, el campo debe tener algún valor, pero este valor puede ser una cadena vacía ("") o espacios en blanco.
// @NotBlank de la especificación de Bean Validation (JSR 380): Esta anotación va más allá de @NotNull. No solo verifica que el valor no sea null, sino que también verifica que la cadena no esté vacía después de eliminar los espacios en blanco al principio y al final. Es decir, no permite cadenas que solo contengan espacios en blanco.

@Data
@Builder
public class ClienteDTO implements Serializable{

    private final Long id;

    @NotBlank(message = "El nombre es obligatorio")
    @Pattern(regexp = "^[A-Za-zÁÉÍÓÚÜÑáéíóúüñ\\s]+$", message = "El nombre solo puede contener letras latinas y espacios")
    @Size(min = 2, max = 50, message = "El nombre debe tener entre 2 y 50 caracteres")
    private String nombre;


    @NotBlank(message = "El apellido es obligatorio")
    @Pattern(regexp = "^[A-Za-zÁÉÍÓÚÜÑáéíóúüñ\\s]+$", message = "El apellido solo puede contener letras latinas y espacios")
    @Size(min = 2, max = 50, message = "El apellido debe tener entre 2 y 50 caracteres")
    private String apellido;


    @NotBlank(message = "El correo es obligatorio")
    @Email(message = "Debe ser una dirección de correo electrónico válida")
    @Size(max = 50, message = "El correo no puede tener más de 50 caracteres")
    private String correo;


    @NotNull(message = "La edad es obligatoria")
    @Min(value = 18, message = "La edad debe ser mayor o igual a 18")
    @Max(value = 125, message = "La edad no puede ser mayor a 125")
    private int edad;


    private final Date fechaRegistro;
}
