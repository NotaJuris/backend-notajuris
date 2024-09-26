FROM openjdk:17
ADD ./notajuris-api.jar notajuris-api.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "notajuris-api.jar"]