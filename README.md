Spring-Batch-Talk-2.0
=====================
*Note*: This example does not work on run.pivotal.io with the free tier for a database (it needs more connections than 4).

This repo contains the presentation and example code from the Heavy Lifting in the Cloud with Spring Batch talk.  You can view a video of the presentation on YouTube here: [Heavy Lifting in the Cloud with Spring Batch](http://www.youtube.com/watch?v=CYTj5YT7CZU). The example code runs a "vuln scanner" which is really nothing more than a port scanner that stores the banners it receives.  Most vulnerability scanners use that banner information to determine if the version of software that replied is vulnerable (based on regexes performed on the banner).

To execute the code, you'll need access to a CloudFoundry installation (CouldFoundry.com is the easiest), VMC installed and Maven 3.  The easiest way to run the sample is via deploying with VMC.

=====================
Running the example
=====================
1. Execute a maven build:
mvn clean install
2. Change directories into the target directory.
3. Select the CloudFoundry target you wish to deploy the application to:

    `$ cf target https://api.run.pivotal.io` 
4. Login to the target you selected

    `$ cf login`
5. Perform a push, however do not allow CloudFoundry to start the app (we need to set the profile first).

    `$ cf push p_jobs --path target/partitioningJobs.war --no-start`
6. From the prompts, enter a name, confirm that it is a Spring application, select that we want a java runtime, 512MB of memory.
7. The application requires 2 services, a database and messaging
8. When the wizard promps you to create a service for this application, say yes.
9. Select cleardb from the options and provide a unique name for the service.
10. Repeate steps 8 and 9 for the messaging service (cloudamqp).
11. Choose not to save your configuration.
12. Once the push is complete, you need to indicate that this application will be the master in the master/slave configuration.  To do that, set the SPRING_PROFILES_ACTIVE and ENVIRONMENT  environment variables with the following command where <APP_NAME> is the applicaiton name you provided in step 6:

    `$ cf set-env <APP_NAME> SPRING_PROFILES_ACTIVE master
    $ cf set-env <APP_NAME> ENVIRONMENT mysql`
     
13. With the application configured, we can start it via:

    `$ cf start <APP_NAME>`
14. Now you'll be able to open a browser and navigate to <APP_NAME>.cfapps.io.
15. To launch the slave application, repeat steps 5 through 13 with a different application name and activating the slave profile in step 11. 
16. To scale the slave application, use the instances command where <SLAVE_APP_NAME> is the name of the slave application deployed in step 15 above:

    `$ cf scale <SLAVE_APP_NAME> --instances <INSTANCES>`
17.  When scaling with CloudFoundry via the scale command, the number of instances is the *total* number instances to be running, not the number to increase/decrease by.

<!--
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

-->