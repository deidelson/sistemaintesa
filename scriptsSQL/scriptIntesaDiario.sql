drop database if exists intesadb;
create database if not exists intesadb;
use intesadb;

drop table if exists Clientes;
drop table if exists Egresos;
drop table if exists Gastos;
drop table if exists Ingresos;

create table Clientes(
	Id_Rubro int AUTO_INCREMENT,
	Nombre varchar(30) not null,
	Telefono1 varchar(30),
	Telefono2 varchar(30),
	Direccion varchar(30),
	Mail varchar(30),
	Facebook varchar(30),
	primary key (Id_Rubro) 		
);

create table Gastos(
	Id_Rubro int AUTO_INCREMENT,
	Nombre varchar(30) not null,
	Telefono1 varchar(30),
	Telefono2 varchar(30),
	Direccion varchar(30),
	Mail varchar(30),
	Facebook varchar(30),
	primary key (Id_Rubro) 	
);

create table Ingresos(
	Id int AUTO_INCREMENT,
	Fecha date not null,
	Descripcion varchar(30),
	Id_Rubro int not null,
	monto double not null,
	primary key (Id),
	foreign key (Id_Rubro) references Clientes(Id_Rubro)
);

create table Egresos(
	Id int AUTO_INCREMENT,
	Fecha date not null,
	Descripcion varchar(30),
	Id_Rubro int not null,
	monto double not null,
	primary key (Id),
	foreign key (Id_Rubro) references Gastos(Id_Rubro)
);

