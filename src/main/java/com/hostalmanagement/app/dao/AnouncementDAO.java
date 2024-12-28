package com.hostalmanagement.app.dao;

import java.util.List;

import com.hostalmanagement.app.model.Anouncement;

public interface AnouncementDAO {
    Anuncio findById(Long id);
    List<Anuncio> getAllAnouncements();

    void save(Anouncement anouncement);
    void update(Anouncement anouncement);
    void delete(Long id);
}
