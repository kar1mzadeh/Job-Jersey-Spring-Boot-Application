
# 🚀 Job Jersey Application

## 📌 Overview  
The **Job Jersey Application** is a **Spring Boot microservices-based** system that allows users to search for jobs based on company reviews. It is built using a **distributed architecture** with **Eureka Service Discovery, API Gateway, OpenFeign for inter-service communication, and Resilience4J Retry for fault tolerance.**  

All microservices are containerized, and the entire system can be **started with a single Docker Compose command**.  

---

## 🏗️ Microservices Architecture  
| Service | Port | Description |
|---------|------|-------------|
| **Job Service** | `8082` | Manages job postings (`/jobs`) |
| **Company Service** | `8081` | Manages company data (`/companies`) |
| **Review Service** | `8083` | Handles company reviews (`/reviews?companyId=`) |
| **API Gateway** | `8084` | Routes traffic & load balances requests |
| **Config Server** | `8080` | Centralized configuration management |
| **Eureka Server** | `8761` | Service discovery & monitoring |
| **RabbitMQ** | `5672` | Message queue for async communication |
| **Zipkin** | `9411` | Distributed tracing |
| **PostgreSQL** | `5432` | Database for storing job, company, and review data |
| **pgAdmin** | `5050` | Database management UI |

---

## ✨ Features  
✅ **Microservices Architecture** (Spring Boot + Eureka + Config Server)  
✅ **API Gateway with Load Balancing** (Spring Cloud Gateway + LB)  
✅ **Service Discovery & Monitoring** (Eureka Server)  
✅ **OpenFeign for Inter-Service Communication**  
✅ **Resilience4J Retry for Fault Tolerance**  
✅ **RabbitMQ for Event-Driven Communication**  
✅ **Distributed Tracing with Zipkin**  
✅ **PostgreSQL for Data Storage**
✅ **Docker Compose for Easy Deployment**  

---

## 🏗️ How OpenFeign & Circuit Breaker Work in This Application  

### 📌 OpenFeign for Inter-Service Communication  
Instead of using **RestTemplate or WebClient**, OpenFeign makes it easier to communicate between microservices.  

### ✅ Feign Client Definitions  

**CompanyClient.java (Calls Company Service)**  
```java
@FeignClient(name="COMPANYMS", url = "${companyms.url}")
public interface CompanyClient {
    @GetMapping("/companies/{id}")
    Company getClient(@PathVariable("id") Long id);
}
```

**ReviewClient.java (Calls Review Service)**  
```java
@FeignClient(name = "REVIEWMS", url = "${reviewms.url}")
public interface ReviewClient {
    @GetMapping("/reviews")
    List<Review> getReviews(@RequestParam("companyId") Long companyId);
}
```

### ✅ How It Is Used in Job Service  

```java
@Autowired
private CompanyClient companyClient;

@Autowired
private ReviewClient reviewClient;

private JobDTO convertToDto(Job job) {
    // Fetch company details
    Company company = companyClient.getClient(job.getCompanyId());
    
    // Fetch reviews
    List<Review> reviews = reviewClient.getReviews(job.getCompanyId());

    // Convert to DTO using a mapper
    JobDTO jobDTO = JobMapper.jobMappingtoDTo(job, company, reviews);
    
    return jobDTO;
}
```

### 🔥 Benefits of OpenFeign  
✅ **No need for RestTemplate or WebClient**  
✅ **Built-in load balancing with Eureka**  
✅ **Simplifies inter-service communication**  
✅ **Declarative REST API calls**  

---


## 🛠️ How to Run the Application  

### ✅ **Run Everything Using Docker Compose**  
You can start all microservices with **one command** using Docker Compose:  
```sh
docker-compose up -d
```

### ✅ **Check If Services Are Running**  
- **Eureka Server:** `http://localhost:8761/`  
- **Zipkin Dashboard:** `http://localhost:9411/`  
- **RabbitMQ Admin Panel:** `http://localhost:15672/`  
- **pgAdmin:** `http://localhost:5050/`  

---

## Contributing

If you find something you'd like to improve or add, follow these steps:

1. **Fork the repository** and clone it to your local machine.
2. **Create a new branch** for your changes.
   ```bash
   git checkout -b your-branch-name
   ```
3. **Make your changes** and commit them.
   ```bash
   git commit -m "Your commit message"
   ```
4. **Push your branch** to your forked repository.
   ```bash
   git push origin your-branch-name
   ```
5. **Open a pull request** to merge your changes.

---

## 🎯 Conclusion  

This project follows **microservices best practices** with **Spring Boot, Eureka, OpenFeign, Circuit Breaker, Docker, RabbitMQ, and Zipkin**. It provides **scalability, fault tolerance, and ease of deployment**.  

