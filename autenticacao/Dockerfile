# Use an official OpenJDK runtime as a parent image
FROM openjdk:17-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy the project’s pom.xml and source code to the container
COPY pom.xml .
COPY src ./src
COPY .mvn .mvn
COPY mvnw .

# Ensure the Maven wrapper script is executable
RUN chmod +x mvnw

# Package the application
RUN ./mvnw package -DskipTests

# Expose the port the application runs on
EXPOSE 8080

# Run the application
CMD ["java", "-jar", "target/autenticacao-0.0.1-SNAPSHOT.jar"]