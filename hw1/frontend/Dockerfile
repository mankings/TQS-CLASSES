# pull official base image
FROM node

# set working directory
WORKDIR /app

# install app dependencies
COPY package.json .
COPY package-lock.json .
RUN npm install --silent

# add app
COPY . .

EXPOSE 5173

# start app
CMD ["npm", "run", "dev"]