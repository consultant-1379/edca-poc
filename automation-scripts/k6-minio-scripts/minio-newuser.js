import http from 'k6/http';
import { check } from 'k6';
import aws4 from './aws4.js';

const bucketName='/?Action=ListQueues';

const MINIO_CREDS = {
  accessKeyId: 'accesstest',
  secretAccessKey:'accesstest123',
};

export default function () {
  const signed = aws4.sign(
    {
      service: 's3',
      hostname:'miniotest.hoff135.rnd.gic.ericsson.se',
      region:'us-east-1',
      expires: 60,
      path:bucketName
    },
    MINIO_CREDS
  );

  let res = http.get(`http://${signed.hostname}${signed.path}`,{
    headers: signed.headers
  });
  
  check(res, {
    'status is 200': (r) => r.status === 200
  });

  console.log(res.body,'--',res.status);
}
