### To build new docker image run:
```
docker build -t simple-service .
```

### To run docker container:
```
docker run -p 8080:8080 -p 8443:8443 simple-service
```