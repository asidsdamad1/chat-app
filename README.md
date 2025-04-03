# Chat Application


## Table of Contents

- [Overview](#overview)
- [Architecture](#architecture)
- [Getting Started](#getting-started)
- [Configuration](#configuration)
- [Usage](#usage)
- [Contributing](#contributing)
- [License](#license)

## Overview
This is a chat application built using Spring Boot, which includes several microservices for handling different functionalities such as authentication, messaging, and configuration management.
It uses RabbitMQ for messaging and Spring Cloud for service discovery and configuration management.

## Architecture

![Architecture Diagram](/storage-img/chat-app.jpg)

## Getting Started

### Prerequisites

- Java 21
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