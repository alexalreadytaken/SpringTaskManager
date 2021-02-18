# Task manager
## service to manage assignments for students. 
## Start Up
To bootstrap the server, you must have the [Postgresql](https://www.postgresql.org/download/) server installed ,
 and you also need to go to the file `src\main\resources\application.properties` in java project and change :
- `server.address and server.port`(ip and port which you want)
- `spring.datasource,.username,.password`(link to your sql schema,login and password mysql user)
- `task.pool.path`(path to directory for storage tasks files)
