package com.byschoo.apirest_pro_studentdto.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.byschoo.apirest_pro_studentdto.Model.Cliente;
import com.byschoo.apirest_pro_studentdto.Service.iClienteService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1")
public class ClienteController {

    @Autowired
    private iClienteService clienteService;

    // POSTMAPPING
    @PostMapping("/clientes")
    public List<Cliente> saveAllClientes(@Valid @RequestBody List<Cliente> clientes) {
        return clienteService.saveAll(clientes);
    }

    @PostMapping ("cliente")
    public Cliente saveCliente(@Valid @RequestBody Cliente cliente){        
        return clienteService.save(cliente);        
    }
    //-----------------------------------------
    

    // PUTMAPPING
    @PutMapping ("cliente")
    public Cliente updateCliente(@Valid @RequestBody Cliente cliente){
        return clienteService.save(cliente);        
    }
    //-----------------------------------------
    

    // GETMAPPING
    @GetMapping ("clientes")
    public List<Cliente> findClientes(){
        return clienteService.findAll();
    }

    @GetMapping ("cliente/{id}")
    public Cliente findClienteById(@PathVariable Long id){
        return clienteService.findById(id); 
    }

    @GetMapping ("clientes/{nombre}")
    public List<Cliente> findClientesByName(@PathVariable String nombre){
        return clienteService.findByNombreLike(nombre);
    }
    
    @GetMapping ("clientes/buscar")
    public List<Cliente> findClientesByNameOrLastName(
        @RequestParam(value = "nombre", required = false) String nombre,
        @RequestParam(value = "apellido", required = false) String apellido){
        return clienteService.findByNameOrLastName(nombre, apellido);
    }
    //-----------------------------------------
    

    // DELETEMAPPING
    @DeleteMapping("cliente/{id}")
    public String deleteCliente(@PathVariable Long id){
        return clienteService.delete(id);        
    }    
}