package com.byschoo.apirest_pro_clientesdto.Controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
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
import com.byschoo.apirest_pro_clientesdto.Exceptions.BadRequestException;
import com.byschoo.apirest_pro_clientesdto.Model.Cliente;
import com.byschoo.apirest_pro_clientesdto.Payload.MensajeResponseSuccess;
import com.byschoo.apirest_pro_clientesdto.Service.iClienteService;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/v1")
public class ClienteController {

    @Autowired
    private iClienteService clienteService;
    
    // POSTMAPPING ------------------------------------------------------------------------------------
    @PostMapping("cliente")
    ResponseEntity<?> saveCliente(@Valid @RequestBody ClienteDTO clienteDTO) {

        try {
            Cliente clienteSave = clienteService.saveCliente(clienteDTO);
            clienteDTO = convertirDeEntidadADTO(clienteSave);
            return new ResponseEntity<>(
                MensajeResponseSuccess.builder()
                    .mensaje("Cliente guardado satisfactoriamente")
                    .object(clienteDTO)
                    .build(),
                HttpStatus.CREATED
            );

        } catch (DataAccessException exDt) {
            throw  new BadRequestException(exDt.getMessage());
        }
    }
    
    //-------------------------------------------------------------------------------------------------
    @PostMapping("/clientes")
    ResponseEntity<?> saveAllClientes(@Valid @RequestBody List<ClienteDTO> clientesDTO) {

        try {
            List<Cliente> clienteSaveAll = clienteService.saveAllClientes(clientesDTO); // Llama al servicio y retorna el resultado
            clientesDTO = new ArrayList<>(); // Crea una lista para almacenar los DTOs

            for (Cliente cliente : clienteSaveAll) {
                ClienteDTO clienteDTO = convertirDeEntidadADTO(cliente); // Convierte cada entidad a DTO
                clientesDTO.add(clienteDTO);
            }

                return new ResponseEntity<>(
                    MensajeResponseSuccess.builder()
                        .mensaje("Clientes guardados satisfactoriamente")
                        .object(clientesDTO)
                        .build(),
                    HttpStatus.CREATED
                );
                
            } catch (DataAccessException exDt) {
                throw  new BadRequestException(exDt.getMessage());
            } 
    }
    //|||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||
    
    
    // GETMAPPING  ------------------------------------------------------------------------------------
    @GetMapping ("clientes")
    ResponseEntity<?> findAllClientes() {

        try {
            List<Cliente> clientes = clienteService.findAllClientes(); // Obtiene las entidades del servicio
            List<ClienteDTO> clientesDTO = new ArrayList<>(); // Crea una lista para almacenar los DTOs
    
                for (Cliente cliente : clientes) {
                    ClienteDTO clienteDTO = convertirDeEntidadADTO(cliente); // Convierte cada entidad a DTO
                    clientesDTO.add(clienteDTO);
                }
    
            return new ResponseEntity<>(
                MensajeResponseSuccess.builder()
                    .mensaje("Búsqueda satisfactoria")
                    .object(clientesDTO) // Retorna la lista de DTOs
                    .build(),
                HttpStatus.OK
            );

        } catch (DataAccessException exDt) {
            throw  new BadRequestException(exDt.getMessage());
        }  
    }
    
    //-------------------------------------------------------------------------------------------------
    @GetMapping ("cliente/{id}")
    ResponseEntity<?> findClienteById(@PathVariable Long id) {
    
        try {
            Cliente cliente = clienteService.findClienteById(id); // Obtiene la entidad del servicio
            ClienteDTO clienteDTO = convertirDeEntidadADTO(cliente); // Convierte la entidad a DTO

            return new ResponseEntity<>(
                MensajeResponseSuccess.builder()
                    .mensaje("Búsqueda satisfactoria")
                    .object(clienteDTO) // Retorna la lista de DTO
                    .build(),
                HttpStatus.OK
            );

        } catch (DataAccessException exDt) {
            throw  new BadRequestException(exDt.getMessage());
        }
    }
    
