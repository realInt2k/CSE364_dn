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

- how to run docker:
- create a file name: "Dockerfile" on your folder, copy the contents. Create another file name: "run.sh" on the same folder, leave it be. Run "Docker build -t mile1:1.0 ." on your terminal, the 'mile1:1.0' can be changed depending your liking. How to run the image in container is explained in the slide.

# 31 Mar 2021: we need your test cases
- suggestion: implement your own score calculation & compare, test the validity of milestone1.initialize().
- can sb validate the dockerfile?

# 30 Mar 2021: How to run skeleton code:
- navigate to your cloned folder (i.e: ~/CSE364_dn)
- On Linux, type: mvn install && java -jar target/CSE364_dn-1.0-SNAPSHOT.jar arg1arg2 ...

- will add this implementation to run.sh later

- create your branch when you wanna change stuffs so it doesn't mess the skeleton up :) (i.e: git checkout -b baotruong; when pushing: git push --set-upstream origin baotruong)
- updated skeleton, we have array objects: ratings, reviewers, movies declared and defined in Milestone1.java, which is called by App.java for conveniences.

- String.split('char') returns a bare String when char isn't found, fixed that.

- a basic solve function is provided, can be wrong, idk, please make test to check implementation (or read the code itself).

- On Windows: Click on Maven, click on "m", choose goal "install", wait til it's done. Then type in terminal at the working folder: java -jar .\target\cse364-project-1.0-SNAPSHOT-jar-with-dependencies.jar arg1 arg2 ...
