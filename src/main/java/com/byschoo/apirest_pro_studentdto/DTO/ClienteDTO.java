package com.byschoo.apirest_pro_studentdto.DTO;

import java.io.Serializable;
import java.util.Date;


import lombok.Builder;
import lombok.Data;

// @Data de Lombok: Genera métodos getters y setters para todos los campos, equals(), toString() y hashCode().
// @Builder de Lombok: Genera un constructor estático interno (llamado "builder") que te permite crear instancias de la clase.


@Data
@Builder
public class ClienteDTO implements Serializable{

    private Long id;
    private String nombre;
    private String apellido;
    private String correo;
    private int edad;
    private Date fechaRegistro;
}
