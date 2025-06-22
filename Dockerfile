FROM eclipse-temurin:17-jdk AS build

WORKDIR /app

# Copiar archivos necesarios para construir
COPY pom.xml .
COPY src ./src

# Construir el jar
RUN ./mvnw clean package -DskipTests

# Imagen final
FROM eclipse-temurin:17-jdk
WORKDIR /app

# Copiar el JAR desde el build
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8091
ENTRYPOINT ["java", "-jar", "app.jar"]
