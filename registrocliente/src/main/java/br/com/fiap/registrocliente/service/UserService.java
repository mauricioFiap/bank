package br.com.fiap.registrocliente.service;

import br.com.fiap.registrocliente.entity.Cliente;
import br.com.fiap.registrocliente.entity.User;

import java.util.List;

public interface UserService {
    public User authenticate(String username, String password);
    public void saveUser(User user);
    public void deleteAll();
}
