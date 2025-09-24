## Docker Commands

```bash
    docker build -t discovery-server:latest .
```

```bash
    docker run -d --name discovery-server -p 8761:8761 discovery-server:latest

```

create folders for each service

```bash
    mkdir client, config, controller, dto\request, dto\x, entity\enums, entity\x, exception, filter, repository, service, utils
```