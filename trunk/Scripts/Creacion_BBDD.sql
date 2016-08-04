-- Motor: MySQL 5.7.12

DROP DATABASE IF EXISTS 20161_service_g1;
CREATE DATABASE 20161_service_g1;
USE 20161_service_g1;

-- DROP USER IF EXISTS 'grupo1'@'localhost';

-- CREATE USER 'grupo1'@'localhost' IDENTIFIED BY 'grupo1';
-- GRANT ALL PRIVILEGES ON * . * TO 'grupo1'@'localhost';

-- Modelo de productos

CREATE TABLE prod_marcas
(
 IdProdMarca serial,
 Nombre varchar(40) unique,
 Fecha_Baja date, 
 Usuario_baja bigint unsigned references pers_personal(IdPersonal),
 PRIMARY KEY (IdProdMarca)
);

INSERT INTO prod_marcas(Nombre) VALUES('Whirlpool');
INSERT INTO prod_marcas(Nombre) VALUES('Phillips');
INSERT INTO prod_marcas(Nombre) VALUES('Samsung');
INSERT INTO prod_marcas(Nombre) VALUES('Patrick');
INSERT INTO prod_marcas(Nombre) VALUES('Sanyo');
INSERT INTO prod_marcas(Nombre) VALUES('Longvie');
INSERT INTO prod_marcas(Nombre) VALUES('Toshiba');

-- Modelo de electrodomesticos


CREATE TABLE elec_electrodomesticos
(
  IdElectro serial,
  idProdMarca bigint unsigned not null,
  Modelo varchar(20),
  Descripcion varchar(60),
  Fecha_Baja date, 
  Usuario_baja bigint unsigned references pers_personal(IdPersonal),
  PRIMARY KEY (IdElectro),
  FOREIGN KEY (IdProdMarca) REFERENCES prod_marcas (IdProdMarca),
  UNIQUE KEY (idProdMarca,Modelo)
);

INSERT INTO elec_electrodomesticos(idProdMarca,Modelo,Descripcion) VALUES(1,'WFX56DX','Cocina – 4 hornallas – 55 Cm. – A gas');
INSERT INTO elec_electrodomesticos(idProdMarca,Modelo,Descripcion) VALUES(1,'WNQ86AB','Lavarropas Carga Frontal – 8 Kg. – Blanco');
INSERT INTO elec_electrodomesticos(idProdMarca,Modelo,Descripcion) VALUES(1,'WNQ86AN','Lavarropas Carga Frontal – 8 Kg. – Negro');
INSERT INTO elec_electrodomesticos(idProdMarca,Modelo,Descripcion) VALUES(2,'PFG500077','LED PHILIPS 40');
INSERT INTO elec_electrodomesticos(idProdMarca,Modelo,Descripcion) VALUES(2,'HP642130','DEPILADORA PHILIPS');
INSERT INTO elec_electrodomesticos(idProdMarca,Modelo,Descripcion) VALUES(2,'HD469120','PAVA ELECTRICA PHILIPS');
INSERT INTO elec_electrodomesticos(idProdMarca,Modelo,Descripcion) VALUES(3,'WF1702WECU','AEGIS BUBBLE lavarropas con Eco Bubble, 7 kg');
INSERT INTO elec_electrodomesticos(idProdMarca,Modelo,Descripcion) VALUES(3,'SMSCLPGS6E','Celular Galaxy S6 edge');
INSERT INTO elec_electrodomesticos(idProdMarca,Modelo,Descripcion) VALUES(3,'SMSCLPGS7','Celular Galaxy S7');
INSERT INTO elec_electrodomesticos(idProdMarca,Modelo,Descripcion) VALUES(4,'HPK137M','Heladera Cycle Defrost Patrick');
INSERT INTO elec_electrodomesticos(idProdMarca,Modelo,Descripcion) VALUES(4,'HPK141DM','Heladera Cycle Defrost Patrick con Dispenser');
INSERT INTO elec_electrodomesticos(idProdMarca,Modelo,Descripcion) VALUES(4,'LPK08E10B','Lavarropas Patrick de 8kg');
INSERT INTO elec_electrodomesticos(idProdMarca,Modelo,Descripcion) VALUES(4,'LPK65E10B','Lavarropas Patrick de 6,5kg');
INSERT INTO elec_electrodomesticos(idProdMarca,Modelo,Descripcion) VALUES(4,'PPK60SN','Purificador Patrick de 60 cm');
INSERT INTO elec_electrodomesticos(idProdMarca,Modelo,Descripcion) VALUES(4,'CPS6656BVS','Cocina de piso Patrick de 56 cm');
INSERT INTO elec_electrodomesticos(idProdMarca,Modelo,Descripcion) VALUES(5,'SA1815ARN','Acondicionador Ventana Sanyo 5100 W');
INSERT INTO elec_electrodomesticos(idProdMarca,Modelo,Descripcion) VALUES(5,'LCE24XH15','TV LED Sanyo 24 " HD');
INSERT INTO elec_electrodomesticos(idProdMarca,Modelo,Descripcion) VALUES(5,'EMCX3014','Microondas Sanyo 30 L Inoxidable');
INSERT INTO elec_electrodomesticos(idProdMarca,Modelo,Descripcion) VALUES(5,'SA1215ARN','Acondicionador Ventana Sanyo 3500 W');
INSERT INTO elec_electrodomesticos(idProdMarca,Modelo,Descripcion) VALUES(6,'18601XF','Cocina Inox.-Rej.fund-TT-Enc. a 1 mano');
INSERT INTO elec_electrodomesticos(idProdMarca,Modelo,Descripcion) VALUES(6,'CN214SSF','CALEFON LONGVIE 14L');
INSERT INTO elec_electrodomesticos(idProdMarca,Modelo,Descripcion) VALUES(7,'TSH22427','Televisor Toshiba 27 pulgadas');
INSERT INTO elec_electrodomesticos(idProdMarca,Modelo,Descripcion) VALUES(7,'TSH22430','Televisor Toshiba 30 pulgadas');

-- Modelo de personal


CREATE TABLE pers_roles
(
  IdRol serial,
  Descripcion varchar(20),
  PRIMARY KEY (IdRol)
);

INSERT INTO pers_roles(IdRol, Descripcion) VALUES(1, 'Superusuario');
INSERT INTO pers_roles(IdRol, Descripcion) VALUES(2, 'Administrativo');
INSERT INTO pers_roles(IdRol, Descripcion) VALUES(3, 'Tecnico');


CREATE TABLE pers_personal 
(
  IdPersonal serial,
  IdRol bigint unsigned not null,
  Nombre varchar(20),
  Apellido varchar(20),
  Usuario varchar(20) unique,
  pass varchar(32),
  Fecha_Baja date, 
  Usuario_baja bigint unsigned references pers_personal(IdPersonal),
  PRIMARY KEY (IdPersonal),
  FOREIGN KEY (IdRol) REFERENCES pers_roles (IdRol),
  UNIQUE KEY (Usuario)
);

INSERT INTO pers_personal (IdRol,Nombre,Apellido,Usuario,Pass) 
VALUES(1,'Lucas','Guaycochea','admin',MD5('admin'));

INSERT INTO pers_personal (IdRol,Nombre,Apellido,Usuario,Pass) 
VALUES(2,'Dario','Rick','drick',MD5('123456'));

INSERT INTO pers_personal (IdRol,Nombre,Apellido,Usuario,Pass) 
VALUES(2,'Fabian','Caputo','fcaputo',MD5('123456'));

INSERT INTO pers_personal (IdRol,Nombre,Apellido,Usuario,Pass) 
VALUES(3,'Jorge','El Tecnico','tecnico',MD5('tecnico'));


-- Modelo de clientes

CREATE TABLE ot_zonas (
    idZonaPosible serial,
    Nombre VARCHAR(60) not null,
    Precio integer not null,
    PRIMARY KEY (idZonaPosible)
);

INSERT INTO ot_zonas(idZonaPosible, Nombre, Precio) VALUES(1, 'Zona 1', 100); 
INSERT INTO ot_zonas(idZonaPosible, Nombre, Precio) VALUES(2, 'Zona 2', 150); 
INSERT INTO ot_zonas(idZonaPosible, Nombre, Precio) VALUES(3, 'Zona 3', 200); 
INSERT INTO ot_zonas(idZonaPosible, Nombre, Precio) VALUES(4, 'Zona 4', 250); 
INSERT INTO ot_zonas(idZonaPosible, Nombre, Precio) VALUES(5, 'Zona 5', 300); 

create table localidades (
codigoPostal integer,
nombre varchar(20),
zonaDeEnvio bigint unsigned,
provincia varchar (20),
primary key (codigoPostal),
foreign key (zonaDeEnvio) references ot_zonas(idZonaPosible)
);

INSERT INTO localidades VALUES ('1667', 'Tortuguitas', 1, 'Buenos Aires');
INSERT INTO localidades VALUES ('1613', 'Los Polvorines', 2, 'Buenos Aires');
INSERT INTO localidades VALUES ('1615', 'Grand Bourg', 2, 'Buenos Aires');
INSERT INTO localidades VALUES ('1663', 'San Miguel', 2, 'Buenos Aires');
INSERT INTO localidades VALUES ('1617', 'General Pacheco', 2, 'Buenos Aires');
INSERT INTO localidades VALUES ('1638', 'Vicente Lopez', 3, 'Buenos Aires');
INSERT INTO localidades VALUES ('1640', 'Acassuso', 3, 'Buenos Aires');
INSERT INTO localidades VALUES ('1642', 'San Isidro', 3, 'Buenos Aires');
INSERT INTO localidades VALUES ('1875', 'Wilde', 4, 'Buenos Aires');
INSERT INTO localidades VALUES ('1878', 'Quilmes', 4, 'Buenos Aires');
INSERT INTO localidades VALUES ('1900', 'La Plata', 5, 'Buenos Aires');

CREATE TABLE cli_clientes
(
  IdCliente serial,
  Nombre varchar(20) not null,
  Apellido varchar(20) not null,
  Direccion varchar(20),
  Codigo_postal integer,
  Telefono varchar(20),
  Email varchar(30),
  Fecha_Baja date, 
  Usuario_baja bigint unsigned references personal(IdPersonal),
  PRIMARY KEY (IdCliente),
  foreign key (Codigo_postal) references localidades (codigoPostal)
);

INSERT INTO cli_clientes (Nombre,Apellido,Direccion,Codigo_postal,Telefono,Email) 
VALUES('Dario','Rick','Eva Peron 710', 1613,'1566060529','dario_rick@outlook.com');

INSERT INTO cli_clientes (Nombre,Apellido,Direccion,Codigo_postal,Telefono,Email) 
VALUES('Fabian','Caputo','Emilio Zola 3154', 1613,'1156655890','fcaputo@hotmail.com');

INSERT INTO cli_clientes (Nombre,Apellido,Direccion,Codigo_postal,Telefono,Email) 
VALUES('Ivan','Quintero','Cura Brochero 1234', 1667,'1135982446','quintero_ivan_93@hotmail.com');

INSERT INTO cli_clientes (Nombre,Apellido,Direccion,Codigo_postal,Telefono,Email) 
VALUES('Jonatan','Alvarez','Perón 1234', 1663,'1137581330','jony.alv86@gmail.com');

INSERT INTO cli_clientes (Nombre,Apellido,Direccion,Codigo_postal,Telefono,Email) 
VALUES('Fernanda','Cazzari','Haití 342', 1667,'1131377455','fernandacazzari@gmail.com');


-- Modelo de envios

create table env_estados_vehiculos (
idEstado serial,
nombreEstado varchar (20),
primary key (idEstado)
);

insert into env_estados_vehiculos (idEstado, nombreEstado) values (1, 'Operativo');
insert into env_estados_vehiculos (idEstado, nombreEstado) values (2, 'En reparación');

CREATE TABLE env_vehiculos
(
  Patente varchar(10) not null unique,
  Marca varchar(20) not null,
  Modelo varchar(20) not null,
  CapacidadCarga bigint not null,
  FechaVencimientoVTV date not null,
  Oblea boolean,
  FechaVencimientoOblea date,
  Estado bigint unsigned not null,
  UsuarioBaja bigint unsigned,
  PRIMARY KEY (Patente),
  foreign key (Estado) references env_estados_vehiculos(idEstado),
  foreign key (UsuarioBaja) references pers_personal (IdPersonal)
); 

INSERT INTO env_vehiculos (Patente, Marca, Modelo,  CapacidadCarga, FechaVencimientoVTV, Oblea, Estado) 
VALUES('WCG680', 'Mercedes Benz', '1114', 12, '2016-06-01', false, 1);
INSERT INTO env_vehiculos (Patente, Marca, Modelo,  CapacidadCarga, FechaVencimientoVTV, Oblea, Estado) 
VALUES('BGH367', 'Fiat', 'Duna', 4, '2017-06-01', false, 1);

CREATE TABLE env_fleteros
(
 IdFletero serial,
 Nombre varchar(20),
 Apellido varchar(20),
 Celular varchar(20),
 RegistroConducir varchar(12) unique,
 FechaVencimientoRegistro date,
 IdVehiculo varchar(10) not null,
 Fecha_Baja date, 
 Usuario_baja bigint unsigned references personal(IdPersonal),
 PRIMARY KEY (IdFletero),
 FOREIGN KEY (IdVehiculo) REFERENCES env_vehiculos (Patente)
);

INSERT INTO env_fleteros(Nombre,Apellido,Celular, RegistroConducir, FechaVencimientoRegistro,idVehiculo) 
VALUES('Juan', 'Araya', '1121223322', '00003564723', '2017-02-01', 'WCG680');
INSERT INTO env_fleteros(Nombre,Apellido,Celular, RegistroConducir, FechaVencimientoRegistro,idVehiculo) 
VALUES('Jorge', 'Porcel', '1123456678', '00007795222', '2016-11-16','WCG680');


CREATE TABLE env_costos_envios
(
 IdCosto serial,
 Codigo_postal integer not null,
 Costo_envio integer not null,
 PRIMARY KEY (idCosto),
 UNIQUE KEY (codigo_postal)
);

INSERT INTO env_costos_envios(Codigo_postal,Costo_envio) 
VALUES(1613,350);


-- Modelo proveedores

CREATE TABLE prov_proveedores
(
 IdProveedor serial,
 Nombre varchar(20),
 Contacto varchar (20),
 Cuit varchar(11) unique,
 Telefono varchar(20),
 Mail varchar(40),
 Fecha_Baja date,
 Usuario_baja bigint unsigned references pers_personal(idPersonal),
 PRIMARY KEY (idProveedor)
);

CREATE TABLE prov_proveedores_incumplidores
(
 IdProveedor integer not null,
 Fecha date
);

INSERT INTO prov_proveedores(IdProveedor, Nombre,Cuit,Mail) 
VALUES(1,'Proveedor1','20111111114','proveedor@proveedor.com');
INSERT INTO prov_proveedores(IdProveedor, Nombre,Cuit,Mail)
VALUES(2,'Siemens','30503364898','ar@siemens.com');
INSERT INTO prov_proveedores(IdProveedor, Nombre,Cuit,Mail)
VALUES(3,'Sony Argentina','30679928879','SonyStoreOnline.SAR@am.sony.com');
INSERT INTO prov_proveedores(IdProveedor, Nombre,Cuit,Mail)
VALUES(4,'Toshiba Argentina','30711965595',' toshiba@grupomst.com');
INSERT INTO prov_proveedores(IdProveedor, Nombre,Cuit,Mail)
VALUES(5,'Samsung Argentina','30684125792',' contacto@samsung.com.ar');
INSERT INTO prov_proveedores(IdProveedor, Nombre,Cuit,Mail)
VALUES(6,'Sanyo Argentina','30642617555',' contacto@sanyo.com.ar');
INSERT INTO prov_proveedores(IdProveedor, Nombre,Cuit,Mail)
VALUES(7,'TengoTodo','27111111118','proveedor@proveedor.com');

-- Modelo de productos

CREATE TABLE prod_piezas
(
 idProdPieza serial,
 idProdMarca bigint unsigned not null,
 idUnico varchar (8) not null unique,
 Descripcion varchar(40),
 Precio_venta decimal(10,4), -- Precio de venta de ElectroR al publico
 bajo_stock smallint not null, -- Indica el mínimo stock que puede tener una pieza antes de que salte la alerta
 Fecha_Baja date,
 Usuario_baja bigint unsigned references pers_personal(idPersonal),
 PRIMARY KEY (idProdPieza),
 FOREIGN KEY (idProdMarca) REFERENCES prod_marcas(idProdMarca) 
);

