import http from 'k6/http';
import { check } from 'k6';
// Import browserified AWSv4 signature library
import aws4 from './aws4.js';

export let options = {
  vus: 1,
  // duration: '30s',
  // thresholds: {
  //   errors: ['rate<0.1'], // <10% errors
  //   http_req_failed: ['rate<0.01'],   // http errors should be less than 1% 
  // }
};

// Get AWS credentials from environment variables
const MINIO_CREDS = {
  accessKeyId: 'accesstest',
  secretAccessKey:'accesstest123',
};

export default function () {
  // Sign the AWS API request
  const signed = aws4.sign(
    {
      service: 's3',
      hostname:'miniotest.hoff135.rnd.gic.ericsson.se',
      region: 'us-east-1',
      expires: 60
    },
    MINIO_CREDS
  );

// Make the actual request to the AWS API including the
  // "Authorization" header with the signature
  let res = http.get('http://'+signed.hostname, {
    headers: signed.headers
  });

  check(res, {
    'status is 200': (r) => r.status === 200
  });

  console.log(res.body);
}
