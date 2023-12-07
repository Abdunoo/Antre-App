FROM node:18 as builder
WORKDIR /antre-web
COPY . .
RUN npm install
RUN npm run build

FROM nginx:latest
COPY --from=builder /antre-web/dist/antre-web /usr/share/nginx/html
EXPOSE 80
