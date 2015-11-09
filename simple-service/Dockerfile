FROM tomcat:8-jre8

ENV JAVA_HOME /usr/lib/jvm/java-8-openjdk-amd64
ENV DOMAIN "docker-host"

# Configure SSL
# Generate self-signed certificate
RUN echo $JAVA_HOME
RUN keytool -genkey -noprompt -alias $DOMAIN -keyalg RSA -keysize 2048 \
    -keystore $HOME/.keystore -storepass "changeit" -keypass "changeit" \
    -dname "CN=${DOMAIN}, OU=dev, O=asg, L=city, S=state, C=country"
RUN keytool -export -alias $DOMAIN -file $HOME/cert.cer \
    -keystore $HOME/.keystore -storepass "changeit"
RUN keytool -import -noprompt -file $HOME/cert.cer -alias $DOMAIN \
    -keystore $JAVA_HOME/jre/lib/security/cacerts -storepass "changeit"
# Enable tomcat SSL
RUN apt-get update && \
    apt-get install -y xmlstarlet
#RUN xmlstarlet ed --inplace -d "/Server/Service/Connector" $CATALINA_HOME/conf/server.xml
RUN xmlstarlet ed --inplace -s "/Server/Service" -t elem -n "Connector" -v "ssl" \
    -i "/Server/Service/Connector[text()='ssl']" -t attr -n "port" -v "8443" \
    -i "/Server/Service/Connector[text()='ssl']" -t attr -n "protocol" -v "org.apache.coyote.http11.Http11Protocol" \
    -i "/Server/Service/Connector[text()='ssl']" -t attr -n "maxThreads" -v "150" \
    -i "/Server/Service/Connector[text()='ssl']" -t attr -n "SSLEnabled" -v "true" \
    -i "/Server/Service/Connector[text()='ssl']" -t attr -n "scheme" -v "https" \
    -i "/Server/Service/Connector[text()='ssl']" -t attr -n "sslProtocol" -v "TLS" \
    -i "/Server/Service/Connector[text()='ssl']" -t attr -n "keystoreFile" -v "${HOME}/.keystore" \
    -i "/Server/Service/Connector[text()='ssl']" -t attr -n "keystoreType" -v "JKS" \
    -i "/Server/Service/Connector[text()='ssl']" -t attr -n "keyAlias" -v $DOMAIN \
    -i "/Server/Service/Connector[text()='ssl']" -t attr -n "keystorePass" -v "changeit" \
    $CATALINA_HOME/conf/server.xml
RUN cat $CATALINA_HOME/conf/server.xml

# Copy artifacts
COPY target/simple-service-1.0-SNAPSHOT.war $CATALINA_HOME/webapps/simple-service.war

# Expose port to use
EXPOSE 8080
           EXPOSE 8443

# Default command to start
CMD ["catalina.sh", "run"]

