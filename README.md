# Supply Fellow Product Service

This is a Spring Boot application that provides a RESTful API for managing products. It's part of the larger Supply Fellow project.

## Technologies Used

- Java
- Spring Boot
- Maven
- PostgreSQL
- JavaScript

## Features

- Fetch all products
- Fetch a product by ID
- Add a new product

## Setup

1. Clone the repository.
2. Update the `src/main/resources/application.yml` file with your PostgreSQL database details.
3. Run the application using Maven: `mvn spring-boot:run`

## API Endpoints

- GET `/products`: Fetch all products
- GET `/products/{id}`: Fetch a product by ID
- POST `/products`: Add a new product

## Frontend

The frontend is a simple HTML/JavaScript application that communicates with the backend API. It allows users to view all products and add new ones.

## Contributing

Pull requests are welcome. For major changes, please open an issue first to discuss 