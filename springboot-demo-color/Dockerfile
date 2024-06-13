FROM maven:3.6.3-openjdk-8-slim
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests
ENTRYPOINT ["java","-jar","target/demo-0.0.1-SNAPSHOT.jar"]