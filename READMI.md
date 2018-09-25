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

Add additional notes about how to deploy this on a live system

1 Create db event in [PostgresSql](https://www.postgresql.org/) on [EventService](https://github.com/liga12/booking/tree/master/event-service)
and run script []