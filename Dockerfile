# BEWARE! run "mvn clean package" before build the image 

# Base image: Tomcat 10 (Jakarta EE 9+ compatible)
FROM tomcat:10.1.16-jdk21-temurin

# Remove default aplications
RUN rm -rf /usr/local/tomcat/webapps/*

# Deploy
COPY target/api-tester.war /usr/local/tomcat/webapps/

# Port
EXPOSE 8080

# Bootstrap Tomcat
CMD ["catalina.sh", "run"]

