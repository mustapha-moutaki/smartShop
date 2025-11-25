# use java image already exist
FROM openjdk:17-jdk-slim

# add working folder inside contaienr
WORKDIR /app

# copy generaeted file jar to the container
COPY target/supplychainix-0.0.1-SNAPSHOT.jar app.jar

# open the port that app will use to run
EXPOSE 8080

# commands run app
ENTRYPOINT ["java", "-jar", "app.jar"]