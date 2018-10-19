# Project Title

The application consist from 4 microservice:

1 [DiscoveryService](https://github.com/liga12/booking/tree/master/discovery-service) -Eureka discovery server   [More Information](https://cloud.spring.io/spring-cloud-netflix/multi/multi_spring-cloud-eureka-server.html)

2 [EventService](https://github.com/liga12/booking/tree/master/event-service) - Microservice works with organizations, events and places

3 [PaymentService](https://github.com/liga12/booking/tree/master/payment-service) - Microservice works with clients' and customers' payments and orders

4 [UserService](https://github.com/liga12/booking/tree/master/user-service) - Microservice works with clients' and customers' personal information

And 3 Api for works with [FeingClient](https://cloud.spring.io/spring-cloud-netflix/multi/multi_spring-cloud-feign.html):

1 [Payment-api](https://github.com/liga12/booking/tree/master/event-api) - Module consist from interface for  work with [PaymentService](https://github.com/liga12/booking/tree/master/payment-service) 

2 [Event-api](https://github.com/liga12/booking/tree/master/event-api) - Module consist from interface for  work with [EventService](https://github.com/liga12/booking/tree/master/event-service) 

3 [User-api](https://github.com/liga12/booking/tree/master/user-api) - Module consist from interface for  work with [UserService](https://github.com/liga12/booking/tree/master/user-service)

[UML diagram](https://drive.google.com/open?id=1O9jh2-UBnswYBR2Yk_80JcH8YVqWsNS7) for [PaymentService](https://github.com/liga12/booking/tree/master/payment-service) - Microservice works with clients' and customers' payments and orders

## Deployment

1 Install [Install Docker](https://docs.docker.com/install/linux/docker-ce/ubuntu/)

2 Install [Install Docker Compose](https://docs.docker.com/compose/install/#install-compose)

3 Go to project main folder on terminal

4 Run on terminal 
    
    sudo docker network create jBoardNetwork_default

5 Run on terminal 

    sudo docker-compose up --build

## Example

1 Create payment 

    url: http://localhost:8081/payments 
    method: put    
    body:    {
    	    "token":"dddddd"
    	 }
    
    response: 1
    
2 Create customer

    url: http://localhost:8082/users/registration 
    method: put    
    body: {
            "name":"a",
            "surname":"sur",
            "type":"CUSTOMER",
            "email":"fg@gmgtyhoo87pipopw",
            "phone":"234234",
            "paymentId":1
           }
    response: 5ba9ded7222f414da16989b5
    
3 Create organization

    url: http://localhost:8080/organizations 
    method: put    
    body: {
             "name": "Tik",
             "location": "kharkov",
             "phone": "1111111111",
             "email": "sdfgdfrddsfsdf@gmail.com",
             "customerId":"5ba9ded7222f414da16989b5"
          }
    response: 1
    
4 Create event

    url: http://localhost:8080/events
    method: put   
    body: {
            "name":"Man in black",
            "type":"CINEMA",
            "description":"aaaaaas",
            "date":15366707039,
            "location":"sdfdsfsdf",
            "minAge":34,
            "photoUrl":"http/.home",
            "artists":"AC/DC",
            "organization": 1
          }
    response: 1
    
5 Create place

    url: http://localhost:8080/places
    method: put   
    body: {
          	"price":6.8,
          	"number":4,
          	"row":2,
          	"event":1,
          	"sectionType":"FIRST_ROW"
          }
    response: 1
    
6 Create payment client

    url: http://localhost:8081/payments
    method: put   
    body: {
          	"token":"ddddd2"
          }
    response: 2
    
7 Create client

    url: http://localhost:8082/users/registration
    method: put   
    body: {
            "name":"vlad",
            "surname":"comrad",
            "type":"CLIENT",
            "email":"fg@gmgtyhoopopw",
            "phone":"234234",
            "paymentId":2
          }
    response: 5ba9dee9222f414da16989b6
    
8 Get all event

    url: http://localhost:8080/events
    method: get
    response: {
                  "content": [
                      {
                          "id": 1,
                          "name": "Man in black",
                          "type": "CINEMA",
                          "description": "aaaaaas",
                          "date": 15366707039,
                          "location": "sdfdsfsdf",
                          "photoUrl": "http/.home",
                          "organization": 1,
                          "artists": "AC/DC",
                          "places": [
                              1
                          ],
                          "visible": true,
                          "minAge": 34
                      }
                  ],
                  "pageable": {
                      "sort": {
                          "sorted": false,
                          "unsorted": true
                      },
                      "pageSize": 5,
                      "pageNumber": 0,
                      "offset": 0,
                      "paged": true,
                      "unpaged": false
                  },
                  "last": true,
                  "totalPages": 1,
                  "totalElements": 1,
                  "first": true,
                  "sort": {
                      "sorted": false,
                      "unsorted": true
                  },
                  "numberOfElements": 1,
                  "size": 5,
                  "number": 0
              }
            
9 Get all palace by event

    url: http://localhost:8080/places?event=1
    method: get  
    response: {
                  "content": [
                      {
                          "id": 1,
                          "price": 6.8,
                          "number": 4,
                          "row": 2,
                          "status": "ACTIVE",
                          "event": 1,
                          "sectionType": "FIRST_ROW"
                      }
                  ],
                  "pageable": {
                      "sort": {
                          "sorted": false,
                          "unsorted": true
                      },
                      "pageSize": 5,
                      "pageNumber": 0,
                      "offset": 0,
                      "paged": true,
                      "unpaged": false
                  },
                  "last": true,
                  "totalPages": 1,
                  "totalElements": 1,
                  "first": true,
                  "sort": {
                      "sorted": false,
                      "unsorted": true
                  },
                  "numberOfElements": 1,
                  "size": 5,
                  "number": 0
              }
              
10 Buy ticket

    url: http://localhost:8081/orders
    method: put   
    body: {
          	"placeId":1,
          	"paymentClient":2,
          	"paymentCustomer":1
          }
    response: 1
    
11 Get order client

    url: http://localhost:8081/orders?id=1
    method: get 
    response: {
                  "content": [
                      {
                          "id": 1,
                          "placeId": 1,
                          "paymentClient": 2,
                          "paymentCustomer": 1,
                          "amount": 6.8
                      }
                  ],
                  "pageable": {
                      "sort": {
                          "sorted": false,
                          "unsorted": true
                      },
                      "pageSize": 20,
                      "pageNumber": 0,
                      "offset": 0,
                      "paged": true,
                      "unpaged": false
                  },
                  "last": true,
                  "totalPages": 1,
                  "totalElements": 1,
                  "first": true,
                  "sort": {
                      "sorted": false,
                      "unsorted": true
                  },
                  "numberOfElements": 1,
                  "size": 20,
                  "number": 0
              }
              
12 Get ticket url

  1 - place id    
  2 - client's payment id  
  6.8 - ticket price   

     url: http://localhost:8082/tickets/1/2/6.8
     method: get 
     response: http://localhost:8082/tickets/getPdf?path=/home/user/11111/153785931vladcomrad.pdf
     

13 Get pdf ticket

     url: http://localhost:8082/tickets/getPdf?path=/home/user/11111/153785931vladcomrad.pdf
     method: get 
     response: PdfFile
        