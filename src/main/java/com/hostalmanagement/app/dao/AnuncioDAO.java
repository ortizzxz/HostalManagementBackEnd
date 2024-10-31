package com.hostalmanagement.app.dao;

import java.util.List;

import com.hostalmanagement.app.model.Anuncio;

public interface AnuncioDAO {
    Anuncio findById(Long id);
    List<Anuncio> getAllAnuncios();

    void save(Anuncio anuncio);
    void update(Anuncio anuncio);
    void delete(Long id);
}
