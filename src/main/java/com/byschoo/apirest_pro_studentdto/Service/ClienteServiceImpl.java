package com.byschoo.apirest_pro_studentdto.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.byschoo.apirest_pro_studentdto.Model.Cliente;
import com.byschoo.apirest_pro_studentdto.Repository.iClienteRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;


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
            ObjectMapper objectMapper = new ObjectMapper(); // ObjectMapper es la clase de Jackson que se encarga de convertir objetos Java a JSON y viceversa.
            objectMapper.enable(SerializationFeature.INDENT_OUTPUT); // Para el formato JSON legible
            String clienteJson = objectMapper.writeValueAsString(clienteDelete); // Convertir el objeto Cliente a una cadena JSON.
            return "EL CLIENTE:\n\n" + clienteJson + "\n\n!! HA SIDO BORRADO EXITOSAMENTE !!";
        } catch (Exception e) {
            return "Error al convertir el cliente a JSON: " + e.getMessage();
        }
    }
    //-----------------------------------------

}
