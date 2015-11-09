### To build new docker image run:
```
docker build -t simple-service ./docker
```

### To run docker container with default "docker-host" domain:
```
docker run -p 8080:8080 -p 8443:8443 simple-service
```

### To run docker container with custom domain:
```
docker run -p 8080:8080 -p 8443:8443 -e DOMAIN="custom-domain" simple-service
```