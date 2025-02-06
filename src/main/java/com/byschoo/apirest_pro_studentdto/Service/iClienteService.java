package com.byschoo.apirest_pro_studentdto.Service;

import java.util.List;

import com.byschoo.apirest_pro_studentdto.DTO.ClienteDTO;
import com.byschoo.apirest_pro_studentdto.Model.Cliente;

public interface iClienteService {

    // POSTMAPPING ------------------------------------------------------------------------------------
    Cliente saveCliente(ClienteDTO clienteDTO);
    List<Cliente> saveAllClientes(List<ClienteDTO> clientesDTO);
    //|||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||


    // GETMAPPING -------------------------------------------------------------------------------------
    List<Cliente> findAllClientes();
    Cliente findClienteById(Long id);

    // Búsqueda personalizada en Repositorio
    List<Cliente> findClientesByNombreLike(String nombre);

    // Búsqueda personalizada en Repositorio
    List<Cliente> findClientesByNameOrLastName(String nombre, String apellido);
    //|||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||

    
    // PUTMAPPING -------------------------------------------------------------------------------------
    Cliente updateCliente(ClienteDTO clienteDTO);
    Cliente updateCliente(Long id, ClienteDTO clienteDTO);
    //|||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||


    // DELETEMAPPING ----------------------------------------------------------------------------------
    void delete(Cliente cliente); //CRUDRepository tiene métodos para eliminar por el ID o enviando la entidad completa.
    //|||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||

}
