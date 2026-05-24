FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app
COPY . .
RUN ./mvnw clean package -DskipTests 2>/dev/null || mvn clean package -DskipTests
EXPOSE 8080
CMD ["java", "-jar", "target/jobportal-1.0.0.jar"]
