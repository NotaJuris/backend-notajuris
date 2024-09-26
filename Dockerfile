FROM openjdk:17
ADD ./notajuris-api.jar notajuris-api.jar
ENTRYPOINT ["javar", "-jar", "notajuris-api.jar"]