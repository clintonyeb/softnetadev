## Softneta Test Application

Assignment for Development Task as a Softneta job application requirement.

### Requirements

1. Java 8
2. MySQL
    

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

These are abstractions that your service layer will call to get/update the data it needs. This layer will generally either call a Database or some other system (eg: LDAP server, web service, or NoSql-type DB)

2. View Layer: Your MVC framework & code of choice
3. Service Layer: Your Controller will call this layer's objects to get or update Models, or other requests.

### Features

### Improvements

### Known Issues