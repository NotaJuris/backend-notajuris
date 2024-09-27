FROM openjdk:22-ea-17-slim
ADD notajuris-api.jar notajuris-api.jar
ENTRYPOINT ["java", "-jar", "notajuris-api.jar"]