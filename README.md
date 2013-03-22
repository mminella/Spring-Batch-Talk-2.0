Spring-Batch-Talk-2.0
=====================
Maven
=====================

mvn clean install cf:push -Dcf.appname=partition
mvn cf:apps

-- set environment variables via vmc :(

mvn cf:start -Dcf.appname=partition






=====================
VMC
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

vmc set-env part JAVA_OPTS "-Dspring.profiles.active=master -DENVIRONMENT=mysql"

vmc start part

-- Look at logs
vmc files part logs
vmc files part tomcat/logs

vmc files part logs/stdout.log >> localstdout.log

-- Scale app
vmc instances part <number-of-instances>

-- access database (after installing "gem install caldecott --no-rdoc --no-ri")
vmc tunnel



ipAddress=74.54.219.210,outputFile=logs/sr.xml












