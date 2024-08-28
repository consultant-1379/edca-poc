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
FROM armdocker.rnd.ericsson.se/proj-ldc/common_base_os_release/sles:3.15.0-19
RUN zypper addrepo --gpgcheck-strict -f https://arm.sero.gic.ericsson.se/artifactory/proj-ldc-repo-rpm-local/common_base_os/sles/3.15.0-19 \
COMMON_BASE_OS_SLES_REPO \
&& zypper --gpg-auto-import-keys refresh \
&& zypper install -l -y curl \
&& zypper install -l -y vim \
&& zypper install -y  java-11-openjdk \
&& zypper clean --all

ENV APP_DIR /app

WORKDIR ${APP_DIR}

RUN mkdir test-data

COPY test-data test-data/
COPY config.properties .
COPY target/custom-collector.jar custom-collector.jar

RUN chmod 777 custom-collector.jar

EXPOSE 8080

#ENTRYPOINT ["java","-jar","custom-collector.jar","--spring.config.additional-location=config.properties"]