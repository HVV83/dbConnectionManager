# DbConnectionManager application
Demo master slave database connection manager project
## Installation
In the project directory execute the following commands to run all the necessary tools, build project and run it
```bash
docker-compose up -d
mvn clean package
java -jar target/dbConnectionManager-1.0-SNAPSHOT.jar
```
### All the log messages with full-text search (Kibana tool) you can find on http://localhost:5601 by "log_data" index
