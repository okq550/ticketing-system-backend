FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests            # Build the JAR (tests skipped for faster CI/image)

FROM eclipse-temurin:17-jre                  # Small runtime image (no compiler/tools)
WORKDIR /app
COPY --from=build /app/target/invoicing-system-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080                                  # Document the app port
ENTRYPOINT ["java", "-jar", "app.jar"]       # Start the Spring Boot application