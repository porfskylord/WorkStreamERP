# Kafka Commands Cheat Sheet (Bitnami Docker)

This cheat sheet covers Kafka commands for **Bitnami Docker** (KRaft mode) including topics, producers, consumers, consumer groups, and troubleshooting.

---

## 1. Enter Kafka Container
```bash
docker exec -it kafka bash
```

## 2. Create Topic
```bash
kafka-topics.sh --bootstrap-server localhost:9092 --create --topic <topic-name> --partitions 1 --replication-factor 1
```
```bash
kafka-topics.sh --bootstrap-server localhost:9092 --create --topic <topic-name> --partitions 2 --replication-factor 1
```

## 3. List Topics
```bash
kafka-topics.sh --bootstrap-server localhost:9092 --list
```

## 4. Describe Topic
```bash
kafka-topics.sh --bootstrap-server localhost:9092 --describe --topic <topic-name>
```

## 5. Delete Topic
```bash
kafka-topics.sh --bootstrap-server localhost:9092 --delete --topic <topic-name>
```

## 6. Produce Messages
```bash
kafka-console-producer.sh --bootstrap-server localhost:9092 --topic <topic-name>
```
```json
{"id":1,"msg":"hello"}
```
```json
{"id":2,"msg":"world"}
```

## 7. Produce Messages with Key
```bash
kafka-console-producer.sh --bootstrap-server localhost:9092 --topic <topic-name> --property "parse.key=true" --property "key.separator=:"
```
```
user1:{"id":1,"msg":"hello"},
user2:{"id":2,"msg":"world"}
```

## 7. Alter Topic
```bash
kafka-topics.sh --alter --bootstrap-server localhost:9092 --topic topic-test --partitions 4
```



## 8. Consume Messages
```bash
kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic <topic-name> --from-beginning
```
## 9. Consume Messages with Group ID
```bash
kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic <topic-name> --from-beginning --group <group-id>
```

## 10. Consume Messages with Key
```bash
kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic <topic-name> --from-beginning --property "print.key=true" --property "key.separator=:") 
```

## 11. List Consumer Groups
```bash
kafka-consumer-groups.sh --bootstrap-server localhost:9092 --list
```

## 12. Describe Consumer Group
```bash
kafka-consumer-groups.sh --bootstrap-server localhost:9092 --describe --group <group-id>
```

## 13. Reset Consumer Group Offset  
```bash
kafka-consumer-groups.sh --bootstrap-server localhost:9092 --reset-offsets --group <group-id> --to-earliest --execute --topic <topic-name>
```

## 14. Reset Consumer Group Offset to Latest
```bash
kafka-consumer-groups.sh --bootstrap-server localhost:9092 --reset-offsets --group <group-id> --to-latest --execute --topic <topic-name>
```

## 15. Reset Consumer Group Offset to Specific Offset
```bash
kafka-consumer-groups.sh --bootstrap-server localhost:9092 --reset-offsets --group <group-id> --to-offset <offset> --execute --topic <topic-name>
```

## 16. Reset Consumer Group Offset to Specific Timestamp
```bash
kafka-consumer-groups.sh --bootstrap-server localhost:9092 --reset-offsets --group <group-id> --to-timestamp <timestamp> --execute --topic <topic-name>
```

## 17. Troubleshooting / Cleanup
```bash
kafka-topics.sh --bootstrap-server localhost:9092 --delete --topic <topic-name>
```
```bash
kafka-topics.sh --bootstrap-server localhost:9092 --describe --topic <topic-name>
```

## 18. Delete a topic manually (dev only)
```bash
docker exec -it kafka bash
```
```bash
cd /bitnami/kafka/data
```
```bash
rm -rf <topic-name>-*
```
```bash
exit
```
```bash
docker restart kafka
```
## 19. Reset Kafka completely (all topics)
```bash
docker-compose down -v
```
```
docker-compose up -d
```






