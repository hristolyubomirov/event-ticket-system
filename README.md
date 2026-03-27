# Event Ticket System

A distributed Spring application designed to handle event management and automated user notifications. This project demonstrates the integration of a relational database, high-speed caching, and a message broker for asynchronous processing.

## Architecture Overview

The system consists of microservices orchestrated by Docker.

- **Spring Boot Application:** The core logic handling REST endpoints and business logic.
- **PostgreSQL:** Primary relational database for storing users and event data.
- **Redis:** Used for caching frequently accessed data to reduce database load and reduce read latency.
- **Apache Kafka and Zookeeper:** Manages the asynchronous message stream for event notifications.
- **Mail Service:** Automated email delivery based on user preferences.
---
# Setup and Installation

### Environment Configuration

**.env** file is used as environment piping to secure sensitive credentials. You must create a file named **.env** with the following parameters included:

`DB_USERNAME=xxxxx`
`DB_PASSWORD=xxxxx`
`DB_URL=jdbc:postgresql://postgre:5432/eventsdb`
`GMAIL_USERNAME=xxxxx@xxx.xx`
`GMAIL_PASSWORD=xxxxx`

### Build the Application

- Compile the java code into Jar file:

`./mvnw clean package -DskipTests`

- Build all images and start all containers:

`docker-compose up --build -d`

---

## Core Workflow

1. **User Interest**: A user registers with a specific preference (e.g., *Movie*).
2. **Event Publishing**: A new event creation appears.
3. **Async Notification**: The system sends a message to Kafka. The consumer checks if the event category and the user preferences are matching and sends an Email to notify the users for the new event.
4. **Data filtering**: Users can search for events using filters, which are optimized by Redis to ensure low-latency responses.

---

## APIs


## Registration

**POST** `/register`
Registers a new user into the system.

Example: `{"name": "username", "email": "xxxx@xxxx.xxx", "prefCategory": "Movie"}`

**GET** `/allusers`
Retrieves the full directory of registered users.

**PATCH** `/addcategory`
Updates a user's interest.

Required Parameters: `name=userName`, `changeCategory=newCategory`

**DELETE** `/delete-user/{userName}`
Removes a user from the database.

---

## Events

**POST** `/events-create`
Publishes a new event and triggers the Kafka notification flow.

Example: `{"category": "Movie", "eventName": "XXXX", "location": "Sofia", "price": 11.99, "ticketsCount": 10}`

**GET** `/events/all`
Displays all available events.

**GET** `/events/search`
Advanced filtering for the event database.

- **By Category**: `?category=Movie`
- **By Price Range**: `?priceFrom=10&priceTo=20`
- **By Location:** `?location=Sofia`
- **By Date (start, end):** `?start={dd/MM/yyyy}&end={dd/MM/yyyy}`
- **By Name:** `?eventName=XXXX` (returns all alike).
- **Combinations:** All could be combined together `?category=Movie&priceTo=20&location=Sofia..`

---

## Booking & Cancellation

**GET** `/bookings`
Returns all tickets associated with a specific user.

**Required Parameter**: `userId=1`

**DELETE** `/bookings/cancel`
Cancels a booking reservation and restores the ticket count.

**Required Parameter**: `bookingId=XX`

---
<img width="643" height="515" alt="blur_booking-sys-email-notify" src="https://github.com/user-attachments/assets/54022ed3-f119-4015-9827-bea51d5d740d" />


