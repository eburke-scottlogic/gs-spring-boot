FROM openjdk:8-jdk-alpine
WORKDIR /Docker
CMD ["gradle", "clean"]
CMD ["gradle", "build"]
COPY ./build/libs/spring-boot-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/Docker/app.jar"]

# docker run -p 8080:8080 myorg/myapp
# in git bash:
# docker ps
# docker stop [CONTAINER ID]
# docker ps