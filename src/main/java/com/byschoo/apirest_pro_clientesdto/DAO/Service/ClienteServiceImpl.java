package com.byschoo.apirest_pro_clientesdto.DAO.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.byschoo.apirest_pro_clientesdto.DAO.Model.Cliente;
import com.byschoo.apirest_pro_clientesdto.DAO.Repository.iClienteRepository;
import com.byschoo.apirest_pro_clientesdto.DTO.ClienteDTO;
import com.byschoo.apirest_pro_clientesdto.Exceptions.ResourceNotFoundException;


/**
 * @Transaction de springframework: Realiza un seguimiento de los cambios realizados en los objetos de dominio durante una transacción. Al final de la transacción, todos los cambios se guardan en la base de datos de una sola vez.
 *  Eficiencia: Reduce el número de viajes a la base de datos, ya que todos los cambios se guardan en una sola operación.
 *  Consistencia: Asegura que todas las operaciones dentro de una transacción se completen o fallen juntas, manteniendo la consistencia de los datos.
 *  Manejo de transacciones: Facilita el manejo de transacciones complejas que involucran múltiples entidades.
 */

@Service
public class ClienteServiceImpl implements iClienteService {
    
    private final iClienteRepository clienteRepository;

    public ClienteServiceImpl(final iClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    private static final Logger logger = LoggerFactory.getLogger(ClienteServiceImpl.class);
    private final ModelMapper modelMapper = new ModelMapper();



    // POSTMAPPING ------------------------------------------------------------------------------------
    @Transactional
    @Override
    public Cliente saveCliente(ClienteDTO clienteDTO) {
        logger.debug("\"SERVICE LAYER - ClienteServiceImpl REQUESTED: saveCliente\""); // SLF4j para loggear
        
        logger.debug("\"SERVICE LAYER - CONVERTING DTO to ENTITY\"");
        Cliente cliente = modelMapper.map(clienteDTO, Cliente.class); // Llama al método de conversión ModelMapper

        logger.info("SERVICE LAYER - ADDING Cliente into DB.");
        return clienteRepository.save(cliente);
    }

    //-------------------------------------------------------------------------------------------------
    @Transactional
    @Override
    public List<Cliente> saveAllClientes(List<ClienteDTO> clientesDTO) {
        logger.debug("\"ClienteServiceImpl REQUESTED: saveCliente\""); // SLF4j para loggear

        List<Cliente> clientes = clientesDTO.stream()
            .map(clienteDTO -> modelMapper.map(clienteDTO, Cliente.class))
            .collect(Collectors.toList());

        logger.info("ADDING Clientes into DB.");
        return (List<Cliente>) clienteRepository.saveAll(clientes);
    }
    //|||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||
    
    // GETMAPPING -------------------------------------------------------------------------------------
    @Transactional(readOnly = true) // Toda transacción de consulta debe ser de solo lectura.
    @Override
    public List<Cliente> findAllClientes() {
        return Optional.ofNullable((List<Cliente>) clienteRepository.findAll())
            .filter(clientes -> !clientes.isEmpty()) // Filtra si la lista no está vacía
            .orElseThrow(() -> new ResourceNotFoundException("No hay registros de clientes", "Exc-E4006", HttpStatus.NOT_FOUND));
    }
    
    
    //-------------------------------------------------------------------------------------------------
    @Transactional(readOnly = true) // Toda transacción de consulta debe ser de solo lectura.
    @Override
    public Cliente findClienteById(Long id) {

        // Obtiene la entidad del repositorio
        return clienteRepository.findById(id)

            // Se envían los argumentos al constructor y se construye el mensaje en el Controller
            .orElseThrow(() -> new ResourceNotFoundException("No hay registros de cliente con el id: " + id, "Exc-E4007", HttpStatus.NOT_FOUND));
    }

    //-------------------------------------------------------------------------------------------------
    @Transactional(readOnly = true) // Toda transacción de consulta debe ser de solo lectura.
    @Override
    public List<Cliente> findClientesByNombreLike(String nombre) {
        return Optional.ofNullable((List<Cliente>) clienteRepository.findByNombresLike(nombre))
            .filter(clientes -> !clientes.isEmpty())
            .orElseThrow(() -> new ResourceNotFoundException("No hay registros de clientes con el nombre: " + nombre, "Exc-E4007", HttpStatus.NOT_FOUND));
    }
    
    //-------------------------------------------------------------------------------------------------
    @Transactional(readOnly = true) // Toda transacción de consulta debe ser de solo lectura.
    @Override
    public List<Cliente> findClientesByNameOrLastName(String nombre, String apellido) {
        return Optional.ofNullable((List<Cliente>) clienteRepository.findByNameOrLastName(nombre, apellido))  // Obtiene las entidades del repositorio
            .filter(clientes -> !clientes.isEmpty())
            .orElseThrow(() -> new ResourceNotFoundException("No hay registros de clientes con la información suministrada", "Exc-E4007", HttpStatus.NOT_FOUND));
    }    
    //|||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||

    // PUTMAPPING -------------------------------------------------------------------------------------
    @Transactional
    @Override
    public Cliente updateCliente(ClienteDTO clienteDTO) {
        Long id = clienteDTO.getId(); // Se obtiene el ID del DTO

        Cliente clienteUpdate = clienteRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("No hay registros de cliente con el id: " + id, "Exc-E4007", HttpStatus.NOT_FOUND));

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
            .orElseThrow(() -> new ResourceNotFoundException("No hay registros de cliente con el id: " + id, "Exc-E4007", HttpStatus.NOT_FOUND));


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


}
