# Account-Service
Hyperskill edu project. Creating spring-based app with auth.

# How to start
1.Clone repository
2.Run .\gradlew.bat Account_Service-task:clean bootRun
Exposed port 28852

OR

1.Install docker
2.Clone repository
3.Run Dockerfile from .\Account Service\task. Run configuration provided for Intellij Idea.
Exposed port 8081

OR

1.Install docker
2.Clone repository
3.cd ".\Account Service\task"; docker build -t account-service .
4.docker run -itd --name account-service-container -p 8081:28852 account-service:latest
Exposed port 8081
