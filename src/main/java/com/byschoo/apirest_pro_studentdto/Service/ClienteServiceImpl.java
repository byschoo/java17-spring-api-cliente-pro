package com.byschoo.apirest_pro_studentdto.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.byschoo.apirest_pro_studentdto.Model.Cliente;
import com.byschoo.apirest_pro_studentdto.Repository.iClienteRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ObjectNode;


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

    // POSTMAPPING
    @Transactional
    @Override
    public Cliente save(Cliente cliente) { // El método "save" guarda y actualiza. No es necesario un método update.
        return clienteRepository.save(cliente);
    }

    @Transactional
    public List<Cliente> saveAll(List<Cliente> clientes){  
        return(List<Cliente>) clienteRepository.saveAll(clientes);
    }
    //-----------------------------------------


    // GETMAPPING
    @Transactional(readOnly = true) // Toda transacción de consulta debe ser de solo lectura.
    @Override
    public List<Cliente> findAll() {
        return (List<Cliente>) clienteRepository.findAll();
    }


    @Transactional(readOnly = true) // Toda transacción de consulta debe ser de solo lectura.
    @Override
    public Cliente findById(Long id) {
        return clienteRepository.findById(id).orElseThrow( 
            () -> new RuntimeException("El cliente con el " + id + " no fue encontrado."));
    }


    @Transactional(readOnly = true) // Toda transacción de consulta debe ser de solo lectura.
    @Override
    public List<Cliente> findByNombreLike(String nombre){
        return (List<Cliente>) clienteRepository.findByNombreLike(nombre);
    }
    
    
    @Transactional(readOnly = true) // Toda transacción de consulta debe ser de solo lectura.
    @Override
    public List<Cliente> findByNameOrLastName(String nombre, String apellido){
        return (List<Cliente>) clienteRepository.findByNameOrLastName(nombre, apellido);
    }    
    //-----------------------------------------


    // DELETEMAPPING
    @Transactional
    @Override
    public String delete(Long id) {
        Cliente clienteDelete = clienteRepository.findById(id).orElseThrow(
            () -> new RuntimeException("El cliente con el " + id + " no fue encontrado."));

        clienteRepository.delete(clienteDelete);

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

            // 1. Crea un nodo raíz para el JSON
            ObjectNode rootNode = objectMapper.createObjectNode();

            // 2. Convierte el objeto Cliente a JSON y añádelo al nodo raíz bajo la clave "cliente"
            String clienteJson = objectMapper.writeValueAsString(clienteDelete);
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
    //-----------------------------------------

}
