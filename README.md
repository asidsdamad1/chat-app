# Auth Service

This project is an authentication service built with Java, Spring Boot, and RabbitMQ. It provides user registration, activation, and password management functionalities.

## Table of Contents

- [Overview](#overview)
- [Architecture](#architecture)
- [Getting Started](#getting-started)
- [Configuration](#configuration)
- [Usage](#usage)
- [Contributing](#contributing)
- [License](#license)

## Overview

The Auth Service is responsible for managing user authentication and registration. It uses RabbitMQ for messaging and integrates with other services through a fanout exchange.

## Architecture

![Architecture Diagram](path/to/your/architecture-image.png)

## Getting Started

### Prerequisites

- Java 17
- Maven
- RabbitMQ
- Docker (optional, for running RabbitMQ)

### Installation

1. Clone the repository:
    ```sh
    git clone https://github.com/yourusername/auth-service.git
    cd auth-service
    ```

2. Build the project:
    ```sh
    mvn clean install
    ```

3. Run the application:
    ```sh
    mvn spring-boot:run
    ```

### Running with Docker

To run RabbitMQ using Docker, use the following command:
```sh
docker run -d --name rabbitmq -p 5672:5672 -p 15672:15672 rabbitmq:3-management