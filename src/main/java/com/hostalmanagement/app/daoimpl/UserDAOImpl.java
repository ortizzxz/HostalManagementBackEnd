package com.hostalmanagement.app.daoimpl;

import java.util.List;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import com.hostalmanagement.app.dao.UserDAO;
import com.hostalmanagement.app.model.Tenant;
import com.hostalmanagement.app.model.User;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

@Repository
public class UserDAOImpl implements UserDAO {

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public User findById(Long id) {
        return entityManager.find(User.class, id);
    }

    @Override
    public List<User> findAll(Tenant tenant) {
        return entityManager.createQuery("FROM User u WHERE u.tenant like :tenant", User.class)
                .setParameter("tenant", tenant)
                .getResultList();
    }

    @Override
    public List<User> findByLastname(final String lastname) {
        return entityManager.createQuery("FROM User u WHERE u.apellido like :nombre", User.class)
                .setParameter("apellido", lastname)
                .getResultList();
    }

    @Override
    public User findByEmail(final String email) {
        return entityManager.createQuery("FROM User u where u.email like :email", User.class)
                .setParameter("email", email)
                .getSingleResult();
    }

    @Override
    public List<User> findByRol(final String rol) {
        return entityManager.createQuery("FROM User u where u.rol like rol", User.class)
                .setParameter("rol", rol)
                .getResultList();
    }

    @Override
    @Transactional
    public void save(User user) {
        //
        String hashedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashedPassword);
        //
        entityManager.persist(user);
    }

    private boolean isBCryptHash(String password) {
        return password != null
                && (password.startsWith("$2a$") || password.startsWith("$2b$") || password.startsWith("$2y$"));
    }

    @Transactional
    public void update(User user) {
        User currentUser = findById(user.getId());

        if (user.getPassword() == null || user.getPassword().isEmpty()) {
            user.setPassword(currentUser.getPassword());
        } else if (!isBCryptHash(user.getPassword())) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        entityManager.merge(user);
    }

    @Override
    @Transactional
    public void remove(Long id) {
        User user = findById(id);
        if (user != null) {
            entityManager.remove(user);
        }
    }

    @Override
    public User findByResetToken(String token) {
        try {
            return entityManager.createQuery("FROM User u WHERE u.resetToken = :token", User.class)
                    .setParameter("token", token)
                    .getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

}
