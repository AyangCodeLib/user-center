FROM nginx
MAINTAINER ayang

WORKDIR /usr/share/nginx/html/
USER root

COPY ./docker/nginx.conf /etc/nginx/conf.d/default.conf

COPY ./dist /usr/share/nginx/html/

EXPOSE 80

ENTRYPOINT  ["nginx","-g","daemon off;"]
