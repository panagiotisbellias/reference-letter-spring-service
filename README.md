# reference-letter-service
Reference-Letter Service, a Spring Boot project in the context of HUA DIT course 'Distributed Systems'

<h3 align="left">Languages and Tools:</h3>
<p align="left"> <a href="https://www.gnu.org/software/bash/" target="_blank" rel="noreferrer"> <img src="https://www.vectorlogo.zone/logos/gnu_bash/gnu_bash-icon.svg" alt="bash" width="40" height="40"/> </a> <a href="https://getbootstrap.com" target="_blank" rel="noreferrer"> <img src="https://raw.githubusercontent.com/devicons/devicon/master/icons/bootstrap/bootstrap-plain-wordmark.svg" alt="bootstrap" width="40" height="40"/> </a> <a href="https://www.docker.com/" target="_blank" rel="noreferrer"> <img src="https://raw.githubusercontent.com/devicons/devicon/master/icons/docker/docker-original-wordmark.svg" alt="docker" width="40" height="40"/> </a> <a href="https://git-scm.com/" target="_blank" rel="noreferrer"> <img src="https://www.vectorlogo.zone/logos/git-scm/git-scm-icon.svg" alt="git" width="40" height="40"/> </a> <a href="https://www.java.com" target="_blank" rel="noreferrer"> <img src="https://raw.githubusercontent.com/devicons/devicon/master/icons/java/java-original.svg" alt="java" width="40" height="40"/> </a> <a href="https://www.linux.org/" target="_blank" rel="noreferrer"> <img src="https://raw.githubusercontent.com/devicons/devicon/master/icons/linux/linux-original.svg" alt="linux" width="40" height="40"/> </a> <a href="https://www.nginx.com" target="_blank" rel="noreferrer"> <img src="https://raw.githubusercontent.com/devicons/devicon/master/icons/nginx/nginx-original.svg" alt="nginx" width="40" height="40"/> </a> <a href="https://www.postgresql.org" target="_blank" rel="noreferrer"> <img src="https://raw.githubusercontent.com/devicons/devicon/master/icons/postgresql/postgresql-original-wordmark.svg" alt="postgresql" width="40" height="40"/> </a> <a href="https://postman.com" target="_blank" rel="noreferrer"> <img src="https://www.vectorlogo.zone/logos/getpostman/getpostman-icon.svg" alt="postman" width="40" height="40"/> </a> <a href="https://spring.io/" target="_blank" rel="noreferrer"> <img src="https://www.vectorlogo.zone/logos/springio/springio-icon.svg" alt="spring" width="40" height="40"/> </a> </p>

## Prerequisites
### Clone Project

Go to a directory of your choice and do
```bash
git clone https://github.com/panagiotisbellias/reference-letter-service.git
```

### Database

#### 1. Using Docker
[Install Docker](https://docs.docker.com/get-docker/)

**Run a postgres container as below**  
Below details can be different  
(--name is database-name: db-postgres,  
 -p is port mapping: 5432,  
 -e is to define environmental variables such as  
 POSTGRES_PASSWORD is the password for default postgres user like pass123  
 -d is for detach mode and  
 finally we specify the image name. It is postgres (the latest version will be used this way)

```bash
docker run --name db-postgres -p 5432:5432 -e POSTGRES_PASSWORD=pass123 -d postgres
```
Check connectivity to postgresql database.  
-h is for the host, -U is for the username of user and -p is for the port database is accessible
```bash
psql -h localhost -U postgres -p 5432
```

#### 2. Using docker-compose
[Here](docker-compose.yaml) is the yaml file that tells docker-compose what to do. So if you don't like the previous way or you are getting bored try this one.  
All you have to do is
```bash
docker-compose up
```
when you are in the root directory of project, where yaml file is located.

In case, database isn't initialized automatically through docker-compose, you can execute the commands below while database is available.
```bash
psql -h localhost -U postgres -p 5432 < assets/db/auth.sql
psql -h localhost -U postgres -p 5432 < assets/db/postgresql-init.sql
```

When you want to stop the postgresql container you can do
```bash
docker-compose down
```

Simple, hah?

## Application Properties

You have to do the following so the spring application can connect with the database properly.  
Create file application.properties in src/main/resources and follow this template entering your own values.
```bash
spring.datasource.url=jdbc:postgresql://localhost/<DB-NAME>
spring.datasource.username=<DB-USER>
spring.datasource.password=<DB-PASSWORD>

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQL81Dialect

spring.data.rest.base-path=/api

management.endpoints.web.exposure.include=*
```

## Run Locally
Use some IDE like IntelliJ or Eclipse. Otherwise, generate and run the .jar file using these commands
```bash
mvn install
java -jar target/reference-letter-service-0.0.1-SNAPSHOT.jar
```