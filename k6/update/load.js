// load.js
import http from 'k6/http';
import { check, group, sleep, fail } from 'k6';

export let options = {
  stages: [
    { duration: '1m', target: 79 }, // simulate ramp-up of traffic from 1 to 15-16 users over 5 minutes.
    { duration: '2m', target: 79 }, // stay at 15-16 users for 10 minutes
    { duration: '10s', target: 0 }, // ramp-down to 0 users
  ],
  thresholds: {
    http_req_duration: ['p(99)<200'], // 99% of requests must complete below 1.5s
  },
};


const BASE_URL = 'https://withemployee.n-e.kr'
const USERNAME = 'ceo1@example.com';
const PASSWORD = '123456';
const COMPANYID = '9';
const TEAMNAME = 'marketing'
const TEAMID = '11'

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

    var teamRequest = JSON.stringify({
        name: TEAMNAME,
        companyId: COMPANYID,
        });

    let teamRes = http.post(`${BASE_URL}/api/teams/${TEAMID}`,teamRequest, authHeaders);
    check(teamRes, { 'update team successfully': (resp) => resp.status === 200 });

    sleep(1)
};