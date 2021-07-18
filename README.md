# LogAnalyzer
A simple REST API to collect logs. A client application can call these APIs to display log 
content of specified file, or top content of the specified file or just search for content.
- API to get the content of log file - http://localhost:8080/v1/loganalyzer/logs?fileName=$fileName
- API to get the top content of log file - http://localhost:8080/v1/loganalyzer/logs/top?fileName=$fileName&top=$top
- API to search text/keyword from log files - http://localhost:8080/v1/loganalyzer/logs/search?keyword=$keyword  

### **Table of Content**
- [Getting Started](#Getting-Started)
- [Testing](#Testing)
- [How it works?](#How-it-works)  
- [Deployment](#Deployment)
- [License](#License)
- [Acknowledgements](#Acknowledgements)

# Getting Started
There are two ways you can run this project. 
1. Download this project and build the jar file on your environment
2. Use the given docker-compose file and build your docker image and deploy it immediately.

### Prerequisites

#### For Development
- Java 8
- Gradle
- IDE

#### For Deployment
- Docker and Docker-Compose utilities

# Testing
- You can run the application through docker, it can be accessed at `http://localhost:8080/home`

- Another way to run the application, is to clone this repository and build on your local 
  environment
  - ./gradlew clean build
  - cd ./build/libs
  - java -jar loganalyzer-0.0.1-SNAPSHOT.jar
  - Once the application started, you can access http://localhost:8080/home
  - Submit the search query. If searching for a file, enter the file name and select the checkbox 
    for file. If searching for text/keyword, enter your text in the search box.

- The application is protected by basic authorization. Username - `adminuser` and Password - 
  `adminpassword`. You will need the same username and password if you want to access the API 
  directly.
  
# How it works?
- Basically once the user submits search query, a recursive search run through 
  ROOT_DIR=`/var/log` & subdirectories to search for the file (if user submitted filename) OR 
  search for text content from file in root directory or sub-directory.

# Deployment
You can deploy this application using docker-compose easily.

The application will be accessible at http://localhost:8080/home

Run from the root directory -
- docker-compose build
- docker-compose up -d

# License
This project is licensed under the MIT-License - see the [LICENSE](./LICENSE) file for details
    


