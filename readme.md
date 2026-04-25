# Ledger System
A simple Java application for managing financial transactions, built with a layered architecture and utilizing HikariCP for efficient database connection pooling. The application includes user authentication and registration features, with database connection details securely loaded from a .env file using the Dotenv library. 
## Features
- User authentication (login and registration)
- Database connection pooling with HikariCP
- Secure configuration management with Dotenv
## Technologies Used
- Java
- HikariCP
- Dotenv
- JDBC
## Setup Instructions
1. Clone the repository:
   ```bash
   git clone https://github.com/raiyan-takrim/Ledger-System.git
   ```
2. Navigate to the project directory:
   ```bash
    cd Ledger-System
    ```
3. Create a `.env` file in the root of the project with the following content:
    ```
    DB_URL=jdbc:mysql://localhost:3306/ledger_db
    DB_USER=your_username
    DB_PASSWORD=your_password
    ```
4. Create the database and necessary tables using the provided SQL scripts in the `resources/db/migrations` directory.
5. Build and run the application using your preferred Java IDE or command line tools.
## Future Enhancements
- Implement the login functionality in the `handleLogin` method.
- Add more features such as transaction management, reporting, etc.
- Implement proper error handling and logging throughout the application.
## Contributing
Contributions are welcome! Please fork the repository and create a pull request with your changes. Make sure to follow the existing code style and include tests for any new features or bug fixes.
## License
This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.