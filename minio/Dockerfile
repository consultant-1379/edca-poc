# COPYRIGHT Ericsson 2021
# The copyright to the computer program(s) herein is the property of
# Ericsson Inc. The programs may be used and/or copied only with written
# permission from Ericsson Inc. or in accordance with the terms and
# conditions stipulated in the agreement/contract under which the
# program(s) have been supplied.
 
FROM armdocker.rnd.ericsson.se/proj-ldc/common_base_os_release/sles:3.31.0-9
RUN zypper addrepo --gpgcheck-strict -f https://arm.sero.gic.ericsson.se/artifactory/proj-ldc-repo-rpm-local/common_base_os/sles/3.31.0-9 \
COMMON_BASE_OS_SLES_REPO \
&& zypper addrepo https://download.opensuse.org/repositories/editors/SLE_15/editors.repo \
&& zypper --gpg-auto-import-keys refresh \
&& zypper install -l -y curl \
&& zypper install -y  java-11-openjdk \
&& zypper install -l -y vim \ 
&& zypper install -l -y lsof \ 
&& zypper clean --all
 
ENV APP_DIR /app
ENV ACCESS_KEY AKIAIOSFODNN7EXAMPLE
ENV SECRET_KEY wJalrXUtnFEMIK7MDENGbPxRfiCYEXAMPLEKEY
WORKDIR ${APP_DIR}
COPY minio-0.0.1-SNAPSHOT.jar minioApp.jar
COPY int.sh int.sh
COPY intWithFile.sh intWithFile.sh
COPY autoUpload.sh autoUpload.sh
COPY autoDownload.sh autoDownload.sh
COPY demoUpload.txt demoUpload.txt
RUN chmod +x int.sh
RUN chmod +x intWithFile.sh
RUN chmod +x autoUpload.sh
RUN chmod +x autoDownload.sh
RUN sed -i 's/\r$//' autoDownload.sh
RUN sed -i 's/\r$//' autoUpload.sh
RUN sed -i 's/\r$//' int.sh
RUN sed -i 's/\r$//' intWithFile.sh
RUN mkdir test
COPY test test/
COPY input.yaml input.yaml
ENTRYPOINT ["java","-jar","minioApp.jar"]