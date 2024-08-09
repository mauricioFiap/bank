package br.com.fiap.autenticacao.service;

import br.com.fiap.autenticacao.entity.User;

public interface UserService {
    public User authenticate(String username, String password);
    public void saveUser(User user);
    public void deleteAll();
}
