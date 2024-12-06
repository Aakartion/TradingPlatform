#!/bin/bash

# Package the spring boot application
docker-compose down && docker-compose up -d postgres # needed for database connection while packaging spring boot app
./mvnw clean package

# Build image
docker build -t trading-app:latest .

# Run the application and required tools using docker-compose
docker-compose down && docker-compose up -d && docker-compose logs -f