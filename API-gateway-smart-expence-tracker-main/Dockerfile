# ---------- build stage ----------
FROM maven:3.9.6-eclipse-temurin-17 AS builder
WORKDIR /app

# Copy only pom first to cache dependencies
COPY pom.xml .

RUN mvn -B -q -DskipTests dependency:go-offline

# Now copy sources and build
COPY src ./src

RUN mvn -B -DskipTests package

# ---------- runtime stage ----------
FROM eclipse-temurin:17-jre
WORKDIR /app

# Pick up the fat jar produced in /app/target/
ARG JAR_FILE=/app/target/*.jar
COPY --from=builder ${JAR_FILE} /app/app.jar

# Your app will listen on 8080 behind Nginx
ENV SERVER_PORT=8080
EXPOSE 8080

ENTRYPOINT ["java","-jar","/app/app.jar"]