INSERT INTO prod_piezas(idProdMarca,idUnico,Descripcion,Precio_venta,bajo_stock) 
VALUES(1,'KT1553W','Kit tornillos ensamble Whirpool', 50, 5);
INSERT INTO prod_piezas(idProdMarca,idUnico,Descripcion,Precio_venta,bajo_stock) 
VALUES(1,'ATM3453I','Lampara 40 watts', 50, 5);
INSERT INTO prod_piezas(idProdMarca,idUnico,Descripcion,Precio_venta,bajo_stock) 
VALUES(1,'ATM34536','Lampara 60 watts',60, 5);
INSERT INTO prod_piezas(idProdMarca,idUnico,Descripcion,Precio_venta,bajo_stock) 
VALUES(1,'ATM34200','Lampara 200 watts',140, 5);
INSERT INTO prod_piezas(idProdMarca,idUnico,Descripcion,Precio_venta,bajo_stock) 
VALUES(1,'TTM84702','Transistor 100 ohm',0.80, 5);
INSERT INTO prod_piezas(idProdMarca,idUnico,Descripcion,Precio_venta,bajo_stock) 
VALUES(1,'TTM84902','Transistor 200 ohm',1.80, 5);
INSERT INTO prod_piezas(idProdMarca,idUnico,Descripcion,Precio_venta,bajo_stock) 
VALUES(2,'PNTLCD04','Pantalla LCD 40',5000, 5);
INSERT INTO prod_piezas(idProdMarca,idUnico,Descripcion,Precio_venta,bajo_stock) 
VALUES(2,'MTR01235','Motor electrico 200w',5000, 5);
INSERT INTO prod_piezas(idProdMarca,idUnico,Descripcion,Precio_venta,bajo_stock) 
VALUES(3,'SMG41962','Display 5 pulgadas',2300, 5);
INSERT INTO prod_piezas(idProdMarca,idUnico,Descripcion,Precio_venta,bajo_stock) 
VALUES(3,'SMG41972','Display 9 pulgadas',4200, 5);
INSERT INTO prod_piezas(idProdMarca,idUnico,Descripcion,Precio_venta,bajo_stock) 
VALUES(4,'QMP31256','Quemador horno Patrick general',200, 5);
INSERT INTO prod_piezas(idProdMarca,idUnico,Descripcion,Precio_venta,bajo_stock) 
VALUES(4,'LDZ31656','Luz horno Patrick general',200, 5);
INSERT INTO prod_piezas(idProdMarca,idUnico,Descripcion,Precio_venta,bajo_stock) 
VALUES(4,'CHSP6542','Chispero Patrick',200, 5);
INSERT INTO prod_piezas(idProdMarca,idUnico,Descripcion,Precio_venta,bajo_stock) 
VALUES(5,'VENT3125','Ventilador Aire Acondicionado Sanyo',248, 5);
INSERT INTO prod_piezas(idProdMarca,idUnico,Descripcion,Precio_venta,bajo_stock) 
VALUES(5,'MTR1262','Motor Aire Acondicionado Sanyo',2000, 5);
INSERT INTO prod_piezas(idProdMarca,idUnico,Descripcion,Precio_venta,bajo_stock) 
VALUES(6,'LNG5431','Quemador calefon Longvie',500, 5);
INSERT INTO prod_piezas(idProdMarca,idUnico,Descripcion,Precio_venta,bajo_stock) 
VALUES(6,'LNG1300','Serpentina calefon Longvie',150, 5);
INSERT INTO prod_piezas(idProdMarca,idUnico,Descripcion,Precio_venta,bajo_stock) 
VALUES(7,'TSAH900','Control remoto Toshiba',400, 5);
INSERT INTO prod_piezas(idProdMarca,idUnico,Descripcion,Precio_venta,bajo_stock) 
VALUES(7,'TSAH902','Ficha antena generica',20, 5);

-- Fin modelo productos

CREATE TABLE prov_provee_marca
(
 idProvXMarca serial,
 idProveedor bigint unsigned not null,
 idProdMarca bigint unsigned not null,
 PRIMARY KEY (idProvXMarca),
 FOREIGN KEY (idProveedor) REFERENCES prov_proveedores(idProveedor),
 FOREIGN KEY (idProdMarca) REFERENCES prod_marcas(idProdMarca)
);

INSERT INTO prov_provee_marca(idProveedor,idProdMarca) 
VALUES(1,1);
INSERT INTO prov_provee_marca(idProveedor,idProdMarca) 
VALUES(1,2);
INSERT INTO prov_provee_marca(idProveedor,idProdMarca) 
VALUES(1,6);
INSERT INTO prov_provee_marca(idProveedor,idProdMarca) 
VALUES(5,3);
INSERT INTO prov_provee_marca(idProveedor,idProdMarca) 
VALUES(5,4);
INSERT INTO prov_provee_marca(idProveedor,idProdMarca) 
VALUES(6,5);
INSERT INTO prov_provee_marca(idProveedor,idProdMarca) 
VALUES(7,1);
INSERT INTO prov_provee_marca(idProveedor,idProdMarca) 
VALUES(7,2);
INSERT INTO prov_provee_marca(idProveedor,idProdMarca) 
VALUES(7,3);
INSERT INTO prov_provee_marca(idProveedor,idProdMarca) 
VALUES(7,4);
INSERT INTO prov_provee_marca(idProveedor,idProdMarca) 
VALUES(7,5);
INSERT INTO prov_provee_marca(idProveedor,idProdMarca) 
VALUES(7,6);
INSERT INTO prov_provee_marca(idProveedor,idProdMarca) 
VALUES(7,7);

CREATE TABLE prov_precios_piezas
(
 idProvXPiezas serial,
 idProveedor bigint unsigned not null,
 idProdPieza bigint unsigned not null,
 precio_compra decimal(10,4) not null,
 PRIMARY KEY (idProvXPiezas),
 FOREIGN KEY (idProveedor) REFERENCES prov_proveedores(idProveedor),
 FOREIGN KEY (idProdPieza) REFERENCES prod_piezas(idProdPieza)
);


-- Modelo de productos


CREATE TABLE prod_estados 
(
  idProdEstado serial,
  Estado varchar(20),
  PRIMARY KEY (idProdEstado)
); 

INSERT INTO prod_estados(idProdEstado, Estado) VALUES(1, 'Disponible');
INSERT INTO prod_estados(idProdEstado, Estado) VALUES(2, 'Vendido');
INSERT INTO prod_estados(idProdEstado, Estado) VALUES(3, 'Rota');
INSERT INTO prod_estados(idProdEstado, Estado) VALUES(4, 'Perdida');

CREATE TABLE prod_piezas_stock -- Cada fila representa un item físico a vender o ya vendido
(
  idProdStock serial,
  idProdPieza bigint unsigned not null,
  idProdEstado  bigint unsigned not null,
  PRIMARY KEY (idProdStock),
  FOREIGN KEY (idProdPieza) REFERENCES prod_piezas(idProdPieza),
  FOREIGN KEY (idProdEstado) REFERENCES prod_estados(idProdEstado)
);

-- Modelo de solicitudes de compra

CREATE TABLE solc_estados 
(
  idSolcEstado serial,
  Descripcion varchar(40),
  PRIMARY KEY (idSolcEstado)
);

INSERT INTO solc_estados(idSolcEstado, Descripcion) VALUES(1, 'Ingresada');
INSERT INTO solc_estados(idSolcEstado, Descripcion) VALUES(2, 'Enviada');
INSERT INTO solc_estados(idSolcEstado, Descripcion) VALUES(3, 'Procesada');
INSERT INTO solc_estados(idSolcEstado, Descripcion) VALUES(4, 'Cancelada');

CREATE TABLE solc_solicitud_compra 
(
  idSolcCompra serial,
  idSolcEstado bigint unsigned not null,
  idUsuarioAlta bigint unsigned not null,
  idProveedor bigint unsigned not null,
  Fecha_Alta date not null,
  Fecha_Procesada date,
  PRIMARY KEY (idSolcCompra),
  FOREIGN KEY (idSolcEstado) REFERENCES solc_estados(idSolcEstado),
  FOREIGN KEY (idUsuarioAlta) REFERENCES pers_personal (idPersonal),
  FOREIGN KEY (idProveedor) REFERENCES prov_proveedores(idProveedor)
);

INSERT INTO solc_solicitud_compra(idSolcEstado,idUsuarioAlta,idProveedor,Fecha_Alta) 
VALUES(1,1,1,now());
INSERT INTO solc_solicitud_compra(idSolcEstado,idUsuarioAlta,idProveedor,Fecha_Alta) 
VALUES(2,1,1,now());


CREATE TABLE solc_piezas_solicitadas
(
  idSolcPiezas serial,
  idSolcCompra bigint unsigned not null,
  idProdPieza  bigint unsigned not null,
  cantidad int not null,
  PRIMARY KEY (idSolcPiezas),
  FOREIGN KEY (idSolcCompra) REFERENCES solc_solicitud_compra(idSolcCompra),
  FOREIGN KEY (idProdPieza) REFERENCES prod_piezas(idProdPieza)
);


CREATE TABLE solc_piezas_entregadas
(
  idSolcPiezas serial,
  idSolcCompra bigint unsigned not null,
  idProdPieza  bigint unsigned not null,
  cantidad int not null,
  PRIMARY KEY (idSolcPiezas),
  FOREIGN KEY (idSolcCompra) REFERENCES solc_solicitud_compra(idSolcCompra),
  FOREIGN KEY (idProdPieza) REFERENCES prod_piezas(idProdPieza)
);

-- Eliminar solc_piezas !
CREATE TABLE solc_piezas 
(
  idSolcPiezas serial,
  idSolcCompra bigint unsigned not null,
  idProdPieza  bigint unsigned not null,
  cantidad int not null,
  PRIMARY KEY (idSolcPiezas),
  FOREIGN KEY (idSolcCompra) REFERENCES solc_solicitud_compra(idSolcCompra),
  FOREIGN KEY (idProdPieza) REFERENCES prod_piezas(idProdPieza)
);

INSERT INTO solc_piezas(idSolcCompra,idProdPieza,cantidad) 
VALUES(1,1,2);
INSERT INTO solc_piezas(idSolcCompra,idProdPieza,cantidad) 
VALUES(1,2,1);
INSERT INTO solc_piezas(idSolcCompra,idProdPieza,cantidad) 
VALUES(2,1,2);
INSERT INTO solc_piezas(idSolcCompra,idProdPieza,cantidad) 
VALUES(2,2,2);



-- Modelo de ordenes de trabajo


CREATE TABLE ot_estados ( -- Posibles estados de una orden de trabajo
    idEstadoPosible serial,
    estado varchar(20) NOT NULL,
    PRIMARY KEY (idEstadoPosible)
);
INSERT INTO ot_estados(idEstadoPosible, estado) VALUES(1,'Ingresada');
INSERT INTO ot_estados(idEstadoPosible, estado) VALUES(2,'Presupuestada');
INSERT INTO ot_estados(idEstadoPosible, estado) VALUES(3,'Aprobada');
INSERT INTO ot_estados(idEstadoPosible, estado) VALUES(4,'Desaprobada');
INSERT INTO ot_estados(idEstadoPosible, estado) VALUES(5,'En reparación');
INSERT INTO ot_estados(idEstadoPosible, estado) VALUES(6,'En espera de piezas');
INSERT INTO ot_estados(idEstadoPosible, estado) VALUES(7,'Reparada');
INSERT INTO ot_estados(idEstadoPosible, estado) VALUES(8,'Irreparable');
INSERT INTO ot_estados(idEstadoPosible, estado) VALUES(9,'Despachada');
INSERT INTO ot_estados(idEstadoPosible, estado) VALUES(10,'Entregada');

CREATE TABLE ot_ordenes_trabajo (
    idOT serial,
    idCliente bigint unsigned not null,
    idElectro bigint unsigned not null,
    descripcion text not null,
    idUsuarioAlta bigint unsigned not null,
    idTecnicoAsoc bigint unsigned,
    esDelivery boolean not null,
    vencPresup date,
    fechaReparado date,
    expiraGarantia date,
    idOtAsociada bigint unsigned,
    estado_orden bigint unsigned not null,
    manoDeObra decimal(10,2),
    domicilio VARCHAR (30),
    codigoPostal integer,
    costoDeEnvio decimal(10,2),
    PRIMARY KEY (idOT),
    FOREIGN KEY (idCliente) REFERENCES cli_clientes (idCliente),
    FOREIGN KEY (idUsuarioAlta) REFERENCES pers_personal (idPersonal),
    FOREIGN KEY (idTecnicoAsoc) REFERENCES pers_personal (idPersonal),
    FOREIGN KEY (idElectro) REFERENCES elec_electrodomesticos (idElectro),
    FOREIGN KEY (estado_orden) REFERENCES ot_estados(idEstadoPosible),
    FOREIGN KEY (codigoPostal) REFERENCES localidades (codigoPostal)  
);

create table env_envios (
idEnvio serial,
idFletero bigint unsigned,
fechaEnvio date,
primary key (idEnvio),
foreign key (idFletero) references env_fleteros(idFletero)
);

create table env_envios_detalle (
id serial,
idOT bigint unsigned,
entregado boolean,
idEnvio bigint unsigned,
primary key(id),
foreign key (idOT) references ot_ordenes_trabajo(idOT),
foreign key (idEnvio) references env_envios(idEnvio)
);

CREATE TABLE ot_piezas_presupuestadas (
    idOTPiezaPresup serial,
    idOT bigint unsigned not null, -- ot asociada
    idProdPieza bigint unsigned,
    costoPresupuestada double(5,2), -- precio al momento de hacer el presupuesto, este puede variar
    PRIMARY KEY (idOTPiezaPresup),
    FOREIGN KEY (idOT) REFERENCES ot_ordenes_trabajo (idOT),
    FOREIGN KEY (idProdPieza) REFERENCES prod_piezas (idProdPieza)
);

CREATE TABLE ot_piezas_usadas (
    idOTPiezaStock serial,
    idProdStock bigint unsigned,
    idOT bigint unsigned not null,
    PRIMARY KEY (idOTPiezaStock),
    FOREIGN KEY (idProdStock) REFERENCES prod_piezas_stock (idProdStock),
    FOREIGN KEY (idOT) REFERENCES ot_ordenes_trabajo (idOT)
);

CREATE TABLE ot_estados_historico -- Guarda histórico de ot_ordenes_trabajo
(
  idOt_estado serial,
  idOT bigint unsigned not null,
  estado_orden bigint unsigned not null,
  Fecha_Alta date not null, 
  Fecha_Baja date,
  PRIMARY KEY (idot_estado),
  FOREIGN KEY (idOT) REFERENCES ot_ordenes_trabajo (idOT),
  FOREIGN KEY (estado_orden) REFERENCES ot_estados(idEstadoPosible)
);


INSERT INTO ot_ordenes_trabajo(idCliente,idElectro,descripcion,idUsuarioAlta,esDelivery,vencPresup,estado_orden,domicilio,codigoPostal)
VALUES(1,1,'OT Ejemplo ingresada',1,FALSE,NOW() + INTERVAL 10 DAY,1,'Eva Peron 710', 1613);
INSERT INTO ot_estados_historico (idOT,estado_orden,Fecha_Alta)
VALUES(1,1,NOW());

INSERT INTO ot_ordenes_trabajo(idCliente,idElectro,descripcion,idUsuarioAlta,esDelivery,vencPresup,estado_orden,domicilio,codigoPostal)
VALUES(2,3,'OT Ejemplo presupuestada',1,FALSE,NOW() + INTERVAL 10 DAY,2,'Emilio Zola 3154',1613);
INSERT INTO ot_estados_historico (idOT,estado_orden,Fecha_Alta,Fecha_Baja)
VALUES(2,1,NOW(),NOW());
INSERT INTO ot_estados_historico (idOT,estado_orden,Fecha_Alta)
VALUES(2,2,NOW());
INSERT INTO ot_piezas_presupuestadas(idOT,idProdPieza,costoPresupuestada)
VALUES(2,1,200);
INSERT INTO ot_piezas_presupuestadas(idOT,idProdPieza,costoPresupuestada)
VALUES(2,1,200);

