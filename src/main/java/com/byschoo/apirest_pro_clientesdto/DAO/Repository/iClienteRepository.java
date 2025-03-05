package com.byschoo.apirest_pro_clientesdto.DAO.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.byschoo.apirest_pro_clientesdto.DAO.Model.Cliente;

public interface iClienteRepository extends JpaRepository<Cliente, Long>{
    // Métodos personalizados
    @Query("SELECT s FROM Cliente s WHERE s.nombre LIKE %:nombre%")
    List<Cliente> findByNombresLike(String nombre);

    /**busqueda por varios campos, lo que se quiere es que cuando ingrese una letra, este se búsque  
     * en los dos campos nombre y apellido no por separado la cual lo realiza la siguiente consulta
    */
    @Query("SELECT s FROM Cliente s WHERE s.nombre LIKE %:nombre% OR s.apellido LIKE %:apellido%")
    List<Cliente> findByNameOrLastName(@Param("nombre") String nombre, @Param("apellido") String apellido); // se quiere que reciba solo un parámetro pero que realice la búsqueda en los dos campos

}
