FROM openjdk:21-jdk-slim
WORKDIR /app

# Копируем JAR файл
COPY target/*.jar app.jar

# Явно указываем порт
EXPOSE 8080

# Запускаем приложение
ENTRYPOINT ["java", "-jar", "-Dserver.port=8080", "app.jar"]