INSERT INTO ot_ordenes_trabajo(idCliente,idElectro,descripcion,idUsuarioAlta,esDelivery,vencPresup,estado_orden,domicilio,codigoPostal)
VALUES(2,3,'OT Ejemplo aprobada',1,FALSE,NOW() + INTERVAL 10 DAY, 3,'Emilio Zola 3154',1613);
INSERT INTO ot_estados_historico (idOT,estado_orden,Fecha_Alta,Fecha_Baja)
VALUES(3,1,NOW(),NOW());
INSERT INTO ot_estados_historico (idOT,estado_orden,Fecha_Alta,Fecha_Baja)
VALUES(3,2,NOW(),NOW());
INSERT INTO ot_estados_historico (idOT,estado_orden,Fecha_Alta)
VALUES(3,3,NOW());
INSERT INTO ot_piezas_presupuestadas(idOT,idProdPieza,costoPresupuestada)
VALUES(3,2,200);
INSERT INTO ot_piezas_presupuestadas(idOT,idProdPieza,costoPresupuestada)
VALUES(3,1,200);


INSERT INTO ot_ordenes_trabajo(idCliente,idElectro,descripcion,idUsuarioAlta,esDelivery,vencPresup,estado_orden,domicilio,codigoPostal)
VALUES(1,3,'OT Ejemplo desaprobada',1,FALSE,NOW() + INTERVAL 10 DAY, 4,'Eva Peron 710', 1613);
INSERT INTO ot_estados_historico (idOT,estado_orden,Fecha_Alta,Fecha_Baja)
VALUES(4,1,NOW(),NOW());
INSERT INTO ot_estados_historico (idOT,estado_orden,Fecha_Alta,Fecha_Baja)
VALUES(4,2,NOW(),NOW());
INSERT INTO ot_estados_historico (idOT,estado_orden,Fecha_Alta)
VALUES(4,4,NOW());
INSERT INTO ot_piezas_presupuestadas(idOT,idProdPieza,costoPresupuestada)
VALUES(4,2,200);
INSERT INTO ot_piezas_presupuestadas(idOT,idProdPieza,costoPresupuestada)
VALUES(4,1,200);

-- Modelo contable

 CREATE TABLE cont_libro_diario
(
  idOperacion serial,
  monto decimal(10,2) not null,
  idOTPiezaStock bigint unsigned, -- FK con las piezas usadas
  idSolcPiezas bigint unsigned, -- FK con las solicitudes de compra
  Fecha date not null,
  PRIMARY KEY (idOperacion),
  FOREIGN KEY (idOTPiezaStock) REFERENCES ot_piezas_usadas (idOTPiezaStock),
  FOREIGN KEY (idSolcPiezas) REFERENCES solc_piezas (idSolcPiezas)
); 

-- Tablas accesorias

CREATE TABLE acc_parametros
(
    parametro VARCHAR(40),
    parm_valor1 VARCHAR(40),
    parm_valor2 VARCHAR(40)
);

INSERT INTO acc_parametros(parametro,parm_valor1)
VALUES ('MAIL_ADMIN','jony.alv86@gmail.com');

INSERT INTO acc_parametros(parametro,parm_valor1)
VALUES ('HASHCODE_PASSWORD','');

INSERT INTO acc_parametros(parametro,parm_valor1)
VALUES ('COMERCIO_NOMBRE','Electro R');

INSERT INTO acc_parametros(parametro,parm_valor1)
VALUES ('COMERCIO_DOMICILIO','Rivadavia 2100');

INSERT INTO acc_parametros(parametro,parm_valor1)
VALUES ('COMERCIO_LOCALIDAD','Los Polvorines');	

INSERT INTO acc_parametros(parametro,parm_valor1)
VALUES ('COMERCIO_CP','1613');	

INSERT INTO acc_parametros(parametro,parm_valor1)
VALUES ('COMERCIO_TELEFONO','11 4663 8854');	


-- Alertas

CREATE TABLE acc_tipo_alerta (
  idTipoAlerta serial,
  tipoAlerta varchar(20)
);

INSERT INTO acc_tipo_alerta (idTipoAlerta,tipoAlerta) VALUES (1,'Bajo Stock');
INSERT INTO acc_tipo_alerta (idTipoAlerta,tipoAlerta) VALUES (2,'Solicitada en OT');


CREATE TABLE acc_alertas
(
    idAlerta serial,
    fecha_Alerta date not null,
    idTipoAlerta bigint unsigned not null, -- TODO: Verificar
    idProdPieza bigint unsigned not null, -- Pieza que falta
    Fecha_Baja date,
    PRIMARY KEY (idAlerta),
	FOREIGN KEY (idTipoAlerta) REFERENCES acc_tipo_alerta (idTipoAlerta),
    FOREIGN KEY (idProdPieza) REFERENCES prod_piezas (idProdPieza)
);


-- Vistas

-- Vista que trae todas las piezas y sus precios de compra y venta
CREATE VIEW view_Precios_Piezas AS
    SELECT 
        piez.idProdPieza, 
        marc.Nombre, 
        piez.idUnico, 
        piez.Descripcion, 
        piez.Precio_venta, 
        piez.bajo_stock,
       -- prov.Nombre,
        ppp.precio_compra
    FROM
        prod_piezas piez
	JOIN prod_marcas marc
		ON marc.IdProdMarca = piez.idProdPieza
	LEFT JOIN prov_precios_piezas ppp
		ON ppp.idProdPieza = piez.idProdPieza
	JOIN prov_proveedores prov
		ON prov.IdProveedor = ppp.idProveedor
	WHERE marc.Fecha_Baja IS NULL
    AND piez.Fecha_Baja IS NULL;



-- Stored procedures


-- SP Personal

DELIMITER $$
CREATE FUNCTION obtenerIdRol(Descripcion Varchar(20)) 
  RETURNS BIGINT UNSIGNED
BEGIN
  DECLARE IdRol BIGINT;

    SELECT pers.IdRol 
    INTO IdRol 
    FROM pers_roles pers 
    WHERE pers.Descripcion = Descripcion;
  
  RETURN IdRol;
END $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE eliminarPersonal (
IN IdPersonal  bigint unsigned,
IN Usuario_baja bigint unsigned,
OUT retCode  int(11),
OUT descErr varchar(60)
) 
BEGIN

  DECLARE vExiste INT;

  DECLARE CONTINUE HANDLER FOR SQLEXCEPTION 
    SET retCode = 1, descErr = 'eliminarPersonal() - Error General';

  SET retCode = 0;
    SET descErr = 'eliminarPersonal() - OK';

  SELECT EXISTS(
        SELECT * FROM pers_personal pers
        WHERE pers.IdPersonal = IdPersonal
        )
  INTO vExiste;
  
  IF (vExiste = 1) 
  THEN  
    UPDATE pers_personal pers
    SET pers.Usuario_baja = Usuario_baja,
    Fecha_Baja = now()
    WHERE pers.IdPersonal = IdPersonal;
  ELSE
    SET retCode = 2;
    SET descErr = 'eliminarPersonal() - El usuario no existe';
  END IF;

END $$
DELIMITER ;


-- SP Clientes

DELIMITER $$
CREATE PROCEDURE insertarCliente (
IN Nombre varchar(20), 
IN Apellido varchar(20), 
IN Direccion varchar(20),  
IN Codigo_postal smallint, 
IN Telefono varchar(20), 
IN Email varchar(30), 
OUT retIdCliente  int(11),
OUT retCode  int(11),
OUT descErr varchar(40)
) 

BEGIN

   DECLARE CONTINUE HANDLER FOR SQLEXCEPTION 
    SET retCode = 1,  descErr = 'insertarCliente() - Error General';

  SET retCode = 0;
    SET descErr = 'insertarCliente() - OK';


  INSERT INTO cli_clientes (
  Nombre,
  Apellido,
  Telefono,
  Email
  )
  VALUES (
  Nombre, 
  Apellido, 
  Telefono,
  Email
  );

  SELECT MAX(IdCliente) 
  INTO retIdCliente
  FROM cli_clientes;
  
  IF (Direccion != '')
  THEN
	UPDATE (cli_clientes)
    SET Direccion = Direccion,
		Codigo_postal = Codigo_postal
    WHERE IdCliente = retIdCliente;
		
  END IF;

END $$
DELIMITER ;


DELIMITER $$
CREATE PROCEDURE eliminarCliente (
IN IdCliente  bigint unsigned,
IN Usuario_baja bigint unsigned,
OUT retCode  int(11),
OUT descErr varchar(60)
) 
BEGIN

  DECLARE vExiste INT;

  DECLARE CONTINUE HANDLER FOR SQLEXCEPTION 
    SET retCode = 1, descErr = 'eliminarCliente() - Error General';

  SET retCode = 0;
    SET descErr = 'eliminarCliente() - OK';

  SELECT EXISTS(
        SELECT * FROM cli_clientes cli
        WHERE cli.idCliente = IdCliente
        )
  INTO vExiste;
  
  IF (vExiste = 1) 
  THEN  
    UPDATE cli_clientes cli
    SET cli.Usuario_baja = Usuario_baja,
    Fecha_Baja = now()
    WHERE cli.IdCliente = IdCliente;
  ELSE
    SET retCode = 2;
    SET descErr = 'eliminarCliente() - El cliente no existe';
  END IF;

END $$
DELIMITER ;

DELIMITER $$


DELIMITER $$
CREATE PROCEDURE modificarCliente (
IN IdCliente int(11),
IN Nombre varchar(20), 
IN Apellido varchar(20), 
IN Direccion varchar(20), 
IN Codigo_postal smallint, 
IN Telefono varchar(20), 
IN Email varchar(30), 
OUT retCode  int(11),
OUT descErr varchar(40)
) 

BEGIN

  DECLARE vExiste INT;

  DECLARE CONTINUE HANDLER FOR SQLEXCEPTION 
    SET retCode = 1, descErr = 'modificarCliente() - Error General';

  SET retCode = 0;
    SET descErr = 'modificarCliente() - OK';

  SELECT EXISTS(
        SELECT * FROM cli_clientes cli
        WHERE cli.IdCliente = IdCliente
        )
  INTO vExiste;

  IF (vExiste = 1) 
  THEN  
    UPDATE cli_clientes cli
    SET 
    cli.Nombre = Nombre,
    cli.Apellido = Apellido,
    cli.Telefono = Telefono,
    cli.Email = Email
    WHERE cli.IdCliente = IdCliente;
    
	IF (Direccion != '')
	THEN
		UPDATE (cli_clientes cli)
		SET cli.Direccion = Direccion,
			cli.Codigo_postal = Codigo_postal
		WHERE cli.IdCliente = IdCliente;
	ELSE  
		UPDATE (cli_clientes cli)
		SET cli.Direccion = null,
			cli.Codigo_postal = null
		WHERE cli.IdCliente = IdCliente;
		
  END IF;
  ELSE
    SET retCode = 2;
    SET descErr = 'modificarCliente() - El cliente no existe';
  END IF;

END $$
DELIMITER ;


DELIMITER $$
CREATE PROCEDURE traerClientes (
OUT retCode  int(11),
OUT descErr varchar(60)
) 
COMMENT 'Devuelve todos los clientes que no esten dados de baja'
BEGIN

  DECLARE CONTINUE HANDLER FOR SQLEXCEPTION 
    SET retCode = 1, descErr = 'traerClientes() - Error General';

  SET retCode = 0;
    SET descErr = 'traerClientes() - OK';

  SELECT * 
  FROM cli_clientes cli
  WHERE Fecha_Baja IS NULL;
  
END $$
DELIMITER ;

-- SP Usuarios

DELIMITER $$

CREATE PROCEDURE insertarUsuario(
IN IdRol bigint unsigned,
IN Nombre varchar(20),
IN Apellido varchar(20),
IN Usuario varchar(20),
IN pass varchar(20),
OUT retCode  int(11),
OUT descErr varchar(60)
) 
BEGIN

  DECLARE CONTINUE HANDLER FOR SQLEXCEPTION 
    SET retCode = 1, descErr = 'insertarUsuario() - Error General';
    
  SET retCode = 0;
    SET descErr = 'insertarUsuario() - OK';
    
	INSERT INTO pers_personal (IdRol, Nombre, Apellido, Usuario, Pass) 
  VALUES(IdRol, Nombre, Apellido, Usuario, MD5(Pass));
    
END $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE eliminarUsuario(
IN IdPersonal bigint unsigned,
IN Usuario_baja bigint unsigned,
OUT retCode  int(11),
OUT descErr varchar(60)
) 
BEGIN

  DECLARE vExiste INT;
    
  DECLARE CONTINUE HANDLER FOR SQLEXCEPTION 
    SET retCode = 1, descErr = 'eliminarUsuario() - Error General';
    
  SET retCode = 0;
    SET descErr = 'eliminarUsuario() - OK';
    
  SELECT EXISTS(
        SELECT * FROM pers_personal pp
        WHERE pp.IdPersonal = IdPersonal
        )
  INTO vExiste;
    
  IF (vExiste = 1) 
  THEN  
    UPDATE pers_personal pp
    SET pp.Usuario_baja = Usuario_baja,
    Fecha_Baja = now()
    WHERE pp.IdPersonal = IdPersonal;
  ELSE
    SET retCode = 2;
    SET descErr = 'eliminarUsuario() - El usuario no existe';
  END IF;

END $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE traerUsuarios ( 
OUT retCode  int(11),
OUT descErr varchar(60)
) 
COMMENT 'Devuelve todos los usuarios que no esten dados de baja'
BEGIN

  DECLARE CONTINUE HANDLER FOR SQLEXCEPTION 
    SET retCode = 1, descErr = 'traerUsuarios() - Error General';

  SET retCode = 0;
    SET descErr = 'traerUsuarios() - OK';

  SELECT pp.IdPersonal, pp.IdRol, pr.Descripcion, pp.Nombre, pp. Apellido, pp.Usuario, pp.Pass
  FROM pers_personal pp, pers_roles pr
  WHERE pp.IdRol = pr.IdRol
  AND pp.Fecha_Baja IS NULL;
    
END $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE traerUsuariosComunes (
OUT retCode  int(11),
OUT descErr varchar(60)
) 
COMMENT 'Devuelve los usuarios que no son administradores'
BEGIN

  DECLARE CONTINUE HANDLER FOR SQLEXCEPTION 
    SET retCode = 1, descErr = 'traerUsuariosComunes() - Error General';

  SET retCode = 0;
    SET descErr = 'traerUsuariosComunes() - OK';

  SELECT pp.IdPersonal, pp.IdRol, pr.Descripcion, pp.Nombre, pp. Apellido, pp.Usuario, pp.Pass
  FROM pers_personal pp, pers_roles pr
  WHERE pp.IdRol = pr.IdRol
  AND pp.Fecha_Baja IS NULL
    AND pp.IdRol != 1;
    
END $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE traerUsuario(
IN Usuario varchar(20),
OUT retCode  int(11),
OUT descErr varchar(60)
) 
BEGIN

  DECLARE vExiste INT;
    
  DECLARE CONTINUE HANDLER FOR SQLEXCEPTION 
    SET retCode = 1, descErr = 'traerUsuario() - Error General';

  SET retCode = 0;
    SET descErr = 'traerUsuario() - OK';

  SELECT EXISTS(
        SELECT * FROM pers_personal pers
        WHERE pers.Usuario = Usuario
        )
  INTO vExiste;
  
  IF (vExiste = 1) 
  THEN  
    SELECT *
    FROM pers_personal pers
    JOIN pers_roles rol
      ON pers.IdRol = rol.IdRol
    WHERE pers.Usuario = Usuario
    AND pers.Fecha_Baja IS NULL;
  ELSE
    SET retCode = 2;
    SET descErr = 'traerUsuario() - El usuario no existe';
  END IF;

    
END $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE verificarPassUsuario(
IN IdPersonal bigint unsigned,
IN pass varchar (32),
OUT retCode  int(11),
OUT descErr varchar(60)
) 
BEGIN

  DECLARE vExiste INT;
    
  DECLARE CONTINUE HANDLER FOR SQLEXCEPTION 
    SET retCode = 1, descErr = 'verificarPassUsuario() - Error General';

  SET retCode = 0;
    SET descErr = 'verificarPassUsuario() - OK';

  SELECT EXISTS(
        SELECT * FROM pers_personal pers
        WHERE pers.IdPersonal = IdPersonal 
        )
  INTO vExiste;
    
  IF (vExiste = 1) 
  THEN  
    SELECT * 
    FROM pers_personal pp 
    WHERE pp.IdPersonal = IdPersonal 
    AND pp.pass = MD5(pass);
  ELSE
    SET retCode = 2;
    SET descErr = 'verificarPassUsuario() - El usuario no existe';
  END IF;

