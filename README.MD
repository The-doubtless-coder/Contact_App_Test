📘 ContactsCatalog - Backend
A backend service for managing contacts with CRUD APIs and a SOAP API for querying contacts by organization name.

📌 Features
CRUD APIs (Create, Read, Update, Delete) for managing contacts
REST API using JAX-RS
SOAP API using JAX-WS (Query contacts by organization name)
Duplicate Check (Prevents duplicate idNumber, email, phoneNumber before insert/update)
Security Enhancements:
Phone numbers are hashed before saving
Names and phone numbers are masked for privacy
🛠️ Tech Stack
Java (JDK 11/17)
JAX-RS (REST API)
JAX-WS (SOAP API)
Maven (for dependency management)
MySQL (Database)

🚀 Running the Project
🔧 Prerequisites
Java 11+
Maven
A running database (MySQL)
Apache Tomcat
intellij IDEA
💻 Setup & Run
Clone the repository

bash
Copy
Edit
git clone https://github.com/The-doubtless-coder/Contact_App_Test.git
cd ContactsCatalog-Backend
Configure Database

Update context.xml with DB credentials.
Build the Project

bash
Copy
Edit
mvn clean install
Run the Project

bash
Copy
Edit
Access APIs:

REST API Base URL: http://localhost:8080/Contact_Registry_Web_App_war/rest/api/contacts
SOAP WSDL URL: http://localhost:8080/Contact_Registry_Web_App_war/ws/contacts?wsdl

📡 API Endpoints
🟢 REST API (JAX-RS)
Method	Endpoint	Description
POST	/api/contacts	Create a new contact
GET	/api/contacts/{id}	Get contact by ID
PUT	/api/contacts/{id}	Update an existing contact
DELETE	/api/contacts/{id}	Delete a contact
🟣 SOAP API (JAX-WS)
WSDL: http://localhost:8080/ws/contacts?wsdl

Operations:

getContactsByOrganization(String orgName) → Returns contacts filtered by organizationName

🔐 Data Security Measures
Phone Number Hashing: Phone numbers are securely hashed before storing in the database.
Data Masking: Names and phone numbers are masked when displaying records.
Duplicate Checks: Prevents storing contacts with the same idNumber, email, or phoneNumber.

📄 License
MIT License (or your preferred license)

