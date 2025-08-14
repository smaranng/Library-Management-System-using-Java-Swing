# 🏦 Library Management System using Java Swing

---

A robust and user-friendly Library Management System built with Java Swing, designed to help libraries efficiently organize, track, and manage their resources. This application enables seamless book management, user tracking, borrowing/returning flows, and feedback handling—all through a modern graphical interface.

---

## ✨ Features at a Glance

- **Book Management:** Add, update, delete, and search for books.
- **User Management:** Store, search, and manage user information (students/teachers).
- **Borrowing System:** Track borrowed and returned books, with automated fine calculation for overdue items (₹1 per day).
- **Customer Feedback:** Collect and review customer feedback to improve library services.
- **Intuitive GUI:** Interactive, easy-to-use Java Swing interface.
- **Database Integration:** Securely persists data using MySQL and JDBC.

---

## 📦 Modules

The system is organized into two primary modules:

### 1. Admin Module
- **Admin Login:** Secure login for administrators.
- **Book Controls:** Add (`insertBooks()`), display (`displayBooks()`), and search (`searchBooks()`) all books.
- **Feedback Review:** View customer feedback via `displayFeedback()`.

### 2. Customer Module
- **Customer Login:** Secure login for students and teachers.
- **Browse & Search:** View all books (`displayBooks()`), search by title (`searchBooks()`).
- **Borrow Books:** Check out books with due dates and availability checks (`borrowBook()`).
- **Return Books:** Return items and handle overdue fines (`returnBook()`).
- **Feedback Submission:** Submit suggestions or feedback (`collectCustomerFeedback()`).

---

## 🛠️ Technologies Used

- **Java Swing:** For a modern, graphical user interface.
- **MySQL:** To store and manage all library data.
- **JDBC:** For secure database connectivity.

---

## 🚀 Getting Started

### 1. **Clone the Repository**

```bash
git clone https://github.com/smaranng/Library-Management-System-using-Java-Swing.git
```

### 2. **Database Setup**

1. **Install MySQL** and create a database named `lms`.
2. **Import Tables:** Use the provided SQL file to set up all necessary tables.
3. **Configure Database Connection:**  
   Update your connection string in the Java code as needed (e.g. `jdbc:mysql://localhost:3306/lms`).  
   Make sure to enter your MySQL credentials in the appropriate place.

### 3. **Prepare Images**

Place the required images (`logo1.png`, `logo2.png`, `logo3.png`) in your `Downloads` directory, or update the image paths in the source code as needed.

### 4. **Run the Application**

Compile and run `LibraryGUI.java` using your preferred Java IDE (Eclipse, VS Code, IntelliJ, etc.).

---

## 🎬 Usage Guide

1. **Launch the application** by running `LibraryGUI.java`.
2. **Admin Login:** Access admin features by logging in with admin credentials.
3. **Customer Login:** Students or teachers log in to borrow/return books and provide feedback.
4. **Navigate:** Use the intuitive GUI to manage books, users, borrowing, returns, and feedback.
5. **Database & Images:** Ensure the MySQL server is running and the images are accessible at the expected paths.

---

## 🛠️ Troubleshooting

- **No Suitable Driver Error:**  
  Ensure the MySQL JDBC Driver is included in your project's classpath.

- **Image Not Displaying:**  
  Double-check that all image paths in the code point to the correct location.

- **Database Errors:**  
  Verify that the MySQL server is running and credentials/connection URL are correct.

---

## 📂 Project Structure

```
Library-Management-System-using-Java-Swing/
│
├── src/
│   ├── LibraryGUI.java
│   ├── Admin.java
│   ├── Customer.java
│   └── ... (other source files)
│
├── SQL/
│   └── lms_schema.sql
│
├── README.md
└── ... (images, docs, etc.)
```

---

## 🤝 Contributing

Contributions, suggestions, and improvements are welcome!  
Feel free to fork the repo, create a pull request, or open an issue if you find a bug.

---

## 👤 Author

**Smaran N G**  
[GitHub](https://github.com/smaranng)

---

**Enjoy managing your library with ease!**

