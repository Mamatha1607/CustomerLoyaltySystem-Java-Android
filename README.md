# **LoyaltyFirst: Customer Loyalty Program System**

## **Overview**

**LoyaltyFirst** is a **customer loyalty program** application that demonstrates the complete design of a 3-tier system, combining **Java Servlets**, **JSP**, **Android**, and **MySQL** database technology. The project aims to create a **real-world loyalty program system** where customers can collect and redeem points, track transactions, and interact with various loyalty program features via a mobile app and a web-based backend.

This project consists of:

- **Server-side business logic** developed using Java Servlets and JSPs, with JDBC integration to interact with a MySQL database.
- **Android client** that serves as the user interface for customers to interact with the loyalty system.
- **Full-stack development** demonstrating interaction between Android apps and a backend system.

---

## **Key Features**

### **Server-Side Logic (Java Servlets & JSPs)**

1. **Login System**:  
   A login system that verifies the user’s credentials (username and password) and returns a customer ID upon successful authentication.

2. **Customer Information**:  
   The system can retrieve customer details such as name and loyalty points via a JSP page.

3. **Transaction History**:  
   Customers can view their complete transaction history, including points earned, transaction dates, and associated amounts.

4. **Prize Redemptions**:  
   Customers can redeem loyalty points for prizes. The system displays available prizes and redemption details such as points required.

5. **Family Loyalty Increase**:  
   The system supports loyalty point distribution to a customer’s family members based on a transaction's details.

### **Android Mobile App**

1. **Login Activity**:  
   Users can log in using their credentials, which are verified through the backend Java servlet.

2. **Customer Dashboard**:  
   Displays the customer’s name, total loyalty points, and their associated image pulled from the server.

3. **Transactions and Redemption**:  
   Allows customers to view their transactions and redeem prizes by selecting from available options.

4. **Family Point Allocation**:  
   Enables customers to allocate points to their family members based on a specified transaction reference.

---

## **Technologies Used**

- **Java Servlets & JSP**: For server-side business logic and web page rendering.
- **Android Studio**: For building the mobile client application.
- **MySQL**: For database management to store customer data, transaction records, and prize information.
- **JDBC**: For connecting the Java backend with the MySQL database.
- **Apache Tomcat**: For hosting the server-side code.

---

## **Project Structure**

### **Backend (Server-Side)**

- **Servlets**: Java code for handling user requests, such as login and retrieving customer data.
- **JSP Pages**: Render customer and transaction data dynamically.
- **Database Schema**: A MySQL database that contains tables for customers, transactions, prizes, etc.

### **Android App**:  
- **MainActivity**: Handles user login and authentication.
- **MainActivity2 - MainActivity6**: Different activities to display customer details, transaction history, prize redemptions, and family point increases.

---

## **How to Run the Project**

### **Step 1: Set Up the Backend**

1. Install **Apache Tomcat** and deploy the project under the `webapps` folder.
2. Set up **MySQL** with the provided database schema and required tables.
3. Start the Tomcat server to handle requests.

### **Step 2: Set Up the Android Client**

1. Open the **Android Studio** project.
2. Run the app using an Android emulator or a physical device.

### **Step 3: Test the System**

- Access backend services through URLs like:  
  `http://127.0.0.1:8080/loyaltyfirst/login?user=certainuser&pass=certainpass`  
- Test the interaction between the Android app and the backend.

