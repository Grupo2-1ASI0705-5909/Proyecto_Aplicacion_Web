FROM amazoncorretto:17
MAINTAINER EDO
COPY tarjet/Trabajo_Aplicaciones_Web-0.0.1-SNAPSHOT.jar d.jar
ENTRYPOINT ["java", "-jar", "d.jar"]