END $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE modificarUsuario(
IN IdPersonal bigint unsigned,
IN IdRol bigint unsigned,
IN Usuario varchar(20),
IN pass varchar(20),
OUT retCode  int(11),
OUT descErr varchar(60)
) 
BEGIN

  DECLARE vExiste INT;

  DECLARE CONTINUE HANDLER FOR SQLEXCEPTION 
    SET retCode = 1, descErr = 'modificarUsuario() - Error General';
    
  SET retCode = 0;
    SET descErr = 'modificarUsuario() - OK';
    
  SELECT EXISTS(
        SELECT * FROM pers_personal pers
        WHERE pers.IdPersonal = IdPersonal
        )
  INTO vExiste;
  
  IF (vExiste = 1) 
  THEN  
    UPDATE pers_personal pp SET
    pp.IdRol = IdRol,
    pp.Usuario = Usuario, 
    pp.Pass = MD5(Pass)
    WHERE pp.IdPersonal = IdPersonal;
  ELSE
    SET retCode = 2;
    SET descErr = 'modificarUsuario() - El usuario no existe';
  END IF;

END $$
DELIMITER ;


-- SP Piezas

DELIMITER $$
CREATE FUNCTION contarDisponibles(idProdPieza bigint unsigned) 
  RETURNS BIGINT UNSIGNED
BEGIN
  DECLARE Cantidad BIGINT;
    
	SELECT COUNT(pps.idProdPieza)
    INTO Cantidad 
    FROM prod_piezas_stock pps
    WHERE pps.idProdEstado = 1 
	AND pps.idProdPieza = idProdPieza;
  
  RETURN Cantidad;
END $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE traerPiezas (
OUT retCode  int(11),
OUT descErr varchar(60)
) 
COMMENT 'Devuelve todas las piezas con sus marcas'
BEGIN

  DECLARE CONTINUE HANDLER FOR SQLEXCEPTION 
    SET retCode = 1, descErr = 'traerPiezas() - Error General';

  SET retCode = 0;
    SET descErr = 'traerPiezas() - OK';
    
  SELECT *, 
		contarDisponibles(idProdPieza) as Cantidad
  FROM prod_piezas piez
  INNER JOIN prod_marcas marc
	ON piez.idProdMarca = marc.idProdMarca
  WHERE marc.Fecha_Baja IS NULL
	AND piez.Fecha_Baja IS NULL;
    
END $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE getPiezaProveedor( 
IN idSolicitud bigint,
OUT retCode  int(11),
OUT descErr varchar(60)
) 
COMMENT 'Devuelve listado de piezas + el precio de compra'
BEGIN

  DECLARE CONTINUE HANDLER FOR SQLEXCEPTION 
    SET retCode = 1, descErr = 'getPiezaProveedor() - Error General';

  SET retCode = 0;
    SET descErr = 'getPiezaProveedor() - OK';
    	
 SELECT piezas.* , marcas.Nombre as nombreMarca, ppp.precio_compra, solc.cantidad
  FROM prod_piezas piezas 
  LEFT JOIN 
		solc_piezas solc
	ON solc.idProdPieza = piezas.idProdPieza 
  LEFT JOIN
	   prov_precios_piezas ppp
	ON ppp.idProdPieza = piezas.idProdPieza
  LEFT JOIN
	   prod_marcas marcas
	ON marcas.IdProdMarca = piezas.idProdMarca  
  WHERE piezas.Fecha_Baja IS NULL
  AND  solc.idSolcCompra = idSolicitud
  AND  ppp.idProveedor = (select sol.idProveedor from solc_solicitud_compra sol where sol.idSolcCompra = solc.idSolcCompra );
 
											
    
END $$
DELIMITER ;


DELIMITER $$
CREATE PROCEDURE insertarPieza(
IN idProdPieza bigint unsigned,
IN idProdMarca bigint unsigned,
IN idUnico varchar(8),
IN Descripcion varchar(40),
IN Precio_venta decimal(10,4),
IN bajo_stock smallint,
OUT retCode  int(11),
OUT descErr varchar(60)
) 
BEGIN

  DECLARE vExiste INT;

  DECLARE CONTINUE HANDLER FOR SQLEXCEPTION 
    SET retCode = 1, descErr = 'insertarPieza() - Error General';
    
  SET retCode = 0;
    SET descErr = 'insertarPieza() - OK';
    
  SELECT EXISTS(
        SELECT * FROM prod_piezas piez
        WHERE piez.idProdPieza = idProdPieza
        )
  INTO vExiste;
    
  IF (vExiste > 0) 
  THEN  
	SET retCode = 2;
    SET descErr = 'insertarPieza() - La pieza ya existe';
  ELSE
	INSERT INTO prod_piezas (
				idProdPieza, 
                idProdMarca, 
                idUnico, 
                Descripcion, 
                Precio_venta, 
                bajo_stock) 
    VALUES (
			idProdPieza,
            idProdMarca,
            idUnico,
            Descripcion,
            Precio_venta,
            bajo_stock);
  END IF;

END $$
DELIMITER ;


DELIMITER $$
CREATE PROCEDURE eliminarPieza(
IN idProdPieza  bigint unsigned,
IN Usuario_baja bigint unsigned,
OUT retCode  int(11),
OUT descErr varchar(60)
) 
BEGIN

  DECLARE vExiste INT;

  DECLARE CONTINUE HANDLER FOR SQLEXCEPTION 
    SET retCode = 1, descErr = 'eliminarPieza() - Error General';
    
  SET retCode = 0;
    SET descErr = 'eliminarPieza() - OK';
    
  SELECT EXISTS(
        SELECT * FROM prod_piezas piez
        WHERE piez.idProdPieza = idProdPieza
        )
  INTO vExiste;
    
  IF (vExiste = 1) 
  THEN  
    UPDATE prod_piezas piez
    SET piez.Usuario_baja = Usuario_baja,
    Fecha_Baja = now()
    WHERE piez.idProdPieza = idProdPieza;
  ELSE
    SET retCode = 2;
    SET descErr = 'eliminarPieza() - La pieza no existe';
  END IF;


END $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE modificarPieza(
IN idProdPieza  bigint unsigned,
IN idProdMarca bigint unsigned,
IN idUnico varchar (8),
IN Descripcion varchar(40),
IN Precio_venta decimal(10,4),
IN bajoStock smallint,
OUT retCode  int(11),
OUT descErr varchar(60)
) 
BEGIN

  DECLARE vExistePieza INT;
  DECLARE existeMarca INT;

  DECLARE CONTINUE HANDLER FOR SQLEXCEPTION 
    SET retCode = 1, descErr = 'modificarPieza() - Error General';
    
  SET retCode = 0;
    SET descErr = 'modificarPieza() - OK';
    
  SELECT EXISTS(
        SELECT * FROM prod_piezas piez
        WHERE piez.idProdPieza = idProdPieza
        )
  INTO vExistePieza;
    
    SELECT COUNT(*)
  INTO existeMarca
    FROM prod_marcas marc
    WHERE marc.IdProdMarca = idProdMarca;
 
  IF (existeMarca = 1)
    THEN
    
    IF (vExistePieza = 1) 
    THEN  
      UPDATE prod_piezas piez
      SET piez.idProdMarca = idProdMarca,
        piez.idUnico = idUnico,
        piez.Descripcion = Descripcion,
        piez.Precio_venta = Precio_venta,
        piez.bajo_stock = bajoStock
      WHERE piez.idProdPieza = idProdPieza;
    ELSE
      SET retCode = 2;
      SET descErr = 'modificarPieza() - La pieza no existe';
    END IF;
        
    ELSE
    SET retCode = 3, descErr = 'modificarPieza() - Error. Marca inexistente';
    END IF;
    
END $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE altaPiezaStockFisico( 
IN idProdPieza  bigint unsigned,
IN cantidad smallint,
OUT retCode  int(11),
OUT descErr varchar(60)
) 
COMMENT 'Da de alta piezas en el stock fisico, impactando en la prod_piezas_stock'
BEGIN

  DECLARE v_contador int unsigned default 0;

  DECLARE CONTINUE HANDLER FOR SQLEXCEPTION 
    SET retCode = 1, descErr = 'altaPiezaStockFisico() - Error General';
    
  SET retCode = 0;
    SET descErr = 'altaPiezaStockFisico() - OK';
  
  
  START TRANSACTION;
   
   WHILE v_contador < cantidad DO
    
    INSERT INTO prod_piezas_stock (idProdPieza,idProdEstado)
    VALUES (idProdPieza,1); -- idProdEstado = 'Disponible'  
    
    SET v_contador = v_contador+1;
    
   END WHILE;
   
  COMMIT;
    
END $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE insModPrecioCompra ( 
IN idProveedor bigint,
IN idProdPieza bigint,
IN precio_compra decimal(10,4),
OUT retCode  int(11),
OUT descErr varchar(60)
) 
COMMENT 'Inserta o modifica el precio de compra de una pieza para un proveedor'
BEGIN

  DECLARE vExiste INT;

  DECLARE CONTINUE HANDLER FOR SQLEXCEPTION 
    SET retCode = 1, descErr = 'insModPrecioCompra() - Error General';

  SET retCode = 0;
    SET descErr = 'insModPrecioCompra() - OK';
    
  SELECT EXISTS(
        SELECT * FROM prov_precios_piezas ppp
        WHERE ppp.idProveedor = idProveedor
        AND ppp.idProdPieza = idProdPieza
        )
  INTO vExiste;	

  IF (vExiste = 1) 
  THEN 
	-- Existe la pieza. La actualizo
    UPDATE prov_precios_piezas ppp
    SET ppp.precio_compra = precio_compra
    WHERE ppp.idProveedor = idProveedor
    AND ppp.idProdPieza = idProdPieza;
 
  ELSE
	-- No existe la pieza. La creo.
    INSERT INTO prov_precios_piezas
    (idProveedor,idProdPieza,precio_compra)
    VALUES 
    (idProveedor,idProdPieza,precio_compra);
  END IF;
    
END $$
DELIMITER ;


DELIMITER $$
CREATE PROCEDURE getPrecioCompra ( 
IN idProveedor bigint,
IN idProdPieza bigint,
OUT retPrecio_compra decimal(10,4),
OUT retCode  int(11),
OUT descErr varchar(60)
) 
COMMENT 'Obtiene el precio de compra de una pieza'
BEGIN

  DECLARE CONTINUE HANDLER FOR SQLEXCEPTION 
    SET retCode = 1, descErr = 'insModPrecioCompra() - Error General';

  SET retCode = 0;
    SET descErr = 'insModPrecioCompra() - OK';
    
  SELECT ppp.precio_compra INTO retPrecio_compra 
  FROM prov_precios_piezas ppp
  WHERE ppp.idProveedor = idProveedor
  AND ppp.idProdPieza = idProdPieza;
    
END $$
DELIMITER ;



DELIMITER $$
CREATE PROCEDURE getPreciosProveedor( 
IN idProveedor bigint,
IN idProdMarca bigint,
OUT retCode  int(11),
OUT descErr varchar(60)
) 
COMMENT 'Devuelve listado de piezas + el precio de compra'
BEGIN

  DECLARE CONTINUE HANDLER FOR SQLEXCEPTION 
    SET retCode = 1, descErr = 'insModPrecioCompra() - Error General';

  SET retCode = 0;
    SET descErr = 'insModPrecioCompra() - OK';
  
  IF (idProveedor != -1)
  THEN
	  SELECT *
	  FROM prod_piezas piez
	  JOIN
		   prov_precios_piezas ppp
		ON ppp.idProdPieza = piez.idProdPieza
	  WHERE piez.Fecha_Baja IS NULL
	  AND piez.idProdMarca = idProdMarca
	  AND ppp.idProveedor = idProveedor;
  ELSE -- Es una SC generica
	  -- Trae todas las piezas que tengan al menos un precio cargado
		SELECT *
		FROM prod_piezas piez
		JOIN prov_precios_piezas ppp
			ON ppp.idProdPieza = piez.idProdPieza
		WHERE ppp.idProveedor =(
									SELECT minppp.idProveedor 
									FROM prov_precios_piezas minppp
									WHERE minppp.idProdPieza = piez.idProdPieza
									ORDER BY minppp.precio_compra
									LIMIT 1)
		AND piez.idProdMarca = idProdMarca;
  END IF;

END $$
DELIMITER ;


-- SP Electrodomesticos


DELIMITER $$
CREATE PROCEDURE insertarElectrodomestico (
IN idProdMarca bigint unsigned, 
IN Modelo varchar(20), 
IN Descripcion varchar(20),  
OUT retIdElectro  int(11),
OUT retCode  int(11),
OUT descErr varchar(60)
) 

BEGIN

  DECLARE CONTINUE HANDLER FOR SQLEXCEPTION 
    SET retCode = 1, descErr = 'insertarElectrodomestico() - Error General';

  SET retCode = 0;
    SET descErr = 'insertarElectrodomestico() - OK';

  INSERT INTO elec_electrodomesticos (
  idProdMarca,
  Modelo,
  Descripcion
  )
  VALUES (
  idProdMarca, 
  Modelo, 
  Descripcion
  );

  SELECT MAX(IdElectro) 
  INTO retIdElectro
  FROM elec_electrodomesticos;

END $$
DELIMITER ;


DELIMITER $$
CREATE PROCEDURE traerElectrodomesticos ( 
OUT retCode  int(11),
OUT descErr varchar(60)
) 
COMMENT 'Devuelve todos los electrodomesticos con sus marcas'
BEGIN

  DECLARE CONTINUE HANDLER FOR SQLEXCEPTION 
    SET retCode = 1, descErr = 'traerElectrodomesticos() - Error General';

  SET retCode = 0;
    SET descErr = 'traerElectrodomesticos() - OK';
    
  SELECT *
  FROM elec_electrodomesticos elec
    JOIN prod_marcas prod
    ON elec.idProdMarca = prod.idProdMarca
  WHERE elec.Fecha_Baja IS NULL;
    
END $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE eliminarElectrodomestico(
IN IdElectro bigint unsigned,
IN Usuario_baja bigint unsigned,
OUT retCode  int(11),
OUT descErr varchar(60)
) 
BEGIN

  DECLARE vExiste INT;
    
  DECLARE CONTINUE HANDLER FOR SQLEXCEPTION 
    SET retCode = 1, descErr = 'eliminarElectrodomestico() - Error General';
    
  SET retCode = 0;
    SET descErr = 'eliminarElectrodomestico() - OK';
    
  SELECT EXISTS(
        SELECT * FROM elec_electrodomesticos elec
        WHERE elec.IdElectro = IdElectro
        )
  INTO vExiste;
  
  IF (vExiste = 1) 
  THEN  
    UPDATE elec_electrodomesticos elec
    SET elec.Usuario_baja = Usuario_baja,
    Fecha_Baja = now()
    WHERE elec.IdElectro = IdElectro;
  ELSE
    SET retCode = 2;
    SET descErr = 'eliminarElectrodomestico() - El elec. no existe';
  END IF;

END $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE modificarElectrodomestico(
IN IdElectro bigint unsigned,
IN Modelo varchar(20), 
IN Descripcion varchar(40),  
OUT retCode  int(11),
OUT descErr varchar(60)
) 
BEGIN

  DECLARE vExiste INT;
  DECLARE existeMarca INT;

  DECLARE CONTINUE HANDLER FOR SQLEXCEPTION 
    SET retCode = 1, descErr = 'modificarElectrodomestico() - Error General';
    
  SET retCode = 0;
    SET descErr = 'modificarElectrodomestico() - OK';
    
  SELECT EXISTS(
        SELECT * FROM elec_electrodomesticos elec
        WHERE elec.IdElectro = IdElectro
        )
  INTO vExiste;
    
  IF (vExiste = 1) 
  THEN  
    UPDATE elec_electrodomesticos elec
    SET elec.Modelo = Modelo,
      elec.Descripcion = Descripcion
    WHERE elec.IdElectro = IdElectro;
  ELSE
    SET retCode = 2;
    SET descErr = 'modificarElectrodomestico() - El elec. no existe';
  END IF;   
    
END $$
DELIMITER ;

-- SP Marcas

DELIMITER $$
CREATE PROCEDURE insertarMarca (
IN Nombre varchar(20), 
OUT retIdMarca int(11),
OUT retCode  int(11),
OUT descErr varchar(40)
) 

BEGIN

  DECLARE CONTINUE HANDLER FOR SQLEXCEPTION 
    SET retCode = 1, descErr = 'insertarMarca() - Error General';

  SET retCode = 0;
    SET descErr = 'insertarMarca() - OK';

  INSERT INTO prod_marcas (Nombre)
  VALUES (Nombre);

  SELECT MAX(IdProdMarca) 
  INTO retIdMarca
  FROM prod_marcas;

END $$
DELIMITER ;

-- SP Proveedores

DELIMITER $$

