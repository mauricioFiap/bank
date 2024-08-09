package br.com.fiap.registrocliente.service;

import br.com.fiap.registrocliente.entity.Cliente;
import br.com.fiap.registrocliente.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteServiceImpl implements ClienteService {

    private final ClienteRepository clienteRepository;

    @Autowired
    public ClienteServiceImpl(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public List<Cliente> findAll() {
        return clienteRepository.findAll();
    }

    public Cliente save(Cliente cliente) throws Exception {
        if(cliente == null) {
            throw new IllegalArgumentException("Cliente não pode ser nulo");
        }
        return clienteRepository.save(cliente);
    }

    public void deleteById(Long id) throws Exception {
        if (id == null) {
            throw new IllegalArgumentException("Id não pode ser nulo");
        }
        clienteRepository.deleteById(id);
    }
}