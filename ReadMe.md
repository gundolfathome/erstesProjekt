# Getting Started With Spring Boot With Kubernetes

### Aufbau eines KIND-Kubernetes-Cluster mit drei Knoten

* Installing with a Package Manager.
* On Windows via Chocolatey (https://chocolatey.org/packages/kind)
* choco install kind -> das Installationsverzeichnis wird auf der Console angezeigt


* AUF DER KONSOLE:


* kind create cluster --kind-config.yaml
* kubectl get nodes  ==>
* es werden drei nodes angezeigt: kind-control-plane, kind-worker, kind-worker2


* Installation INGRESS:


* kubectl apply -f https://raw.githubusercontent.com/kubernetes/ingress-nginx/main/deploy/static/provider/kind/deploy.yaml


* TEST:


* kubectl apply -f test-deployment.yaml
* The output shows three new objects have been created: pod/test-app, service/test-service und ingress.networking.k8s.io/test-ingress
* kubectl port-forward service/test-service 5678:5678
* the following output appears:  
* ............Forwarding from 127.0.0.1:5678 -> 5678
* ............Forwarding from [::1]:5678 -> 5678
* Open another terminal window and test the deployment by typing: curl localhost:5678
* or open a browser and type in the address bar the URL: localhost:5678
* on the other console and in the browser should appear the following message: The test has been successful!
* kubectl delete -f test-deployment.yaml


* Notwendige yaml-Dateien zur Ausführung des Spring Boot Microservices:


* kubectl apply -f product-pod-definition.yaml
* kubectl apply -f product-service-definition.yaml
* kubectl apply -f ingress-definition.yaml
* kubectl apply -f deployment.yaml


### Docker Commands


* Vor der Docker-Containerisierung des Projekts die Endpoints mit Postman testen.
* Maven clean und Maven install ausführen
* Auf der Kommandozeile im Verzeichnis des Dockerfiles dieses Projekts folgenden Befehl ausführen: docker build -t product-svc-temp .



* VERSIONSÄNDERUNG


* Bei jeder neuen Änderung der SpringBoot-Applikation, diese beim Taggen entsprechend erhöhen:
* docker tag product-svc-temp:latest <<AUS ALT doesbattel/product-svc:1.0.0 wird z.B. NEU doesbattel/product-svc:1.0.1>>
* docker container run -p 8080:8080 -itd --name product-svc-app doesbattel/product-svc:1.0.1

* Vor dem Pushen, die Endpoints mit Postman testen.


* docker push doesbattel/product-svc:1.0.1


* in der Datei "deployment.yaml" die Versionsnummer des images anpassen
* Auf der Kommandozeile im deployments-Verzeichnis dieses Projekts folgenden Befehl ausführen:kubectl apply -f deployment.yaml
* den Docker Container mit dem Namen "product-svc-app" löschen
* jetzt können die Endpoints des Microservices im Kubernetes-Cluster wieder mit Postman getestet werden.


*        FOLGENDER BEFEHL GIBT DIE IMAGES ALLER PODS AUS (auf der Git-Bash-Console):

* kubectl get pods --all-namespaces -o jsonpath='{range .items[*]}{"\n"}{.metadata.name}{":\t"}{range .spec.containers[*]}{.image}{", "}{end}{end}' | sort
