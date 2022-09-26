FROM amazoncorretto:17.0.4

RUN mkdir -p /usr/local/account-service

WORKDIR /usr/local/account-service

RUN yum update -y && yum upgrade -y

COPY ./build/libs/Account_Service-task.jar /usr/local/account-service/Account_Service-task.jar

ENTRYPOINT ["java", "-jar", "./Account_Service-task.jar"]