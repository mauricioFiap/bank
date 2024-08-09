package br.com.fiap.registrocliente.service;

import br.com.fiap.registrocliente.entity.Cliente;

import java.util.List;

public interface ClienteService {
    List<Cliente> findAll();
    Cliente save(Cliente cliente) throws Exception;
    void deleteById(Long id) throws Exception;

}
