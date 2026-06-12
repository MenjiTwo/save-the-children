# Save the Children - Volunteer Management API

This repository contains the backend infrastructure for the **Save the Children Philippines** Volunteer application tracking system. It provides a robust, scalable REST API and a normalized relational database designed to capture, validate, and manage volunteer data.

## 🏗 Architecture

The system is built on a 3-tier architecture, currently exposing the Database and Application layers:

1. **Database Layer (MySQL 8.0)**: A fully normalized 3rd Normal Form (3NF) relational database.
2. **Application Layer (Spring Boot 3.2)**: A Java-based REST API utilizing Spring Data JPA (Hibernate) for robust ORM mapping, input validation, and business logic enforcement.
3. **Frontend Layer**: A vanilla HTML/CSS/JS frontend application located in the `frontend/` directory. The API is configured with Cross-Origin Resource Sharing (CORS) to accept requests smoothly.

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

### Quick Start Script (Windows Only)

This guide is specifically tailored for Windows environments. If you are a frontend developer, follow these exact steps to get the backend running locally so you can start hitting the APIs.

**Requirements:**
- **Java 22** (or higher) installed. Download from [https://adoptium.net/](https://adoptium.net/). Make sure to check "Add to PATH" during installation.
- **MySQL 8.0** running on port `3306`. Works with XAMPP, MySQL Installer, WAMP, or Laragon.

#### Step 1: Clone the Repository
Open PowerShell (Start Menu → type `PowerShell` → Enter) and run:
```powershell
git clone https://github.com/MenjiTwo/save-the-children.git
cd save-the-children
```

#### Step 2: Run setup.bat (One-Click Setup)
Double-click **`setup.bat`** from File Explorer, or run it in PowerShell:
```powershell
.\setup.bat
```

The script automatically handles everything:
1. **Validates Java** — checks if Java is installed and provides download links if not.
2. **Finds MySQL** — auto-detects `mysql.exe` across common installation paths (XAMPP, MySQL Installer, WAMP, Laragon). If it can't find it, it will ask you to paste the path manually.
3. **Tests MySQL connection** — verifies the credentials work before running any SQL scripts.
4. **Creates the database** — runs `schema.sql` (8 tables) and `seed.sql` (10 mock applicants + catalog data).
5. **Downloads Java dependencies** — like running `pip install -r requirements.txt`, but for Java.
6. **Starts the API server** — boots up the Spring Boot server on port `8080`.

Once the terminal says `Tomcat started on port 8080`, your backend is fully active!

**Test it in your browser:**
Navigate to 👉 `http://localhost:8080/api/catalog/skills` to verify the backend is returning JSON data.

> **Note:** The default MySQL credentials are `root` / `root1234`. If yours are different, edit `backend\src\main\resources\application.properties` before running the script.