CREATE PROCEDURE insertarProveedor(
IN Nombre varchar(20),
IN Contacto varchar(20),
IN Cuit varchar(11),
IN Telefono varchar(20),
IN Mail varchar(30),
OUT retIdProveedor  int(11),
OUT retCode  int(11),
OUT descErr varchar(60)
) 
BEGIN

  DECLARE CONTINUE HANDLER FOR SQLEXCEPTION 
    SET retCode = 1, descErr = 'insertarProveedor() - Error General';
    
  SET retCode = 0;
    SET descErr = 'insertarProveedor() - OK';
    
  INSERT INTO prov_proveedores (
	Nombre,
	Contacto,
	Cuit,
    Telefono,
    Mail
  )
  VALUES (
	Nombre, 
    Contacto,
	Cuit,
    Telefono,
    Mail
  );

  SELECT MAX(IdProveedor) 
  INTO retIdProveedor
  FROM prov_proveedores;

END $$
DELIMITER ;

DELIMITER $$

CREATE PROCEDURE insertarProveedorIncumplidor(
IN Id varchar(20),
OUT retCode  int(11),
OUT descErr varchar(120)
) 
BEGIN

  DECLARE CONTINUE HANDLER FOR SQLEXCEPTION 
    SET retCode = 1, descErr = 'insertarProveedorIncumplidor() - Error General';
    
  SET retCode = 0;
    SET descErr = 'insertarProveedorIncumplidor() - OK';
    
  INSERT INTO prov_proveedores_incumplidores (
	IdProveedor,Fecha
  )
  VALUES (
	Id,now()
  );

END $$
DELIMITER ;

DELIMITER $$

CREATE PROCEDURE modificarProveedor(
IN idProveedor bigint,
IN Nombre varchar(20),
IN Contacto varchar(20),
IN Cuit varchar(11),
IN Telefono varchar(20),
IN Mail varchar(30),
OUT retCode  int(11),
OUT descErr varchar(60)
) 
BEGIN

  DECLARE vExiste INT;

  DECLARE CONTINUE HANDLER FOR SQLEXCEPTION 
    SET retCode = 1, descErr = 'modificarProveedor() - Error General';
    
  SET retCode = 0;
    SET descErr = 'modificarProveedor() - OK';
    
    
  SELECT EXISTS(
        SELECT * FROM prov_proveedores pp
        WHERE pp.IdProveedor = idProveedor
        )
  INTO vExiste;
    
  IF (vExiste = 1) 
  THEN
    UPDATE prov_proveedores pp
    SET 
    pp.Nombre = Nombre,
    pp.Contacto = Contacto,
    pp.Cuit = Cuit,
    pp.Telefono = Telefono,
    pp.Mail = Mail
    WHERE pp.IdProveedor = idProveedor;
  ELSE
    SET retCode = 2;
    SET descErr = 'modificarProveedor() - El proov. no existe';
  END IF;

END $$
DELIMITER ;

DELIMITER $$

CREATE PROCEDURE insertarProveeMarca(
IN idProveedor bigint,
IN idProdMarca bigint,
OUT retCode  int(11),
OUT descErr varchar(60)
) 
BEGIN

  DECLARE CONTINUE HANDLER FOR SQLEXCEPTION 
    SET retCode = 1, descErr = 'insertarProveeMarca() - Error General';
    
  SET retCode = 0;
    SET descErr = 'insertarProveeMarca() - OK';
    
  INSERT INTO prov_provee_marca (
    idProveedor,
    idProdMarca
  )
  VALUES (
  idProveedor,
    idProdMarca
  );

END $$
DELIMITER ;

DELIMITER $$

CREATE PROCEDURE borrarProveeMarca(
IN idProveedor bigint,
IN idProdMarca bigint,
OUT retCode  int(11),
OUT descErr varchar(60)
) 
BEGIN

  DECLARE vExiste INT;

  DECLARE CONTINUE HANDLER FOR SQLEXCEPTION 
    SET retCode = 1, descErr = 'borrarProveeMarca() - Error General';
    
  SET retCode = 0;
    SET descErr = 'borrarProveeMarca() - OK';
    
  SELECT EXISTS(
        SELECT * FROM prov_provee_marca ppm
        WHERE  ppm.idProveedor = idProveedor
        AND ppm.idProdMarca = idProdMarca
        )
  INTO vExiste;
  
  IF (vExiste = 1) 
  THEN  
    DELETE ppm FROM prov_provee_marca ppm
    WHERE ppm.idProveedor = idProveedor
    AND ppm.idProdMarca = idProdMarca;
  ELSE
    SET retCode = 2;
    SET descErr = 'borrarProveeMarca() - La relacion no existe';
  END IF;
   
END $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE traerProveedores ( 
OUT retCode  int(11),
OUT descErr varchar(60)
) 
COMMENT 'Devuelve todos los proveedores'
BEGIN

  DECLARE CONTINUE HANDLER FOR SQLEXCEPTION 
    SET retCode = 1, descErr = 'traerProveedores() - Error General';

  SET retCode = 0;
    SET descErr = 'traerProveedores() - OK';

  SELECT * 
    FROM prov_proveedores
    WHERE Fecha_Baja IS NULL;
    
END $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE traerProveedor ( 
IN idProveedor BIGINT UNSIGNED, 
OUT retCode  int(11),
OUT descErr varchar(60)
) 
COMMENT 'Devuelve proveedor por id'
BEGIN

  DECLARE vExiste INT;

  DECLARE CONTINUE HANDLER FOR SQLEXCEPTION 
    SET retCode = 1, descErr = 'traerProveedor() - Error General';

  SET retCode = 0;
    SET descErr = 'traerProveedor() - OK';

  SELECT EXISTS(
        SELECT * FROM prov_proveedores prov
        WHERE prov.idProveedor = idProveedor
        )
  INTO vExiste;
    
  IF (vExiste = 1) 
  THEN  
    SELECT * 
    FROM prov_proveedores prov
    WHERE 
    prov.idProveedor = idProveedor
    AND Fecha_Baja IS NULL;
  ELSE
    SET retCode = 2;
    SET descErr = 'traerProveedor() - El proveedor no existe';
  END IF;
    
END $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE traerMarcasProvistas (
IN idProveedor bigint unsigned,
OUT retCode  int(11),
OUT descErr varchar(60)
)
BEGIN

  DECLARE CONTINUE HANDLER FOR SQLEXCEPTION 
    SET retCode = 1, descErr = 'traerMarcasProvistas() - Error General';

  SET retCode = 0;
    SET descErr = 'traerMarcasProvistas() - OK';

  SELECT * 
    FROM prov_provee_marca ppm, prod_marcas pm
    WHERE ppm.idProveedor = idProveedor
    AND ppm.idProdMarca = pm.IdProdMarca;
    
END $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE eliminarProveedor(
IN IdProveedor bigint unsigned,
IN Usuario_baja bigint unsigned,
OUT retCode  int(11),
OUT descErr varchar(60)
) 
BEGIN

  DECLARE CONTINUE HANDLER FOR SQLEXCEPTION 
    SET retCode = 1, descErr = 'eliminarProveedor() - Error General';
    
  SET retCode = 0;
    SET descErr = 'eliminarProveedor() - OK';
    
    UPDATE prov_proveedores pp
  SET pp.Usuario_baja = Usuario_baja,
  Fecha_Baja = now()
  WHERE pp.IdProveedor = IdProveedor;
  
  DELETE 
    FROM prov_proveedores_incumplidores
    WHERE prov_proveedores_incumplidores.IdProveedor = IdProveedor;

END $$
DELIMITER ;


DELIMITER $$
CREATE PROCEDURE obtenerProveedorMasBarato ( 
IN idProdPieza bigint unsigned,
OUT retCode  int(11),
OUT descErr varchar(60)
) 
COMMENT 'Dado una pieza, devuelve el proveedor que la tiene mas barata'
BEGIN

  DECLARE CONTINUE HANDLER FOR SQLEXCEPTION 
    SET retCode = 1, descErr = 'obtenerProveedorMasBarato() - Error General';

  SET retCode = 0;
    SET descErr = 'obtenerProveedorMasBarato() - OK';

  SELECT * 
    FROM prov_proveedores prov
    JOIN prov_precios_piezas prec
		ON prov.idProveedor = prec.idProveedor
    WHERE prov.Fecha_Baja IS NULL
    AND prec.idProdPieza = idProdPieza
    ORDER BY prec.precio_compra
    LIMIT 1;
    
END $$
DELIMITER ;

-- SP de stock

DELIMITER $$

CREATE PROCEDURE descontarStock(
IN idOT int(11),
OUT retCode int(11),
OUT descErr varchar(60)
) 
COMMENT 'Asocia las piezas del stock físico a una OT. Se debe llamar únicamente cuando la OT pase a estado Reparada'
BEGIN

  DECLARE sinRegistros INT DEFAULT FALSE;

  DECLARE vidOTItems BIGINT UNSIGNED;
  DECLARE vidProdPieza BIGINT UNSIGNED;
  DECLARE vidProdStock BIGINT UNSIGNED;
  
  -- Busco los registros de la ot_items
  DECLARE datosCur CURSOR 
  FOR SELECT it.idOTItems,
			 it.idProdPieza
  FROM ot_items it
  WHERE it.idOT = idOT;

  DECLARE CONTINUE HANDLER FOR NOT FOUND SET sinRegistros = TRUE;
  
  DECLARE CONTINUE HANDLER FOR SQLEXCEPTION 
    SET retCode = 1, descErr = 'descontarStock() - Error General';
    
  SET retCode = 0;
    SET descErr = 'descontarStock() - OK';
  
  read_loop: LOOP
	FETCH datosCur INTO 
	vidOTItems,
	vidProdPieza;
	IF sinRegistros THEN
		LEAVE read_loop;
	END IF;
    
    -- Tomo un item físico de la prod_piezas_stock en estado Disponible
	SELECT stock.idProdStock
    INTO vidProdStock
    FROM prod_piezas_stock stock 
    WHERE stock.idProdEstado = 1
    AND stock.idProdPieza = vidProdPieza
    LIMIT 1;
    
    -- Agrego en ot_items el valor idProdStock
	UPDATE ot_items it
    SET it.idProdStock = vidProdStock
    WHERE it.idOTItems = vidOTItems;
  
	-- Cambio el estado de la piezas a "vendido"
    UPDATE prod_piezas_stock stock
    SET stock.idProdEstado = 2
    WHERE stock.idProdStock = vidProdStock;
		
  END LOOP read_loop;
      
END $$
DELIMITER ;

DELIMITER $$

CREATE PROCEDURE cambiarEstadoDePieza(	-- mas abarcativo
IN idPieza int(11),
IN idEstadoAnterior int(11),
IN idEstadoNuevo int(11),
OUT retCode int(11),
OUT descErr varchar(60)
) 
BEGIN


  DECLARE vidProdStock BIGINT UNSIGNED;
  
  DECLARE CONTINUE HANDLER FOR SQLEXCEPTION 
    SET retCode = 1, descErr = 'cambiarEstadoDePieza() - Error General';
    
  SET retCode = 0;
    SET descErr = 'cambiarEstadoDePieza() - OK';
  

    -- Tomo un item físico de la prod_piezas_stock en estado viejo
	SELECT MIN(stock.idProdStock)
    INTO vidProdStock
    FROM prod_piezas_stock stock 
    WHERE stock.idProdEstado = idEstadoAnterior
    AND stock.idProdPieza = idPieza;
  
	-- Cambio el estado de la piezas al nuevo
    UPDATE prod_piezas_stock stock
    SET stock.idProdEstado = idEstadoNuevo
    WHERE stock.idProdStock = vidProdStock;
		
      
END $$
DELIMITER ;

-- SP de contabilidad

DELIMITER $$

CREATE PROCEDURE impactarContabilidadSC(
IN idSolcCompra  int(11),
OUT retCode  int(11),
OUT descErr varchar(60)
) 
BEGIN

  DECLARE sinRegistros INT DEFAULT FALSE;
  DECLARE cantRegistros INT;
  DECLARE cantRegSolp INT;
  
  DECLARE vIdSolcPiezas INT;
  DECLARE vidProdPieza INT;
  DECLARE vCantidad INT;
  DECLARE vPrecio_compra INT;
  
  DECLARE vMonto INT;

  -- Busco las piezas y sus precios de compra, asociadas a esa solicitud
  DECLARE datosCur CURSOR 
  FOR SELECT solp.idSolcPiezas,
			 solp.idProdPieza, 
			 solp.cantidad,
             ppp.precio_compra
  FROM solc_solicitud_compra solc,
	   solc_piezas solp,
	   prov_precios_piezas ppp
  WHERE solp.idSolcCompra = idSolcCompra
  AND solp.idProdPieza = ppp.idProdPieza
  AND solc.idSolcCompra = solp.idSolcCompra
  AND solc.idProveedor = ppp.idProveedor;
  
  DECLARE CONTINUE HANDLER FOR NOT FOUND SET sinRegistros = TRUE;
  
  DECLARE CONTINUE HANDLER FOR SQLEXCEPTION 
    SET retCode = 1, descErr = 'impactarContabilidadSC() - Error General';
    
  SET retCode = 0;
    SET descErr = 'impactarContabilidadSC() - OK';
    
   OPEN datosCur;
 
   SELECT FOUND_ROWS() INTO cantRegistros ;
   
   SELECT COUNT(*) 
   INTO cantRegSolp
   FROM solc_piezas solp
   WHERE solp.idSolcCompra = idSolcCompra;
  
   -- Comparo la cantidad de registros con los que hay en la solc_piezas
   IF cantRegistros < cantRegSolp
   THEN
		-- Posible falta o inconsistencia de precios en la prov_precios_piezas
		SET retCode = 2, descErr = 'impactarContabilidadSC() - Error al obtener precios de compra';
   ELSE
	   read_loop: LOOP
		FETCH datosCur INTO 
		vIdSolcPiezas,
		vidProdPieza, 
		vCantidad, 
		vPrecio_compra;
		IF sinRegistros THEN
		  LEAVE read_loop;
		END IF;
		
		-- Multiplico el precio de la pieza por la cantidad que compré
		SELECT vCantidad * vPrecio_compra
		INTO vMonto;
		
		-- Impacto el monto en la cont_libro_diario
		INSERT INTO cont_libro_diario (monto,idOTItems,idSolcPiezas,Fecha)
		VALUES(vMonto,null,vIdSolcPiezas,now());
		
	   END LOOP read_loop;
   
   END IF;
   
   CLOSE datosCur;
      
END $$
DELIMITER ;


DELIMITER $$

CREATE PROCEDURE impactarContabilidadOT( 
IN idOT  int(11),
OUT retCode  int(11),
OUT descErr varchar(60)
) 
BEGIN

  DECLARE sinRegistros INT DEFAULT FALSE;
  
  DECLARE vidOTItems BIGINT UNSIGNED;
  DECLARE vPrecio DECIMAL(10,2);

  -- Busco los items de laOT y sus precios de venta
  DECLARE datosCur CURSOR 
  FOR SELECT it.idOTItems,	 
			 it.precio
  FROM  ot_items it
  WHERE it.idOT = idOT;

  DECLARE CONTINUE HANDLER FOR NOT FOUND SET sinRegistros = TRUE;
 
  DECLARE CONTINUE HANDLER FOR SQLEXCEPTION 
    SET retCode = 1, descErr = 'impactarContabilidadOT() - Error General';
    
  SET retCode = 0;
    SET descErr = 'impactarContabilidadOT() - OK';
    
  OPEN datosCur;
  
  	   read_loop: LOOP
		FETCH datosCur INTO 
		vidOTItems,
		vPrecio;
		IF sinRegistros THEN
		  LEAVE read_loop;
		END IF;
		
		-- Impacto el monto en la cont_libro_diario
		INSERT INTO cont_libro_diario (monto,idOTItems,idSolcPiezas,Fecha)
		VALUES(vPrecio,vidOTItems,null,now());
		
	   END LOOP read_loop;

  CLOSE datosCur;

END $$
DELIMITER ;


-- SP Solicitudes de compra

DELIMITER $$

CREATE PROCEDURE insertarSolicitudCompra(
IN idSolcEstado bigint unsigned,
IN idUsuarioAlta bigint unsigned,
IN idProveedor bigint unsigned,
OUT retIdSC  int(11),
OUT retCode  int(11),
OUT descErr varchar(60)
) 
BEGIN

  DECLARE CONTINUE HANDLER FOR SQLEXCEPTION 
    SET retCode = 1, descErr = 'insertarSolicitudCompra() - Error General';
    
  SET retCode = 0;
    SET descErr = 'insertarSolicitudCompra() - OK';
    
    INSERT INTO solc_solicitud_compra
    ( 
  idSolcEstado, -- el estado inicial es 1
  idUsuarioAlta,
  idProveedor,
  Fecha_Alta
  )
    VALUES 
  (
  idSolcEstado,
  idUsuarioAlta,
  idProveedor,
  NOW()
  );
    
  SELECT MAX(idSolcCompra) 
  INTO retIdSC
  FROM solc_solicitud_compra;
    
