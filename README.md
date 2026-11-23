# 🗺️ Google Maps Crawler & Mail Campaign API

A **reactive Spring Boot backend** that searches **Google Maps** for businesses by location, place type, or text, stores the results as **contacts**, and lets users run **email campaigns** with reusable HTML designs against those contacts.

Think of it as a small lead-generation engine: *find businesses → save them as contacts → send them a campaign*.

## ✨ What it does
- 🔎 **Google Places search** by query, location, place type, phone number, or place ID (Google Maps Services SDK)
- 📇 **Contacts management**, including bulk save and **CSV import** (OpenCSV)
- 🧾 **Scrape tasks** to track crawling jobs per user
- ✉️ **Mail campaigns** with stored **HTML designs** and per-user **SMTP configuration**
- 📤 **Email relay** endpoint to send emails through the user's SMTP server (Angus Mail)
- 🔐 **JWT authentication** with Spring Security, plus RSA endpoints to encrypt / decrypt sensitive data
- ⚡ Fully **non-blocking** with Spring WebFlux and reactive MongoDB

## 🛠️ Tech stack
| Layer | Technology |
|-------|------------|
| Language | Java 17 |
| Framework | Spring Boot 3.3, Spring WebFlux |
| Security | Spring Security, JJWT, RSA encryption |
| Database | MongoDB (reactive) |
| Integrations | Google Maps Services, Angus Mail, OpenCSV |
| Container | Docker, Docker Compose (Spring Boot Docker Compose support) |

## 🚀 Run it

### Prerequisites
- Java 17+ and Maven
- Docker
- A **Google Maps API key** with the Places API enabled

### 1. Configure your keys
Provide your Google Maps API key and JWT secret through environment variables or `src/main/resources/application.yml`. Never commit real keys.

### 2. Start the app
~~~bash
mvn spring-boot:run
~~~
Thanks to Spring Boot's Docker Compose support, MongoDB from `docker-compose.yml` is started automatically.

### Or build the Docker image
~~~bash
mvn clean package -DskipTests
docker build -t map-crawler .
~~~

## 📡 API tour
All endpoints are under the base path `/v1/api/google/crawler`.

### 🔐 Auth & security
| Method | Endpoint | Description |
|--------|----------|-------------|
| `POST` | `/auth/register` | Register a user |
| `POST` | `/auth/login` | Log in and receive a JWT |
| `GET` | `/obtain/public-rsa/key` | Get the RSA public key |
| `POST` | `/encrypt/data` / `/decrypt/data` | Encrypt / decrypt data |
| `POST` | `/store/stmp-config/{userId}` | Save a user's SMTP configuration |
| `GET` | `/smtp-first-config/{id}` | Get an SMTP configuration |
| `DELETE` | `/delete/smtp/{id}` | Delete an SMTP configuration |

### 🔎 Google Maps search
| Method | Endpoint | Description |
|--------|----------|-------------|
| `GET` | `/search` | Search places |
| `POST` | `/search/by/query` | Search by free-text query |
| `POST` | `/search/by/place-type` | Search contacts by place type |
| `GET` | `/search/location` | Search places near a location |
| `GET` | `/search/places-type` | Search by place type |
| `GET` | `/search/details/{placeId}` | Get place details |
| `GET` | `/search/photo` | Get a place photo by reference |
| `GET` | `/search/text_or_phone_number` | Search by text or phone number |
| `POST` | `/search/mail-list` | Build a mail list from search results |

### 📇 Contacts
| Method | Endpoint | Description |
|--------|----------|-------------|
| `GET` | `/data/all` · `/data/{id}` | List / get contacts |
| `POST` | `/data/store` · `/data/store/all` | Save one / many contacts |
| `PUT` | `/data/update/{id}` | Update a contact |
| `DELETE` | `/data/delete/{id}` | Delete a contact |
| `POST` | `/upload/csv` | Import contacts from a CSV file |
| `GET` | `/all/stored/csv` | List imported CSV files |

### ✉️ Campaigns, designs and tasks
| Resource | Endpoints |
|----------|-----------|
| Mail campaigns | `/mail/campaign/all`, `/mail/campaign/{id}`, `/mail/campaign/user/{id}`, `/save`, `/update/{id}`, `/delete/{id}` |
| HTML designs | `/html-design/all`, `/html-design/{id}`, `/add`, `/update/{id}`, `/delete/{id}` |
| Scrape tasks | `/scrape/task/all`, `/scrape/task/{id}`, `/scrape/task/user/{userId}`, `/save`, `/update/{id}`, `/delete/{id}` |
| Email relay | `POST /send-email/{userId}` |

## 🏗️ Project structure
~~~text
googlemapcrawler
├── configuration   # Google Maps client, JWT utility, security
├── constants       # Route and domain constants
├── controller      # REST controllers (auth, crawler, contacts, campaigns...)
├── entity          # MongoDB documents (Contacts, MailCampaign, ScrapeTask, Smtp, User...)
├── repository      # Reactive repositories
└── services        # Business logic
~~~

## 🗺️ Roadmap
- [ ] Move all credentials to environment variables
- [ ] Add unit and integration tests (Testcontainers + MongoDB)
- [ ] Add OpenAPI documentation
- [ ] Add a GitHub Actions build

## 👤 Author

**Lazaro Noel Guerra Medina**, Java Backend Engineer

> *"Make it work, make it right, make it fast."* ☕
