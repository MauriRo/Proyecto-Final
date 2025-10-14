-- 1. Tabla empleados
CREATE TABLE empleados (
    id_empleado NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    dni_empleado NUMBER NOT NULL UNIQUE,
    nombre VARCHAR2(255) NOT NULL,
    apellido VARCHAR2(255) NOT NULL,
    email VARCHAR2(255) NOT NULL UNIQUE,
    password_hash VARCHAR2(255) NOT NULL,
    rol VARCHAR2(50) DEFAULT 'usuario' NOT NULL CHECK (rol IN ('usuario', 'admin')),
    celular VARCHAR2(255),
    activo NUMBER(1) DEFAULT 1,
    fecha_creacion DATE DEFAULT SYSDATE
);
/

-- 2. Tabla clientes
CREATE TABLE clientes (
    dni_cliente NUMBER PRIMARY KEY,
    nombre VARCHAR2(255) NOT NULL,
    apellido VARCHAR2(255) NOT NULL,
    email VARCHAR2(255),
    celular VARCHAR2(255),
    fecha_registro DATE DEFAULT SYSDATE
);
/

-- 3. Tabla habitaciones
CREATE TABLE habitaciones (
    id_habitacion NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    numero_habitacion VARCHAR2(10) NOT NULL UNIQUE,
    piso NUMBER NOT NULL,
    tipo VARCHAR2(50) NOT NULL CHECK (tipo IN ('Individual', 'Doble', 'Suite', 'Familiar')),
    capacidad NUMBER NOT NULL,
    descripcion VARCHAR2(255),
    precio_noche NUMBER(10,2) NOT NULL,
    estado VARCHAR2(50) DEFAULT 'Disponible' CHECK (estado IN ('Disponible', 'Ocupada', 'Mantenimiento', 'Limpieza'))
);
/

-- 4. Tabla reservas
CREATE TABLE reservas (
    id_reserva NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    id_habitacion NUMBER NOT NULL,
    dni_cliente NUMBER NOT NULL,
    id_empleado NUMBER NOT NULL,
    fecha_inicio DATE NOT NULL,
    fecha_fin DATE NOT NULL,
    cantidad_noches NUMBER NOT NULL,
    total_reserva NUMBER(10,2) NOT NULL,
    estado VARCHAR2(50) DEFAULT 'Pendiente' CHECK (estado IN ('Pendiente', 'Confirmada', 'Activa', 'Finalizada', 'Cancelada')),
    observaciones CLOB,
    fecha_creacion DATE DEFAULT SYSDATE,
    fecha_actualizacion DATE DEFAULT SYSDATE,
    
    CONSTRAINT fk_reserva_habitacion FOREIGN KEY (id_habitacion) REFERENCES habitaciones(id_habitacion),
    CONSTRAINT fk_reserva_cliente FOREIGN KEY (dni_cliente) REFERENCES clientes(dni_cliente),
    CONSTRAINT fk_reserva_empleado FOREIGN KEY (id_empleado) REFERENCES empleados(id_empleado)
);
/

-- 1. Insertar empleados
INSERT INTO empleados (dni_empleado, nombre, apellido, email, password_hash, rol, celular) 
VALUES (71234567, 'María', 'Gonzales', 'maria@hotel.com', 'hashed_password_123', 'admin', '987654321');
INSERT INTO empleados (dni_empleado, nombre, apellido, email, password_hash, rol, celular) 
VALUES (72345678, 'Carlos', 'López', 'carlos@hotel.com', 'hashed_password_456', 'usuario', '987654322');
INSERT INTO empleados (dni_empleado, nombre, apellido, email, password_hash, rol, celular) 
VALUES (73456789, 'Ana', 'Martínez', 'ana@hotel.com', 'hashed_password_789', 'usuario', '987654323');
COMMIT;
/

-- 2. Insertar clientes
INSERT INTO clientes (dni_cliente, nombre, apellido, email, celular) 
VALUES (81234567, 'Juan', 'Ramírez', 'juan.ramirez@email.com', '987123456');
INSERT INTO clientes (dni_cliente, nombre, apellido, email, celular) 
VALUES (82345678, 'Laura', 'Silva', 'laura.silva@email.com', '987123457');
INSERT INTO clientes (dni_cliente, nombre, apellido, email, celular) 
VALUES (83456789, 'Roberto', 'Mendoza', 'roberto.mendoza@email.com', '987123458');
INSERT INTO clientes (dni_cliente, nombre, apellido, email, celular) 
VALUES (84567890, 'Sofia', 'Castro', 'sofia.castro@email.com', '987123459');
COMMIT;
/

