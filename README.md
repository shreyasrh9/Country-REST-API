# Country-REST-API

## Installation

```sh
git clone https://github.com/shreyasrh9/Country-REST-API.git
cd Country-REST-API
mvn package
docker build -t <Image Name of your choice> .
docker run -p <Host port of your choice>:8080
```

| Plugins | Links |
| ------ | ------ |
| API documention using Swagger | http://localhost:{port}/swagger-ui/index.html |
| H2 console | http://localhost:{port}/h2-console |
