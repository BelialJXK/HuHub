#!/bin/sh

# Check if the container exists
if [ "$(docker ps -aq --filter name=$container_name)" ]; then
  # Stop and remove the container
  docker stop $container_name
  docker rm $container_name
fi


