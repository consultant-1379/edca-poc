# COPYRIGHT Ericsson 2020
# The copyright to the computer program(s) herein is the property of
# Ericsson Inc. The programs may be used and/or copied only with written
# permission from Ericsson Inc. or in accordance with the terms and
# conditions stipulated in the agreement/contract under which the
# program(s) have been supplied.
 
FROM armdocker.rnd.ericsson.se/proj-ldc/common_base_os_release/sles:3.15.0-19
RUN zypper addrepo --gpgcheck-strict -f https://arm.sero.gic.ericsson.se/artifactory/proj-ldc-repo-rpm-local/common_base_os/sles/3.15.0-19 \
COMMON_BASE_OS_SLES_REPO \
&& zypper addrepo https://download.opensuse.org/repositories/editors/SLE_15/editors.repo \
&& zypper --gpg-auto-import-keys refresh \
&& zypper install -l -y curl \
&& zypper install -y  java-11-openjdk \
&& zypper install -l -y vim \ 
&& zypper install -l -y lsof \ 
&& zypper clean --all
 
ENV APP_DIR /app
WORKDIR ${APP_DIR}
COPY drgSimulator.jar drgSimulatorApp.jar
COPY int.sh int.sh
COPY auto.sh auto.sh
ENTRYPOINT ["java","-jar","drgSimulatorApp.jar"]