create database finaldaute;
use finaldaute;
create table telefono(
codTels int primary key auto_increment,
telefono varchar(9),
telDescrip varchar(30)
);
create table departamento(
idDepartamento int primary key auto_increment,
nombreDepto varchar(30),
cantEmpleados int,
codTels int
);
alter table departamento
add constraint fkCodTels
foreign key(codTels)
references telefono(codTels);
create table usuarios(
idusuario int primary key,
usuario varchar(30),
contra varchar(30),
nivel varchar(30)
);
create table empleado(
codEmpleado int primary key auto_increment,
nombres varchar(30),
apellidos varchar(30),
idDepartamento int,
salario float,
edad int,
idUsuario int
);
alter table empleado
add constraint fk_idDepartamento
foreign key(idDepartamento)
references departamento(idDepartamento);
alter table empleado
add constraint fk_idusuario
foreign key(idUsuario)
references usuarios(idusuario);
insert into telefono(telefono,telDescrip) values('2132-7536','Oficina Principal');
insert into departamento(nombreDepto,cantEmpleados, codTels) values ('Escuela de Computación',25,1);
insert into usuarios values(1,'perezila','123','Admin'),(2,'goku33','123','Auditor'),(3,'Shirigami','123','Simple Usuario');
insert into empleado(nombres, apellidos, idDepartamento, salario, edad, idUsuario)
values('Juana','Perez',1,1200,31,1),
('Edmundo','Gonzalez',1,250,34,2),
('Armando Esteban','Quito',1,150,25,3);
