CREATE DATABASE contactsDB;
USE contactsDB;

CREATE TABLE contacts (
                          id INT AUTO_INCREMENT PRIMARY KEY,
                          full_name VARCHAR(255) NOT NULL,
                          phone_number VARCHAR(20) NOT NULL UNIQUE,
                          email VARCHAR(255) UNIQUE,
                          id_number VARCHAR(50) UNIQUE,
                          dob DATE,
                          gender VARCHAR(10),
                          organization VARCHAR(255),
                          masked_name VARCHAR(255),
                          masked_phone VARCHAR(20),
                          hashed_phone VARCHAR(64) NOT NULL
);

-- Optional: Create an index for faster lookup based on phone number
CREATE INDEX idx_contacts_phone ON contacts(phone_number);
