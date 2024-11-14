FROM gradle:8.3.0-jdk21 AS build
WORKDIR /app
COPY . .

RUN gradle bootJar --no-daemon

# Stage 2: Create the final image
FROM openjdk:21-slim
WORKDIR /app

COPY --from=build /app/build/libs/*.jar discografia.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "discografia.jar"]
