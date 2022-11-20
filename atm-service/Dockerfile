FROM maven:3.6.2-jdk-12 AS build
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package

EXPOSE 9002
CMD ["java","-jar","/home/app/target/atm-service-0.0.1-SNAPSHOT.jar"]