INSERT INTO user (name, lastname, email, password, rol) VALUES
('Juan', 'Pérez', 'juan.perez@example.com', 'password123', 'admin'),
('María', 'Gómez', 'maria.gomez@example.com', 'password456', 'recepcion'),
('Luis', 'Rodríguez', 'luis.rodriguez@example.com', 'password789', 'limpieza'),
('Ana', 'Martínez', 'ana.martinez@example.com', 'password1011', 'mantenimiento'),
('Carlos', 'Sánchez', 'carlos.sanchez@example.com', 'password1213', 'unknown');

INSERT INTO user (name, lastname, email, password, rol) VALUES
('Prueba', 'Usuario', 'prueba@usuario.com', 'password1213', 'limpieza');

DELETE FROM anouncement ;

INSERT INTO `anouncement` (`title`, `content`, `post_date`, `expiration_date`)
VALUES ('Broadcast Funcionando De Nuevo', 'Contenido del anuncio de otro usuario en otra máquina.', '2025-03-29 19:30:00', '2025-04-29 10:00:00');

DELETE FROM anouncement;