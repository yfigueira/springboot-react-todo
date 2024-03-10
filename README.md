# *Simple CRUD Todo Application*
<span>
<img src="https://seeklogo.com/images/S/spring-logo-9A2BC78AAF-seeklogo.com.png" alt="Spring logo" style="margin: 20px" width=75>
<img src="https://seeklogo.com/images/R/react-logo-7B3CE81517-seeklogo.com.png" alt="React logo" style="margin: 20px" width=80>
<img src="https://seeklogo.com/images/D/docker-logo-E3A71BA745-seeklogo.com.png" alt="Docker logo" style="margin: 20px" width=100>
<img src="https://seeklogo.com/images/P/postgresql-logo-5309879B58-seeklogo.com.png" alt="PostgreSQL logo" style="margin: 20px" width=100>
</span>

## *Description*
This project was an exercise in creating a fullstack web application, using technologies I have been learning about for a while:

- Java & Springboot on the server side, integrated with a PostgreSQL database;
- TypeScript & React on the client side

My goal was also to get some experience with various elements of the software development process:

- Database migration - using Liquibase
- Unit testing & Integration testing - using JUnit and Testcontainers
- RESTful service design concepts, including Hypermedia As The Engine Of Application State
- Integrating the application layers in a multicontainer environment - using Docker with Docker Compose

## *Running the application*
### *Requirements*
In order to run this project you will need to have Docker installed on your computer.

### *Running the app*
You can run this project on Linux or using WSL on Windows.

To run the project you will need to:

- Clone this repository: `git clone https://github.com/yfigueira/springboot-react-todo.git`
- Navigate to the project root directory: `cd springboot-react-todo`
- If you're using WSL on Windows, make sure Docker desktop is running!
- Run the project: `docker-compose up --build`
- In your browser, navigate to `http://localhost:5173`

Additionally, if you want to access the api, you'll find it at: `http://localhost:8080/api/todos`
