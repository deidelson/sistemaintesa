drop database if exists intesadbTest;
create database if not exists intesadbTest;
use intesadbTest;

drop table if exists Clientes;
drop table if exists ClientesMock;
drop table if exists Egresos;
drop table if exists Gastos;
drop table if exists Ingresos;

create table Clientes(
	Id_Rubro int,
	Nombre varchar(30) not null,
	Telefono1 varchar(30),
	Telefono2 varchar(30),
	Direccion varchar(30),
	Mail varchar(30),
	Facebook varchar(30),
	primary key (Id_Rubro) 		
);

create table ClientesMock(
	Id_Rubro int,
	Nombre varchar(30) not null,
	Telefono1 varchar(30),
	Telefono2 varchar(30),
	Direccion varchar(30),
	Mail varchar(30),
	Facebook varchar(30),
	primary key (Id_Rubro) 		
);

create table Gastos(
	Id_Rubro int,
	Nombre varchar(30) not null,
	Telefono1 varchar(30),
	Telefono2 varchar(30),
	Direccion varchar(30),
	Mail varchar(30),
	Facebook varchar(30),
	primary key (Id_Rubro) 	
);

create table Ingresos(
	Id int,
	Fecha date not null,
	Descripcion varchar(30),
	Id_Rubro int not null,
	monto double not null,
	primary key (Id),
	foreign key (Id_Rubro) references ClientesMock(Id_Rubro)
);

create table Egresos(
	Id int,
	Fecha date not null,
	Descripcion varchar(30),
	Id_Rubro int not null,
	monto double not null,
	primary key (Id),
	foreign key (Id_Rubro) references Gastos(Id_Rubro)
);