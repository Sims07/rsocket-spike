FROM java:8
VOLUME /tmp
COPY target/rsocket-client-0.0.1-SNAPSHOT.jar app.jar
RUN chmod 777 app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
