FROM eclipse-temurin:17-jdk as builder
WORKDIR /app
COPY . .
RUN ./mvnw clean package -DskipTests

FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar

# Crear directorio para uploads y darle permisos
RUN mkdir -p /uploads/productos /uploads/categorias
RUN chmod -R 755 /uploads

EXPOSE 2001
CMD ["java", "-jar", "app. jar"]
