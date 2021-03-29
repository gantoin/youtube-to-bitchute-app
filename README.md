build:

```shell
docker build --tag=bitchute-uploader-api:latest .
```

run:

```shell
docker run -p8090:8080 bitchute-uploader-api:latest
```

call:

```shell
curl localhost:8090/
```
