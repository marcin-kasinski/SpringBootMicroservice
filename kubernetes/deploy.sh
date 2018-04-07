#!/bin/bash

#aby wykonac skrypt bez podawania hasła:
#sudo visudo
# potem dodajesz wpis: 
#marcin ALL = NOPASSWD: /home/marcin/SpringBootMicroservice/kubernetes/deploy.sh, /sbin/restart
#
#wc -l $(ls)
# echo  'ABC'$(echo "XXX" )

cd /home/marcin/SpringBootMicroservice/docker
docker build -f dockerfile -t springbootmicroservice . && docker tag springbootmicroservice marcinkasinski/springbootmicroservice && docker push marcinkasinski/springbootmicroservice
echo "End pushing springbootmicroservice"

kubectl delete pod $( kubectl get pod | grep springbootmicroservice-deployment  | head -n1 | sed -e 's/\s.*$//' )

echo "End deleting pod"