END $$
DELIMITER ;


DELIMITER $$

CREATE PROCEDURE insertarPiezasSolicitud(
IN idSolcCompra bigint unsigned,
IN idProdPieza bigint unsigned,
IN cantidad int,
OUT retCode  int(11),
OUT descErr varchar(60)
) 
BEGIN

  DECLARE CONTINUE HANDLER FOR SQLEXCEPTION 
    SET retCode = 1, descErr = 'insertarPiezasSolicitud() - Error General';
    
  SET retCode = 0;
    SET descErr = 'insertarPiezasSolicitud() - OK';
    
    INSERT INTO solc_piezas
    ( 
  idSolcCompra,
  idProdPieza,
  cantidad
  )
    VALUES 
  (
  idSolcCompra,
  idProdPieza,
  cantidad
  );
        
END $$
DELIMITER ;


DELIMITER $$

CREATE PROCEDURE traerSolicitudes(
OUT retCode  int(11),
OUT descErr varchar(60)
) 
COMMENT 'Devuelve todos las solicitudes'
BEGIN

  DECLARE CONTINUE HANDLER FOR SQLEXCEPTION 
    SET retCode = 1, descErr = 'traerSolicitudes() - Error General';
    
  SET retCode = 0;
    SET descErr = 'traerSolicitudes() - OK';
  
  SELECT *
  FROM solc_solicitud_compra solc
  JOIN prov_proveedores prov
    ON solc.idProveedor = prov.IdProveedor
  JOIN solc_estados est
    ON solc.idSolcEstado = est.idSolcEstado;    
    
END $$
DELIMITER ;


DELIMITER $$

CREATE PROCEDURE traerSolicitudXid(
IN idSolcCompra bigint unsigned,
OUT retCode  int(11),
OUT descErr varchar(60)
) 
COMMENT 'Devuelve la solicitud por id'
BEGIN

  DECLARE CONTINUE HANDLER FOR SQLEXCEPTION 
    SET retCode = 1, descErr = 'traerSolicitudes() - Error General';
    
  SET retCode = 0;
    SET descErr = 'traerSolicitudes() - OK';
  
  SELECT *
  FROM solc_solicitud_compra solc
  JOIN prov_proveedores prov
    ON solc.idProveedor = prov.IdProveedor
  JOIN solc_estados est
    ON solc.idSolcEstado = est.idSolcEstado
  WHERE solc.idSolcCompra = idSolcCompra;    
    
END $$
DELIMITER ;

DELIMITER $$

CREATE PROCEDURE traerProcesadasPorFecha(
IN estado int,
IN inicio date,
IN fin date,
OUT retCode  int(11),
OUT descErr varchar(60)
) 

BEGIN

  DECLARE CONTINUE HANDLER FOR SQLEXCEPTION 
    SET retCode = 1, descErr = 'traerProcesadasPorFecha() - Error General';
    
  SET retCode = 0;
    SET descErr = 'traerProcesadasPorFecha() - OK';
  
  SELECT *
  FROM solc_solicitud_compra solc
  JOIN prov_proveedores prov
    ON solc.idProveedor = prov.IdProveedor
  JOIN solc_estados est
    ON solc.idSolcEstado = est.idSolcEstado
  WHERE solc.idSolcEstado = estado
  AND solc.Fecha_Procesada BETWEEN inicio AND fin
  ORDER BY solc.Fecha_Procesada;    
    
END $$
DELIMITER ;



DELIMITER $$

CREATE PROCEDURE eliminarPiezasSolicitud(
IN idSolcCompra bigint unsigned,
OUT retCode  int(11),
OUT descErr varchar(60)
) 
BEGIN

  DECLARE CONTINUE HANDLER FOR SQLEXCEPTION 
    SET retCode = 1, descErr = 'eliminarPiezasSolicitud() - Error General';
    
  SET retCode = 0;
    SET descErr = 'eliminarPiezasSolicitud() - OK';
    
  DELETE solc FROM solc_piezas solc
  WHERE solc.idSolcCompra = idSolcCompra;
    
END $$
DELIMITER ;


DELIMITER $$

CREATE PROCEDURE modificarSolicitudCompra(
IN idSolcCompra  int(11),
IN idSolcEstado bigint unsigned,
IN idUsuarioAlta bigint unsigned,
IN idProveedor bigint unsigned,
OUT retCode  int(11),
OUT descErr varchar(60)
) 
BEGIN

  DECLARE vExiste INT;

  DECLARE CONTINUE HANDLER FOR SQLEXCEPTION 
    SET retCode = 1, descErr = 'modificarSolicitudCompra() - Error General';
    
  SET retCode = 0;
    SET descErr = 'modificarSolicitudCompra() - OK';
    
  SELECT EXISTS(
        SELECT * FROM solc_solicitud_compra solc
        WHERE solc.idSolcCompra = idSolcCompra
        )
  INTO vExiste;
    
  IF (vExiste = 1) 
  THEN
    UPDATE solc_solicitud_compra solc
    SET solc.idSolcEstado = idSolcEstado,
      solc.idUsuarioAlta = idUsuarioAlta,
      solc.idProveedor = idProveedor
    WHERE solc.idSolcCompra = idSolcCompra;
  ELSE
    SET retCode = 2;
    SET descErr = 'modificarSolicitudCompra() - La SC no existe';
  END IF;
    
END $$
DELIMITER ;

DELIMITER $$

CREATE PROCEDURE cambiarEstadoSC(
IN idSolcCompra  int(11),
IN idSolcEstado bigint unsigned,
OUT retCode  int(11),
OUT descErr varchar(60)
) 
BEGIN

  DECLARE vExiste INT;
  DECLARE vEstadoActual INT;
    DECLARE vEsCambioValido INT DEFAULT 0;

  DECLARE CONTINUE HANDLER FOR SQLEXCEPTION 
    SET retCode = 1, descErr = 'cambiarEstadoSC() - Error General';
    
  SET retCode = 0;
    SET descErr = 'cambiarEstadoSC() - OK';
    
  
  SELECT EXISTS(
        SELECT * FROM solc_solicitud_compra solc
        WHERE solc.idSolcCompra = idSolcCompra
        )
  INTO vExiste;
  
  IF (vExiste = 1) 
  THEN
  
    -- Traigo el estado actual
    SELECT solc.idSolcEstado
    INTO vEstadoActual
        FROM solc_solicitud_compra solc
        WHERE solc.idSolcCompra = idSolcCompra;
        
        -- Validación de cambio de estados
        IF (vEstadoActual = 1 and idSolcEstado = 2) -- Si pasa de Ingresada a Enviada
        THEN
			SET vEsCambioValido = 1;
        END IF;
        
    IF (vEstadoActual in (1,2) and idSolcEstado = 3) -- Si pasa de Ingrsada o Enviada a Procesada
        THEN
			-- Registro el pago al proveedor en el libro diario
			-- CALL impactarContabilidadSC(idSolcCompra,@retCode,@descErr);
            -- Doy de baja las alertas que esten pendientes
            CALL darDeBajaAlertasPendientes(idSolcCompra,@retCode,@descErr); 
			SET vEsCambioValido = 1;
        END IF;
        
    IF (vEstadoActual = 1 and idSolcEstado = 4) -- Si pasa de Ingresada a Cancelada
        THEN
			SET vEsCambioValido = 1;
        END IF;
        
        
	-- Si el cambio de estado es válido, hago update
	IF (vEsCambioValido = 1)
	THEN
      UPDATE solc_solicitud_compra solc
      SET solc.idSolcEstado = idSolcEstado,
      solc.Fecha_Procesada = now()	-- jony
      WHERE solc.idSolcCompra = idSolcCompra;    
    ELSE
      SET retCode = 2;
      SET descErr = 'cambiarEstadoSC() - Cambio de estado inválido';
    END IF;
    
    ELSE
    SET retCode = 2;
    SET descErr = 'cambiarEstadoSC() - La SC no existe';
  END IF;
    
END $$
DELIMITER ;


-- SP Ordenes de trabajo

DELIMITER $$
CREATE PROCEDURE cambiarVencimientoPresup ( 
IN idOT bigint unsigned,
IN vencPresup date, 
OUT retCode  int(11),
OUT descErr varchar(60)
) 
COMMENT 'Cambia el vencimiento al presupuesto de una OT determinada.'
BEGIN

  DECLARE CONTINUE HANDLER FOR SQLEXCEPTION 
    SET retCode = 1, descErr = 'cambiarVencimientoPresup() - Error General';

  SET retCode = 0;
    SET descErr = 'cambiarVencimientoPresup() - OK';
  
  UPDATE ot_ordenes_trabajo ot
  SET ot.vencPresup = vencPresup
  WHERE ot.idOt = idOT;
  
        
END $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE agregarPiezasUsadasOT (
IN idOT bigint unsigned,
IN idPieza bigint unsigned, 
OUT retCode  int(11),
OUT descErr varchar(80)
)

BEGIN
  
    DECLARE vidProdStock BIGINT UNSIGNED;
    
    DECLARE CONTINUE HANDLER FOR SQLEXCEPTION 
    SET retCode = 1, descErr = 'agregarPiezasUsadasOT() - Error General';

  SET retCode = 0;
    SET descErr = 'agregarPiezasUsadasOT() - OK';
    
    SELECT MIN(stock.idProdStock)
    INTO vidProdStock
    FROM prod_piezas_stock stock 
    WHERE stock.idProdEstado = 1
    AND stock.idProdPieza = idPieza;
    
    UPDATE prod_piezas_stock stock
    SET stock.idProdEstado = 2
    WHERE stock.idProdStock = vidProdStock;
    
    INSERT INTO ot_piezas_usadas
    (idProdStock, idOT)
    VALUES
    (vidProdStock, idOT);

END $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE agregarItemOt ( 
IN idOT bigint unsigned,
IN idProdPieza bigint unsigned, 
IN horasTrabajo integer,
IN IdCostoV bigint unsigned,
IN precio decimal(10,2),
IN esPresup boolean,
OUT retCode  int(11),
OUT descErr varchar(80)
) 

BEGIN

  DECLARE CONTINUE HANDLER FOR SQLEXCEPTION 
    SET retCode = 1, descErr = 'agregarItemOt() - Error General';

  SET retCode = 0;
    SET descErr = 'agregarItemOt() - OK';
  
  IF (idProdPieza IS NOT NULL AND IdCostoV IS NOT NULL)
  THEN
    SET retCode = 2;
    SET descErr = 'agregarItemOt() - Error. No se pueden recibir ambos parámetros a la vez';
  ELSE
    IF (idProdPieza IS NOT NULL)  
    THEN
      INSERT INTO ot_items (idOT,idProdPieza,precio,esPresup)
      VALUES (idOT,idProdPieza,precio,esPresup);
    END IF;
    
    IF (IdCostoV IS NOT NULL)
        THEN
      INSERT INTO ot_items(idOT,IdCostoV,horasTrabajo,precio,esPresup)
      VALUES (idOT,IdCostoV,horasTrabajo,precio,esPresup);
    END IF;
  END IF;
        
END $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE traerOTsConEstado ( 
IN idEstado bigint unsigned,
OUT retCode  int(11),
OUT descErr varchar(60)
) 
COMMENT 'Devuelve todas las OT y sus datos con un estado en particular'
BEGIN

  DECLARE CONTINUE HANDLER FOR SQLEXCEPTION 
    SET retCode = 1, descErr = 'traerOTsConEstado() - Error General';

  SET retCode = 0;
    SET descErr = 'traerOTsConEstado() - OK';

  SELECT * 
    FROM ot_ordenes_trabajo ot
  JOIN elec_electrodomesticos elec
    ON elec.IdElectro = ot.idElectro
  JOIN prod_marcas marca
    ON elec.idProdMarca=marca.idProdMarca
  JOIN cli_clientes cli
    ON  ot.idCliente = cli.IdCliente
  JOIN pers_personal pers1
    ON ot.idUsuarioAlta = pers1.IdPersonal
  LEFT JOIN pers_personal pers2
    ON ot.idTecnicoAsoc = pers2.IdPersonal 
  LEFT JOIN pers_personal pers3
    ON ot.idTecnicoPresup = pers3.IdPersonal 
  LEFT JOIN env_fleteros env
    ON  ot.idFleteroAsoc = env.IdFletero
  WHERE ot.estado_orden = idEstado;
        
END $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE traerItemsOT ( 
IN idOt bigint unsigned,
OUT retCode  int(11),
OUT descErr varchar(60)
) 
COMMENT 'Devuelve los items de una OT'
BEGIN

  DECLARE CONTINUE HANDLER FOR SQLEXCEPTION 
    SET retCode = 1, descErr = 'traerItemsOT() - Error General';

  SET retCode = 0;
    SET descErr = 'traerItemsOT() - OK';
    
        
  SELECT *
    FROM ot_items it
  WHERE  it.idOt = idOt;
        
END $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE traerOT (
IN idOt bigint unsigned,
OUT retCode  int(11),
OUT descErr varchar(60)
) 
COMMENT 'Devuelve una OT en particular'
BEGIN

  DECLARE CONTINUE HANDLER FOR SQLEXCEPTION 
    SET retCode = 1, descErr = 'traerOT() - Error General';

  SET retCode = 0;
    SET descErr = 'traerOT() - OK';

  SELECT * 
    FROM ot_ordenes_trabajo ot
    WHERE ot.idOt = idOt;
    
END $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE crearOT(
IN idCliente bigint unsigned,
IN idElectro bigint unsigned,
IN descripcion text,
IN idUsuarioAlta bigint unsigned,
IN esDelivery boolean,
IN costoDeEnvio decimal(5,2),
IN domicilioEntrega varchar(20),
in codigoPostalEntrega int,
OUT retIdOT  int(11),
OUT retCode  int(11),
OUT descErr varchar(60)
) 

BEGIN

	DECLARE CONTINUE HANDLER FOR SQLEXCEPTION 
    SET retCode = 1, descErr = 'crearOT() - Error General';
    
	SET retCode = 0;
    SET descErr = 'crearOT() - OK';
    
    INSERT INTO ot_ordenes_trabajo (
    idCliente,
    idElectro,
    descripcion,
    idUsuarioAlta,
    esDelivery,
    costoDeEnvio,
	estado_orden
    ) VALUES (
    idCliente,
    idElectro,
    descripcion,
    idUsuarioAlta,
    esDelivery,
    costoDeEnvio,
	1
    );

    
    SELECT MAX(idOT) 
	INTO retIdOT
	FROM ot_ordenes_trabajo;
    
    IF (domicilioEntrega != '')
	THEN
		UPDATE (ot_ordenes_trabajo)
		SET domicilio = domicilioEntrega,
			codigoPostal = codigoPostalEntrega
		WHERE idOT = retIdOT;
	END IF;
  
  -- Inserto en la tabla de histórico
  
	INSERT INTO ot_estados_historico(
	idOT,
	estado_orden,
	Fecha_Alta
	)
	VALUES (
	retIdOT,
	1,
	now()
	);
	
    
END $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE traerOTs (
OUT retCode  int(11),
OUT descErr varchar(60)
) 
BEGIN

  DECLARE CONTINUE HANDLER FOR SQLEXCEPTION 
    SET retCode = 1, descErr = 'traerOTs() - Error General';

  SET retCode = 0;
    SET descErr = 'traerOTs() - OK';

  SELECT * 
    FROM ot_ordenes_trabajo;
    
