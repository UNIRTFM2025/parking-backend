# Usar una imagen base de Java (puedes ajustar la versión si usas otra)
FROM eclipse-temurin:17-jdk

# Crear un directorio dentro del contenedor
WORKDIR /app

# Copiar el jar compilado
COPY target/parking-backend.jar app.jar

# Exponer el puerto que usa Spring Boot
EXPOSE 8091

# Ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]
