#!/bin/bash

# run a container
image=$1
docker run -it  --name delme --rm "$image" /bin/bash
