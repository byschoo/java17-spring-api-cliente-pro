package com.byschoo.apirest_pro_clientesdto.Controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.byschoo.apirest_pro_clientesdto.DTO.ClienteDTO;
import com.byschoo.apirest_pro_clientesdto.Model.Cliente;
import com.byschoo.apirest_pro_clientesdto.Payload.MessageResponseSuccess;
import com.byschoo.apirest_pro_clientesdto.Service.iClienteService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1")
public class ClienteController {

    private final iClienteService clienteService;

    public ClienteController(final iClienteService clienteService) {
        this.clienteService = clienteService;
    }
    

    
    // POSTMAPPING ------------------------------------------------------------------------------------
    @PostMapping("cliente")
    ResponseEntity<?> saveCliente(@Valid @RequestBody ClienteDTO clienteDTO) {
        log.debug("\"CONTROLLER LAYER - ClienteController POSTMAPPING REQUESTED - saveCliente - EndPoint ../api/v1/cliente\""); // SLF4j para loggear
        
        log.debug("\"CONTROLLER LAYER - SENDING REQUEST TO SERVICE LAYER\"");
        Cliente clienteSave = clienteService.saveCliente(clienteDTO);
        
        log.debug("\"CONTROLLER LAYER - PREPARING RESPONSE - CONVERTING ENTITY to DTO\"");
        clienteDTO = convertirDeEntidadADTO(clienteSave);

        log.debug("\"CONTROLLER LAYER - TRANSMITTING RESPONSE\"");
        return new ResponseEntity<>(
            MessageResponseSuccess.builder()
                .mensaje("Cliente guardado satisfactoriamente")
                .object(clienteDTO)
                .build(),
            HttpStatus.CREATED
        );
    }
    
    //-------------------------------------------------------------------------------------------------
    @PostMapping("/clientes")
    ResponseEntity<?> saveAllClientes(@Valid @RequestBody List<ClienteDTO> clientesDTO) {
        log.debug("\"ClienteController POSTMAPPING REQUESTED: saveALLClienteS - EndPoint /clienteS\""); // SLF4j para loggear

        List<Cliente> clienteSaveAll = clienteService.saveAllClientes(clientesDTO); // Llama al servicio y retorna el resultado
        clientesDTO = new ArrayList<>(); // Crea una lista para almacenar los DTOs

        for (Cliente cliente : clienteSaveAll) {
            ClienteDTO clienteDTO = convertirDeEntidadADTO(cliente); // Convierte cada entidad a DTO
            clientesDTO.add(clienteDTO);
        }

        return new ResponseEntity<>(
            MessageResponseSuccess.builder()
                .mensaje("Clientes guardados satisfactoriamente")
                .object(clientesDTO)
                .build(),
            HttpStatus.CREATED
        );
    }
    //|||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||
    
    
    // GETMAPPING  ------------------------------------------------------------------------------------
    @GetMapping ("clientes")
    ResponseEntity<?> findAllClientes() {
        log.debug("\"ClienteController GETMAPPING REQUESTED: findAllClientes - EndPoint /clienteS\""); // SLF4j para loggear

        List<Cliente> clientes = clienteService.findAllClientes(); // Obtiene las entidades del servicio
        List<ClienteDTO> clientesDTO = new ArrayList<>(); // Crea una lista para almacenar los DTOs

        for (Cliente cliente : clientes) {
            ClienteDTO clienteDTO = convertirDeEntidadADTO(cliente); // Convierte cada entidad a DTO
            clientesDTO.add(clienteDTO);
        }

        return new ResponseEntity<>(
            MessageResponseSuccess.builder()
                .mensaje("Búsqueda satisfactoria")
                .object(clientesDTO) // Retorna la lista de DTOs
                .build(),
            HttpStatus.OK
        );
    }
    
    //-------------------------------------------------------------------------------------------------
    @GetMapping ("cliente/{id}")
    ResponseEntity<?> findClienteById(@PathVariable Long id) {
        log.debug("\"ClienteController GETMAPPING REQUESTED: findClienteById - EndPoint /cliente/{id}\""); // SLF4j para loggear

        Cliente cliente = clienteService.findClienteById(id); // Obtiene la entidad del servicio
        ClienteDTO clienteDTO = convertirDeEntidadADTO(cliente); // Convierte la entidad a DTO

        return new ResponseEntity<>(
            MessageResponseSuccess.builder()
                .mensaje("Búsqueda satisfactoria")
                .object(clienteDTO) // Retorna la lista de DTO
                .build(),
            HttpStatus.OK
        );
}
    
