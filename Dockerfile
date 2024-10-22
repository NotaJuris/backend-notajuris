FROM maven:3.9.9-amazoncorretto-17 AS build
WORKDIR /notajuris
COPY pom.xml .
RUN mvn dependency:resolve
ADD src ./src
RUN mvn clean install

FROM amazoncorretto:17-alpine3.17
WORKDIR /usr/src/app
ENV PORT=5000
RUN rm -f /etc/localtime && ln -s /usr/share/zoneinfo/America/Sao_Paulo /etc/localtime
COPY --from=build /api-docker/target/*.jar /usr/src/app/notajuris-api.jar

ENTRYPOINT ["java", "-Dfile.encoding=UTF-8", "-jar", "/usr/src/app/notajuris-api.jar", "--server.port=${PORT}"]

EXPOSE ${PORT}