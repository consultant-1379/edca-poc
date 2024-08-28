# Custom Collector Simulator

Custom Collector Simulator (CCS) is a simulator program used to imitate the behavior of Custom
Collector for the EDCA deployment. CCS has following three use-cases which can be run in either automation 
or interactive manner based on the argument provided to CCS at the time of execution.
- Upload Metadata to Catalog Service.
- Upload Data-file to BDR.
- Send Notification to DMaaP-Kafka.

## Usage
CCS is packaged as a java-executable (jar) and can be execute either directly or with
the help of provided shell script.

#### Mandatory Arguments
```shell script
--spring.config.additional-location=<property-file>
# custom collector configuration file. This file acts as the input for CCS.

--edca.nameSpace=<edca-nameSpace>
# EDCA chart deployed nameSpace which custom collector uses to perform operations.

--[automation|interactive]
# to run the CCS in either automation or interactive mode.
```

#### Configuration
CCS has dependecy on other EDCA component and can not run without them. 
Here are the list of dependencies that should be up and running 
in a CCD cluster with exposed endpoints in order to run CCS properly.
- **Catalog Service** - to manage metadata related use-cases through CCS.
- **Bulk Data Repository (BDR)** - to upload data files to object-storage-mn.
- **Kafka Service** - to send notifications to the other EDCA clients, DRG and ENM for example.

**Example Configuration**
```properties
# variable declared for using EDCA Chart nameSpace
edca.nameSpace=<nameSpace to be used where EDCA chart is deployed>

# Kafka Configuration details
edca.kafka.topic=<kafka-topic>
edca.kafka.bootstrap.urls=<comma-separated-list-of-bootstrap-urls>

# BDR Configuration details
edca.bdr.host=<edca-bdr-host>
edca.bdr.port=<edca-bdr-port>
edca.bdr.bucket=<bucket-name>
edca.bdr.accesskey=<edca-bdr-accesskey>
edca.bdr.secretkey=<edca-bdr-secretkey>

# Catalog Service Configuration details
edca.catalog.host=<edca-catalog-service-host>
edca.catalog.port=<edca-catalog-service-port>

# Test Data
edca.test.metadataFile=<path-to-metadata.json>
edca.test.dataFile=<path-to-data.json>
edca.test.counter=10
```
**TEST DATA** section must include appropriate data and metadata file in order to run the CCS is automation mode.


### Example Test Data
**Metadata**
```json
{
  "dataType": "CmTrafficMeas",
  "dataNotifications": "cmtopic",
  "dataSubType": [
    "PM_GPEH",
    "PM_STATISTICAL_RAW",
    "PM_BSC_CTR_RAW"
  ],
  "sourceType": [
    "eNB",
    "gNB"
  ],
  "encoding": "CSV",
  "specRef": "",
  "address": ""
}
```

# Execution of Custom Collector

#### using java 
```shell script
$ mvn clean package
$ java -jar target\custom-collector.jar --spring.config.additional-location=config.properties --edca.nameSpace=<ns> --automation
```

#### using shell
```shell script
$ mvn clean package
$ ./collector.sh -c config.properties -a

Usage: ./collector.sh  -c <file> [-a|i] [-d <test-data-dir>]
Available Options:
-c          custom collector properties
-a          automation mode
-i          interactive mode
-h          show usage guide

Example Commands:
1. "./collector.sh -c config.properties -a" #automation
2. "./collector.sh -c config.properties -i" #interactive
```

