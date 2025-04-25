#!/bin/bash
set -e

echo "Packagin the proyect..."
mvn clean package

echo "Building Docker image..."
docker build -t naizfit-api-tester .

echo "Bootstraping the container in http://localhost:8080/"
docker run -p 8080:8080 naizfit-api-tester

