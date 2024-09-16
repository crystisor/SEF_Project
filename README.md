# SEF_Project


## Description
This project is a Library Management System, which allows users and managers to log in, manage books, and perform various tasks based on their roles. The system features an interactive graphical user interface, data persistence using a database, and supports role-based access control with secure login mechanisms (passwords stored using encryption). The application supports account creation and session management (login/logout) and includes several key features to manage the library's collection.

## Table of Contents
* [Features](Features)
* [Installation](Installation)
* [Usage](Usage)  
  - [Example](Example_Scrrens)
* [Use Cases](Use_Cases)
* [License](License)

## Features
* Interactive Graphical Interface: Provides an easy-to-use GUI for library management tasks.
* Role-based Access: Users can log in with different roles (User or Manager), each role having different permissions and features.
* Account Creation: New accounts can be created, with passwords securely hashed before storage.
* Book Management: Users can browse, borrow, and return books, while managers can add, update, and remove books from the system.
* Data Persistence: All data, including user accounts, book details, and borrowing records, is stored in a database.
* Session Management: Secure login and logout functionalities are available for users and managers.

## Installation
Clone this repository to your local machine:

    git clone https://github.com/yourusername/library-management-system.git

Navigate into the project directory:

    cd library-management-system
Set up the database:
  * Create a new database in your preferred in mySQL
  * Run the database schema file (e.g., schema.sql) to set up the necessary tables:

        mysql -u root -p library_db < schema.sql

## Usage
* Launch the application.
* On the login screen, choose to log in as either a User or Manager:
    - User Role: Browse books, borrow and return books.
    - Manager Role: Manage the library's inventory by adding, removing, or updating book details.

* If you don't have an account, you can create one by clicking on the "Sign Up" button.

    - Passwords are securely hashed and stored in the database.

* Once logged in, use the interactive graphical interface to navigate through the system's features.

 **Example Screens**  
      Login Screen: Provides options to log in or create a new account.  
      User Dashboard: Displays available books, borrowed books, and options to borrow/return.  
      Manager Dashboard: Allows adding, updating, or removing books from the system.  

## Use Cases
**Sea-Level Use Cases (Major Features)**  

  **User Login**: A user can log in using valid credentials, gaining access to the library system.  
  **Manager Login**: A manager can log in and manage the library's inventory.  
  **Book Borrowing**: Users can browse available books and borrow them if they are in stock.  
  **Book Management (Manager Only)**: Managers can add new books, update existing books, or remove books from the library.  

**Fish-Level Use Cases (Minor Features)**  

**Account Creation**: Users and managers can create accounts with secure password storage.  
**Book Return**: Users can return borrowed books and update their borrowing history. 

## License
  This is an open-source project made in academic purpose as a project in one of my computer-science faculty subjects.
