# Running the whole service

docker build ./video-service --tag video-service:latest
docker build ./identity-service --tag identity-service:latest
docker build ./gateway --tag gateway:latest
docker build ./service-discovery --tag discovery-service:latest
docker build ./notification-service --tag notification-service:latest
docker build ./streamify-fe --tag streamify-fe:latest


docker-compose up