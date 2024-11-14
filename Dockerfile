# Stage 1: Build the application
FROM gradle:8.1.1-jdk21 AS build
WORKDIR /app
COPY . .

RUN gradle bootJar --no-daemon

# Stage 2: Create the final image
FROM openjdk:21-slim
WORKDIR /app

# Copy the JAR file from the build stage
COPY --from=build /app/build/libs/*.jar discografia.jar

# Expose the port that the application will run on
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "discografia.jar"]