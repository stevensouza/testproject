#!/bin/bash

# remove all containers with exit status
docker rm $(docker ps -a -q -f status=exited)
