## Sample3: Push docker image to docker registry

- This sample creates a docker image and push it to docker registry. 

- Following artifacts will be generated from this sample.
    ``` 
    $> docker images
    anuruddhal/helloworld-push:v2.0.0
    
    $> tree
    ├── docker_push_sample.jar
    └── docker
        └── Dockerfile
    ```
### How to run:

1. Open docker_push_sample.bal file and change the registry url attribute.
```bash
@docker:DockerConfig {
    push:true,
    registry:"index.docker.io/$env{DOCKER_USERNAME},
    name:"helloworld-push",
    tag:"v2.0.0",
    username: "$env{DOCKER_USERNAME}",
    password: "$env{DOCKER_PASSWORD}"
}
```

2. Export docker username and password.
```bash
export DOCKER_USERNAME=<username>
export DOCKER_PASSWORD=<password>
```

3. Compile the  docker_push_sample.bal file. 
```bash
$> ballerina build docker_push_sample.bal
Compiling source
        docker_push_sample.bal

Generating executables
        docker_push_sample.jar

Generating docker artifacts...
        @docker                  - complete 3/3 

        Run the following command to start a Docker container:
        docker run -d -p 9090:9090 index.docker.io/anuruddhal/helloworld-push:v2.0.0

```

4. hello-world-docker.jar, Dockerfile and docker image will be generated: 
```bash
$> tree
.
├── README.md
├── docker_push_sample.bal
├── docker_push_sample.jar
└── docker
    └── Dockerfile
```

5. Verify the docker image is created:
```bash
$> docker images
REPOSITORY                        TAG                 IMAGE ID            CREATED             SIZE
anuruddhal/helloworld-push        v2.0.0              70a5e621b84d        2 minutes ago       102MB
```

6. Login to https://hub.docker.com/ and verify image is pushed.

![alt tag](./DockerRegistry.png)