# Cloud Native PDF Service
Spring Boot based PDF generation microservice for use in the Cloud Native initiative

This project is configured to run off a minikube installation which by default is at 192.168.99.100

&nbsp;


## Dependencies
This projects has dependencies on the ```cloud-native-poc-pdf-api``` and ```cloud-native-poc-pdf-client``` project
&nbsp;


## Running on minikube
&nbsp;

### Start up minikube
```bash
minikube start  
```
&nbsp;


### Enter minikube docker environment
```bash
eval $(minikube docker-env)  
```
&nbsp;


### Deploy the application to the kubernetes cluster
```bash
kubectl create -f cloud-native-poc-pdf-deployment.yaml  
kubectl create -f cloud-native-poc-pdf-service.yaml  
```
&nbsp;


### Verify that pod is running
```bash
kubectl get pods  
```
&nbsp;


### Verify that service is running
```bash
kubectl get services  
```
&nbsp;


### To refer to the PDF microservice within kubernetes use the following name
```bash
pdf:8083
```