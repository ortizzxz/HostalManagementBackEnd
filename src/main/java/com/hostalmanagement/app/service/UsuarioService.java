package com.hostalmanagement.app.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hostalmanagement.app.DTO.UsuarioDTO;
import com.hostalmanagement.app.dao.UsuarioDAO;
import com.hostalmanagement.app.model.Usuario;
import com.hostalmanagement.app.model.Usuario.RolEnum;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioDAO usuarioDAO;

    public UsuarioDTO findUsuarioById(Long id) {
        Usuario usuario = usuarioDAO.findById(id);
        return toDTO(usuario);
    }

    public List<UsuarioDTO> findAllUsuarios() {
        List<Usuario> usuarios = usuarioDAO.findAll();
        return usuarios.stream().map(this::toDTO).collect(Collectors.toList());
    }

    private UsuarioDTO toDTO(Usuario usuario) {
        String rolName = usuario.getRol() != null ? usuario.getRol().name().toUpperCase() : "UNKNOWN";
        return new UsuarioDTO(usuario.getId(), usuario.getNombre(), usuario.getApellido(), usuario.getEmail(),
                rolName);
    }

    private Usuario toEntity(UsuarioDTO usuarioDTO) {
        return new Usuario(
                usuarioDTO.getNombre(),
                usuarioDTO.getApellido(),
                usuarioDTO.getEmail(),
                null,
                RolEnum.valueOf(usuarioDTO.getRol()));
    }

    public UsuarioDTO crearUsuario(UsuarioDTO usuarioDTO) {
        Usuario usuario = toEntity(usuarioDTO);
        usuarioDAO.save(usuario);
        return toDTO(usuario);
    }

    public UsuarioDTO actualizarUsuario(Long id, UsuarioDTO usuarioDTO) {
        Usuario usuarioExistente = usuarioDAO.findById(id);
        if (usuarioExistente != null) {
            usuarioExistente.setNombre(usuarioDTO.getNombre());
            usuarioExistente.setApellido(usuarioDTO.getApellido());
            usuarioExistente.setEmail(usuarioDTO.getEmail());
            usuarioExistente.setRol(RolEnum.valueOf(usuarioDTO.getRol())); // Asigna el nuevo rol
            
            usuarioDAO.update(usuarioExistente);
            return toDTO(usuarioExistente);
        }
        return null;
    }

    public boolean eliminarUsuario(Long id) {
        Usuario usuarioExistente = usuarioDAO.findById(id);
        if (usuarioExistente != null) {
            usuarioDAO.remove(id);
            return true;
        }
        return false;
    }

}
