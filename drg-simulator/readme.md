*******************
How to Use Drg
*******************

Step 1 :
	DRG Simulator is a Spring Boot Console Application which deploy as helm chart

Step 2:
	Login into pod

Step 2.1:
	Note :- configuration can be change from values.yaml file or --set key=value or inside the pod
	(One Time Process) To Run DRG Application Configuration need to be change after deployment
		
	export CATALOG_URL=<Working_Catalog_Get_Url>
	
	export MINIO_SECRETKEY=<secret_key>
	export MINIO_ACCESSKEY=<access_key>

	
	export BDR_ENDPOINT="/catalog/v1/bulk-data-repository"
	export DATAPROVIDER_ENDPOINT="/catalog/v1/data-provider-type"
	export DATASPACE_ENDPOINT="/catalog/v1/dataspace"
	export FILEFORMAT_ENDPOINT="/catalog/v1/file-format"
	export MESSAGEBUS_ENDPOINT="/catalog/v1/message-bus"
	export NOTIFICATIONTOPIC_ENDPOINT="/catalog/v1/notification-topic"

	export SFTP_USER=drg
	export SFTP_PASS=drg
	
	if we want to use the service from another namespace then we can use like:
	<service_name>.<namespace>
	for e.g.
		eric-data-object-storage-mn.edca-sprint2

step 2.2
	****************
	 How to Run
	****************
	Modes Supported 
		1. Automation Mode (default)
		2. Interactive Mode
		
		1. Automation Mode
		 	=> Run command (bash auto.sh)
		 		java -Dspring.application.json='{"server.port":8080}' -jar drgSimulatorApp.jar 
		 		"OR"
		 		java -Dspring.application.json='{"server.port":8080}' -jar drgSimulatorApp.jar --interactive=false
		 		"OR"
		 		java -Dspring.application.json='{"server.port":8080}' -jar drgSimulatorApp.jar --a
		 		
		2. Interactive Mode 
			=> Run command (bash int.sh)
		 		java -Dspring.application.json='{"server.port":8080}' -jar drgSimulatorApp.jar --interactive=true
				"OR"
		 		java -Dspring.application.json='{"server.port":8080}' -jar drgSimulatorApp.jar --i
		 		"OR"
		 		java -Dspring.application.json='{"server.port":8080}' -jar drgSimulatorApp.jar --i --a

Files Are downloaded in sftp_downloads or minio_downloads as endpointInterface
