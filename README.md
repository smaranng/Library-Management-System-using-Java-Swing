

## 🏦Library Management System using Java Swing**
---

A Java Swing-based Library Management System that allows users to manage books, track borrowed items, and store user details. The system is designed to help libraries organize their resources efficiently.

---

**Modules**


The system is organized into two major modules:

- Admin Module


- Customer Module


**Core Features**


- Book Management: Add, update, delete, and search for books.


- User Management: Store and manage user information.


- Borrowing System: Track borrowed and returned books with a fine of ₹1 imposed per day if a book is returned past the due date.


Customer Feedback: Allows customers to provide feedback, viewable by the admin.


Graphical User Interface (GUI): Interactive and user-friendly GUI built with Java Swing.


Database Connectivity: Connects to a MySQL database to store and retrieve information securely.


**Admin Module**


Admin Login - The handleAdminLogin() method allows the admin to log in with a username and password.


Add Books - The insertBooks() method enables the admin to add new books to the system.


Display Books - The displayBooks() method displays a list of all available books.


Search Books - The searchBooks() method allows for searching books by title.


View Customer Feedback - The displayFeedback() method lets the admin view feedback submitted by customers.


**Customer Module**


Customer Login - The handleStudentTeacherLogin() method allows customers to log in and access available features.


View Books - Customers can use displayBooks() to view a list of books.


Search Books - The searchBooks() method enables customers to search for a book by title.


Borrow Book - Using the borrowBook() method, customers can borrow a book by entering its ID. The method checks availability and provides a due date.


Return Book - The returnBook() method allows customers to return borrowed books. If returned after the due date, a fine of ₹1 per day is imposed.


Provide Feedback - The collectCustomerFeedback() method allows customers to submit feedback, stored in the database.


**Technologies Used**


Java Swing for the graphical interface


MySQL for the backend database


JDBC for database connectivity


**Setup and Installation**


Clone the repository:

Copy code


git clone https://github.com/smaranng/LibManagementSystem-using-Java-Swing.git


**Database Setup:**

Install MySQL and create a database named lms.


Import the provided SQL file to set up tables.


Configure the Database Connection:

Ensure the database connection details in your code (e.g., jdbc:mysql://localhost:3306/lms) match your MySQL configuration.


**Save Images:**

Place images logo1.png, logo2.png, logo3.png in your Downloads directory.


**Run the Application:**

Compile and run the LibraryGUI.java file in your preferred IDE (e.g., Eclipse, VS Code).


**Launching the System:**


Open the main application window by running LibraryGUI.java.


Navigating the System:


Use the provided GUI to add or manage books, track user information, check out or return books, and submit feedback.


**Troubleshooting**


No Suitable Driver Error: Ensure the MySQL JDBC Driver is added to your project’s classpath.


Image Not Displaying: Confirm that image paths are correctly specified in the code.

