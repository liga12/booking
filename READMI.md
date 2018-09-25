# Project Title

The application consist from 4 microservice:

1 [DiscoveryService](https://github.com/liga12/booking/tree/master/discovery-service) -Eureka discovery server   [More Information](https://cloud.spring.io/spring-cloud-netflix/multi/multi_spring-cloud-eureka-server.html)

2 [EventService](https://github.com/liga12/booking/tree/master/event-service) - Microservice for work with organizations, events and places

3 [PaymentService](https://github.com/liga12/booking/tree/master/payment-service) - Microservice for work with clients' and customers' payments and orders

4 [UserService](https://github.com/liga12/booking/tree/master/user-service) - Microservice for work with users' and customers' personal information

And 3 modules:

1 [Payment-api](https://github.com/liga12/booking/tree/master/event-api) - Module consist from interface for  work with [PaymentService](https://github.com/liga12/booking/tree/master/payment-service) on [FeingClient](https://cloud.spring.io/spring-cloud-netflix/multi/multi_spring-cloud-feign.html)

2 [Event-api](https://github.com/liga12/booking/tree/master/event-api) - Module consist from interface for  work with [EventService](https://github.com/liga12/booking/tree/master/event-service) on [FeingClient](https://cloud.spring.io/spring-cloud-netflix/multi/multi_spring-cloud-feign.html)

3 [User-api](https://github.com/liga12/booking/tree/master/user-api) - Module consist from interface for  work with [UserService](https://github.com/liga12/booking/tree/master/user-service) on [FeingClient](https://cloud.spring.io/spring-cloud-netflix/multi/multi_spring-cloud-feign.html)

## Deployment

1 On [EventService](https://github.com/liga12/booking/tree/master/event-service)

1.1 Create db event in [PostgresSql](https://www.postgresql.org/) 

1.2 Run script [createSchema.sql](https://github.com/liga12/booking/blob/master/event-service/src/main/resources/createSchema.sql)

2 On [PaymentService](https://github.com/liga12/booking/tree/master/payment-service)

2.2 Create db payment in [PostgresSql](https://www.postgresql.org/) 

2.2 Run script [createSchema.sql](https://github.com/liga12/booking/blob/master/payment-service/src/main/resources/createSchema.sql)

3 Run [DiscoveryServiceApplication](https://github.com/liga12/booking/tree/master/discovery-service/src/main/java/com/booking/server) default port 8761 [View setting](https://github.com/liga12/booking/blob/master/discovery-service/src/main/resources/bootstrap.yml) 

4 Run [EventServiceApplication](https://github.com/liga12/booking/blob/master/event-service/src/main/java/com/booking/event/EventServiceApplication.java) default port 8080 and name event-service [View setting](https://github.com/liga12/booking/blob/master/event-service/src/main/resources/bootstrap.yml) 

5 Run [PaymentServiceApplication](https://github.com/liga12/booking/blob/master/payment-service/src/main/java/com/booking/payment/PaymentServiceApplication.java) default port 8081 and name payment-service [View setting](https://github.com/liga12/booking/blob/master/payment-service/src/main/resources/bootstrap.yml) 

6 Run [UserServiceApplication](https://github.com/liga12/booking/blob/master/user-service/src/main/java/com/booking/user/UserServiceApplication.java) default port 8082 and name user-service [View setting] (https://github.com/liga12/booking/blob/master/user-service/src/main/resources/bootstrap.yml) 