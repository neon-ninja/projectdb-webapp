# Projects Database Test Suite

By Nick Young

This test suite is built using behat, and relies on the mink extension. It uses the Goutte headless driver
for non-javascript scenarios (identified with the nojs tag), and the selenium2 extension/webdriver for javascript
scenarios (identified by the javascript tag).
It is self-cleaning - it removes any changes it makes to the database at the end of the script.

## Dependencies:

* php 5.3.2+
* php5-curl
* java
* firefox

## Installation

1. Use composer to install behat and the required dependencies (from composer.json), with the command:
    php composer.phar install
    This will download ~90MB, so will take a few minutes
2. Download the latest version of the selenium server standalone jar from
    https://code.google.com/p/selenium/downloads/list
    It is recommended to place it in tests/bin
    
## Configuration

Edit behat.yml to change the url to run the tests against if required.

## Writing tests

Edit an existing feature in the features folder, or create a new one if it is semantically different
To see what steps are defined, run the command
  bin\behat -dl
  or
  bin\behat -di
  to also see where those steps are defined
  
Custom steps can be placed in features/bootstrap/FeatureContext.php

## Usage

Prerequisites:
  Run generate_admin_and_adviser.sql on your local database to generate adviser/admin accounts. Uncomment Line 39 of AuditFilter to specify Behat Adviser as the default adviser
  for javascript tests. Set remoteUserHeader=RemoteUser in configuration.properties.

From the tests directory,

Start selenium server running with the command
  java -jar bin/selenium-server-standalone-2.37.0.jar

To run the entire test suite, use the command:
  bin/behat
  
To run a portion of it, specify the tags you wish to run with
  bin/behat --tags=tag1,tag2
  
Use the ~ character as the NOT symbol
For example, to run everthing except the cleanup steps, run 
  bin/behat --tags=~cleanup
  
Then, to run just the cleanup steps, use
  bin/behat --tags=cleanup
  



