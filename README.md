# JAX-RS application

This is a simple JAX-RS application a made for learning porpuses. It performs CRUD operations for Products via HTTP requests. This application is configured to be run in a WildFly server, using a Postgres database. Since this is a very simple project, the process to deploy and run the application is manual. Check the ENV-SETUP.md on how to setup your machine to be able to run the application. If you machine is ready, check the steps below on how to run and use the application.

### Database

Since this application uses Postgres database, there is a `docker-compose.yml` provided in the root directory of this project. Run it and create the database:

```sql
CREATE DATABASE productdb;
```

### Build

Run the following command to build and generate the project's .war file.

```
mvn clean package
```

The generated file should be located at `target/jaxrscrud.war`.

### Deployment

Locate in your system the location of your wilfdly server installation. Copy the file generated in the previous step into `/standalone/deployments` inside the wildfly directory.

## Run server

Navigate to the root directory of your wildfly server installation. Enter the `/bin` directory, and run the `standalone.bat` file. This should get the server up and running. You can use the requests below to use and tests the application.

## Requests

### List all products

```
curl --location 'http://localhost:8080/jaxrscrud/api/products'
```

### Create new product
```
curl --location 'http://localhost:8080/jaxrscrud/api/products' \
--header 'Content-Type: application/json' \
--data '{
    "name": "Keyboard",
    "price": 99.99
}'
```

### Update existing product

```
curl --location --request PUT 'http://localhost:8080/jaxrscrud/api/products/1' \
--header 'Content-Type: application/json' \
--data '{
    "name": "Keyboard",
    "price": 10.50
}'
```

### Delete product

```
curl --location --request DELETE 'http://localhost:8080/jaxrscrud/api/products/1'
```

