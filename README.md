# 📌 Prueba Técnica - Ingeniero de Desarrollo (Impuestos)

Este proyecto Spring Boot implementa una API REST que permite cargar un archivo CSV a una base de datos PostgreSQL, realizar consultas por diferentes criterios y exportar los resultados en formato Excel.

---

## 🛠️ Tecnologías utilizadas

- Java 17
- Spring Boot
- PostgreSQL
- Maven
- Lombok
- Apache POI (para exportar a Excel)

---

## 🚀 Instrucciones para ejecutar el proyecto

### 1️⃣ Crear la base de datos `PRUEBA01` en PostgreSQL

```sql
CREATE DATABASE PRUEBA01;
2️⃣ Crear la tabla impuestos
sql

CREATE TABLE IF NOT EXISTS public.impuestos (
    sticker INTEGER NOT NULL PRIMARY KEY,
    fecha_movimiento DATE NOT NULL,
    fecha_recaudo DATE NOT NULL,
    tipo_horario VARCHAR(1),
    nro_id NUMERIC(15,0) DEFAULT 0,
    nro_form NUMERIC DEFAULT 0,
    valor NUMERIC(15,0) DEFAULT 0
);

---

3️⃣ Configurar application.properties
Ubicado en src/main/resources/application.properties:


spring.datasource.url=jdbc:postgresql://localhost:5432/PRUEBA01
spring.datasource.username=postgres
spring.datasource.password=TU_CONTRASEÑA
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

⚠️ Reemplaza TU_CONTRASEÑA por tu contraseña real de PostgreSQL.

---

📦 Funcionalidad
Este proyecto permite:

✅ Cargar un archivo CSV a la base de datos

✅ Consultar datos por fecha

✅ Obtener resumen por tipo de horario (A o N)

✅ Buscar un registro por el campo Sticker

✅ Exportar todos los registros a un archivo Excel (.xlsx)

---

🧪 Endpoints disponibles

Método	Endpoint	                             Descripción

POST    /api/impuestos/upload	                 Carga un archivo CSV
GET	    /api/impuestos/fecha?fecha=yyyy-MM-dd	 Consulta registros por fecha de movimiento
GET	    /api/impuestos/resumen?horario=A	     Consulta resumen por tipo de horario A
GET	    /api/impuestos/resumen?horario=N	     Consulta resumen por tipo de horario N
GET	    /api/impuestos/sticker/{id}	             Consulta un registro por sticker
GET	    /api/impuestos/exportar	                 Exporta todos los registros a Excel

---

📄 Estructura esperada del archivo CSV
El archivo debe tener los siguientes campos separados por comas:
Sticker,Fecha de movimiento,Fecha de recaudo,Tipo de horario,Número de identificación,Número de formulario,Valor
Ejemplo de línea:


10001,2023-05-12,2023-05-13,A,123456789,456789,120000

---

Estructura del proyecto

src/
├── main/
│   ├── java/
│   │   └── com/ejemplo/
│   │       ├── controller/
│   │       ├── model/
│   │       ├── repository/
│   │       ├── service/
│   │       └── util/
│   └── resources/
└── test/

---

Ejecución.

mvn spring-boot:run

---

👤 Autor
Brayan Pérez
