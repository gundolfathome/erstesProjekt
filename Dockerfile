FROM openjdk:17
RUN mkdir /app
WORKDIR /app

ADD target/product-svc-0.0.1-SNAPSHOT.jar /app/app.jar
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]