END $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE cambiarEstadoOT(
IN idOT bigint unsigned, 
IN idEstadoPosible bigint unsigned, 
OUT retCode  int(11),
OUT descErr varchar(60)
) 
BEGIN

  DECLARE vExiste INT;
  DECLARE vEstadoActual INT;
  DECLARE vEsCambioValido INT DEFAULT 0;

  DECLARE CONTINUE HANDLER FOR SQLEXCEPTION 
    SET retCode = 1, descErr = 'cambiarEstadoOT() - Error General';
    
  SET retCode = 0;
    SET descErr = 'cambiarEstadoOT() - OK';
    
  SELECT EXISTS(
        SELECT * FROM ot_ordenes_trabajo ot 
        WHERE ot.idOT = idOT
        )
  INTO vExiste;
    
  IF (vExiste = 1) 
  THEN
   
	-- Traigo el estado actual
 /*   
    SELECT his.estado_orden
    INTO vEstadoActual
	FROM ot_estados_historico his
	WHERE his.idOT = idOT
    AND his.Fecha_Baja IS NULL;
*/ -- TODO: Verificar historico

    SELECT ot.estado_orden
    INTO vEstadoActual
	FROM ot_ordenes_trabajo ot
	WHERE ot.idOT = idOT;

	-- Validación de cambio de estados
	IF (vEstadoActual = 1 and idEstadoPosible = 2) -- Si pasa de Ingresada a Presupuestada
	THEN
      SET vEsCambioValido = 1;
	END IF;
    
	IF (vEstadoActual = 2 and idEstadoPosible in (3,4)) -- Si pasa de Presupuestada a Aprobada o Desaprobada
	THEN
      SET vEsCambioValido = 1;
	END IF;
  
  	IF (vEstadoActual = 3 and idEstadoPosible in (5,6)) -- Si pasa de Aprobada a En reparación o a En espera de piezas
	THEN
      SET vEsCambioValido = 1;
	END IF;
    
	IF (vEstadoActual in (3,5) and idEstadoPosible = 7) -- Si pasa de Aprobada o En reparación a Reparada
	THEN
	  -- Seteo fecha de reparación
      UPDATE ot_ordenes_trabajo ot
      SET ot.fechaReparado = now() 
      WHERE ot.idOT = idOT;
      
      -- Tomo los items del stock y los utilizo
 --     CALL descontarStock(idOT,@retCode,@descErr); TODO: Verificar
      
      SET vEsCambioValido = 1;
	END IF;

	IF (vEstadoActual = 5 and idEstadoPosible = 6) -- Si pasa de En reparación a En espera de piezas
	THEN
      SET vEsCambioValido = 1;
	END IF;
    
	IF (vEstadoActual = 7 and idEstadoPosible = 9) -- Si pasa de Reparada a Despachada
	THEN
      SET vEsCambioValido = 1;
	END IF;
    
	IF (vEstadoActual in (7,9) and idEstadoPosible in (9,10)) -- Si pasa de Despachada o Reparada a Reparada o Entregada
	THEN
	  -- Registro el cobro en el libro diario
-- 	  CALL impactarContabilidadOT(idOT,@retCode,@descErr);
      SET vEsCambioValido = 1;
	END IF;
    
    IF (vEsCambioValido = 1)
    THEN
		
        UPDATE ot_estados_historico his
        SET his.Fecha_Baja = now()
        WHERE his.idOT = idOT
        AND his.Fecha_Baja IS NULL;
        
		UPDATE ot_ordenes_trabajo ot 
		SET estado_orden=idEstadoPosible 
		WHERE ot.idOT = idOT;

		INSERT INTO ot_estados_historico(
		idOT,
		estado_orden,
		Fecha_Alta
		)
		VALUES (
		idOT,
		idEstadoPosible,
		now()
		);
	
    ELSE
      SET retCode = 2;
      SET descErr = 'cambiarEstadoOt() - Cambio de estado inválido';
    END IF;

  ELSE
    SET retCode = 2;
    SET descErr = 'cambiarEstadoOT() - La OT no existe';
  END IF;

END $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE traerOTasignadasA ( 

IN idFletero bigint unsigned,

OUT retCode  int(11),
OUT descErr varchar(60)
) 
BEGIN

  DECLARE CONTINUE HANDLER FOR SQLEXCEPTION 
    SET retCode = 1, descErr = 'traerOTasignadasA() - Error General';

  SET retCode = 0;
    SET descErr = 'traerOTasignadasA() - OK';

  SELECT * 
    FROM ot_ordenes_trabajo ot
  JOIN elec_electrodomesticos elec
    ON elec.IdElectro = ot.idElectro
  JOIN prod_marcas marca
    ON elec.idProdMarca=marca.idProdMarca
  JOIN cli_clientes cli
    ON  ot.idCliente = cli.IdCliente
  JOIN pers_personal pers1
    ON ot.idUsuarioAlta = pers1.IdPersonal
  LEFT JOIN pers_personal pers2
    ON ot.idTecnicoAsoc = pers2.IdPersonal 
  LEFT JOIN pers_personal pers3
    ON ot.idTecnicoPresup = pers3.IdPersonal 
  LEFT JOIN env_fleteros env
    ON  ot.idFleteroAsoc = env.IdFletero
  WHERE ot.idFleteroAsoc = idFletero;
        
END $$
DELIMITER ;


-- SP Accesorios

DELIMITER $$
CREATE PROCEDURE getCostoVariable(
IN Tipo_Costo varchar(30), 
OUT Precio decimal(10,2), 
OUT retCode  int(11),
OUT descErr varchar(60)
) 
BEGIN

  DECLARE CONTINUE HANDLER FOR SQLEXCEPTION 
    SET retCode = 1, descErr = 'getCostoVariable() - Error General';
    
  SET retCode = 0;
    SET descErr = 'getCostoVariable() - OK';
    
  SELECT CAST(cost.Precio  AS Decimal (10,2)) INTO Precio
  FROM ot_costos_variables cost
  WHERE cost.Tipo_Costo = Tipo_Costo; -- COSTO_HORA_MANO_OBRA 

END $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE insertarFletero (
IN Nombre varchar(20), 
IN Apellido varchar(20),
IN Celular varchar(20),
IN RegistroConducir varchar(12),
IN FechaVencimientoRegistro date,
IN IdVehiculo varchar(10),  
OUT retIdFletero  int(11),
OUT retCode int(11),
OUT descErr varchar(40)
) 

BEGIN

  DECLARE CONTINUE HANDLER FOR SQLEXCEPTION 
    SET retCode = 1, descErr = 'insertarFletero() - Error General';

  SET retCode = 0;
    SET descErr = 'insertarFletero() - OK';

  INSERT INTO env_fleteros (
  Nombre,
  Apellido,
  Celular,
  RegistroConducir,
  FechaVencimientoRegistro,
  IdVehiculo
  )
  VALUES (
  Nombre,
  Apellido,
  Celular,
  RegistroConducir,
  FechaVencimientoRegistro,
  IdVehiculo
  );

  SELECT MAX(IdFletero) 
  INTO retIdFletero
  FROM env_fleteros;

END $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE borrarFletero(
IN IdFletero bigint unsigned,
IN Usuario_baja bigint unsigned,
OUT retCode  int(11),
OUT descErr varchar(60)
) 
BEGIN

  DECLARE CONTINUE HANDLER FOR SQLEXCEPTION 
    SET retCode = 1, descErr = 'borrarFletero() - Error General';
    
  SET retCode = 0;
    SET descErr = 'borrarFletero() - OK';
    
    UPDATE env_fleteros f
  SET f.Usuario_baja = Usuario_baja,
  Fecha_Baja = now()
  WHERE f.IdFletero = IdFletero;

END $$
DELIMITER ;

-- SP accesorios

DELIMITER $$
CREATE PROCEDURE obtenerParametro(
IN clave varchar (40),
OUT valor1 varchar(40),
OUT valor2 varchar(40),
OUT retCode  int(11),
OUT descErr varchar(60)
) 
BEGIN

  DECLARE CONTINUE HANDLER FOR SQLEXCEPTION 
    SET retCode = 1, descErr = 'obtenerParametro() - Error General';

  SET retCode = 0;
    SET descErr = 'obtenerParametro() - OK';

    SELECT acc.parm_valor1, acc.parm_valor2
    INTO valor1, valor2
    FROM acc_parametros 
    WHERE parametro = clave;
    
END $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE updateClaveDesbloqueo(
IN clave VARCHAR(32),
OUT retCode  int(11),
OUT descErr varchar(60)
) 
BEGIN

  DECLARE CONTINUE HANDLER FOR SQLEXCEPTION 
    SET retCode = 1, descErr = 'updateClaveDesbloqueo() - Error General';
    
  SET retCode = 0;
    SET descErr = 'updateClaveDesbloqueo() - OK';
    
    UPDATE acc_parametros 
    SET parm_valor1 = MD5(clave)
    WHERE parametro = 'HASHCODE_PASSWORD';

END $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE verificarClaveReest(
IN clave varchar (32),
OUT retCode  int(11),
OUT descErr varchar(60)
) 
BEGIN

  DECLARE CONTINUE HANDLER FOR SQLEXCEPTION 
    SET retCode = 1, descErr = 'verificarClaveReest() - Error General';

  SET retCode = 0;
    SET descErr = 'verificarClaveReest() - OK';

    SELECT * FROM acc_parametros 
    WHERE parametro = 'HASHCODE_PASSWORD' 
    AND parm_valor1 = MD5(clave);
    
END $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE reestablecerSuperUsuario(
OUT retCode  int(11),
OUT descErr varchar(60)
) 
BEGIN

  DECLARE CONTINUE HANDLER FOR SQLEXCEPTION 
    SET retCode = 1, descErr = 'reestablecerSuperUsuario() - Error General';

  SET retCode = 0;
    SET descErr = 'reestablecerSuperUsuario() - OK';

    UPDATE pers_personal 
  SET Usuario = 'admin', pass = MD5('admin') 
    WHERE IdRol = 1;
    
    UPDATE acc_parametros
    SET parm_valor1 = ''
    WHERE parametro = 'HASHCODE_PASSWORD';
    
END $$
DELIMITER ;

-- SP Reportes

DELIMITER $$
CREATE PROCEDURE RepProveedoresIncumplidores(
IN fechaDesde date,
IN fechaHasta date,
OUT retCode  int(11),
OUT descErr varchar(60)
) 
COMMENT 'Reporte ranking de "Proveedores Incumplidores"'
BEGIN

  DECLARE CONTINUE HANDLER FOR SQLEXCEPTION 
    SET retCode = 1, descErr = 'Error General';
  
  SET retCode = 0;
    SET descErr = 'RepProveedoresIncumplidores() - OK';
    
  SELECT prov.Nombre, prov.Cuit, COUNT(*) as Cantidad
	FROM prov_proveedores_incumplidores inc
    INNER JOIN prov_proveedores prov on inc.IdProveedor = prov.IdProveedor
    WHERE inc.Fecha BETWEEN fechaDesde AND fechaHasta
    GROUP BY prov.Nombre
    ORDER BY Cantidad DESC;
	
    
END $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE RepElectrodomesticos(
IN variableAjuste int(10),
IN fechaDesde date,
IN fechaHasta date,
OUT retCode  int(11),
OUT descErr varchar(60)
) 
COMMENT 'Reporte ranking de "Electrodomésticos más reparados"'
BEGIN

  DECLARE CONTINUE HANDLER FOR SQLEXCEPTION 
    SET retCode = 1, descErr = 'RepPiezas() - Error General';
  
  SET retCode = 0;
    SET descErr = 'RepElectrodomesticos() - OK';
	
	
SELECT * FROM
				(
				  SELECT m.Nombre as Marca, 
						 el.Descripcion as Descripcion,
						 el.Modelo as Modelo,
						 COUNT(*) AS 'Cantidad',
						 (COUNT(ot.idElectro)* 100 / 
                         (SELECT COUNT(*) FROM ot_ordenes_trabajo ot 
                         WHERE ot.estado_orden IN (7,9,10) 
						AND ot.fechaReparado  BETWEEN  fechaDesde AND fechaHasta)
                         )  AS Porcentaje
				  FROM ot_ordenes_trabajo ot 
				  INNER JOIN elec_electrodomesticos el 
					ON ot.idElectro = el.idElectro
				  INNER JOIN prod_marcas m 
					ON el.idProdMarca = m.IdProdMarca
				  WHERE  ot.estado_orden IN (7,9,10)  
				  AND ot.fechaReparado  BETWEEN  fechaDesde AND fechaHasta
				  GROUP BY ot.idElectro
				  ORDER BY Porcentaje DESC
				  ) principal
	WHERE principal.Porcentaje > variableAjuste
UNION ALL 
	SELECT * FROM
				(
				  SELECT 'Otros' as Marca, 
						 '' as Descripcion,
						 '' as Modelo,                   
						(					SELECT COUNT(*) FROM  (
											SELECT m.Nombre as Marca, 
											 el.Descripcion as Descripcion,
											 el.Modelo as Modelo,
											 COUNT(*) AS 'Cantidad',
											 (COUNT(ot.idElectro)* 100 / 
											 (SELECT COUNT(*) FROM ot_ordenes_trabajo ot 
											 WHERE ot.estado_orden IN (7,9,10) 
											 AND ot.fechaReparado  BETWEEN  fechaDesde AND fechaHasta)
											 )  AS 'Porcentaje'
											FROM ot_ordenes_trabajo ot 
											INNER JOIN elec_electrodomesticos el ON ot.idElectro = el.idElectro
											INNER JOIN prod_marcas m ON el.idProdMarca = m.IdProdMarca
											WHERE  ot.estado_orden IN (7,9,10)
										  AND ot.fechaReparado  BETWEEN  fechaDesde AND fechaHasta
											GROUP BY ot.idElectro
											ORDER BY 3 DESC
											) as principal
											WHERE principal.Porcentaje < variableAjuste
						) as Cantidad ,
						(
											(SELECT COUNT(*)    FROM  (
											SELECT m.Nombre as Marca, 
											 el.Descripcion as Descripcion,
											 el.Modelo as Modelo,
											 COUNT(*) AS 'Cantidad',
											 (COUNT(ot.idElectro)* 100 / 
											 (SELECT COUNT(*) FROM ot_ordenes_trabajo ot 
											 WHERE ot.estado_orden IN (7,9,10) 
										 AND ot.fechaReparado  BETWEEN  fechaDesde AND fechaHasta)
											 )  AS Porcentaje
											FROM ot_ordenes_trabajo ot 
											INNER JOIN elec_electrodomesticos el ON ot.idElectro = el.idElectro
											INNER JOIN prod_marcas m ON el.idProdMarca = m.IdProdMarca
											WHERE  ot.estado_orden IN (7,9,10)
											  AND ot.fechaReparado  BETWEEN  fechaDesde AND fechaHasta
											GROUP BY ot.idElectro
											ORDER BY 3 DESC
											) as principal
											WHERE principal.Porcentaje < variableAjuste
											)
                        * 100 / 
                         (SELECT COUNT(*) FROM ot_ordenes_trabajo ot 
                         WHERE ot.estado_orden IN (7,9,10) 
						  AND ot.fechaReparado  BETWEEN  fechaDesde AND fechaHasta)
                         ) as Porcentaje
                    
                ORDER BY Porcentaje DESC
				  ) otros
	WHERE otros.Porcentaje > 0;
    
END $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE RepPiezas(
IN variableAjuste int(10),
IN fechaDesde date,
IN fechaHasta date,
OUT retCode  int(11),
OUT descErr varchar(60)
) 
COMMENT 'Reporte ranking de "Piezas más insumidas"'
BEGIN

 DECLARE CONTINUE HANDLER FOR SQLEXCEPTION 
    SET retCode = 1, descErr = 'RepPiezas() - Error General';

 SET retCode = 0;
    SET descErr = 'RepPiezas() - OK';

SELECT * FROM
  (
	  SELECT pps.idProdPieza AS 'Pieza',
				 pm.Nombre AS 'Marca',
				 pp.Descripcion AS 'Descripcion',
				 COUNT(*) AS 'Cantidad',
				 (
					COUNT(pps.idProdPieza)* 100 / 
					(SELECT COUNT(*) 
					 FROM prod_piezas_stock pps 
					 WHERE pps.idProdEstado IN (2))
				 ) AS 'Porcentaje'
	 FROM prod_piezas_stock pps
	 INNER JOIN ot_piezas_usadas pusd
		ON pps.idProdStock = pusd.idProdStock
	 INNER JOIN ot_estados_historico his
		ON his.idOT = pusd.idOT
	 INNER JOIN prod_piezas pp 
		ON pps.idProdPieza = pp.idProdPieza
	 INNER JOIN prod_marcas pm 
		ON pp.idProdMarca = pm.IdProdMarca
	 WHERE pps.idProdEstado = 2 -- Pieza vendida
     AND his.estado_orden = 7 -- Orden reparada
     AND his.Fecha_Alta BETWEEN fechaDesde AND fechaHasta -- Filtro por fecha
	 GROUP BY pps.idProdPieza
	 ORDER BY Porcentaje DESC
  ) AS principal
  WHERE principal.Porcentaje > variableAjuste
  
UNION ALL 

