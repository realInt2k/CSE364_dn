# getting base image
FROM ubuntu:20.04

MAINTAINER int2k pal <int2k@unist.ac.kr>
WORKDIR /root/project
COPY run.sh /root/project
RUN chmod +x run.sh

# exe during building
RUN apt-get update
RUN apt-get install -y 
RUN apt-get install -y software-properties-common
RUN apt-get install -y openssh-server
RUN apt-get install -y git gcc g++ python3 vim python3-pip
RUN pip3 install essential_generators
RUN apt-get install -y openjdk-11-jdk
RUN apt install -y  maven

#exe after building
CMD ["/bin/bash"]
