## Docker Commands

```bash
    docker build -t discovery-server:latest .
```

```bash
    docker run -d --name discovery-server -p 8761:8761 discovery-server:latest

```