package com.byschoo.apirest_pro_clientesdto.Service;

import java.util.List;

import com.byschoo.apirest_pro_clientesdto.DTO.ClienteDTO;
import com.byschoo.apirest_pro_clientesdto.Model.Cliente;

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
    Cliente updateCliente(ClienteDTO clienteDTO, Long id);
    //|||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||


    // DELETEMAPPING ----------------------------------------------------------------------------------
    void deleteCliente(Long id); //CRUDRepository tiene métodos para eliminar por el ID o enviando la entidad completa.
    //|||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||

}
