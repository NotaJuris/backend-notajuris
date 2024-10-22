FROM maven:3.9.9-amazoncorretto-17 AS build
WORKDIR /notajuris
COPY pom.xml .
RUN mvn dependency:resolve
ADD src ./src
ENV MYSQLHOST=autorack.proxy.rlwy.net
ENV MYSQLPORT=38089
ENV MYSQLDATABASE=railway
ENV MYSQLUSER=root
ENV MYSQLPASSWORD=QsKYQhjOGCLgdNjaTPMeRVjaWrmTweZb
ENV JWTKEY=+mqg6nduxaToY/pTJDpV2OucHfRfXabwzryKyIQ14RV2tYRf+nY1kfLjRa3z3GPvx752ejPght6QhhMNzHBcQ==
ENV REDISHOST=junction.proxy.rlwy.net
ENV REDISPORT=31810
ENV REDISUSER=default
ENV REDISPASSWORD=bZlNMwffrZDHrIQwqdDAwWsBjUSJIFup
ENV SPRINGPROFILE=prod
ENV CRYPTOKEY=Op7MXfkvpfC3XvHO
RUN mvn clean install

FROM amazoncorretto:17-alpine3.17
WORKDIR /usr/src/app
ENV PORT=5005
RUN rm -f /etc/localtime && ln -s /usr/share/zoneinfo/America/Sao_Paulo /etc/localtime
COPY --from=build /api-docker/target/*.jar /usr/src/app/notajuris-api.jar

ENTRYPOINT ["java", "-Dfile.encoding=UTF-8", "-jar", "/usr/src/app/notajuris-api.jar", "--server.port=${PORT}"]

EXPOSE ${PORT}