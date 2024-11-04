# Stage 1: Build the application with Maven
FROM maven as build
WORKDIR /app
COPY . .
RUN mvn install

# Stage 2: Run the application
FROM openjdk:17-jdk-slim
WORKDIR /app
# Copy the generated JAR file from the build stage
COPY --from=build /app/target/Uterk.jar /app/
EXPOSE 8089
ENTRYPOINT ["java", "-jar", "Uterk.jar"]
