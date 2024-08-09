package br.com.fiap.registrocliente.service;


import br.com.fiap.registrocliente.entity.Cliente;
import br.com.fiap.registrocliente.repository.ClienteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ClienteServiceImplTest {

    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private ClienteServiceImpl clienteService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findAllReturnsListOfClientes() {
        Cliente cliente1 = new Cliente();
        Cliente cliente2 = new Cliente();
        when(clienteRepository.findAll()).thenReturn(Arrays.asList(cliente1, cliente2));

        List<Cliente> clientes = clienteService.findAll();

        assertEquals(2, clientes.size());
        verify(clienteRepository, times(1)).findAll();
    }

    @Test
    void saveReturnsSavedCliente() throws Exception {
        Cliente cliente = new Cliente();
        when(clienteRepository.save(cliente)).thenReturn(cliente);

        Cliente savedCliente = clienteService.save(cliente);

        assertNotNull(savedCliente);
        verify(clienteRepository, times(1)).save(cliente);
    }

    @Test
    void deleteByIdDeletesCliente() throws Exception {
        Long id = 1L;
        doNothing().when(clienteRepository).deleteById(id);

        clienteService.deleteById(id);

        verify(clienteRepository, times(1)).deleteById(id);
    }

    @Test
    void findAllReturnsEmptyListWhenNoClientes() {
        when(clienteRepository.findAll()).thenReturn(Arrays.asList());

        List<Cliente> clientes = clienteService.findAll();

        assertTrue(clientes.isEmpty());
        verify(clienteRepository, times(1)).findAll();
    }

    @Test
    void saveThrowsExceptionWhenClienteIsNull() {
        assertThrows(IllegalArgumentException.class, () -> clienteService.save(null));
    }

    @Test
    void deleteByIdThrowsExceptionWhenIdIsNull() {
        assertThrows(IllegalArgumentException.class, () -> clienteService.deleteById(null));
    }
}
