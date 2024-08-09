package br.com.fiap.registropagamento.service;


import br.com.fiap.registropagamento.entity.User;

public interface UserService {
    public User authenticate(String username, String password);
    public void saveUser(User user);
    public void deleteAll();
}
