Spring-Batch-Talk-2.0
=====================

This repo contains the presentation and example code from the Heavy Lifting in the Cloud with Spring Batch talk.  The example code runs a "vuln scanner" which is really nothing more than a port scanner that stores the banners it receives.  Most vulnerability scanners use that banner information to determine if the version of software that replied is vulnerable (based on regexes performed on the banner).

To execute the code, you'll need access to a Cloud Foundry installation ([Pivotal Hosted CF](http://docs.cloudfoundry.com/docs/dotcom/getting-started.html) is the easiest), the [cf command-line tool](http://docs.cloudfoundry.com/docs/using/managing-apps/cf/index.html), and Maven 3.

Running the example
===================

1. Execute a maven build:
~~~
mvn clean install
~~~
2. Select the CloudFoundry target you wish to deploy the application to:

    `$ cf target https://api.run.pivotal.io` 
3. Login to the target you selected

    `$ cf login`
4. Deploy the applications. The `cf` CLI will read the included `manifest.yml` and start both a `master` and `slave` application. 

    `$ cf push`
14. Now you'll be able to open a browser and navigate to either the `master` or `slave` application. The URL assigned to the each application is displayed in the output from `cf push`.

