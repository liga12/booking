FROM java:8-jre
WORKDIR /app
ADD ./target/discovery.jar /app/
CMD ["java", "-jar", "-Xmx200m",  "discovery.jar"]

EXPOSE 8761