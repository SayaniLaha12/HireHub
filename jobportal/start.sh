#!/bin/bash
echo "======================================="
echo "  HireHub Job Portal — Starting Up"
echo "======================================="
echo ""
echo "Checking Java..."
java -version 2>&1 || { echo "ERROR: Java not found. Install Java 17+ from https://adoptium.net"; exit 1; }
echo ""
echo "Starting Spring Boot application..."
echo "Open http://localhost:8080 once you see 'Started JobPortalApplication'"
echo ""
mvn spring-boot:run
