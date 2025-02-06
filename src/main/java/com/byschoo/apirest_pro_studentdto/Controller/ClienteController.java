package com.byschoo.apirest_pro_studentdto.Controller;

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

import com.byschoo.apirest_pro_studentdto.DTO.ClienteDTO;
import com.byschoo.apirest_pro_studentdto.Model.Cliente;
import com.byschoo.apirest_pro_studentdto.Payload.MensajeResponse;
import com.byschoo.apirest_pro_studentdto.Service.iClienteService;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/v1")
public class ClienteController {

    @Autowired
    private iClienteService clienteService;
    
    // POSTMAPPING ------------------------------------------------------------------------------------
    @PostMapping("cliente")
    public ResponseEntity<?> saveCliente(@Valid @RequestBody ClienteDTO clienteDTO){

        try {
            Cliente clienteSave = clienteService.saveCliente(clienteDTO);
            clienteDTO = convertirDeEntidadADTO(clienteSave);
            return new ResponseEntity<>(
                MensajeResponse.builder()
                .mensaje("CLIENTE GUARDADO CON EXITO")
                .object(clienteDTO)
                .build()
                , HttpStatus.CREATED);

            } catch (DataAccessException exDt) {
            return new ResponseEntity<>(
                MensajeResponse.builder()
                .mensaje(exDt.getMessage())
                    .object(clienteDTO)
                    .build()
                , HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    //-------------------------------------------------------------------------------------------------
    @PostMapping("/clientes")
    public ResponseEntity<?> saveAllClientes(@Valid @RequestBody List<ClienteDTO> clientesDTO) {

        try {
            List<Cliente> clienteSaveAll = clienteService.saveAllClientes(clientesDTO); // Llama al servicio y retorna el resultado
            clientesDTO = new ArrayList<>(); // Crea una lista para almacenar los DTOs

            for (Cliente cliente : clienteSaveAll) {
                ClienteDTO clienteDTO = convertirDeEntidadADTO(cliente); // Convierte cada entidad a DTO
                clientesDTO.add(clienteDTO);
                }

                return new ResponseEntity<>(
                MensajeResponse.builder()
                .mensaje("CLIENTES GUARDADOS CON EXITO")
                .object(clientesDTO)
                .build()
                , HttpStatus.CREATED);
                
            } catch (DataAccessException exDt) {
            return new ResponseEntity<>(
                MensajeResponse.builder()
                    .mensaje(exDt.getMessage())
                    .object(clientesDTO)
                    .build()
                    , HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    //|||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||
    
    
    // GETMAPPING  ------------------------------------------------------------------------------------
    @GetMapping ("clientes")
    public ResponseEntity<?> findAllClientes() {
    
        try {
            List<Cliente> clientes = clienteService.findAllClientes(); // Obtiene las entidades del servicio

                if(clientes.isEmpty()){
                    return new ResponseEntity<>(
                        MensajeResponse.builder()
                            .mensaje("NO HAY REGISTROS")
                            .object(clientes)
                            .build()
                        , HttpStatus.OK);
                }

            List<ClienteDTO> clientesDTO = new ArrayList<>(); // Crea una lista para almacenar los DTOs
    
                for (Cliente cliente : clientes) {
                    ClienteDTO clienteDTO = convertirDeEntidadADTO(cliente); // Convierte cada entidad a DTO
                    clientesDTO.add(clienteDTO);
                }
    
            return new ResponseEntity<>(
                MensajeResponse.builder()
                    .mensaje("BUSQUEDA EXITOSA")
                    .object(clientesDTO) // Retorna la lista de DTOs
                    .build()
                , HttpStatus.OK);
            
        } catch (DataAccessException exDt) {
            return new ResponseEntity<>(
                MensajeResponse.builder()
                    .mensaje(exDt.getMessage())
                    .object(null)
                    .build()
                , HttpStatus.INTERNAL_SERVER_ERROR);
        }        
    }
    
    //-------------------------------------------------------------------------------------------------
    @GetMapping ("cliente/{id}")
    public ResponseEntity<?> findClienteById(@PathVariable Long id){
    
        try {
            Cliente cliente = clienteService.findClienteById(id); // Obtiene la entidad del servicio
            ClienteDTO clienteDTO = convertirDeEntidadADTO(cliente); // Convierte la entidad a DTO
            return new ResponseEntity<>(
                MensajeResponse.builder()
                    .mensaje("BUSQUEDA EXITOSA")
                    .object(clienteDTO) // Retorna la lista de DTO
                    .build()
                , HttpStatus.OK);
    
        } catch (DataAccessException exDt) {
            return new ResponseEntity<>(
                MensajeResponse.builder()
                    .mensaje(exDt.getMessage())
                    .object(null)
                    .build()
                , HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    //-------------------------------------------------------------------------------------------------
    @GetMapping ("clientes/{nombre}")
    public ResponseEntity<?> findClientesByName(@PathVariable String nombre){
    
        try {
            List<Cliente> clientes = clienteService.findClientesByNombreLike(nombre); // Obtiene las entidades del servicio
            
                if(clientes.isEmpty()){
                    return new ResponseEntity<>(
                        MensajeResponse.builder()
                            .mensaje("NO HAY REGISTROS")
                            .object(clientes)
                            .build()
                        , HttpStatus.OK);
                }            

            List<ClienteDTO> clientesDTO = new ArrayList<>(); // Crea una lista para almacenar los DTOs
    
                for (Cliente cliente : clientes) {
                    ClienteDTO clienteDTO = convertirDeEntidadADTO(cliente); // Convierte cada entidad a DTO
                    clientesDTO.add(clienteDTO);
                }
    
            return new ResponseEntity<>(
                MensajeResponse.builder()
                    .mensaje("BUSQUEDA EXITOSA")
                    .object(clientesDTO) // Retorna la lista de DTOs
                    .build()
                , HttpStatus.OK);
    
        } catch (DataAccessException exDt) {
            return new ResponseEntity<>(
                MensajeResponse.builder()
                    .mensaje(exDt.getMessage())
                    .object(null)
                    .build()
                , HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    //-------------------------------------------------------------------------------------------------
    @GetMapping ("clientes/buscar")
    public ResponseEntity<?> findClientesByNameOrLastName(
                                                @RequestParam(value = "nombre", required = false) String nombre,
                                                @RequestParam(value = "apellido", required = false) String apellido){
        try {
            List<Cliente> clientes = clienteService.findClientesByNameOrLastName(nombre, apellido); // Obtiene las entidades del servicio
            
                if(clientes.isEmpty()){
                    return new ResponseEntity<>(
                        MensajeResponse.builder()
                            .mensaje("NO HAY REGISTROS")
                            .object(clientes)
                            .build()
                        , HttpStatus.OK);
                }            
            
            List<ClienteDTO> clientesDTO = new ArrayList<>(); // Crea una lista para almacenar los DTOs
    
                for (Cliente cliente : clientes) {
                    ClienteDTO clienteDTO = convertirDeEntidadADTO(cliente); // Convierte cada entidad a DTO
                    clientesDTO.add(clienteDTO);
                }
    
            return new ResponseEntity<>(
                MensajeResponse.builder()
                    .mensaje("BUSQUEDA EXITOSA")
                    .object(clientesDTO) // Retorna la lista de DTOs
                    .build()
                , HttpStatus.OK);
    
        } catch (DataAccessException exDt) {
            return new ResponseEntity<>(
                MensajeResponse.builder()
                    .mensaje(exDt.getMessage())
                    .object(null)
                    .build()
                , HttpStatus.INTERNAL_SERVER_ERROR);
        }        
    }
    //|||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||
    
    
    // PUTMAPPING  ------------------------------------------------------------------------------------
    @PutMapping ("cliente")
    public ResponseEntity<?> updateCliente(@Valid @RequestBody ClienteDTO clienteDTO){

        try {
            Cliente clienteUpdate = clienteService.updateCliente(clienteDTO);  // Llama al servicio
            clienteDTO = convertirDeEntidadADTO(clienteUpdate);
            return new ResponseEntity<>(
                MensajeResponse.builder()
                    .mensaje("CLIENTE ACTUALIZADO CON EXITO")
                    .object(clienteDTO)
                    .build()
                , HttpStatus.CREATED);       

        } catch (DataAccessException exDt) {
            return new ResponseEntity<>(
                MensajeResponse.builder()
                    .mensaje(exDt.getMessage())
                    .object(clienteDTO)
                    .build()
                , HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PutMapping ("cliente/{id}")
    public ResponseEntity<?> updateCliente(@PathVariable Long id, 
                                           @Valid @RequestBody ClienteDTO clienteDTO){

        try {
            Cliente clienteUpdate = clienteService.updateCliente(id, clienteDTO);  // Llama al servicio
            clienteDTO = convertirDeEntidadADTO(clienteUpdate);
            return new ResponseEntity<>(
                MensajeResponse.builder()
                    .mensaje("CLIENTE ACTUALIZADO CON EXITO")
                    .object(clienteDTO)
                    .build()
                , HttpStatus.CREATED);       

        } catch (DataAccessException exDt) {
            return new ResponseEntity<>(
                MensajeResponse.builder()
                    .mensaje(exDt.getMessage())
                    .object(clienteDTO)
                    .build()
                , HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    //|||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||
    
        
    // DELETEMAPPING  ---------------------------------------------------------------------------------
    @DeleteMapping("cliente/{id}")
    public ResponseEntity<?> deleteCliente(@PathVariable Long id){

        try {
            Cliente clienteDelete = clienteService.findClienteById(id);
            clienteService.delete(clienteDelete);
            return new ResponseEntity<>(clienteDelete, HttpStatus.NO_CONTENT);

        } catch (DataAccessException exDt) {
            return new ResponseEntity<>(
                MensajeResponse.builder()
                    .mensaje(exDt.getMessage())
                    .object(null)
                    .build()
                , HttpStatus.INTERNAL_SERVER_ERROR);
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