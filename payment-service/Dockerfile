FROM java:8-jre
#COPY init.sql /docker-entrypoint-initdb.d/
WORKDIR /app


ADD ./target/payment-service.jar /app/

#ENV POSTGRES_DB event

#COPY init.sql /docker-entrypoint-initdb.d/init.sql
#ADD init.sql /docker-entrypoint-initdb.d/


CMD ["java", "-jar", "-Xmx200m",  "payment-service.jar"]

#HEALTHCHECK --interval=10s --timeout=3s CMD curl -f http://localhost:8761/health || exit 1
EXPOSE 8081