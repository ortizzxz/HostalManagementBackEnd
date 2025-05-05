CREATE TABLE anouncement (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    content TEXT NOT NULL,
    post_date TIMESTAMP NOT NULL,
    expiration_date TIMESTAMP NOT NULL
);
CREATE TABLE bill (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    issue_date TIMESTAMP NOT NULL,
    total_amount DOUBLE NOT NULL,
    state ENUM('PENDIENTE', 'PAGADA', 'CANCELADA') NOT NULL,
    id_reserva BIGINT UNIQUE,
    CONSTRAINT fk_bill_reservation FOREIGN KEY (id_reserva) REFERENCES reservation(id)
);

CREATE TABLE check_in_out (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    id_reserva BIGINT UNIQUE,
    in_date TIMESTAMP NOT NULL,
    out_date TIMESTAMP NOT NULL,
    CONSTRAINT fk_checkinout_reservation FOREIGN KEY (id_reserva) REFERENCES reservation(id)
);

CREATE TABLE guest (
    nif VARCHAR(50) PRIMARY KEY,
    name VARCHAR(255),
    lastname VARCHAR(255),
    email VARCHAR(255) UNIQUE,
    phone VARCHAR(50),
    register_date TIMESTAMP NOT NULL
);

CREATE TABLE guest_reservation (
    nif_huesped VARCHAR(50),
    id_reserva BIGINT,
    PRIMARY KEY (nif_huesped, id_reserva),
    CONSTRAINT fk_guest FOREIGN KEY (nif_huesped) REFERENCES guest(nif),
    CONSTRAINT fk_reservation FOREIGN KEY (id_reserva) REFERENCES reservation(id)
);

CREATE TABLE inventory (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    item VARCHAR(255) NOT NULL,
    amount INT NOT NULL,
    warning_level INT NOT NULL,
    last_update TIMESTAMP NOT NULL
);

CREATE TABLE reserva (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    id_habitacion BIGINT NOT NULL,
    in_date DATE NOT NULL,
    out_date DATE NOT NULL,
    state ENUM('CONFIRMADA', 'CANCELADA', 'COMPLETADA') NOT NULL,
    CONSTRAINT fk_reservation_room FOREIGN KEY (id_habitacion) REFERENCES room(id)
);

CREATE TABLE room (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    number INT NOT NULL,
    type VARCHAR(255) NOT NULL,
    capacity INT NOT NULL,
    base_rate DOUBLE NOT NULL,
    state ENUM('DISPONIBLE', 'OCUPADO', 'EN_LIMPIEZA', 'MANTENIMIENTO') NOT NULL
);
CREATE TABLE user (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    lastname VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    rol ENUM('admin', 'recepcion', 'limpieza', 'mantenimiento', 'unknown') NOT NULL
);

CREATE TABLE notifications (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    table_name VARCHAR(255) NOT NULL, -- Name of the table where the change occurred
    event_type ENUM('INSERT', 'UPDATE', 'DELETE') NOT NULL, -- Type of change
    record_id BIGINT NOT NULL, -- ID of the record that was changed
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP -- Timestamp of the event
);

-- Triggers For User


ALTER TABLE reservation
MODIFY COLUMN in_date TIMESTAMP NOT NULL,
MODIFY COLUMN out_date TIMESTAMP NOT NULL;
