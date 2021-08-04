create schema tp_died;

create table tp_died.estacion(
	id integer primary key,
	nombre varchar(50),
	apertura time,
	cierre time,
	operativa boolean);


create table tp_died.linea (
	id integer primary key,
	nombre varchar(50), 
	color_r integer,
	color_g integer,
	color_b integer,
	activa boolean);

create table tp_died.ruta(
	id integer primary key,
	id_origen integer references tp_died.estacion (id) on delete cascade,
	id_destino integer references tp_died.estacion (id) on delete cascade,
	id_linea integer references tp_died.linea (id) on delete cascade,
	distancia double precision,
	duracion integer,
	capacidad integer,
	activa boolean,
	costo double precision);

create table tp_died.boleto(
	numero integer primary key, 
	id_origen integer references tp_died.estacion(id) on delete cascade,
	id_destino integer references tp_died.estacion(id) on delete cascade,
	email_cliente varchar(70),
	nombre_cliente varchar(50),
	fechaVenta timestamp,
	costo double precision,
	camino varchar(200));

create table tp_died.mantenimiento(
	id integer primary key,
	id_estacion integer references tp_died.estacion (id) on delete cascade,
	fecha_inicio timestamp,
	fecha_fin timestamp,
	observaciones varchar(500));