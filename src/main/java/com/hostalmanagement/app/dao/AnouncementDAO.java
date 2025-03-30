package com.hostalmanagement.app.dao;

import java.util.List;

import com.hostalmanagement.app.model.Anouncement;

public interface AnouncementDAO {
    Anouncement findById(Long id);
    List<Anouncement> getAllAnouncements();
    List<Anouncement> findByIdGreaterThan(long id);

    void save(Anouncement anouncement);
    void update(Anouncement anouncement);
    void delete(Long id);
}
