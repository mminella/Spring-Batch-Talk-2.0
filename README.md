Spring-Batch-Talk-2.0
=====================

-- Login
vmc target https://api.cloudfoundry.com
vmc target
vmc login
vmc info

-- Configure services
vmc info --services
vmc create-service mysql mysql-js
vmc create-service rabbitmq rabbitmq-js

-- Deploy first app
vmc push --no-start

vmc bind-service mysql-js partition
vmc bind-service rabbitmq-js partition

vmc set-env partition JAVA_OPTS "-Dspring.profiles.active=master -DENVIRONMENT=mysql"

vmc start partition

-- Look at logs
vmc files partition logs
vmc files partition tomcat/logs

vmc files partition logs/stdout.log >> localstdout.log

-- Scale app
vmc instances partition <number-of-instances>

ipAdress=<MY_ADDRESS>,outputFile=logs/something.xml
