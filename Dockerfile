FROM openjdk:16
EXPOSE 8080
COPY target/*.jar mobile-web-ws-0.0.1.jar
ENTRYPOINT ["java","-jar","/mobile-web-ws-0.0.1.jar"]
