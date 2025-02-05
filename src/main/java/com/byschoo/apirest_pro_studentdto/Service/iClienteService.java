package com.byschoo.apirest_pro_studentdto.Service;

import java.util.List;

import com.byschoo.apirest_pro_studentdto.DTO.ClienteDTO;
import com.byschoo.apirest_pro_studentdto.Model.Cliente;

public interface iClienteService {

    // POSTMAPPING
    Cliente save(ClienteDTO clienteDTO); // El método "save" guarda y actualiza. No es necesario un método update.
    List<Cliente> saveAll(List<ClienteDTO> clientesDTO);
    //-----------------------------------------


    // GETMAPPING
    List<Cliente> findAll();
    Cliente findById(Long id);

    // Búsqueda personalizada en Repositorio
    List<Cliente> findByNombreLike(String nombre);

    // Búsqueda personalizada en Repositorio que requiere un parámetro pero que realice la búsqueda en los dos campos
    List<Cliente> findByNameOrLastName(String nombre, String apellido);
    //-----------------------------------------


    // DELETEMAPPING
    Cliente delete(Long id); //CRUDRepository tiene métodos para eliminar por el ID o enviando la entidad completa.
    //-----------------------------------------

}
