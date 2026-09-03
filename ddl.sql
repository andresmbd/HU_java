CREATE TABLE empleados(
	id SERIAL PRIMARY KEY,
	id_empleado INT NOT NULL UNIQUE CHECK(id_empleado > 0),
	nombre VARCHAR(100) NOT NULL,
	edad INT NOT NULL CHECK(edad >= 18),
	salario NUMERIC(10,2) NOT NULL CHECK(salario > 0)
);
alter table empleados add column tipo VARCHAR(100) NOT NULL CHECK(tipo IN('DESARROLLADOR', 'GERENTE'));