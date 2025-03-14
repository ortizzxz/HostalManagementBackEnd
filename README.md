# Hostal Management System
El sistema de gestión de hostales es una aplicación backend desarrollada con Spring Boot. Permite gestionar las reservas de habitaciones, el inventario, el registro de huéspedes, los usuarios, y las facturas, entre otras funciones. Esta aplicación está pensada para facilitar la administración de un hostal, o pequeños hoteles, mejorando la eficiencia en las operaciones diarias.

## Tecnologías Utilizadas
- Spring Boot: Framework de Java para el desarrollo de aplicaciones backend.
- JPA (Java Persistence API): Para la persistencia de datos en bases de datos relacionales.
- Hibernate: Para la gestión de entidades JPA y la creación de las tablas SQL.
- MySQL: Sistema de gestión de bases de datos relacionales.
- Java 17+: Lenguaje de programación utilizado para el backend.

## Características Principales
1. Gestión de Habitaciones
- Creación, edición y eliminación de habitaciones.
- Asignación de tipo de habitación (Disponible, Ocupada, En Limpieza, Mantenimiento).
- Asignación de capacidad y tarifa base.

2. Gestión de Reservas
- Los huéspedes pueden hacer reservas de habitaciones.
- Registro de fecha de entrada y salida de cada reserva.
- Estado de la reserva (Confirmada, Cancelada, Completada).
- Vínculo con el CheckIn/CheckOut.

3. Gestión de Huéspedes
- Los huéspedes tienen un NIF (número de identificación fiscal), nombre, apellido, email, teléfono y fecha de registro.
- Los huéspedes pueden estar relacionados con múltiples reservas.

4. Gestión de Facturas
- Generación de facturas asociadas a las reservas.
- Control del estado de la factura (Pendiente, Pagada, Cancelada).

5. Gestión de Usuarios
- Creación y administración de usuarios con roles específicos (Admin, Recepcionista, Limpieza, Mantenimiento).
- Los usuarios tienen nombre, apellido, email, contraseña y rol.

6. Inventario
- Gestión del inventario de suministros del hostal.
- Control de niveles de inventario y alertas de bajo stock.

7. CheckIn/CheckOut
- Registro de la fecha y hora de entrada y salida de los huéspedes.
- Vínculo con las reservas de los huéspedes.

## Modelo de Datos
La base de datos se compone de las siguientes tablas principales:

- User: Tabla para gestionar los usuarios del sistema (administradores, recepcionistas, personal de limpieza, etc.).
- Room: Tabla para gestionar las habitaciones del hostal, incluyendo su tipo, capacidad, tarifa base y estado.
- Reservation: Tabla para registrar las reservas de las habitaciones, asociadas con los huéspedes.
- Guest: Tabla para gestionar la información personal de los huéspedes, incluyendo nombre, apellido, email, teléfono y fecha de registro.
- GuestReservation: Tabla intermedia para gestionar las relaciones entre huéspedes y reservas.
- Bill: Tabla para gestionar las facturas asociadas a las reservas.
- Inventory: Tabla para gestionar los artículos del inventario del hostal, con niveles de stock y alertas.

## Requisitos Previos
Antes de ejecutar la aplicación, asegúrate de tener los siguientes requisitos instalados:

- Java 17+: Para ejecutar la aplicación Spring Boot.
- MySQL: Base de datos relacional.
- Maven: Para la gestión de dependencias y construcción del proyecto.

## Configuración
1. Clona el repositorio:
```
git clone https://github.com/tu-usuario/hostal-management-app.git
cd hostal-management-app
```

2. Configura la base de datos MySQL:
- Crea una base de datos llamada hostal_management en tu servidor MySQL.
- Configura los detalles de conexión en el archivo src/main/resources/application.properties:

```
spring.datasource.url=jdbc:mysql://localhost:3306/hostal_management
spring.datasource.username=usuario
spring.datasource.password=contraseña
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.jpa.hibernate.ddl-auto=update
spring.jpa.database-platform=org.hibernate.dialect.MySQL8Dialect
```

3. Ejecuta la aplicación.

4. Accede a la API:

La aplicación se ejecutará por defecto en http://localhost:8081. (O el puerto que hayas configurado)
Puedes hacer peticiones REST a las siguientes rutas (ejemplos):
- GET /users: Obtener todos los usuarios.
- POST /users: Crear un nuevo usuario.
- GET /rooms: Obtener todas las habitaciones.
- POST /reservations: Crear una nueva reserva.

## Estructura del Proyecto
```
src
├── main
│   ├── java
│   │   └── com
│   │       └── hostalmanagement
│   │           └── app
│   │               ├── model      # Clases de Entidad
│   │               ├── controller # Controladores REST
│   │               ├── service    # Lógica de negocio
│   │               └── repository # Interfaces JPA
│   ├── resources
│   │   └── application.properties # Configuración de la base de datos
│   └── test
│       └── java
│           └── com
│               └── hostalmanagement
│                   └── app
│                       └── service # Pruebas unitarias
```

## Contribuciones
¡Las contribuciones son bienvenidas! Si deseas mejorar la aplicación, puedes hacer un fork del repositorio, realizar tus cambios y luego enviar un Pull Request. Asegúrate de que tus cambios no rompan la funcionalidad existente y estén cubiertos con pruebas.
