## Softneta Test Application

Assignment for Development Task as a Softneta job application requirement.

### Requirements

1. Java 8
2. MySQL

The application was tested using Linux - Debian 9.1, MYSQL version 15.1 and OpendJDK version 1.8.0_171.
It is nevertheless expected to work on major platforms.

### Installation

#### Configuring MYSQL

You need to configure a MYSQL instance to be able to run the application. The application uses an in-mmenory database(h2) for testing, but requires a real database to run.

After you have installed a MYSQL instance:
Login and create a username and password for the application to use.

```sql
    GRANT ALL PRIVILEGES ON *.* TO 'softneta'@'localhost' IDENTIFIED BY 'softnetapassword';
```

This create a `username` and a password as the application is already configured with (src/main/resources/application.properties).
If you wish to change the provided username and password, then you will have to change the configuration as well.

#### Running the Application

1. Easy Run

After MYSQL is setup, you can run the application from the included jar file.

Note that, the jar file will not work if you changed the MYSQL default username and password.

>java -jar target/softnetadev-0.0.1.jar  

2. Manual Run

For a more involved running, setup your MYSQL instance and provide the username and password in the application.properties configuration file.
You can then run your application using the main class (src/main/java/com/clintonyeb/SoftnetaDev/Application.java)


### Architecture

This project uses the MVCS architecture.

1. Models (Data Access Objects) - (src/main/java/com/clintonyeb/SoftnetaDev/models)
2. View Layer (JSP)- (src/main/webapp/WEB-INF/views)
3. Controller (Coordinator for Views and Models) - (src/main/java/com/clintonyeb/SoftnetaDev/controllers) 
3. Service Layer (Services controllers) - (src/main/java/com/clintonyeb/SoftnetaDev/services) 

Tests:
The application is fully tested. (src/test)

### Features

1. Beautify interface - Cards
2. Generating RSS Feed from given URL
3. Front-End and Back-End User Data Validation
4. Deleting feeds
5. Adding new feeds
6. Background feed refresh
7. Good error handling
8. Async services for speed

### Improvements

1. User management - Login and Registration
2. Web Sockets - for instant notifications on RSS Feeds
3. Adding more tests
4. Improving UI

### Known Issues
