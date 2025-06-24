# Use the official Tomcat image with Java 17
FROM tomcat:10-jdk17

# Set the working directory inside the container
WORKDIR /usr/local/tomcat/webapps

# Copy the WAR file into the Tomcat webapps directory
COPY target/YourApp.war /usr/local/tomcat/webapps/

# Expose Tomcat's default port
EXPOSE 8080

# Start Tomcat
CMD ["catalina.sh", "run"]