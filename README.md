# youtube-to-bitchute-api

As curl:

```shell
curl --location --request POST 'localhost:8080/upload' \
--header 'Content-Type: application/json' \
--data-raw '{
    "user": "my_mail@gmail.com",
    "password": "myPassword",
    "youtubePath": "https://www.youtube.com/watch?v=7X8II6J-6mU"
}'
```

