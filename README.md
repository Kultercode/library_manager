# Library Manager
Library Manager Application

To use the application with a gui I used Swagger UI.

To run the application, if you are in the same folder as the file, you should type this into the command line:  
java -jar library_manager-1.2.jar

After the app successfully started, you should type this into a web browser and hit enter: http://localhost:8080/swagger-ui/index.html  
There you can test the functions of the application.

By clicking on the different API calls you can see example requests and can also try them out with custom inputs. After a request it shows various information about the response it got from the application.

If you want to access the database behind the app, insert this into the search bar: http://localhost:8080/h2-console  
There change the JDBC URL to: jdbc:h2:mem:testdb  
Username: sa  
Password: password

After a successful login, you can do various things with the database, like making an sql query to take a look at every row in the tables.

Kristóf Juhász
