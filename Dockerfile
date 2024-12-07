FROM amazoncorretto:21

WORKDIR /app

COPY ./target/TradingPlatform-1.0.0.jar /app/trading-app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-Dspring.profiles.active=kube", "-jar", "/app/trading-app.jar"]
#ENTRYPOINT ["java", "-jar", "/app/trading-app.jar"]