    //-------------------------------------------------------------------------------------------------
    @GetMapping ("clientes/{nombre}")
    ResponseEntity<?> findClientesByName(@PathVariable String nombre) {
        try {
            List<Cliente> clientes = clienteService.findClientesByNombreLike(nombre); // Obtiene las entidades del servicio
            List<ClienteDTO> clientesDTO = new ArrayList<>(); // Crea una lista para almacenar los DTOs
    
                for (Cliente cliente : clientes) {
                    ClienteDTO clienteDTO = convertirDeEntidadADTO(cliente); // Convierte cada entidad a DTO
                    clientesDTO.add(clienteDTO);
                }
    
            return new ResponseEntity<>(
                MensajeResponseSuccess.builder()
                    .mensaje("Búsqueda satisfactoria")
                    .object(clientesDTO) // Retorna la lista de DTOs
                    .build(),
                HttpStatus.OK
            );

        } catch (DataAccessException exDt) {
            throw  new BadRequestException(exDt.getMessage());
        }
    }
    
    //-------------------------------------------------------------------------------------------------
    @GetMapping ("clientes/buscar")
    ResponseEntity<?> findClientesByNameOrLastName (
                                @RequestParam(value = "nombre", required = false) String nombre,
                                @RequestParam(value = "apellido", required = false) String apellido) {

        try {
            List<Cliente> clientes = clienteService.findClientesByNameOrLastName(nombre, apellido); // Obtiene las entidades del servicio
            List<ClienteDTO> clientesDTO = new ArrayList<>(); // Crea una lista para almacenar los DTOs
    
                for (Cliente cliente : clientes) {
                    ClienteDTO clienteDTO = convertirDeEntidadADTO(cliente); // Convierte cada entidad a DTO
                    clientesDTO.add(clienteDTO);
                }
    
            return new ResponseEntity<>(
                MensajeResponseSuccess.builder()
                    .mensaje("Búsqueda satisfactoria")
                    .object(clientesDTO) // Retorna la lista de DTOs
                    .build(),
                HttpStatus.OK
            );

        } catch (DataAccessException exDt) {
            throw  new BadRequestException(exDt.getMessage());
        }
    }
    //|||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||
    
    
    // PUTMAPPING  ------------------------------------------------------------------------------------
    @PutMapping ("cliente")
    ResponseEntity<?> updateCliente(@Valid @RequestBody ClienteDTO clienteDTO) {

        try {
            Cliente clienteUpdate = clienteService.updateCliente(clienteDTO);  // Llama al servicio
            clienteDTO = convertirDeEntidadADTO(clienteUpdate);
            
            return new ResponseEntity<>(
                MensajeResponseSuccess.builder()
                    .mensaje("Datos del cliente actualizados satisfactoriamente")
                    .object(clienteDTO)
                    .build(),
                HttpStatus.CREATED
            );       

        } catch (DataAccessException exDt) {
            throw  new BadRequestException(exDt.getMessage());
        }  
    }


    @PutMapping ("cliente/{id}")
    ResponseEntity<?> updateCliente(@Valid @RequestBody ClienteDTO clienteDTO,
                                           @PathVariable Long id) {

        try {
            Cliente clienteUpdate = clienteService.updateCliente(clienteDTO, id);  // Llama al servicio
            clienteDTO = convertirDeEntidadADTO(clienteUpdate);
            
            return new ResponseEntity<>(
                MensajeResponseSuccess.builder()
                    .mensaje("Datos del cliente actualizados satisfactoriamente")
                    .object(clienteDTO)  // Retorna la lista de DTOs
                    .build(),
                HttpStatus.CREATED
            );

        } catch (DataAccessException exDt) {
            throw  new BadRequestException(exDt.getMessage());
        }
    }
    //|||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||
    
        
    // DELETEMAPPING  ---------------------------------------------------------------------------------
    @DeleteMapping("cliente/{id}")
    ResponseEntity<?> deleteCliente(@PathVariable Long id) {

        try {

            clienteService.deleteCliente(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        } catch (DataAccessException exDt) {
            throw  new BadRequestException(exDt.getMessage());
        }
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