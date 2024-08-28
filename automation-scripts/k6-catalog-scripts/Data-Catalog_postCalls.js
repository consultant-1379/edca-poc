import http from 'k6/http';
import { check,sleep } from 'k6';
import { SharedArray } from "k6/data";

const hostname='http://catalog-test.hoff135.rnd.gic.ericsson.se';

const bulkDataRepositoryEndpoint='/catalog/v1/bulk-data-repository';
const messageBusEndpoint='/catalog/v1/message-bus';
const notificationTopicEndpoint='/catalog/v1/notification-topic';
const dataSpaceEndpoint='/catalog/v1/data-space';
const dataProviderTypeEndpint='/catalog/v1/data-provider-type';
const dataCollectorEndpoint='/catalog/v1/data-collector';
const fileFormatEndpoint='/catalog/v1/file-format';

var bdrData = JSON.parse(open("./Data-Catalog-json/bulk-data-repository.json"));
var msgBusData = JSON.parse(open("./Data-Catalog-json/message-bus.json"));
var notificationTopicData = JSON.parse(open("./Data-Catalog-json/notification-topic.json"));
var dataSpaceData = JSON.parse(open("./Data-Catalog-json/data-space.json"));
var dataProviderTypeData = JSON.parse(open("./Data-Catalog-json/data-provider-type.json"));
var dataCollectorData = JSON.parse(open("./Data-Catalog-json/data-collector.json"));
var fileFormatData = JSON.parse(open("./Data-Catalog-json/file-format.json"));


export let options = {
    vus: 1,
    iterations: 1
    // duration: '30s',
    // stages: [
    //   { duration: '20s', target: 10 } // simulate ramp-up of traffic from 1 to 100 users over 5 minutes.
    // ],
    // thresholds: {
    //     checks: [{ threshold: 'rate == 1.00', abortOnFail: true }]
    // }
};

export default function testSuite() {

    let responses = http.batch([
        ['POST', `${hostname}${bulkDataRepositoryEndpoint}`,JSON.stringify(bdrData), { headers: { 'Content-Type': 'application/json' } }],
        ['POST', `${hostname}${messageBusEndpoint}`, JSON.stringify(msgBusData), { headers: { 'Content-Type': 'application/json' } }],
        // ['POST', `${hostname}${notificationTopicEndpoint}`, JSON.stringify(notificationTopicData), { headers: { 'Content-Type': 'application/json' } }],
        // ['POST', `${hostname}${dataSpaceEndpoint}`, JSON.stringify(dataSpaceData), { headers: { 'Content-Type': 'application/json' } }],
        // ['POST', `${hostname}${dataProviderTypeEndpint}`, JSON.stringify(dataProviderTypeData), { headers: { 'Content-Type': 'application/json' } }],
         ['POST', `${hostname}${dataCollectorEndpoint}`, JSON.stringify(dataCollectorData), { headers: { 'Content-Type': 'application/json' } }],
        // ['POST', `${hostname}${fileFormatEndpoint}`, JSON.stringify(fileFormatData), { headers: { 'Content-Type': 'application/json' } }]
      ]);
      for (let i = 0; i < responses.length; i++) {
        check(responses[i], {
            'Status Check': (res) => res.status === 201
        });
      }
}
