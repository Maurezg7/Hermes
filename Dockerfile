# ... (Tu configuración inicial de compilación con Maven que vimos antes)
FROM eclipse-temurin:21-jre-jammy
WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar

# CORRECCIÓN CLÍTICA: Informa de manera explícita al proxy de Render qué puerto escuchar
EXPOSE 10000

# Aseguramos que corra en el puerto 10000 mediante parámetro de ejecución
ENTRYPOINT ["java", "-jar", "-Dserver.port=10000", "app.jar"]
