CREATE TABLE empleados (
    id SERIAL PRIMARY KEY,
	id_empleado INT NOT NULL UNIQUE CHECK(id_empleado > 0),
    nombre VARCHAR(100) NOT NULL,
    edad SMALLINT NOT NULL,
    salario DECIMAL(12,2) NOT NULL,
    calificaciones DOUBLE PRECISION[] NOT NULL,
    promedio_desempeno DECIMAL(8,2) NOT NULL,
	feedback TEXT NOT NULL,
    tipo VARCHAR(20) NOT NULL CHECK(tipo IN('DESARROLLADOR', 'GERENTE')),
    lenguaje_principal VARCHAR(100),
    presupuesto_mensual DECIMAL(12,2)
);

