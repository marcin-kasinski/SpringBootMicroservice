#!/bin/bash
/etc/init.d/filebeat start

java -jar -Dspring.profiles.active=$SPRING_PROFILE -Dlogging.level.org.apache.http=DEBUG SpringBootMicroservice-0.0.1-SNAPSHOT.jar