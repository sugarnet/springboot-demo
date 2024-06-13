FROM maven:3.6.3-openjdk-8-slim
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Just documentation.
# This container needs Docker or OpenShift to help with networking
EXPOSE 8080

# OpenShift picks up this label and creates a service
LABEL io.openshift.expose-services 8080/http

ENTRYPOINT ["java","-jar","target/demo-0.0.1-SNAPSHOT.jar"]