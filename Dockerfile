# Stage 1
FROM gradle:8.11.0-jdk21 AS build
WORKDIR /app
COPY . .

RUN gradle clean bootJar --no-daemon --stacktrace --info

# Stage 2
FROM openjdk:21-slim
WORKDIR /app

COPY --from=build /app/build/libs/*.jar discografia.jar

EXPOSE 8080
EXPOSE 443

# Run the application
ENTRYPOINT ["java", "-jar", "discografia.jar"]
