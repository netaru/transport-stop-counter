FROM node:20

WORKDIR /home/node/app
COPY package.json /home/node/app
COPY public /home/node/app/public
COPY src /home/node/app/src
RUN npm install

CMD [ "npm", "start" ]
EXPOSE 3000
