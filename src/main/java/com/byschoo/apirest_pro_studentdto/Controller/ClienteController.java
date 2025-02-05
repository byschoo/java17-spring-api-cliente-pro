package com.byschoo.apirest_pro_studentdto.Controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.byschoo.apirest_pro_studentdto.DTO.ClienteDTO;
import com.byschoo.apirest_pro_studentdto.Model.Cliente;
import com.byschoo.apirest_pro_studentdto.Service.iClienteService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ObjectNode;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1")
public class ClienteController {

    @Autowired
    private iClienteService clienteService;

    // POSTMAPPING ---------------------------------------------------------------------------------
    @PostMapping("/clientes")
    @ResponseStatus(HttpStatus.CREATED)
    public List<Cliente> saveAllClientes(@Valid @RequestBody List<ClienteDTO> clientesDTO) {
        return clienteService.saveAll(clientesDTO); // Llama al servicio y retorna el resultado
    }
    
    
    @PostMapping("cliente")
    @ResponseStatus(HttpStatus.CREATED)
    public Cliente saveCliente(@Valid @RequestBody ClienteDTO clienteDTO){
        return clienteService.save(clienteDTO);
    }
    //----------------------------------------------------------------------------------------------
    
    
    // PUTMAPPING  ---------------------------------------------------------------------------------
    @PutMapping ("cliente")
    @ResponseStatus(HttpStatus.CREATED)
    public Cliente updateCliente(@Valid @RequestBody ClienteDTO clienteDTO){
        return clienteService.save(clienteDTO);        
    }
    //----------------------------------------------------------------------------------------------
    
    
    // GETMAPPING  ---------------------------------------------------------------------------------
    @GetMapping ("clientes")
    @ResponseStatus(HttpStatus.OK)
    public List<ClienteDTO> findAllClientes() {
        List<Cliente> clientes = clienteService.findAll(); // Obtiene las entidades del servicio
        List<ClienteDTO> clientesDTO = new ArrayList<>(); // Crea una lista para almacenar los DTOs

            for (Cliente cliente : clientes) {
                ClienteDTO clienteDTO = convertirDeEntidadADTO(cliente); // Convierte cada entidad a DTO
                clientesDTO.add(clienteDTO);
            }

        return clientesDTO; // Retorna la lista de DTOs
    }
    
    @GetMapping ("cliente/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ClienteDTO findClienteById(@PathVariable Long id){
        Cliente cliente = clienteService.findById(id); // Obtiene la entidad del servicio
        ClienteDTO clienteDTO = convertirDeEntidadADTO(cliente); // Convierte la entidad a DTO
        return clienteDTO;
    }
    
    @GetMapping ("clientes/{nombre}")
    @ResponseStatus(HttpStatus.OK)
    public List<ClienteDTO> findClientesByName(@PathVariable String nombre){
        List<Cliente> clientes = clienteService.findByNombreLike(nombre); // Obtiene las entidades del servicio
        List<ClienteDTO> clientesDTO = new ArrayList<>(); // Crea una lista para almacenar los DTOs

            for (Cliente cliente : clientes) {
                ClienteDTO clienteDTO = convertirDeEntidadADTO(cliente); // Convierte cada entidad a DTO
                clientesDTO.add(clienteDTO);
            }
        return clientesDTO; // Retorna la lista de DTOs
    }
    
    @GetMapping ("clientes/buscar")
    @ResponseStatus(HttpStatus.OK)
    public List<ClienteDTO> findClientesByNameOrLastName(
        @RequestParam(value = "nombre", required = false) String nombre,
        @RequestParam(value = "apellido", required = false) String apellido){                        
            List<Cliente> clientes = clienteService.findByNameOrLastName(nombre, apellido); // Obtiene las entidades del servicio
            List<ClienteDTO> clientesDTO = new ArrayList<>(); // Crea una lista para almacenar los DTOs

            for (Cliente cliente : clientes) {
                ClienteDTO clienteDTO = convertirDeEntidadADTO(cliente); // Convierte cada entidad a DTO
                clientesDTO.add(clienteDTO);
            }
        return clientesDTO; // Retorna la lista de DTOs
    }
    //----------------------------------------------------------------------------------------------
        
        
    // DELETEMAPPING  ------------------------------------------------------------------------------
    @DeleteMapping("cliente/{id}")
    @ResponseStatus(HttpStatus.OK)
    public String deleteCliente(@PathVariable Long id){

        try {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

        // 1. Crea un nodo raíz para el JSON
        ObjectNode rootNode = objectMapper.createObjectNode();

        // 2. Convierte el objeto Cliente a JSON y añádelo al nodo raíz bajo la clave "cliente"
        String clienteJson = objectMapper.writeValueAsString(clienteService.delete(id));
        JsonNode clienteNode = objectMapper.readTree(clienteJson); // Parsea el JSON del cliente
        rootNode.set("cliente", clienteNode);

        // 3. Añade el mensaje al nodo raíz
        rootNode.put("mensaje", "!! SE HA BORRADO EXITOSAMENTE !!");

        // 4. Convierte el nodo raíz a una cadena JSON
        String respuestaJson = objectMapper.writeValueAsString(rootNode);

        return respuestaJson;

        } catch (Exception e) {
            return "Error al convertir el cliente a JSON: " + e.getMessage();
        }
    }
    //----------------------------------------------------------------------------------------------


    // Método para convertir
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
}