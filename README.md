# 📧 Email Service with Apache Kafka

A Spring Boot application that uses Apache Kafka for asynchronous email processing. The primary objective of this project is to send a beautifully formatted welcome email to users after successful registration or login.

## 🚀 Features

* Send automated welcome emails
* Apache Kafka Producer & Consumer implementation
* Asynchronous event-driven architecture
* HTML Email Templates
* Spring Boot REST APIs
* User registration event processing
* Clean and responsive email design

## 🛠️ Tech Stack

* Java
* Spring Boot
* Apache Kafka
* Spring Kafka
* Java Mail Sender
* Maven

## 📂 Project Structure

```text
src/main/java
├── config
│   └── KafkaConfig
├── controller
│   └── SignupController
├── kafka
│   ├── EmailProducer
│   └── EmailConsumer
├── model
│   ├── EmailFormat
│   └── SignupRequest
├── service
│   └── EmailService
└── EmailserviceApplication
```

## ⚙️ How It Works

1. User signs up on the platform.
2. A signup event is published to Kafka.
3. Kafka Producer sends the event to a topic.
4. Kafka Consumer listens for incoming events.
5. Email Service processes the event.
6. A personalized welcome email is delivered to the user.

## 📬 Welcome Email Preview

The application sends a professional welcome email similar to the example below:

* Personalized greeting
* Account creation confirmation
* Call-to-action button
* Clean and modern email layout

## 📸 Project Screenshots

<img width="916" height="486" alt="Screenshot 2026-06-05 180645" src="https://github.com/user-attachments/assets/59bf4550-0563-4b6a-ad6c-37806fd3ee21" />


## ▶️ Running the Project

Clone the repository:

```bash
git clone https://github.com/arshad-shaikh81/Email-Service.git
```

Navigate to the project directory:

```bash
cd Email-Service
```

Run the application:

```bash
mvn spring-boot:run
```

Or start the application directly using:

```java
EmailserviceApplication.java
```

## 🔮 Future Enhancements

* Password Reset Emails
* Login Alert Notifications
* Email Queue Monitoring
* Docker Support
* Microservices Deployment
* Email Analytics Dashboard

## 👨‍💻 Author

### Arshad Shaikh

Backend Developer passionate about Java, Spring Boot, Apache Kafka, and Microservices.

* GitHub: https://github.com/arshad-shaikh81
* LinkedIn: https://www.linkedin.com/in/arshad-shaikh-990878365/
