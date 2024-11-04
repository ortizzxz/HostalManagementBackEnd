package com.hostalmanagement.app.daoimpl;

import java.util.List;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import com.hostalmanagement.app.dao.UsuarioDAO;
import com.hostalmanagement.app.model.Usuario;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@Repository
public class UsuarioDAOImpl implements UsuarioDAO{

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public Usuario findById(Long id) {
        return entityManager.find(Usuario.class, id);
    }

    @Override
    public List<Usuario> findAll(){
        return entityManager.createQuery("FROM Usuario u", Usuario.class)
                .getResultList();
    }

    @Override
    public List<Usuario> findByApellido(final String apellido) {
        return entityManager.createQuery("FROM Usuario u WHERE u.apellido like :nombre", Usuario.class)
                .setParameter("apellido", apellido)
                .getResultList();
    }

    @Override
    public Usuario findByEmail(final String email) {
        return entityManager.createQuery("FROM Usuario u where u.email like :email", Usuario.class)
                .setParameter("email", email)
                .getSingleResult();
    }

    @Override
    public List<Usuario> findByRol(final String rol) {
        return entityManager.createQuery("FROM Usuario u where u.rol like rol", Usuario.class)
                .setParameter("rol", rol)
                .getResultList();
    }

    @Override
    @Transactional
    public void save(Usuario usuario) {
        // 
        String hashedPassword = passwordEncoder.encode(usuario.getPassword());
        usuario.setPassword(hashedPassword);
        // 
        entityManager.persist(hashedPassword);
    }

    @Override
    @Transactional
    public void update(Usuario usuario) {
        Usuario currentUser = findById(usuario.getId());
    
        if (usuario.getPassword() == null || usuario.getPassword().isEmpty()) { // si no se cambia la contraseña 
            usuario.setPassword(currentUser.getPassword());
        } else {
            usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        }
    }

    @Override
    @Transactional
    public void remove(Long id) {
        Usuario usuario = findById(id);
        if (usuario != null){
            entityManager.remove(usuario);
        }
    }
    
}
