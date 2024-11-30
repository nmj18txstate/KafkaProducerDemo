Kafka Producer Demo
Project Description
This project demonstrates a Kafka Producer built using Spring Boot. The producer is configured to send messages to a Kafka topic using REST API endpoints. The Kafka setup utilizes Docker containers for both the Kafka broker and Zookeeper.

Technologies Used
Java 22.0.1 (JDK 22)
Spring Boot 3.x
Apache Kafka
Docker (for Kafka and Zookeeper)
Postman (for testing API endpoints)
Setup Instructions

1. Pre-Requisites
Install Docker Desktop:
Verify installation with docker --version.
Install Postman or any HTTP client for testing the REST API.
Install Git for version control.

2. Run Kafka and Zookeeper Using Docker
Start Zookeeper:
docker run -d --name zookeeper -p 2181:2181 zookeeper

Start Kafka:
docker run -d --name kafka -p 9092:9092 --link zookeeper:zookeeper \
    -e KAFKA_ZOOKEEPER_CONNECT=zookeeper:2181 \
    -e KAFKA_ADVERTISED_LISTENERS=PLAINTEXT://localhost:9092 \
    -e KAFKA_OFFSETS_TOPIC_REPLICATION_FACTOR=1 \
    confluentinc/cp-kafka

Verify Containers: Run docker ps to ensure zookeeper and kafka containers are running.

3. Build and Run the Project
Clone the repository:
git clone 
cd KafkaProducerDemo
Open the project in an IDE (IntelliJ or Eclipse).

Build and run the project:
Use the IDE's "Run" button.
Alternatively, use the terminal:
mvn spring-boot:run
Verify the application is running on http://localhost:8080.

How to Test:
1. API Endpoint
The Kafka producer exposes a REST endpoint to send messages to the Kafka topic.

Endpoint: POST http://localhost:8080/api/kafka/publish
Query Parameter: message (The message to be sent to the topic)
Example Request in Postman
URL:
http://localhost:8080/api/kafka/publish?message=HelloKafkaDocker
Response:
json
{
  "status": "success",
  "message": "Message sent to Kafka topic successfully."
}

2. Verify Kafka Topic Messages
Open a Kafka consumer to listen to the topic:
docker exec kafka kafka-console-consumer --bootstrap-server localhost:9092 --topic test-topic --from-beginning


Publish messages using Postman, and observe the output in the console.

Kafka Topic Details:
Topic Name: test-topic
Replication Factor: 1
Partitions: 1

Commands to Manage Topics:
List Topics:
docker exec kafka kafka-topics --list --bootstrap-server localhost:9092
Describe Topic:
docker exec kafka kafka-topics --describe --bootstrap-server localhost:9092 --topic test-topic

Project Structure:
1. Java Classes
KafkaProducerConfig:
Configures the Kafka producer properties, including the bootstrap server and serializers.
KafkaProducerController:
REST controller exposing the /publish endpoint to send messages.
KafkaMessageProducer:
Service that handles the Kafka producer logic and sends messages to the configured topic.
KafkaProducerDemoApplication:
Main class to bootstrap the Spring Boot application.

2. Configuration
application.yml:

spring:
  kafka:
    bootstrap-servers: localhost:9092
    producer:
      key-serializer: org.apache.kafka.common.serialization.StringSerializer
      value-serializer: org.apache.kafka.common.serialization.StringSerializer
    topic:
      name: test-topic

Sample Producer Logs:
When you publish a message via Postman, the logs should show:

INFO  --- [nio-8080-exec-2] o.a.k.clients.producer.KafkaProducer : [Producer clientId=producer-1] Message sent successfully to topic test-topic

Consumer Logs:
Run the consumer command to see the following:

HelloKafkaDocker

Known Issues:
Consumer not consuming messages:
Ensure the consumer is running and pointing to the correct topic and broker.

Docker conflict error:
Remove any old containers with:
docker rm -f kafka
docker rm -f zookeeper
