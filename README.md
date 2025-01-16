# Finance App

## Overview
Finance App is a financial management system designed to help users track their investments, manage notifications, and handle various financial operations efficiently.

## Features
- Portfolio evaluation with pie chart visualization
- Investment tracking and management
- User notifications and alerts
- Support center with ticket management
- Secure credential storage
- Unit and integration tests using JUnit
  
Project Structure
📂 src - Main source code directory
    📂 main/java/main - Core application logic
    📂 test/java/test - Unit and integration tests

📂 Data Files - CSV files for storing financial data
    📄 credentials.csv - Secure user credentials storage
    📄 investment.csv - Investment-related data
    📄 notes.csv - User notes and transactions
    📄 notifications.csv - Notification and alert logs
    📄 prices.csv - Asset price data

📂 Modules
    📂 notifications - Notification management
    📂 portfolio - Portfolio evaluation and visualization
    📂 posts - User-generated content and financial insights
    📂 profilemanagement - User profile and account settings
    📂 supportcenter - Customer support ticket management

📂 Testing
    📂 blackbox - Black-box testing cases
    📂 whitebox - White-box testing cases


## Dependencies
- Java (JDK 11+)
- Maven (for dependency management)
- JUnit (for testing)

## Installation

### Running from Source Code
1. Clone the repository:
   ```sh
   git clone https://github.com/your-repo/finance-app.git
   ```
2. Navigate to the project directory:
   ```sh
   cd finance-app
   ```
3. Build the project using Maven:
   ```sh
   mvn clean install
   ```
4. Run the application:
   ```sh
   java -jar target/finance-app.jar
   ```

### Running from a ZIP File
If you have downloaded the project as a ZIP file, follow these steps:

1. **Unzip the file**:  
   - On Windows: Right-click on the ZIP file and select **Extract All...**  
   - On macOS/Linux: Run the following command:
     ```sh
     unzip finance-app.zip
     ```

2. **Navigate to the project folder**:
   ```sh
   cd finance-app
   ```

3. **Build the project** (ensure Maven is installed):
   ```sh
   mvn clean install
   ```

4. **Run the application**:
   ```sh
   java -jar target/finance-app.jar
   ```

### Running the Project in Eclipse IDE
1. **Open Eclipse IDE**.
2. **Import the project**:
   - Click **File** → **Import**.
   - Select **Existing Maven Projects** and click **Next**.
   - Browse to the extracted project folder and select it.
   - Click **Finish**.
3. **Ensure JDK and Maven are configured** in the project settings.
4. **Run the application**:
   - Navigate to the `Main.java` file in the `src/main/java/main` directory.
   - Right-click the file and select **Run As → Java Application**.

### Running the Project via Terminal
1. **Navigate to the project directory**:
   ```sh
   cd finance-app
   ```
2. **Compile the project manually (if needed)**:
   ```sh
   javac -d bin src/main/java/main/*.java
   ```
3. **Run the project manually (if needed)**:
   ```sh
   java -cp bin main.Main
   ```

### Running Tests Using JUnit
#### Running Tests in Eclipse IDE:
1. Open Eclipse and ensure the project is imported.
2. Navigate to the **test/java/test** directory.
3. Locate the test files (e.g., `TestMain.java`).
4. Right-click the test file and select **Run As → JUnit Test**.

#### Running Tests via Terminal:
1. Navigate to the project root directory:
   ```sh
   cd finance-app
   ```
2. Run the test cases using Maven:
   ```sh
   mvn test
   ```