SELECT * FROM
(
	SELECT 'Otras' AS 'Pieza',
		   'Otras' AS 'Marca', 
		   'Otras' AS 'Descripcion',
		   SUM(Cantidad) AS 'Cantidad', 
		   SUM(Porcentaje) AS 'Porcentaje' 
	FROM
	  (
		  SELECT 
				COUNT(*) AS 'Cantidad',
				(
					COUNT(pps.idProdPieza)* 100 / 
					(SELECT COUNT(*) 
					FROM prod_piezas_stock pps 
					WHERE pps.idProdEstado IN (2))
				) AS 'Porcentaje'
		 FROM prod_piezas_stock pps
         INNER JOIN ot_piezas_usadas pusd
			ON pps.idProdStock = pusd.idProdStock
		 INNER JOIN ot_estados_historico his
			ON his.idOT = pusd.idOT
		 INNER JOIN prod_piezas pp 
			ON pps.idProdPieza = pp.idProdPieza
		 INNER JOIN prod_marcas pm 
			ON pp.idProdMarca = pm.IdProdMarca
		 WHERE pps.idProdEstado = 2 -- Pieza vendida
		 AND his.estado_orden = 7 -- Orden reparada
		 AND his.Fecha_Alta BETWEEN fechaDesde AND fechaHasta -- Filtro por fecha
		 GROUP BY pps.idProdPieza
	  ) AS otros
	WHERE otros.Porcentaje <= variableAjuste
    AND otros.Cantidad > 0
) AS filtro -- Si el filtro "Otras" no trae nada, no hago el union con la fila
WHERE filtro.Cantidad IS NOT NULL;  
    
END $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE ReporteHojaDeRutaPorFletero(
IN fletero bigint unsigned,
OUT retCode  int(11),
OUT descErr varchar(60)
) 
BEGIN

  DECLARE CONTINUE HANDLER FOR SQLEXCEPTION 
    SET retCode = 1, descErr = 'ReporteHojaDeRutaPorFletero() - Error General';

  SET retCode = 0;
    SET descErr = 'ReporteHojaDeRutaPorFletero() - OK';
	
select 
	cli.apellido as ApellidoCliente,
	cli.nombre as NombreCliente,
	elec.descripcion as Electrodomestico,
	ot.idOT as IdOT,
	ot.descripcion as DescripcionOT,
	cli.direccion as Domicilio,
	f.apellido as ApellidoFletero,
	f.nombre as NombreFletero,
	env.fechaEnvio as FechaEntrega,
    ot.costoDeEnvio as Costo,
    loc.nombre as Localidad,
    cli.Codigo_Postal as CP,
    loc.provincia as Provincia,
    cli.Telefono as Telefono

from 
	env_envios env, 
	env_fleteros f, 
	cli_clientes cli, 
	elec_electrodomesticos elec,
	ot_ordenes_trabajo ot, 
	env_envios_detalle det,
    ot_zonas zonas,
    localidades loc
    
where env.idFletero = f.idFletero
	and ot.idCliente = cli.idCliente
	and ot.idElectro = elec.idElectro
	and ot.idOT = det.idOT
	and env.idEnvio = det.idEnvio
    and f.idFletero = 1
    and env.fechaEnvio = now()-1
    and ot.codigoPostal = loc.codigoPostal
    and loc.zonaDeEnvio = zonas.idZonaPosible
	and det.entregado = 0;

    
END $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE RepContable(
IN fechaDesde date,
IN fechaHasta date,
OUT ingresos int(11),
OUT egresos int(11),
OUT retCode  int(11),
OUT descErr varchar(60)
) 
COMMENT 'Dado un rango de fechas, devuelve los ingresos y egresos'
BEGIN

  DECLARE CONTINUE HANDLER FOR SQLEXCEPTION 
    SET retCode = 1, descErr = 'RepContable() - Error General';

  SET retCode = 0;
    SET descErr = 'RepContable() - OK';
	
  SELECT SUM(cont.monto)
  INTO ingresos
  FROM cont_libro_diario cont
  WHERE cont.Fecha BETWEEN fechaDesde AND fechaHasta
  AND cont.idOTItems IS NOT NULL;  

  SELECT SUM(cont.monto)
  INTO egresos
  FROM cont_libro_diario cont
  WHERE cont.Fecha BETWEEN fechaDesde AND fechaHasta
  AND cont.idSolcPiezas IS NOT NULL ;  
    
END $$

-- SP Alertas

DELIMITER $$
CREATE PROCEDURE traerAlertas (
OUT retCode  int(11),
OUT descErr varchar(60)
) 
COMMENT 'Devuelve todas las alertas que no esten dadas de baja y la cantidad de piezas disponibles'
BEGIN

  DECLARE CONTINUE HANDLER FOR SQLEXCEPTION 
    SET retCode = 1, descErr = 'traerAlertas() - Error General';

  SET retCode = 0;
    SET descErr = 'traerAlertas() - OK';

    SELECT *, COUNT(idProdEstado) AS cantidad 
    FROM acc_alertas acc
    JOIN acc_tipo_alerta tipo
		ON acc.idTipoAlerta = tipo.idTipoAlerta
    JOIN prod_piezas piez
		ON piez.idProdPieza = acc.idProdPieza
	JOIN prod_marcas marc
		ON marc.idProdMarca = piez. idProdMarca
	JOIN prod_piezas_stock stock
		on piez.idProdPieza = stock.idProdPieza
	WHERE acc.Fecha_Baja IS NULL 
	AND marc.Fecha_Baja IS NULL
	AND piez.Fecha_Baja IS NULL
    AND stock.idProdEstado = 1 -- Piezas disponibles
  		GROUP BY piez.idProdPieza;
    
    
END $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE darDeBajaAlertasPendientes (
IN idSolcCompra bigint unsigned,
OUT retCode  int(11),
OUT descErr varchar(60)
) 
COMMENT 'Recorre todos los items de la SC y de baja las alertas que esten pendientes. 
		Llamar al procedimiento únicamente al procesar una solicitud de compra.'
BEGIN

  DECLARE sinRegistros INT DEFAULT FALSE;
  
  DECLARE vidProdPieza BIGINT UNSIGNED;
  DECLARE vCantidad BIGINT UNSIGNED;
  DECLARE vExisteAlerta INT;
  DECLARE vBajo_stock INT;
  DECLARE vStockActual INT;
  
  -- Traigo todas las piezas de la SC
  DECLARE datosCur CURSOR 
  FOR SELECT solp.idProdPieza,
			 solp.cantidad
  FROM solc_piezas solp
  WHERE solp.idSolcCompra = idSolcCompra;

  DECLARE CONTINUE HANDLER FOR NOT FOUND SET sinRegistros = TRUE;

  DECLARE CONTINUE HANDLER FOR SQLEXCEPTION 
    SET retCode = 1, descErr = 'darDeBajaAlertasPendientes() - Error General';

  SET retCode = 0;
    SET descErr = 'darDeBajaAlertasPendientes() - OK';
    
 
  OPEN datosCur;
  
  -- Recorro pieza por pieza de la SC
  read_loop: LOOP
  FETCH datosCur INTO 
	vidProdPieza,
	vCantidad;
  IF sinRegistros THEN
		LEAVE read_loop;
  END IF;

    -- Verifico si existe una alerta pendiente por la pieza en cuestión
	SELECT EXISTS(
	SELECT * FROM acc_alertas acc
	WHERE acc.idProdPieza = vidProdPieza
	AND acc.fecha_baja IS NULL
	)
	INTO vExisteAlerta;

    -- Si hay alertas pendientes
	IF (vExisteAlerta != 0)
    THEN
    
        -- Traigo el bajo stock de la pieza en cuestión
        SELECT piez.bajo_stock
        INTO vBajo_stock
        FROM prod_piezas piez
        WHERE piez.idProdPieza = vidProdPieza;
    
		-- Traigo el stock actual de la pieza en cuestión
        SELECT COUNT(*)
        INTO vStockActual
        FROM prod_piezas_stock stock
        WHERE idProdPieza = vidProdPieza 
        AND idProdEstado = 1; -- Stock disponible para la venta
	
		-- Verifico que lo que hay + lo que llegó supera el bajo stock
		IF (vStockActual + vCantidad > vBajo_stock)
        THEN
			-- Si habia algúna alerta, la doy de baja
			UPDATE acc_alertas aler
            SET aler.Fecha_Baja = NOW()
            WHERE aler.idProdPieza = vidProdPieza;
            
        END IF;
        
    END IF;

  END LOOP read_loop;
  
  CLOSE datosCur;


END $$
DELIMITER ;


DELIMITER $$
CREATE PROCEDURE obtenerUltimaSCPendiente (
IN idProdPieza bigint unsigned,
OUT idSolcCompra bigint unsigned,
OUT retCode  int(11),
OUT descErr varchar(60)
) 
COMMENT 'Devuelve la última SC pendiente en donde se pidió la pieza'
BEGIN

  DECLARE CONTINUE HANDLER FOR SQLEXCEPTION 
    SET retCode = 1, descErr = 'obtenerUltimaSCPendiente() - Error General';

  SET retCode = 0;
    SET descErr = 'obtenerUltimaSCPendiente() - OK';
	
  
  SELECT solc.idSolcCompra
  INTO idSolcCompra
  FROM solc_solicitud_compra solc
  JOIN solc_piezas solp
	ON solp.idSolcCompra = solp.idSolcCompra
  WHERE
	solc.idSolcEstado = 2 -- Estado enviada
  AND solp.idProdPieza = idProdPieza
  LIMIT 1;
 
    
END $$
DELIMITER ;


-- Triggers

DELIMITER $$

-- Trigger: notificar_cliente
CREATE TRIGGER notificar_cliente
AFTER INSERT ON ot_estados_historico
FOR EACH ROW 
BEGIN
-- Cuando una orden de trabajo se finaliza, se deberá notificar al cliente, mediante correo
-- electrónico o teléfono, para que éste retire su producto reparado. Si se notifica por 
-- correo electrónico, se deberá indicar el producto que se reparó, horario y días en los 
-- que se pueda retirar, como así también la dirección del local.
END$$
DELIMITER ;

DELIMITER $$
-- Trigger: cambio_stock_actual
CREATE TRIGGER cambio_stock_actual
AFTER UPDATE ON prod_piezas_stock 
FOR EACH ROW 
BEGIN
-- Notificar al usuario administrativo las piezas de stock con las que se cuenta con poca
-- existencia. Además, indicar aquellas piezas que es urgente comprar ya que deben 
-- utilizarse en productos recibidos y están en faltante.

DECLARE vidProdEstado bigint unsigned;
DECLARE vidProdPieza bigint unsigned;
DECLARE vstock_actual smallint;
DECLARE vbajo_stock smallint;
DECLARE vExiste INT;

SELECT NEW.idProdEstado INTO vidProdEstado;
SELECT NEW.idProdPieza INTO vidProdPieza;


IF vidProdEstado IN (2,3,4) -- Si se vendió, rompió o perdió una pieza
THEN
  -- Verifico cuantas de las mismas quedaron disponibles luego de hacer la venta o reajuste
    SELECT COUNT(*) 
    INTO vstock_actual 
    FROM prod_piezas_stock prod
    WHERE prod.idProdPieza = vidProdPieza
    AND prod.idProdEstado = 1; -- Estado disponible
    
    -- Traigo el bajo stock parametrizado de la pieza
    SELECT piez.bajo_stock
    INTO vbajo_stock
    FROM prod_piezas piez
    WHERE piez.idProdPieza = vidProdEstado;
    
    -- Chequeo si bajó el stock por debajo del límite
    IF vstock_actual <= vbajo_stock
	THEN
	-- Verifico si ya existe una alerta activa por la pieza
		SELECT EXISTS(
        SELECT * FROM acc_alertas acc
        WHERE acc.idProdPieza = vidProdPieza
        AND acc.fecha_baja IS NULL
        )
		INTO vExiste;
    
		IF (vExiste = 0) 
        THEN
			-- Si no hay alertas pendientes, creo una nueva
			INSERT INTO acc_alertas (fecha_Alerta,idTipoAlerta, idProdPieza)
			VALUES(NOW(), 1 , vidProdPieza); -- TODO: tipo de alerta 1. Verificar
			
        END IF;
    END IF;
END IF;

END$$
DELIMITER ;


DELIMITER $$
-- Trigger: cambio_parametro_bajo_stock
CREATE TRIGGER cambio_parametro_bajo_stock
AFTER UPDATE ON prod_piezas
FOR EACH ROW 
BEGIN
-- Verifica si cambió el valor parametrizado de bajo stock para una pieza
-- En caso de haber cambiado, verifico si corresponde lanzar una alerta

DECLARE vbajoStock bigint unsigned;
DECLARE vidProdPieza bigint unsigned;

DECLARE vStockActual INT;
DECLARE vExiste INT;

SELECT NEW.bajo_stock INTO vbajoStock;
SELECT NEW.idProdPieza INTO vidProdPieza;

-- Verifico cuanto stock hay actualmente
SELECT COUNT(*) 
INTO vStockActual 
FROM prod_piezas_stock prod
WHERE prod.idProdPieza = vidProdPieza
AND prod.idProdEstado = 1; -- Estado disponible
    
-- Verifico si ya existe una alerta activa por la pieza
SELECT EXISTS(
SELECT * FROM acc_alertas acc
WHERE acc.idProdPieza = vidProdPieza
AND acc.fecha_baja IS NULL
)
INTO vExiste;    

-- Si el stock actual es menor o igual al bajo stock parametrizado
IF (vStockActual <= vbajoStock)
THEN    
	IF (vExiste = 0) 
	THEN
		-- Si no hay alertas pendientes, creo una nueva
		INSERT INTO acc_alertas (fecha_Alerta,idTipoAlerta, idProdPieza)
		VALUES(NOW(), 1 , vidProdPieza); -- TODO: tipo de alerta 1. Verificar
			
	END IF;
ELSE
	IF (vExiste > 0) 
	THEN
		-- Si existe una alerta, la doy de baja
		-- CALL darDeBajaAlertasPendientes(idSolcCompra,@retCode,@descErr);
        UPDATE acc_alertas acc
        SET acc.Fecha_Baja = now()
        WHERE acc.idProdPieza = vidProdPieza
        AND acc.Fecha_Baja IS NOT NULL;
	END IF;
END IF;


END$$
DELIMITER ;

-- Inserts de piezas ficticias

call altaPiezaStockFisico(1,50,@retCode,@descErr);
call altaPiezaStockFisico(2,8,@retCode,@descErr);
call altaPiezaStockFisico(3,8,@retCode,@descErr);
call altaPiezaStockFisico(4,9,@retCode,@descErr);
call altaPiezaStockFisico(5,300,@retCode,@descErr);
call altaPiezaStockFisico(6,25,@retCode,@descErr);
call altaPiezaStockFisico(7,60,@retCode,@descErr);
call altaPiezaStockFisico(8,26,@retCode,@descErr);
call altaPiezaStockFisico(9,6,@retCode,@descErr);
call altaPiezaStockFisico(10,9,@retCode,@descErr);
call altaPiezaStockFisico(11,10,@retCode,@descErr);
call altaPiezaStockFisico(12,8,@retCode,@descErr);
call altaPiezaStockFisico(13,70,@retCode,@descErr);
call altaPiezaStockFisico(14,40,@retCode,@descErr);
call altaPiezaStockFisico(15,15,@retCode,@descErr);
call altaPiezaStockFisico(16,8,@retCode,@descErr);
call altaPiezaStockFisico(17,90,@retCode,@descErr);
call altaPiezaStockFisico(18,50,@retCode,@descErr);
call altaPiezaStockFisico(19,30,@retCode,@descErr);

-- Inserto algunos precios de proveedores

INSERT INTO prov_precios_piezas(idProveedor,idProdPieza,precio_compra) VALUES(1,1,25);
INSERT INTO prov_precios_piezas(idProveedor,idProdPieza,precio_compra) VALUES(1,2,30);
INSERT INTO prov_precios_piezas(idProveedor,idProdPieza,precio_compra) VALUES(1,3,100);
INSERT INTO prov_precios_piezas(idProveedor,idProdPieza,precio_compra) VALUES(1,4,80);
INSERT INTO prov_precios_piezas(idProveedor,idProdPieza,precio_compra) VALUES(1,5,120);
INSERT INTO prov_precios_piezas(idProveedor,idProdPieza,precio_compra) VALUES(1,6,400);
INSERT INTO prov_precios_piezas(idProveedor,idProdPieza,precio_compra) VALUES(1,7,350);
INSERT INTO prov_precios_piezas(idProveedor,idProdPieza,precio_compra) VALUES(1,8,600);
INSERT INTO prov_precios_piezas(idProveedor,idProdPieza,precio_compra) VALUES(1,16,40);
INSERT INTO prov_precios_piezas(idProveedor,idProdPieza,precio_compra) VALUES(1,17,60);