package br.com.fiap.registrocliente.controller;

// ClienteController.java
import br.com.fiap.registrocliente.entity.Cliente;
import br.com.fiap.registrocliente.request.ClienteRequest;
import br.com.fiap.registrocliente.response.ClienteResponse;
import br.com.fiap.registrocliente.service.ClienteServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cliente")
public class ClienteController {

    @Autowired
    private ClienteServiceImpl clienteService;

    @PostMapping
    public ResponseEntity<ClienteResponse> registrarCliente(@RequestBody ClienteRequest clienteRequest) {
        try {
            Cliente novoCliente = clienteService.save(Cliente.from(clienteRequest));
            ClienteResponse clienteResponse = new ClienteResponse(novoCliente.getId());
            return ResponseEntity.ok(clienteResponse);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}