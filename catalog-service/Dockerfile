#
# COPYRIGHT Ericsson 2020
#
#
#
# The copyright to the computer program(s) herein is the property of
#
# Ericsson Inc. The programs may be used and/or copied only with written
#
# permission from Ericsson Inc. or in accordance with the terms and
#
# conditions stipulated in the agreement/contract under which the
#
# program(s) have been supplied.
#

#FROM armdocker.rnd.ericsson.se/proj-am/sles/sles-jdk:1.3.0-31
#ARG JAR_FILE
#ARG COMMIT
#ARG BUILD_DATE
#ARG APP_VERSION
#LABEL \
#    org.opencontainers.image.title=eric-ms-chassis-jsb \
#    org.opencontainers.image.created=$BUILD_DATE \
#    org.opencontainers.image.revision=$COMMIT \
#    org.opencontainers.image.vendor=Ericsson \
#    org.opencontainers.image.version=$APP_VERSION

#ADD target/${JAR_FILE} demo-app.jar
#COPY src/main/resources/jmx/* /jmx/
#RUN chmod 600 /jmx/jmxremote.password
#CMD java ${JAVA_OPTS} -Dcom.sun.management.jmxremote=true -Dcom.sun.management.jmxremote.port=1099 -Dcom.sun.management.jmxremote.authenticate=true -Dcom.sun.management.jmxremote.ssl=false -Dcom.sun.management.jmxremote.rmi.port=1099 -Dcom.sun.management.jmxremote.password.file=/jmx/jmxremote.password -Dcom.sun.management.jmxremote.access.file=/jmx/jmxremote.access -jar demo-app.jar

FROM armdocker.rnd.ericsson.se/proj-am/sles/sles-openjdk8:latest
ARG APP_VERSION
COPY catalog-service/target/catalog-service-1.0.0-SNAPSHOT.jar /demo-app.jar
CMD java -jar /demo-app.jar
