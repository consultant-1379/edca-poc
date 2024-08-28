import http from 'k6/http';
import { check } from 'k6';

const hostname='http://catalog-test.hoff135.rnd.gic.ericsson.se';

const bulkDataRepositoryEndpoint='/catalog/v1/bulk-data-repository';
const messageBusEndpoint='/catalog/v1/message-bus';
const notificationTopicEndpoint='/catalog/v1/notification-topic';
const dataSpaceEndpoint='/catalog/v1/data-space';
const dataProviderTypeEndpint='/catalog/v1/data-provider-type';
const fileFormatEndpoint='/catalog/v1/file-format';

export let options = {
    vus: 1,
    iterations: 1,
    // duration: '30s',
    // stages: [
    //   { duration: '20s', target: 10 } // simulate ramp-up of traffic from 1 to 100 users over 5 minutes.
    // ],
    thresholds: {
        checks: [{ threshold: 'rate == 1.00', abortOnFail: true }]
    }
};

export default function testSuite() {

    let responses = http.batch([
        ['GET', `${hostname}${bulkDataRepositoryEndpoint}`, null, { tags: { endpoint: 'bulkDataRepository' } }],
        ['GET', `${hostname}${messageBusEndpoint}`, null, { tags: { endpoint: 'messageBus' } }],
        ['GET', `${hostname}${notificationTopicEndpoint}`, null, { tags: { endpoint: 'notificationTopic' } }],
        ['GET', `${hostname}${dataSpaceEndpoint}`, null, { tags: { endpoint: 'dataSpace' } }],
        ['GET', `${hostname}${dataProviderTypeEndpint}`, null, { tags: { endpoint: 'dataProviderType' } }],
        ['GET', `${hostname}${fileFormatEndpoint}`, null, { tags: { endpoint: 'fileFormat' } }]
      ]);
      for (let i = 0; i < responses.length; i++) {
        check(responses[i], {
            'Status Check': (res) => res.status === 200
        });
      }
}
