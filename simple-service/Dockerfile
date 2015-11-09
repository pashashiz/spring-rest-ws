FROM tomcat:8-jre8

ENV JAVA_HOME /usr/lib/jvm/java-8-openjdk-amd64

# Install xml starlet
RUN apt-get update &&  apt-get install -y xmlstarlet

# Add scripts
ADD configure-ssl.sh bin/configure-ssl.sh
RUN sed -i 's/\r$//' bin/configure-ssl.sh
# Add artifacts
COPY target/simple-service-1.0-SNAPSHOT.war webapps/simple-service.war

# Expose port to use
EXPOSE 8080
EXPOSE 8443

# Default command to start
CMD configure-ssl.sh && catalina.sh run