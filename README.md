#Test Assignment 

I will able to deliver this project by 23/10/17.

Migration from Spring JDBC to Spring + JPA (Hibernate)

## Configuration
The application has been setup by providing regular database connection parameters.  
The setting are provided in config.properties (overrideable in the environment variables) and jdbcTemp.properties and they include -
* database.driver.classname=com.mysql.cj.jdbc.Driver
* database.url=jdbc:mysql://localhost:3306/fintech_ws?serverTimezone=UTC
* database.username=database_username
* database.password=database_password
* hibernate.hbm2ddl.auto=update
* hibernate.dialect=org.hibernate.dialect.MySQL5Dialect
* hibernate.show_sql=false

## Step need to follow :-

* Change the database password in all the config and jdbcTemp properties file
* Run "fintech.sql" script resides in sql folder.
