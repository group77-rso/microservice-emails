FROM amazoncorretto:18
RUN mkdir /app

WORKDIR /app

ADD ./api/target/emails-api-1.0.0-SNAPSHOT.jar /app

EXPOSE 8083

CMD ["java", "-jar", "emails-api-1.0.0-SNAPSHOT.jar"]
