export ACCESS_KEY=AKIAIOSFODNN7EXAMPLE
export SECRET_KEY=wJalrXUtnFEMIK7MDENGbPxRfiCYEXAMPLEKEY

java -jar minioApp.jar --a --u --host='http://eric-data-object-storage-mn.base:9000' --bucket='pm-data' --path='demoUpload.txt' --uCount=10