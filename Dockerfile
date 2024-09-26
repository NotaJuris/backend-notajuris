FROM openjdk:17
ADD ./notajuris-api.jar notajuris-api.jar
EXPOSE 5005
ENTRYPOINT ["java", "-jar", "notajuris-api.jar"]