    //-------------------------------------------------------------------------------------------------
    @GetMapping ("clientes/{nombre}")
    ResponseEntity<?> findClientesByName(@PathVariable String nombre) {
        log.debug("\"ClienteController GETMAPPING REQUESTED: findClientesByName - EndPoint /clientes/{nombre}\""); // SLF4j para loggear

        List<Cliente> clientes = clienteService.findClientesByNombreLike(nombre); // Obtiene las entidades del servicio
        List<ClienteDTO> clientesDTO = new ArrayList<>(); // Crea una lista para almacenar los DTOs

        for (Cliente cliente : clientes) {
            ClienteDTO clienteDTO = convertirDeEntidadADTO(cliente); // Convierte cada entidad a DTO
            clientesDTO.add(clienteDTO);
        }

        return new ResponseEntity<>(
            MessageResponseSuccess.builder()
                .mensaje("Búsqueda satisfactoria")
                .object(clientesDTO) // Retorna la lista de DTOs
                .build(),
            HttpStatus.OK
        );
    }
    
    //-------------------------------------------------------------------------------------------------
    @GetMapping ("clientes/buscar")
    ResponseEntity<?> findClientesByNameOrLastName (
                                @RequestParam(value = "nombre", required = false) String nombre,
                                @RequestParam(value = "apellido", required = false) String apellido) {
        log.debug("\"ClienteController GETMAPPING REQUESTED: findClientesByNameOrLastName - EndPoint /clientes/buscar\""); // SLF4j para loggear

        List<Cliente> clientes = clienteService.findClientesByNameOrLastName(nombre, apellido); // Obtiene las entidades del servicio
        List<ClienteDTO> clientesDTO = new ArrayList<>(); // Crea una lista para almacenar los DTOs

            for (Cliente cliente : clientes) {
                ClienteDTO clienteDTO = convertirDeEntidadADTO(cliente); // Convierte cada entidad a DTO
                clientesDTO.add(clienteDTO);
            }

        return new ResponseEntity<>(
            MessageResponseSuccess.builder()
                .mensaje("Búsqueda satisfactoria")
                .object(clientesDTO) // Retorna la lista de DTOs
                .build(),
            HttpStatus.OK
        );
    }
    //|||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||
    
    
    // PUTMAPPING  ------------------------------------------------------------------------------------
    @PutMapping ("cliente")
    ResponseEntity<?> updateCliente(@Valid @RequestBody ClienteDTO clienteDTO) {
        log.debug("\"ClienteController PUTMAPPING REQUESTED: updateCliente - EndPoint /cliente\""); // SLF4j para loggear

        Cliente clienteUpdate = clienteService.updateCliente(clienteDTO);  // Llama al servicio
        clienteDTO = convertirDeEntidadADTO(clienteUpdate);
        
        return new ResponseEntity<>(
            MessageResponseSuccess.builder()
                .mensaje("Datos del cliente actualizados satisfactoriamente")
                .object(clienteDTO)
                .build(),
            HttpStatus.CREATED
        );       

    }


    @PutMapping ("cliente/{id}")
    ResponseEntity<?> updateCliente(@Valid @RequestBody ClienteDTO clienteDTO,
                                           @PathVariable Long id) {
        log.debug("\"ClienteController PUTMAPPING REQUESTED: updateCliente - EndPoint /cliente/{id}\""); // SLF4j para loggear

        Cliente clienteUpdate = clienteService.updateCliente(clienteDTO, id);  // Llama al servicio
        clienteDTO = convertirDeEntidadADTO(clienteUpdate);
        
        return new ResponseEntity<>(
            MessageResponseSuccess.builder()
                .mensaje("Datos del cliente actualizados satisfactoriamente")
                .object(clienteDTO)  // Retorna la lista de DTOs
                .build(),
            HttpStatus.CREATED
        );
    }
    //|||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||
    
        
    // DELETEMAPPING  ---------------------------------------------------------------------------------
    @DeleteMapping("cliente/{id}")
    ResponseEntity<?> deleteCliente(@PathVariable Long id) {
        log.debug("\"ClienteController DELETEMAPPING REQUESTED: deleteCliente - EndPoint /cliente/{id}\""); // SLF4j para loggear

        clienteService.deleteCliente(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    //|||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||


    // Método para convertir --------------------------------------------------------------------------
    private ClienteDTO convertirDeEntidadADTO(Cliente cliente) {
        return ClienteDTO.builder()
            .id(cliente.getId())
            .nombre(cliente.getNombre())
            .apellido(cliente.getApellido())
            .correo(cliente.getCorreo())
            .edad(cliente.getEdad())
            .fechaRegistro(cliente.getFechaRegistro())
            .build();
    }
    //|||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||
}