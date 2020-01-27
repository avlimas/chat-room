# Chat-room

A simple RESTful application to send or receive a message. 

If you do not have any mongodb instance in your system, you can use docker instance inside the root folder: 
``` 
cd <path-to-your-chat-room-root-folder>
docker-compose up -d
docker ps
``` 
A docker instance with demo data will be created with name "chat_mongo".
To compile the application, please run these commands:
``` 
./gradlew clean build
``` 
To run the application, please use these commands:
``` 
cd <path-to-your-chat-room-folder>/build/libs
java -jar chat-room-1.0.0.jar
``` 
You can connect to the application by these endpoints below. By default, the hostname is locahost and port is 8080.
The default setting for the mongodb is:
 - hostname:localhost
 - port: 27017
 - database_name: chat_db
 
## Prequisites

MongoDB, JDK8 are required but Docker for Windows/ Docker Toolbox is optional.

## REST Endpoints
There are 6 endpoints in this application:

- Send a message: http://localhost:8080/api/v1/chat/send-message
  This endpoint require a JSON body:
  
- Get incoming messages of a user: http://localhost:8080/api/v1/chat/incoming-messages?receiver={receiver}
- Get outcoming messages by a user: http://localhost:8080/api/v1/chat/outcoming-messages?sender={sender}
- Get message details: http://localhost:8080/api/v1/chat/message-details?subject={subject}
- Estimation of messages per day: http://localhost:8080/api/v1/chat/estimate-messages-today
- Estimation of messages per week:http://localhost:8080/api/v1/chat/estimate-messages-weekly
