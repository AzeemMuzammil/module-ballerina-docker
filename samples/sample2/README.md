## Sample:2: Change docker image's tag and registry with annotations

- This sample runs simple ballerina hello world service in a docker container with annotation configurations to 
change image name, tag and registry. 

- Following artifacts will be generated from this sample.
    ``` 
    $> docker images
    docker.abc.com/helloworld:v1.0
    
    $> tree
    ├── hello_world_docker.jar
    └── docker
        └── Dockerfile
    ```
### How to run:

1. Compile the  hello_world_docker.bal file. Command to run docker image will be printed on success:
```bash
$> bal build hello_world_docker.bal
Compiling source
        hello_world_docker.bal

Generating executables
        hello_world_docker.jar

Generating docker artifacts...
        @docker                  - complete 2/2 

        Run the following command to start a Docker container:
        docker run -d -p 9090:9090 docker.abc.com/helloworld:v1.0
```

2. hello_world_docker.jar, Dockerfile and docker image will be generated: 
```bash
$> tree
.
├── README.md
├── hello_world_docker.bal
├── hello_world_docker.jar
└── docker
    └── Dockerfile
```

3. Verify the docker image is created:
```bash
$> docker images
REPOSITORY                 TAG                 IMAGE ID            CREATED              SIZE
docker.abc.com/helloworld   v1.0              df83ae43f69b        2 minutes ago        102MB

```

4. Run docker image as a container (Use the command printed on screen in step 1):
```bash
$> docker run -d -p 9090:9090 docker.abc.com/helloworld:v1.0
130ded2ae413d0c37021f2026f3a36ed92e993c39c260815e3aa5993d947dd00
```

5. Verify docker container is running:
```bash
$> docker ps
CONTAINER ID        IMAGE                            COMMAND                  CREATED                  STATUS              PORTS                    NAMES
130ded2ae413        docker.abc.com/helloworld:v1.0   "/bin/sh -c 'balleri…"   Less than a second ago   Up 3 seconds        0.0.0.0:9090->9090/tcp   thirsty_hopper
```

6. Access the hello world service with curl command:
```bash
$> curl -k https://localhost:9090/helloWorld/sayHello
Hello, World!
```

7. Remove docker instance and image.
```bash
$> docker rm -f 130ded2ae413
$> docker rmi docker.abc.com/helloworld:v1.0
```