package br.com.fiap.geracartao.service;


import br.com.fiap.geracartao.entity.User;

public interface UserService {
    public User authenticate(String username, String password);
    public void saveUser(User user);
    public void deleteAll();
}
