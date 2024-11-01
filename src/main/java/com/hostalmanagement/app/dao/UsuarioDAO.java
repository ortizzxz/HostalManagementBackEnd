package com.hostalmanagement.app.dao;

import java.util.List;

import com.hostalmanagement.app.model.Usuario;

public interface UsuarioDAO {
    Usuario findById(Long id);
    List<Usuario> findByApellido(String apellido);
    Usuario findByEmail(String email);  
    List<Usuario> findByRol(String rol);

    void save(Usuario usuario);
    void update(Usuario usuario);
    void remove(Long id);
}
