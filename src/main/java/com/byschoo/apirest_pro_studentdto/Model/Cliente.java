package com.byschoo.apirest_pro_studentdto.Model;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// @Data de Lombok: Genera métodos getters y setters para todos los campos, equals(), toString() y hashCode().
// @NotBlank de Validation: Verifica que la cadena no sea nula, ni esté vacía (""), ni contenga solo espacios en blanco.
// @NotNull de Validation: Verificar que un valor no sea nulo (null).


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "clientes")
public class Cliente implements Serializable{

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre", nullable = false)
    @NotBlank(message = "El nombre es obligatorio")
    @Pattern(regexp = "^[A-Za-zÁÉÍÓÚÜÑáéíóúüñ\\s]+$", message = "El nombre solo puede contener letras latinas y espacios")
    @Size(min = 2, max = 50, message = "El nombre debe tener entre 2 y 50 caracteres")
    private String nombre;

    @Column(name = "apellido", nullable = false)
    @NotBlank(message = "El apellido es obligatorio")
    @Pattern(regexp = "^[A-Za-zÁÉÍÓÚÜÑáéíóúüñ\\s]+$", message = "El apellido solo puede contener letras latinas y espacios")
    @Size(min = 2, max = 50, message = "El apellido debe tener entre 2 y 50 caracteres")
    private String apellido;

    @Column(name = "correo", nullable = false)
    @NotBlank(message = "El correo no puede estar en blanco")
    @Email(message = "Debe ser una dirección de correo electrónico válida")
    @Size(max = 255, message = "El correo no puede tener más de 255 caracteres")
    private String correo;

    @Column(name = "edad", nullable = false)
    @NotNull(message = "La edad es obligatoria y debe ser mayor de edad")
    @Min(value = 18, message = "La edad debe ser mayor o igual a 18")
    @Max(value = 125, message = "La edad no puede ser mayor a 125")
    private int edad;

    @Column(name = "fechaRegistro")
    @NotNull(message = "La fecha de registro no puede ser nula")
    @DateTimeFormat(pattern = "yyyy-MM-dd") // Para la entrada de datos
    @JsonFormat(pattern = "yyyy-MM-dd") // Para la salida JSON
    private Date fechaRegistro;
}
