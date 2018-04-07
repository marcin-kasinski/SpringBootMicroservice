#!/bin/bash
/etc/init.d/filebeat start
#java -jar SpringBootWeb-0.0.1-SNAPSHOT.jar --spring.cloud.consul.discovery.ipAddress=$HOST --spring.cloud.consul.discovery.port=$PORT0 --spring.cloud.consul.host=$HOST
java -jar SpringBootMicroservice-0.0.1-SNAPSHOT.jar