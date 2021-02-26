From openjdk:8
Expose 8080
Add target/countries-docker.jar countries-docker.jar
ENTRYPOINT ["java", "-jar", "/countries-docker.jar"]