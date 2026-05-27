# Save the Children - Volunteer Management API

This repository contains the backend infrastructure for the **Save the Children Philippines** Volunteer application tracking system. It provides a robust, scalable REST API and a normalized relational database designed to capture, validate, and manage volunteer data.

## 🏗 Architecture

The system is built on a 3-tier architecture, currently exposing the Database and Application layers:

1. **Database Layer (MySQL 8.0)**: A fully normalized 3rd Normal Form (3NF) relational database.
2. **Application Layer (Spring Boot 3.2)**: A Java-based REST API utilizing Spring Data JPA (Hibernate) for robust ORM mapping, input validation, and business logic enforcement.
3. **Frontend Layer (TBD)**: The API is configured with Cross-Origin Resource Sharing (CORS) to accept requests from any Javascript-based frontend application (React, Vue, Next.js).

---

## 💾 Database Schema (3NF)

The database (`savethechildren_volunteer_db`) consists of 8 precisely mapped tables to ensure data integrity and eliminate redundancies.

### Main Entities
*   `applicant_Details`: The core table containing the 1-to-1 atomic attributes of an applicant (e.g., Name, Address, Contact details, Availability).
    *   **Primary Key**: `applicant_id` (Auto-generated format: `A001`, `A002`)

### 1:0..1 Relationships (One-to-Optional-One)
*   `applicant_licenseCertification`: Stores professional licenses.
*   `previous_volunteer_Engagement`: Stores past volunteer experiences.
*   *Implementation Note: These use the `applicant_id` as their Primary Key (acting as both PK and FK) to strictly enforce the 1:0..1 cardinality constraint.*

### Catalog Entities (Lookup Tables)
*   `skill_Catalog`: Defines the 11 valid skills (`S01` to `S11`).
*   `interest_Catalog`: Defines the 3 valid areas of interest (`IC1` to `IC3`).

### Many-to-Many Junction Tables
*   `applicant_Languages`: Maps applicants to the languages they speak.
*   `applicant_Skills`: Maps applicants to their selected skills from the catalog.
*   `applicant_Interests`: Maps applicants to their selected interests from the catalog.

---

## 🚀 API Endpoints

The backend runs on `http://localhost:8080` and exposes the following JSON endpoints:

### 1. Create a New Applicant
**POST** `/api/applicants`
Accepts a JSON payload representing the applicant. The backend automatically generates the sequential `Applicant ID`.
*   **Payload Example**:
    ```json
    {
      "lastName": "Doe",
      "firstName": "John",
      "emailAddress": "john@example.com",
      "mobilePhone": "0999 123 4567",
      "gender": "Male",
      "birthdate": "1990-01-01",
      "citizenship": "Filipino",
      "availabilityOption": "Weekends",
      "hoursPerDay": 8,
      "willingToDeploy": true,
      "languages": ["English", "Tagalog"],
      "skillCodes": ["S01", "S05"],
      "interestCodes": ["IC2"],
      "licenseCertification": null,
      "volunteerEngagement": null
    }
    ```
    *(Note: Refer to the database schema for the complete list of ~25 mandatory NOT NULL fields required in the POST payload).*

### 2. Fetch All Applicants (Admin Only)
**GET** `/api/applicants`
*   **Headers Required**: `X-Role: ADMIN`
*   **Returns**: An array of all applicants with their nested relationships fully resolved.

### 3. Search / Filter Applicants (Admin Only)
**GET** `/api/applicants/search`
*   **Headers Required**: `X-Role: ADMIN`
*   **Query Parameters** (Optional):
    *   `lastName` (String)
    *   `skillCode` (String, e.g., `S01`)
    *   `language` (String, e.g., `Tagalog`)
    *   `interestCode` (String, e.g., `IC1`)
    *   `willingToDeploy` (Boolean: `true` / `false`)

### 4. Fetch Catalogs (Public)
**GET** `/api/catalog/skills`
**GET** `/api/catalog/interests`
*   **Returns**: The predefined JSON list of valid catalog items and their corresponding ID codes.

---

## 🛠 Setup & Installation

### Quick Start Script for Frontend Developers
If you already have Java 22+ and MySQL running locally, open your terminal and run this script to instantly clone, build, and start the backend:

```bash
# 1. Clone the repository
git clone https://github.com/MenjiTwo/save-the-children.git
cd save-the-children

# 2. (Optional) Run the database scripts if you haven't yet
# Ensure MySQL is running on port 3306 with user 'root' and password 'root1234'
# mysql -u root -proot1234 < db/schema.sql
# mysql -u root -proot1234 < db/seed.sql

# 3. Start the Spring Boot Backend Server
cd backend

# On Windows:
.\mvnw.cmd spring-boot:run

# On Mac/Linux:
./mvnw spring-boot:run
```
*(The server will boot up and be accessible at `http://localhost:8080/api/applicants`)*

### Manual Prerequisites
*   **Java 22** or higher
*   **MySQL 8.0** or higher
*   **Maven** (Optional, the wrapper can be used)

### 1. Database Initialization
1.  Log in to your local MySQL server using `root` and password `root1234`.
2.  Navigate to the `db/` folder in this repository.
3.  Execute `schema.sql` to generate the 8 tables.
4.  Execute `seed.sql` to populate the catalogs and inject 10 mock applicants.

### 2. Running the Server
1.  Open a terminal in the `backend/` directory.
2.  Run the application using Maven:
    ```bash
    mvn spring-boot:run
    ```
    *(If using the wrapper on Windows: `.\mvnw.cmd spring-boot:run`)*
3.  The server will initialize Hibernate, validate the schema against the database, and start listening on port `8080`.
