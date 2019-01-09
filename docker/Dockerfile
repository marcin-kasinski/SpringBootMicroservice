FROM openjdk:9
MAINTAINER Marcin Kasi�ski <marcin.kasinski@gmail.com> 




########################################## starting Logstash  ##########################################

# install Filebeat
#ENV FILEBEAT_VERSION=filebeat-5.5.2-amd64.deb
ENV FILEBEAT_VERSION=filebeat-6.4.2-amd64.deb

RUN mkdir /usr/src/myapp \
&& mkdir /usr/src/myapp/logs \
&& touch /usr/src/myapp/logs/application.log
#&& curl -L -O https://artifacts.elastic.co/downloads/beats/filebeat/${FILEBEAT_VERSION} \
#&& dpkg -i ${FILEBEAT_VERSION} \
#  && rm ${FILEBEAT_VERSION}

# configure Filebeat
#ADD filebeatstokafka.yml /etc/filebeat/filebeat.yml
#ADD filebeatstologstash.yml /etc/filebeat/filebeat.yml
#ADD filebeatstoelasticsearch.yml /etc/filebeat/filebeat.yml


#RUN /etc/init.d/filebeat start

ADD start.sh /usr/src/myapp/start.sh
RUN sed -i -e 's/\r//g' /usr/src/myapp/start.sh \
&& chmod +x /usr/src/myapp/start.sh

COPY SpringBootMicroservice-0.0.1-SNAPSHOT.jar /usr/src/myapp/SpringBootMicroservice-0.0.1-SNAPSHOT.jar
WORKDIR /usr/src/myapp
#CMD /etc/init.d/filebeat start && java -jar SpringBootMicroservice-0.0.1-SNAPSHOT.jar
#CMD ls -l /
#CMD wget -O https://artifacts.elastic.co/downloads/beats/filebeat/${FILEBEAT_VERSION} 
#CMD cat /filebeat_5.5.2_amd64.deb
#CMD /etc/init.d/filebeat start 
EXPOSE 7777



ENTRYPOINT [ "/usr/src/myapp/start.sh" ]