FROM amazoncorretto:21

WORKDIR /app

COPY target/app.jar /app

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/app.jar"]