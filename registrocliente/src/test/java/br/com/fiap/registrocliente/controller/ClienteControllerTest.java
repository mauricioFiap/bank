package br.com.fiap.registrocliente.controller;

import br.com.fiap.registrocliente.entity.Cliente;
import br.com.fiap.registrocliente.request.ClienteRequest;
import br.com.fiap.registrocliente.response.ClienteResponse;
import br.com.fiap.registrocliente.service.ClienteServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ClienteControllerTest {

    @Mock
    private ClienteServiceImpl clienteService;

    @InjectMocks
    private ClienteController clienteController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void registrarClienteReturnsOkWhenClienteIsSaved() throws Exception {
        ClienteRequest clienteRequest = new ClienteRequest();
        Cliente cliente = new Cliente();
        cliente.setId("1");
        when(clienteService.save(any(Cliente.class))).thenReturn(cliente);

        ResponseEntity<ClienteResponse> response = clienteController.registrarCliente(clienteRequest);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        //assertEquals(1L, response.getBody().getId());
        verify(clienteService, times(1)).save(any(Cliente.class));
    }

    @Test
    void registrarClienteReturnsInternalServerErrorWhenExceptionIsThrown() throws Exception {
        ClienteRequest clienteRequest = new ClienteRequest();
        when(clienteService.save(any(Cliente.class))).thenThrow(new RuntimeException());

        ResponseEntity<ClienteResponse> response = clienteController.registrarCliente(clienteRequest);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNull(response.getBody());
        verify(clienteService, times(1)).save(any(Cliente.class));
    }
}