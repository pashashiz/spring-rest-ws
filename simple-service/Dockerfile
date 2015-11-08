FROM tomcat:8-jre8

# Copy artifacts
COPY target/simple-service-1.0-SNAPSHOT.war $CATALINA_HOME/webapps/simple-service.war

# Expose port to use
EXPOSE 8080

# Default command to start
CMD ["catalina.sh", "run"]

