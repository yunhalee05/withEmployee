
// stress.js
import http from 'k6/http';
import { check, group, sleep, fail } from 'k6';

export let options = {
  stages: [
    { duration: '30s', target: 100 }, // below normal load
    { duration: '1m', target: 100 },
    { duration: '30s', target: 200 }, // normal load
    { duration: '1m', target: 200 },
    { duration: '30s', target: 300 }, // around the breaking point
    { duration: '1m', target: 300 },
    { duration: '30s', target: 400 }, // beyond the breaking point
    { duration: '1m', target: 400 },
    { duration: '2m', target: 0 }, // scale down. Recovery stage.
  ],
  thresholds: {
    http_req_duration: ['p(99)<200'], // 99% of requests must complete below 1.5s
  },
};


const BASE_URL = 'https://withemployee.n-e.kr'
const USERNAME = 'user1@example.com';
const PASSWORD = '123456';

export default function ()  {

    var payload = JSON.stringify({
        username: USERNAME,
        password: PASSWORD,
        });

    var params = {
        headers: {
            'Content-Type': 'application/json',
        },
    };

    let loginRes = http.post(`${BASE_URL}/api/login`, payload, params);
    check(loginRes, {'logged in successfully': (resp) => resp.json('token') !== '',});

    let authHeaders = {
        headers: {
            Authorization: `Bearer ${loginRes.json('token')}`,
            'Content-Type': 'application/json',
        },  
    };

    const keyword = "c"
    let companyRes = http.get(`${BASE_URL}/api/companies?keyword=${keyword}`, authHeaders);
    check(companyRes, { 'find companies by keyword successfully': (resp) => resp.status === 200 });

    let conversationRes = http.get(`${BASE_URL}/api/conversations?userId=${loginRes.json('user').id}`, authHeaders);
    check(conversationRes, { 'find conversations by userId successfully': (resp) => resp.status === 200 });

    sleep(1)
};
