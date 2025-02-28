package com.byschoo.apirest_pro_clientesdto.Repository;

import static org.assertj.core.api.Assertions.assertThat;


import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import com.byschoo.apirest_pro_clientesdto.Model.Cliente;


@DataJpaTest
public class iClienteRepositoryTest {

    @Autowired
    private iClienteRepository clienteRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    private Cliente cliente1;
    private Cliente cliente2;

    @BeforeEach
void setUp() {
    // Datos de prueba (SIN IDs)
    cliente1 = Cliente.builder()
        .nombre("José")
        .apellido("Agustín")
        .correo("josevaneg@gamil.com")
        .edad(51)
        .build();

    cliente2 = Cliente.builder()
        .nombre("María")
        .apellido("Pérez")
        .correo("mariaperez@example.com")
        .edad(25)
        .build();

    testEntityManager.persist(cliente1);
    testEntityManager.persist(cliente2);
    testEntityManager.flush(); // Importante: fuerza la persistencia antes de los tests

    // Recupera los clientes CON IDs GENERADOS
    cliente1 = testEntityManager.find(Cliente.class, cliente1.getId()); // Actualiza cliente1 con el ID real
    cliente2 = testEntityManager.find(Cliente.class, cliente2.getId()); // Actualiza cliente2 con el ID real
}

    @Test
    void testFindByNameOrLastName() {
        List<Cliente> clientes = clienteRepository.findByNameOrLastName("José", "Agustín");
        assertThat(clientes).isNotEmpty();
        assertThat(clientes).hasSize(1);
        assertThat(clientes.get(0).getNombre()).isEqualTo("José");
        assertThat(clientes.get(0).getApellido()).isEqualTo("Agustín");

        clientes = clienteRepository.findByNameOrLastName("Agustín", "José"); // Prueba con los parámetros invertidos
        assertThat(clientes).isNotEmpty();
        assertThat(clientes).hasSize(1);
        assertThat(clientes.get(0).getNombre()).isEqualTo("José");
        assertThat(clientes.get(0).getApellido()).isEqualTo("Agustín");

        clientes = clienteRepository.findByNameOrLastName("José", "Pérez"); // Prueba con nombre y apellido diferentes
        assertThat(clientes).hasSize(0); // No debería encontrar nada

        clientes = clienteRepository.findByNameOrLastName("os", "st"); // Prueba con parte del nombre y apellido
        assertThat(clientes).hasSize(1); // Debería encontrar a José Agustín

    }

    @Test
    void testFindByNombresLike() {
        List<Cliente> clientes = clienteRepository.findByNombresLike("José");
        assertThat(clientes).isNotEmpty();
        assertThat(clientes).hasSize(1);
        assertThat(clientes.get(0).getNombre()).isEqualTo("José");

        clientes = clienteRepository.findByNombresLike("os"); // Prueba con parte del nombre
        assertThat(clientes).hasSize(1);
        assertThat(clientes.get(0).getNombre()).isEqualTo("José");

        clientes = clienteRepository.findByNombresLike("Mar"); // Prueba con parte del nombre
        assertThat(clientes).hasSize(1);
        assertThat(clientes.get(0).getNombre()).isEqualTo("María");

    }

    @Test
    void testFindById() {
        Optional<Cliente> clienteOptional = clienteRepository.findById(cliente1.getId()); // Usa el ID real de cliente1
        assertThat(clienteOptional).isPresent();
        Cliente clienteEncontrado = clienteOptional.get();
        assertThat(clienteEncontrado.getNombre()).isEqualTo("José");
    }


    // Otros tests para los métodos del repositorio...
}