# Use the standard OpenJDK image
FROM openjdk:21

WORKDIR /app

# Copy the pre-built JAR file
COPY target/discovery-server-*.jar app.jar

# Expose the application ports
EXPOSE 8761

# Set the entry point
ENTRYPOINT ["java", "-jar", "app.jar"]