# ---- build stage ----
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /workspace

# Копируем pom и исходники и собираем проект
COPY pom.xml .
COPY src ./src
RUN mvn -B -f pom.xml -DskipTests package

# ---- run stage ----
FROM eclipse-temurin:17-jre-jammy
WORKDIR /app
COPY --from=build /workspace/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["sh", "-c", "java -jar /app/app.jar"]
