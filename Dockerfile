FROM amazoncorretto:21

WORKDIR /app

COPY target/TradingPlatform-1.0.0.jar /app

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/trading-app.jar"]