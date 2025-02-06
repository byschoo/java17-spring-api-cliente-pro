package com.byschoo.apirest_pro_studentdto.Service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.byschoo.apirest_pro_studentdto.DTO.ClienteDTO;
import com.byschoo.apirest_pro_studentdto.Model.Cliente;
import com.byschoo.apirest_pro_studentdto.Repository.iClienteRepository;


/**
 * @Transaction de springframework: Realiza un seguimiento de los cambios realizados en los objetos de dominio durante una transacción. Al final de la transacción, todos los cambios se guardan en la base de datos de una sola vez.
 *  Eficiencia: Reduce el número de viajes a la base de datos, ya que todos los cambios se guardan en una sola operación.
 *  Consistencia: Asegura que todas las operaciones dentro de una transacción se completen o fallen juntas, manteniendo la consistencia de los datos.
 *  Manejo de transacciones: Facilita el manejo de transacciones complejas que involucran múltiples entidades.
 */

@Service
public class ClienteServiceImpl implements iClienteService {

    
    @Autowired
    private iClienteRepository clienteRepository;

    // POSTMAPPING ------------------------------------------------------------------------------------
    @Transactional
    @Override
    public Cliente saveCliente(ClienteDTO clienteDTO) {
        Cliente cliente = convertirDeDTOaEntidad(clienteDTO); // Llama al método de conversión
        return clienteRepository.save(cliente);
    }

    //-------------------------------------------------------------------------------------------------
    @Transactional
    @Override
    public List<Cliente> saveAllClientes(List<ClienteDTO> clientesDTO) {
        List<Cliente> clientes = new ArrayList<>();

        for (ClienteDTO clienteDTO : clientesDTO) {
            Cliente cliente = convertirDeDTOaEntidad(clienteDTO); // Usamos el método de conversión
            clientes.add(cliente);
        }

        return (List<Cliente>) clienteRepository.saveAll(clientes);
    }
    //|||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||
    
    // GETMAPPING -------------------------------------------------------------------------------------
    @Transactional(readOnly = true) // Toda transacción de consulta debe ser de solo lectura.
    @Override
    public List<Cliente> findAllClientes() {
        return (List<Cliente>) clienteRepository.findAll(); // Obtiene las entidades del repositorio
    }
    
    //-------------------------------------------------------------------------------------------------
    @Transactional(readOnly = true) // Toda transacción de consulta debe ser de solo lectura.
    @Override
    public Cliente findClienteById(Long id) {
        return clienteRepository.findById(id).orElseThrow( // Obtiene la entidad del repositorio
            () -> new RuntimeException("El cliente con el " + id + " no fue encontrado."));
    }

    //-------------------------------------------------------------------------------------------------
    @Transactional(readOnly = true) // Toda transacción de consulta debe ser de solo lectura.
    @Override
    public List<Cliente> findClientesByNombreLike(String nombre){
        List<Cliente> clientes = clienteRepository.findByNombreLike(nombre);
            
            if (clientes.isEmpty()) {
                throw new RuntimeException("No se encontraron clientes con el nombre: " + nombre);
            }
        
        return clientes;
    }
    
    //-------------------------------------------------------------------------------------------------
    @Transactional(readOnly = true) // Toda transacción de consulta debe ser de solo lectura.
    @Override
    public List<Cliente> findClientesByNameOrLastName(String nombre, String apellido){
        return (List<Cliente>) clienteRepository.findByNameOrLastName(nombre, apellido);  // Obtiene las entidades del repositorio
    }    
    //|||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||

    // PUTMAPPING -------------------------------------------------------------------------------------
    @Transactional
    @Override
    public Cliente updateCliente(ClienteDTO clienteDTO) {
        Cliente clienteUpdate = clienteRepository.findById(clienteDTO.getId()).orElseThrow( 
            () -> new RuntimeException("EL CLIENTE CON EL " + clienteDTO.getId() + " NO FUE ENCONTRADO."));

        clienteUpdate.setNombre(clienteDTO.getNombre());
        clienteUpdate.setApellido(clienteDTO.getApellido());
        clienteUpdate.setCorreo(clienteDTO.getCorreo());
        clienteUpdate.setEdad(clienteDTO.getEdad());
        clienteUpdate.setFechaRegistro(clienteDTO.getFechaRegistro());

        return clienteRepository.save(clienteUpdate);
    }

    //-------------------------------------------------------------------------------------------------
    @Transactional
    @Override
    public Cliente updateCliente(Long id, ClienteDTO clienteDTO) {
        Cliente clienteUpdate = clienteRepository.findById(id).orElseThrow( 
            () -> new RuntimeException("EL CLIENTE CON EL " + id + " NO FUE ENCONTRADO."));

        clienteUpdate.setNombre(clienteDTO.getNombre());
        clienteUpdate.setApellido(clienteDTO.getApellido());
        clienteUpdate.setCorreo(clienteDTO.getCorreo());
        clienteUpdate.setEdad(clienteDTO.getEdad());
        clienteUpdate.setFechaRegistro(clienteDTO.getFechaRegistro());

        return clienteRepository.save(clienteUpdate);
    }
    //|||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||


    // DELETEMAPPING ----------------------------------------------------------------------------------
    @Transactional
    @Override
    public void delete(Cliente cliente) {
        clienteRepository.delete(cliente);
    }
    //|||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||


    // Métodos DeDTOaENTIDAD --------------------------------------------------------------------------
    private Cliente convertirDeDTOaEntidad(ClienteDTO clienteDTO) {
        return Cliente.builder()
            .nombre(clienteDTO.getNombre())
            .apellido(clienteDTO.getApellido())
            .correo(clienteDTO.getCorreo())
            .edad(clienteDTO.getEdad())
            .fechaRegistro(clienteDTO.getFechaRegistro())
            .build();
    }
    //|||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||

}
