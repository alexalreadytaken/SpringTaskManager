FROM amazoncorretto:11-alpine-jdk
COPY taskManager-0.0.1-SNAPSHOT.jar stm.jar
ENTRYPOINT ["java","-jar","stm.jar"]