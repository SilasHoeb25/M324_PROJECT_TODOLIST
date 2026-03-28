# --- Build-Stage: React-App bauen ---
FROM node:20 AS build
WORKDIR /app

# package.json & lockfile kopieren
COPY package*.json ./
RUN npm install

# Rest vom Code
COPY . .
RUN npm run build


FROM nginx:alpine
WORKDIR /usr/share/nginx/html

COPY nginx.conf /etc/nginx/conf.d/default.conf

COPY --from=build /app/dist ./

EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]