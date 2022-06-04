FROM nginx

COPY nginx.conf /etc/nginx/nginx.conf 
COPY fullchain.pem /etc/letsencrypt/live/withemployee.n-e.kr/fullchain.pem
COPY privkey.pem /etc/letsencrypt/live/withemployee.n-e.kr/privkey.pem