FROM eclipse-temurin:17-jre  
COPY target/application-0.0.1-SNAPSHOT.jar java-app.jar
ENTRYPOINT [ "java", "-jar" , "java-app.jar"]