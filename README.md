# Person-Service
 
### Service-description 
- the service is responsible for creating a simple Person service with the ability to:
1. Adding new person
2. update the name of a person
3. delete person 
4. show all persons
5. show person details


### tech stack used (runtime environment, frameworks, key libraries)
the service is developed with:
1. Java 17
2. spring boot
3. tomcat server
4. MYSQL DB
5. the service is using mapStruct to change dto to model and from model to dto
6. springfox for creating documentation 
7. the service run on docker
8. used cache (caffeine)

### how to: build the service run automatic tests run the service locally
- the service could be run on docker 
- you can run the following command 'docker-compose up -d'
- and you can send a request on port 8081 for example to send post 


- I have attached as well postman collection you can you it for testing manually
- To run the test from the command line run 'mvn test'
