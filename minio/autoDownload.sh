export ACCESS_KEY=AKIAIOSFODNN7EXAMPLE
export SECRET_KEY=wJalrXUtnFEMIK7MDENGbPxRfiCYEXAMPLEKEY

java -jar minioApp.jar --a --d --host='http://eric-data-object-storage-mn.base:9000' --bucket='pm-data'  --uCount=10