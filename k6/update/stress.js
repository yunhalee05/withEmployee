
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
const USERNAME = 'ceo1@example.com';
const PASSWORD = '123456';
const COMPANYID = '9';
const COMPANYNAME = 'space1'
const COMPANYDESCRIPTION = 'find space for you'
const USERID = '2'

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

    var companyRequest = JSON.stringify({
        name: COMPANYNAME,
        description: COMPANYDESCRIPTION,
        ceoId: USERID
        });

    let companyRes = http.post(`${BASE_URL}/api/companies/${COMPANYID}`,companyRequest, authHeaders);
    check(companyRes, { 'update company successfully': (resp) => resp.status === 200 });

    sleep(1)
};