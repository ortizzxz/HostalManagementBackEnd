package com.hostalmanagement.app.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hostalmanagement.app.model.Usuario;

@Repository
public interface UsuarioDAO {
    Usuario findById(Long id);
    List<Usuario> findAll();
    List<Usuario> findByApellido(String apellido);
    Usuario findByEmail(String email);  
    List<Usuario> findByRol(String rol);

    void save(Usuario usuario);
    void update(Usuario usuario);
    void remove(Long id);
}
