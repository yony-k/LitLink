FROM openjdk:17
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} litlink.jar
ENV TZ=Asia/Seoul
ENTRYPOINT ["java", "-jar", "/litlink.jar"]