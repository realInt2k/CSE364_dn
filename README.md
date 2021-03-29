# CSE364_dn
Software Engineering course


Milestone 1 due: 11:59PM, Friday, April 2, 2021


# Set up your Dockerfile (ver 1)

\# getting base image<br />
FROM ubuntu:20.04<br />
MAINTAINER int2k pal <int2k@unist.ac.kr><br />
WORKDIR /root/project<br />
COPY run.sh /root/project<br />
RUN chmod +x run.sh<br />
\# exe during building<br />
RUN apt-get update<br />
RUN apt-get install -y<br />
RUN apt-get install -y software-properties-common<br />
RUN apt-get install -y openssh-server<br />
RUN apt-get install -y git gcc g++ python3 vim python3-pip<br />
RUN pip3 install essential_generators<br />
RUN apt-get install -y openjdk-11-jdk<br />
RUN apt install -y  maven<br />
\# exe after building<br />
CMD ["/bin/bash"]<br />

# 30 Mar 2021: How to run skeleton code:
- navigate to your cloned folder (i.e: ~/CSE364_dn)
- type: mvn install && java -jar target/CSE364_dn-1.0-SNAPSHOT.jar
- will add this implementation to run.sh later

- create your branch when you wanna change stuffs so it doesn't mess the skeleton up :) (i.e: git checkout -b baotruong; when pusshing: git push --set-upstream origin baotruong)
- updated skeleton, we have array objects: ratings, reviewers, movies declared and defined in Milestone1.java, which is called by App.java for conveniences.
