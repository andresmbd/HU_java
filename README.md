# Corporate Talent Hub

A Java console application for managing a company's human talent: it allows you to register, list, update, and delete employees (Developers and Managers), as well as generate a performance report, persisting the information in a PostgreSQL database.

## Coder

- **Andrés Barrios**
- Clan: **Puerta De Oro**

## Technologies

- Java 21
- Maven
- PostgreSQL 15
- PostgreSQL JDBC Driver (`org.postgresql:postgresql:42.7.3`)
- Docker / Docker Compose (to set up the database)

## Features

- Register employees (Developer or Manager) with data validation, quarterly ratings, and feedback.

- List all registered employees.

- Update the name and salary of an existing employee.

- Delete an employee by ID.

- Generate a performance report.

## Prerequisites

- Java JDK 21 or higher installed.

- Maven installed (or use the one included with your IDE).

- Docker and Docker Compose installed (for the database).

- A Maven-compatible IDE (NetBeans, IntelliJ IDEA, VS Code, etc.), optional.

## How to Download the Project

You can download it by cloning the GitHub repository:

```bash
git clone https://github.com/andresmbd/HU_java.git
cd HU_java
```

## How to Run the Project

### 1. Starting the Database with Docker

The project includes a `docker-compose.yml` file that starts a PostgreSQL container with the necessary configuration. From the project root, run:

```bash
docker compose up -d
```

This creates a container called `corporate_db_postgres` with:

- Database: `corporate_db`
- Username: `postgres`
- Password: `postgres`
- Exposed port: `5433` (mapped to the container's internal port `5432`)

### 2. Create the employees table

With the container running, execute the script `script.sql` (located in the project root) against the `corporate_db` database. You can do this, for example, with a client like DBeaver, pgAdmin, or directly from the terminal:

```bash
docker exec -i corporate_db_postgres psql -U postgres -d corporate_db < script.sql
```

### 3. Verify the connection configuration

The database connection is defined in:

```
src/main/java/com/riwi/talent/conexionbd/ConexionBD.java
```

By default, it uses:

```
URL: jdbc:postgresql://localhost:5433/corporate_db
Username: postgres
Password: postgres
```

If you changed the port, username, or password in `docker-compose.yml`, update these values ​​to match.

### 4. Compile and Run the Application

With Maven, from the project root:

```bash
mvn clean compile
mvn exec:java
```

Or, generate the `.jar` file and run it:

```bash
mvn clean package
java -cp target/CorporateTalentHub-1.0-SNAPSHOT.jar:<path-to-postgresql-driver> com.riwi.talent.App
```

You can also open the project directly in your IDE (NetBeans, IntelliJ, VS Code with a Java extension) and import it as a Maven project, then run the `App.java` class from there.

## Project Structure

```
HU_java/
├── docker-compose.yml
├── pom.xml
├── script.sql
└── src/main/java/com/riwi/talent/

├── App.java

├── conexionbd/

│ └── ConexionBD.java

├── controller/

│ └── EmpleadoController.java

├── modelo/

│ ├── Desarrollador.java

│ ├── Empleado.java

│ ├── Manager.java
│ ├── dao/
│ │ ├── EmployeeDAO.java
│ │ └── EmployeeDAOImpl.java
│ └── record/
│ └── PerformanceReport.java
└── view/
└── EmployeeView.java
```

## Notes

- When registering an employee, three ratings are requested (one per quarter), which are used to automatically calculate the average performance.

- Employees under 18 years of age cannot be registered.

- When deleting an employee, the system requests confirmation before executing the action.