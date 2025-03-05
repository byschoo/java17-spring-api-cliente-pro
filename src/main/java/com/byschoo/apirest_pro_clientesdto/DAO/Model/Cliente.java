package com.byschoo.apirest_pro_clientesdto.DAO.Model;

import java.io.Serializable;
import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// @Data de Lombok: Genera métodos getters y setters para todos los campos, equals(), toString() y hashCode().


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
    private String nombre;

    @Column(name = "apellido", nullable = false)
    private String apellido;

    @Column(name = "correo", nullable = false)
    private String correo;

    @Column(name = "edad", nullable = false)
    private int edad;

    @Column(name = "fechaRegistro", updatable = false) // No se permite actualizar la fecha
    @CreationTimestamp // Se genera automáticamente al crear la entidad
    @JsonFormat(pattern = "yyyy-MM-dd") // Para la salida JSON
    private final Date fechaRegistro = new Date();
}
