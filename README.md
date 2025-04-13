To launch project use that commands in project folder:

docker-compose up -d

mvn exec:java -D"exec.mainClass"="com.example.imagedl.RestServiceApplication"

Use Ctrl+C to stop application.

Use "docker-compose down" after you don't need container with Postgres and others.
