FROM maven:3.9.9-amazoncorretto-17 AS build
WORKDIR /notajuris
ARG MYSQLHOST
ARG MYSQLPORT
ARG MYSQLDATABASE
ARG MYSQLUSER
ARG MYSQLPASSWORD
ARG JWTKEY
ARG REDISHOST
ARG REDISPORT
ARG REDISUSER
ARG REDISPASSWORD
ARG SPRINGPROFILE
ARG CRYPTOKEY
COPY pom.xml .
RUN mvn dependency:resolve
ADD src ./src
RUN mvn clean install

FROM amazoncorretto:17-alpine3.17
WORKDIR /usr/src/app
ARG PORT
RUN rm -f /etc/localtime && ln -s /usr/share/zoneinfo/America/Sao_Paulo /etc/localtime
COPY --from=build /notajuris/target/*.jar /usr/src/app/notajuris-api.jar

ENTRYPOINT ["java", "-Dfile.encoding=UTF-8", "-jar", "/usr/src/app/notajuris-api.jar", "--server.port=${PORT}"]

EXPOSE ${PORT}