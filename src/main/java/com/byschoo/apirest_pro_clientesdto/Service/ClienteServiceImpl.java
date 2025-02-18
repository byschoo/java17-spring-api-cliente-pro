package com.byschoo.apirest_pro_clientesdto.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.byschoo.apirest_pro_clientesdto.DTO.ClienteDTO;
import com.byschoo.apirest_pro_clientesdto.Exceptions.ResourceNotFoundException;
import com.byschoo.apirest_pro_clientesdto.Model.Cliente;
import com.byschoo.apirest_pro_clientesdto.Repository.iClienteRepository;


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
        return Optional.ofNullable((List<Cliente>) clienteRepository.findAll())
            .filter(clientes -> !clientes.isEmpty()) // Filtra si la lista no está vacía
            .orElseThrow(() -> new ResourceNotFoundException("No hay registros de clientes", "Exc-E4006", null, HttpStatus.NOT_FOUND));
    }
    
    
    //-------------------------------------------------------------------------------------------------
    @Transactional(readOnly = true) // Toda transacción de consulta debe ser de solo lectura.
    @Override
    public Cliente findClienteById(Long id) {

        // Obtiene la entidad del repositorio
        return clienteRepository.findById(id)

            // Se envían los argumentos al constructor y se construye el mensaje en el Controller
            .orElseThrow(() -> new ResourceNotFoundException("No hay registros de cliente con el id: " + id, "Exc-E4007", null, HttpStatus.NOT_FOUND));
    }

    //-------------------------------------------------------------------------------------------------
    @Transactional(readOnly = true) // Toda transacción de consulta debe ser de solo lectura.
    @Override
    public List<Cliente> findClientesByNombreLike(String nombre) {
        return Optional.ofNullable((List<Cliente>) clienteRepository.findByNombresLike(nombre))
            .filter(clientes -> !clientes.isEmpty())
            .orElseThrow(() -> new ResourceNotFoundException("No hay registros de clientes con el nombre: " + nombre, "Exc-E4007", null, HttpStatus.NOT_FOUND));
    }
    
    //-------------------------------------------------------------------------------------------------
    @Transactional(readOnly = true) // Toda transacción de consulta debe ser de solo lectura.
    @Override
    public List<Cliente> findClientesByNameOrLastName(String nombre, String apellido) {
        return Optional.ofNullable((List<Cliente>) clienteRepository.findByNameOrLastName(nombre, apellido))  // Obtiene las entidades del repositorio
            .filter(clientes -> !clientes.isEmpty())
            .orElseThrow(() -> new ResourceNotFoundException("No hay registros de clientes con la información suministrada", "Exc-E4007", null, HttpStatus.NOT_FOUND));
    }    
    //|||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||

    // PUTMAPPING -------------------------------------------------------------------------------------
    @Transactional
    @Override
    public Cliente updateCliente(ClienteDTO clienteDTO) {
        Long id = clienteDTO.getId(); // Se obtiene el ID del DTO

        Cliente clienteUpdate = clienteRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("No hay registros de cliente con el id: " + id, "Exc-E4007", null, HttpStatus.NOT_FOUND));

        clienteUpdate.setNombre(clienteDTO.getNombre());
        clienteUpdate.setApellido(clienteDTO.getApellido());
        clienteUpdate.setCorreo(clienteDTO.getCorreo());
        clienteUpdate.setEdad(clienteDTO.getEdad());

        return clienteRepository.save(clienteUpdate);
    }

    //-------------------------------------------------------------------------------------------------
    @Transactional
    @Override
    public Cliente updateCliente(ClienteDTO clienteDTO, Long id) {
        Cliente clienteUpdate = clienteRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("No hay registros de cliente con el id: " + id, "Exc-E4007", null, HttpStatus.NOT_FOUND));


        clienteUpdate.setNombre(clienteDTO.getNombre());
        clienteUpdate.setApellido(clienteDTO.getApellido());
        clienteUpdate.setCorreo(clienteDTO.getCorreo());
        clienteUpdate.setEdad(clienteDTO.getEdad());

        return clienteRepository.save(clienteUpdate);
    }
    //|||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||


    // DELETEMAPPING ----------------------------------------------------------------------------------
    @Transactional
    @Override
    public void deleteCliente(Long id) {

        Cliente clienteDelete = findClienteById(id);
        clienteRepository.delete(clienteDelete);
    }
    //|||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||


    // Métodos DeDTOaENTIDAD --------------------------------------------------------------------------
    private Cliente convertirDeDTOaEntidad(ClienteDTO clienteDTO) {
        return Cliente.builder()
            .nombre(clienteDTO.getNombre())
            .apellido(clienteDTO.getApellido())
            .correo(clienteDTO.getCorreo())
            .edad(clienteDTO.getEdad())
            .build();
    }
    //|||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||
}