-- 3. Insertar habitaciones
INSERT INTO habitaciones (numero_habitacion, piso, tipo, capacidad, descripcion, precio_noche) 
VALUES ('101', 1, 'Individual', 1, 'Habitación individual con baño privado', 120.00);
INSERT INTO habitaciones (numero_habitacion, piso, tipo, capacidad, descripcion, precio_noche) 
VALUES ('102', 1, 'Doble', 2, 'Habitación doble con cama queen size', 180.00);
INSERT INTO habitaciones (numero_habitacion, piso, tipo, capacidad, descripcion, precio_noche) 
VALUES ('201', 2, 'Suite', 4, 'Suite familiar con sala y cocineta', 320.00);
INSERT INTO habitaciones (numero_habitacion, piso, tipo, capacidad, descripcion, precio_noche) 
VALUES ('202', 2, 'Doble', 2, 'Habitación doble con vista al mar', 200.00);
INSERT INTO habitaciones (numero_habitacion, piso, tipo, capacidad, descripcion, precio_noche) 
VALUES ('301', 3, 'Familiar', 5, 'Habitación familiar grande', 380.00);
COMMIT;
/

-- 4. Insertar reservas
INSERT INTO reservas (id_habitacion, dni_cliente, id_empleado, fecha_inicio, fecha_fin, cantidad_noches, total_reserva, estado, observaciones) 
VALUES (1, 81234567, 1, DATE '2024-01-15', DATE '2024-01-18', 3, 360.00, 'Finalizada', 'Cliente frecuente');
INSERT INTO reservas (id_habitacion, dni_cliente, id_empleado, fecha_inicio, fecha_fin, cantidad_noches, total_reserva, estado, observaciones) 
VALUES (2, 82345678, 2, DATE '2024-01-20', DATE '2024-01-25', 5, 900.00, 'Confirmada', 'Luna de miel');
INSERT INTO reservas (id_habitacion, dni_cliente, id_empleado, fecha_inicio, fecha_fin, cantidad_noches, total_reserva, estado, observaciones) 
VALUES (3, 83456789, 1, DATE '2024-01-22', DATE '2024-01-24', 2, 640.00, 'Activa', 'Familia con niños');
INSERT INTO reservas (id_habitacion, dni_cliente, id_empleado, fecha_inicio, fecha_fin, cantidad_noches, total_reserva, estado, observaciones) 
VALUES (4, 84567890, 3, DATE '2024-02-01', DATE '2024-02-05', 4, 800.00, 'Pendiente', 'Solicitó vista al mar');
COMMIT;
/

-- SP para crear nueva reserva
CREATE OR REPLACE PROCEDURE sp_crear_reserva(
    p_id_habitacion IN NUMBER,
    p_dni_cliente IN NUMBER,
    p_id_empleado IN NUMBER,
    p_fecha_inicio IN DATE,
    p_fecha_fin IN DATE,
    p_observaciones IN CLOB DEFAULT NULL,
    p_id_reserva OUT NUMBER
)
AS
    v_cantidad_noches NUMBER;
    v_precio_noche NUMBER(10,2);
    v_total_reserva NUMBER(10,2);
BEGIN
    v_cantidad_noches := p_fecha_fin - p_fecha_inicio;
    
    -- Obtener precio de la habitación
    SELECT precio_noche INTO v_precio_noche 
    FROM habitaciones 
    WHERE id_habitacion = p_id_habitacion;
    
    v_total_reserva := v_cantidad_noches * v_precio_noche;
    
    -- Insertar reserva
    INSERT INTO reservas (id_habitacion, dni_cliente, id_empleado, fecha_inicio, fecha_fin, cantidad_noches, total_reserva, observaciones)
    VALUES (p_id_habitacion, p_dni_cliente, p_id_empleado, p_fecha_inicio, p_fecha_fin, v_cantidad_noches, v_total_reserva, p_observaciones)
    RETURNING id_reserva INTO p_id_reserva;
    
    -- Actualizar estado de la habitación
    UPDATE habitaciones SET estado = 'Ocupada' WHERE id_habitacion = p_id_habitacion;
    
    COMMIT;
END;
/

-- SP para finalizar reserva
CREATE OR REPLACE PROCEDURE sp_finalizar_reserva(p_id_reserva IN NUMBER)
AS
BEGIN
    UPDATE reservas 
    SET estado = 'Finalizada', 
        fecha_actualizacion = SYSDATE
    WHERE id_reserva = p_id_reserva;
    
    -- Liberar habitación
    UPDATE habitaciones 
    SET estado = 'Disponible'
    WHERE id_habitacion = (SELECT id_habitacion FROM reservas WHERE id_reserva = p_id_reserva);
    
    COMMIT;
END;
/

-- Verificar datos insertados
SELECT 'Empleados' as Tabla, COUNT(*) as Cantidad FROM empleados
UNION ALL
SELECT 'Clientes', COUNT(*) FROM clientes
UNION ALL
SELECT 'Habitaciones', COUNT(*) FROM habitaciones
UNION ALL
SELECT 'Reservas', COUNT(*) FROM reservas;
/

-- Consultar reservas activas
SELECT * FROM vw_reservas_activas;
/

-- Consultar disponibilidad de habitaciones
SELECT * FROM vw_disponibilidad_habitaciones;
/

-- Consultar empleados por rol
SELECT nombre, apellido, email, rol 
FROM empleados 
WHERE activo = 1 
ORDER BY rol, apellido;
/