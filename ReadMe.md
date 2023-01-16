# Getting Started With Spring Boot With Kubernetes

## Aufbau eines **KIND-Kubernetes-Cluster** mit drei Knoten
- Installing  with a Package Manager.
- On Windows via Chocolatey (https://chocolatey.org/packages/kind)

### eine Konsole öffnen und in das Projektverzeichnis wechseln
- choco install kind -> das Installationsverzeichnis wird auf der Console angezeigt
- dort im Ordner *kubernetes* : **kind create cluster --create-kind-cluster.yaml**
- kubectl get nodes  ==>
- es werden **drei** nodes angezeigt: kind-control-plane, kind-worker, kind-worker2

### Installation INGRESS:
- kubectl apply -f https://raw.githubusercontent.com/kubernetes/ingress-nginx/main/deploy/static/provider/kind/deploy.yaml

### TEST:
- im Ordner *kubernetes* : **kubectl apply -f test-deployment.yaml**
- The output shows three new objects have been created: pod/test-app, service/test-service und ingress.networking.k8s.io/test-ingress
- im Ordner *kubernetes* : **kubectl port-forward service/test-service 5678:5678**
- the following output appears:
- **Forwarding from 127.0.0.1:5678 -> 5678**
- **Forwarding from 127.0.0.1:5678 -> 5678**
- Open **another** terminal window and test the deployment by typing: curl localhost:5678
- or open a **browser** and type in the address bar the URL: localhost:5678
- on the other console and in the browser should appear the following message: **The test has been successful!**
- im Ordner *kubernetes* : **kubectl delete -f test-deployment.yaml**


### Notwendige yaml-Dateien zur Ausführung des Spring Boot Microservices:
- im Ordner *kubernetes* : **kubectl apply -f pod-product.yaml**
- im Ordner *kubernetes* : **kubectl apply -f service-product.yaml**
- im Ordner *kubernetes* : **kubectl apply -f ingress-product.yaml**
- im Ordner *kubernetes* : **kubectl apply -f deployment-product.yaml**


### TEST der Spring Boot Applikation mit Postman
- Vor der Docker-Containerisierung dieses Projekts werden die Endpoints der Spring Boot Applikation mit **Postman** getestet.
- Für POST kann z.B. folgender Body eingegeben werden: {
    "name": "Apple iPhone 333",
    "description": "Foldable iphone",
    "price": "2500"
}
- ==> mit dem Request **POST http://localhost:8080/products/** wird dann das Produkt gespeichert.
- ==> mit dem Request **GET http://localhost:8080/products/** werden alle Produkte in Postman ausgegeben. Daraus lassen sich 
  dann auch die **ID-Nummer(n)** ermitteln, die im nächsten GET Request verwendet werden können.
- mit dem Request **GET http://localhost:880/products/ID-Nummer** wird das entsprechende Produkt
  in Postman ausgegeben.

### Docker Commands
- in **Eclipse** Maven clean und Maven install ausführen
- Auf der Kommandozeile im **Verzeichnis des Dockerfiles** dieses Projekts folgenden Befehl ausführen: **docker build -t product-svc-temp .**
- ==> dann einen neuen Tag vergeben: **docker tag product-svc-temp:latest doesbattel/product-svc:1.0.0**
- ==> **docker container run -p 8080:8080 -itd --name product-svc-app doesbattel/product-svc:1.0.0**
- **Vor** dem Pushen, die Endpoints wie oben beschrieben mit Postman testen.
- ==> **docker push doesbattel/product-svc:1.0.0**

### Bei Versionsänderung
- Bei jeder neuen Änderung der SpringBoot-Applikation, diese beim Taggen entsprechend erhöhen:
- docker tag product-svc-temp:latest <<AUS ALT doesbattel/product-svc:**1.0.0** wird z.B. NEU doesbattel/product-svc:**1.0.1**>>
- docker container run -p 8080:8080 -itd --name product-svc-app doesbattel/product-svc:**1.0.1**
- Vor dem Pushen, die Endpoints mit Postman testen.
- ==> **docker push doesbattel/product-svc:1.0.1**

### die Anwendung im Kubernetes-Cluster ausführen
- **Wichtig** : in der Datei "deployment-product.yaml" die Versionsnummer des images anpassen
- im Ordner *kubernetes* : **kubectl apply -f deployment-product.yaml**
- den Docker Container mit dem Namen "product-svc-app" löschen
- jetzt können die Endpoints des Microservices im Kubernetes-Cluster wieder wie oben beschrieben mit Postman getestet werden.

FOLGENDER BEFEHL GIBT DIE IMAGES ALLER PODS AUS (auf der Git-Bash-Console):
allen 
- `kubectl get pods --all-namespaces -o jsonpath='{range .items[*]}{"\n"}{.metadata.name}{":\t"}{range .spec.containers[*]}{.image}{", "}{end}{end}' | sort`
