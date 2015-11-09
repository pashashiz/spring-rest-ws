#!/bin/bash
# Arg $1 - certificate domain name, "docker-host" by default

# Init variables
DOMAIN=${DOMAIN:-"docker-host"}

if [ -z ${JAVA_HOME} ]
then
  echo -e "\$JAVA_HOME env variable should be specified"
  exit 1
fi

if [ -z ${CATALINA_HOME} ]
then
  echo -e "\$CATALINA_HOME env variable should be specified"
  exit 1
fi

# Configure SSL
echo "Generate certificate for [${DOMAIN}] and apply for java [${JAVA_HOME}] and tomcat [${CATALINA_HOME}]"
# Generate self-signed certificate
keytool -genkey -noprompt -alias $DOMAIN -keyalg RSA -keysize 2048 \
    -keystore ${HOME}/.keystore -storepass "changeit" -keypass "changeit" \
    -dname "CN=${DOMAIN}, OU=dev, O=asg, L=city, S=state, C=country"
keytool -export -alias ${DOMAIN} -file $HOME/cert.cer \
    -keystore $HOME/.keystore -storepass "changeit"
keytool -import -noprompt -file $HOME/cert.cer -alias ${DOMAIN} \
    -keystore ${JAVA_HOME}/jre/lib/security/cacerts -storepass "changeit"
#RUN xmlstarlet ed --inplace -d "/Server/Service/Connector" $CATALINA_HOME/conf/server.xml
xmlstarlet ed --inplace -s "/Server/Service" -t elem -n "Connector" -v "ssl" \
    -i "/Server/Service/Connector[text()='ssl']" -t attr -n "port" -v "8443" \
    -i "/Server/Service/Connector[text()='ssl']" -t attr -n "protocol" -v "org.apache.coyote.http11.Http11Protocol" \
    -i "/Server/Service/Connector[text()='ssl']" -t attr -n "maxThreads" -v "150" \
    -i "/Server/Service/Connector[text()='ssl']" -t attr -n "SSLEnabled" -v "true" \
    -i "/Server/Service/Connector[text()='ssl']" -t attr -n "scheme" -v "https" \
    -i "/Server/Service/Connector[text()='ssl']" -t attr -n "sslProtocol" -v "TLS" \
    -i "/Server/Service/Connector[text()='ssl']" -t attr -n "keystoreFile" -v "${HOME}/.keystore" \
    -i "/Server/Service/Connector[text()='ssl']" -t attr -n "keystoreType" -v "JKS" \
    -i "/Server/Service/Connector[text()='ssl']" -t attr -n "keyAlias" -v ${DOMAIN} \
    -i "/Server/Service/Connector[text()='ssl']" -t attr -n "keystorePass" -v "changeit" \
    ${CATALINA_HOME}/conf/server.xml
