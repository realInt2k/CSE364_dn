# CSE364_dn
Software Engineering course


Milestone 1 due: 11:59PM, Friday, April 2, 2021

# Milestone 1 hightlight:

**Argument handling explain:**
- 0 argument => simply compile the project, nothing happens, a message will be prompt to let user know what to input.
- 1 arguments => no occupation specified, select "other" as default.
- 2 arguments => normal calculation cases.
- ≥ 2 arguments => warning prompt.
<br/>
**Cases handling**
- If occupation input is incorrect => warning prompt.
- If any of the genres' input is incorect => find no matches.
- If you're happy and you know it => clap your hand.
<br/>
**Works from team members <--- edit please **
- Dung: make working environments, work on main implementations, a few fixes here and there in corner cases.
- Luong: 
- Bao:
- Bekatan: 

# Set up your Dockerfile (ver 1)

```dockerfile
# Getting base image
FROM ubuntu:20.04
MAINTAINER int2k pal <int2k@unist.ac.kr>
WORKDIR /root/project
COPY run.sh /root/project
RUN chmod +x run.sh
# Exec during building
RUN apt-get update
RUN apt-get install -y
RUN apt-get install -y software-properties-common
RUN apt-get install -y openssh-server
RUN apt-get install -y git gcc g++ python3 vim python3-pip
RUN pip3 install essential_generators
RUN apt-get install -y openjdk-11-jdk
RUN apt install -y  maven
# Exec after building
CMD ["/bin/bash"]
```

- How to run docker:
- Create a file name: "Dockerfile" on your folder, copy the contents. Create another file name: "run.sh" on the same folder, leave it be. Run "Docker build -t mile1:1.0 ." on your terminal, the 'mile1:1.0' can be changed depending your liking. How to run the image in container is explained in the slide.

# 31 Mar 2021: We need your test cases
- Suggestion: implement your own score calculation & compare, test the validity of milestone1.initialize().
- Can sb validate the dockerfile?

# 30 Mar 2021: How to run skeleton code:
- Navigate to your cloned folder (i.e: ~/CSE364_dn)
- On Linux, type: mvn install && java -jar target/CSE364_dn-1.0-SNAPSHOT.jar arg1arg2 ...

- Will add this implementation to run.sh later

- Create your branch when you wanna change stuffs so it doesn't mess the skeleton up :) (i.e: git checkout -b baotruong; when pushing: git push --set-upstream origin baotruong)
- Updated skeleton, we have array objects: ratings, reviewers, movies declared and defined in Milestone1.java, which is called by App.java for conveniences.

- String.split('char') returns a bare String when char isn't found, fixed that.

- A basic solve function is provided, can be wrong, idk, please make test to check implementation (or read the code itself).

- On Windows: Click on Maven, click on "m", choose goal "install", wait til it's done. Then type in terminal at the working folder: java -jar .\target\cse364-project-1.0-SNAPSHOT-jar-with-dependencies.jar arg1 arg2 ...
