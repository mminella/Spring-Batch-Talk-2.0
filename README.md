Spring-Batch-Talk-2.0
=====================
This repo contains the presentation and example code from the Heavy Lifting in the Cloud with Spring Batch talk.  The example code runs a "vuln scanner" which is really nothing more than a port scanner that stores the banners it receives.  Most vulnerability scanners use that banner information to determine if the version of software that replied is vulnerable (based on regexes performed on the banner).

To execute the code, you'll need access to a CloudFoundry installation (CouldFoundry.com is the easiest), VMC installed and Maven 3.  The easiest way to run the sample is via deploying with VMC.

=====================
Running the example
=====================
1. Execute a maven build:
mvn clean install
2. Change directories into the target directory.
3. Select the CloudFoundry target you wish to deploy the application to:
$ vmc target https://api.cloudfoundry.com
4. Login to the target you selected
$ vmc login
5. Perform a push, however do not allow CloudFoundry to start the app (we need to set the profile first).
$ vmc push --no-start
6. From the prompts, enter a name, confirm that it is a Spring application, select that we want a java runtime, 512MB of memory.
7. The application requires 2 services, Mysql and RabbitMQ

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












