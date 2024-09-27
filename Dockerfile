FROM openjdk:17
ADD ./target/notajuris-api.jar notajuris-api.jar
ENTRYPOINT ["java", "-jar", "notajuris-api.jar"]