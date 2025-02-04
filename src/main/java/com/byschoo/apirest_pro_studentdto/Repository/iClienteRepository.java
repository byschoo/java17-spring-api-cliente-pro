package com.byschoo.apirest_pro_studentdto.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.byschoo.apirest_pro_studentdto.Model.Cliente;

public interface iClienteRepository extends CrudRepository<Cliente, Long>{
    // Métodos personalizados
    @Query("SELECT s FROM Cliente s WHERE s.nombre LIKE %:nombre%")
    List<Cliente> findByNombreLike(String nombre);

    /**busqueda por varios campos, lo que se quiere es que cuando ingrese una letra, este se búsque  
     * en los dos campos nombre y apellido no por separado la cual lo realiza la siguiente consulta
    */
    @Query("select s from Cliente s where s.nombre = :nombre or s.apellido = :apellido")
    List<Cliente> findByNameOrLastName(@Param("nombre") String nombre, @Param("apellido") String apellido); // se quiere que reciba solo un parámetro pero que realice la búsqueda en los dos campos

}
