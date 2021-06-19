# CSE364_dn
Software Engineering course

Milestone 4 due: 11:59PM, Juné 20th, 2021

# Milestone 4 highlights:

## Where is database set up ?:
- In com.unist.webApp.InputController, constructor.
- For start time reason, saving the ratings.dat to database is fine, but it takes a LONG time, so I commented that part out. 

## How to run (3 ways):

### Manually run, with Tomcat catalina (Assuming you have jdk 11 and maven in PATH):
- Clone this repo.
- Swap pom.xml and pom_war.xml file. (i.e: "pom.xml" -> "pom_jar.xml" and "pom_war.xml" -> "pom.xml")
- Go to local the cloned Github directory, type "mvn package" => will produce CSE364_dn-1.0.war at ${pwd}/target folder.
- Copy that CSE364_dn-1.0 war file to the webapps folder under tomcat directory which you downloaded from internet (apache-tomcat-9.0.8.tar.gz at http://mirror.23media.de/apache/tomcat/tomcat-9/v9.0.8/bin/).
- In the bin/ folder of tomcat, type "./catalina.sh run" to start the server.
- Go to http://localhost:8080/CSE364_dn-1.0/ on your web browser and start searching for you movies.
- Go to http://localhost:8080/CSE364_dn-1.0/movies to see all movies in JSON format.

### Manually run, with terminal (Assuming you have jdk 11 and maven in PATH):
- Clone this repo. Go to the cloned local Github directory.
- Type "mvn package" => It will produce CSE364_dn-1.0.jar at ${pwd}/target folder.
- Type "java -jar /target/CSE364_dn-1.0.jar".
- Go to http://localhost:8080/ on your web browser and start searching for you movies.
- Go to http://localhost:8080/movies to see all movies in JSON format.

### Run with docker (Assuming you have Docker in PATH):
- To the folder that has 3 files: "Dockerfile", "run.sh", "CSE364_dn-1.0.war". 
- Type "docker build <path-to-dockerfile> -t imageName ." where 'imageName' is whatever you name it. Wait until it finishes, it will takes a long time.
- Type "docker run -d -p 8080:8080 imageName". This will match YOUR 8080 port with this container 8080, so the app can work on YOUR local host.
- Voila, now you can go to http://localhost:8080/CSE364_dn-1.0/index.html on your favorite browser to test the app.
- Go to http://localhost:8080/CSE364_dn-1.0/movies to see all movies in JSON format.

## Features

### How to use features
- Very simple app. 3 main functions: 
- Main page displays the top 10 movies of the day.
- Go to "pick top 10" page and enter your age, occupation, prefered genres to get the top 10 based on your interest.
- Go to "find similar movie" page and enter the title, the amount of similar movies you wanna see, and search.

### Case handling:
- Go to localhost:8080/ and it will direct you to index.html
- In the "find similar movie" part, due to the search query can be extremely long and slow, 50 movies will be displayed at a time, and if you want to see more, you have to click the buttom at the bottom of the page to see more.
- There's no error, unless you forcefully input negative integer or unreadable integer at the "age" or "limit" fields.
- Click on movies title will direct you to imdb page (some link has been broken, because imdb pages themselves don't exist)
- Some of the poster will not be visible, instead, you will see the picture of confused character "Vincent Vega" from Pulp Fiction, as an annotation of "where?".

# Milestone 3 highlight:
- Dung Nguyen: implement the main algorithm for part 3, testing, improve code flow and add fix corner cases for part 2 and part 3. Write unit tests for the mile3 classes, controller classes and more.
- Bekatan Satyev: peer programming with Dung in the production of part 3 and part 2.
- Bao Truong: Providing new run.sh and Dockerfile, adding more unit test to get maximum coverages in some classes. Try to implement the project with tutorial including a database, but it's not working yet.

_All were trying really hard._

## Milestone 3 algorithm (mile3 + changes in mile2)
- No genres in mile2 now isn't an error, we simply recommend movies based on the remaining factors (if inputed) like age, occupation, gender.
- In movie recommendation with input movie title, we sort the movie data set with relevancy first, then score second. Relevancy of user movie X and a random movie Y is defined as the percentage of matches in X's genres and Y's genres. 
## Milestone 3 case handling:
- Accept movie title with errors in spacing, although letters in title should be precise and the year should be precisely included (i.e: "(1996)" = ok, but "(1996" = not ok)
- If movie is not found, then recommend movies based solely on score, and a warning will be prompted in a non-JSON format.
- Missing arguments will result in a warning, and movies are still recommended (as if the preferences in arguments are neutral)
- Argument error like negative age (mile2) or limit (mile3) will result in a badarg JSON.
- GET or POST in curl works the same.
- No graphics in web server yet.
- "localhost/" will have text "Greeting from group 5".

# Milestone 2 highlight:
- Dung Nguyen: Design the main algorithm, code the main implementation, do some jUnit cases.
- Bekatan Satyev: Code the jUnit tests, refactor some small parts of the code.
- Bao Truong: Help in designing the algorithm in the first stage, made the new run.sh.

# Milestone 2 Relevancy and Similarity algorithm - fix May 2nd 2021.
- The algorithm is implemented in the file **Milestone2.java**.
## Filter first
- We filter the relevant data: movies that contains the specific genres, reviews that for the movies with specific genres, Users that review movies in specific genres.
- If after filtering we have < 10 movies, we will select more random movies (that doesn't have the matching genres with User-input of-course)

## Threshold
- Then we calculate the threshold Number of valid samples = n / (1-n\*e^2) where n = number of valid Users and e is error in percentage.

## How relevance scores are calculated:
- The weight of each Age, Gender, Occupation, Genre defined in respective AgeGapScore, GenderGapScore, etc.. are to be modified by dev. The primitive values of these are written in com.help.defaultScore, the inter-ratio between them does the job.
- The Similarity between the reviewer Y and our customer X in term of Age, Gender, etc. will determine how many percentage should we take from weight of each Age, Gender, etc. respecively. The similarity functions are defined under \*GapScore.java, with \*.getPercentCompare method to calculate similarity between obj A and B.
- For example, if Y has the same age as X, and semi-different in every other fields, then in the similarity score, 100% of the weight for Age is taken, and for other fields (Gender, Occupation, etc..), some% of the weight is taken. The sum of the weights taken is the similarity score of the people making the review.
- We go thru the review list, if the review i is for the movie j, then the relevanceScore of movie j will be increased by the similarity score of the reviewer i.
- We then divide the relevance score of each movie i by the number of reviews for it, to get Average Relevance Score.

## Get top relevant movie:
- We then sort the movie list according to the Criteria (1).
- We then find the largest score gap exists within the list. If the gap exists in the very early index ( < 10% of number of movies), then we take the first 5% of the movie data set into consideration. If it doesn't exist in the indices under 10% of the number of movies, we take all the movies before the gap into consideration.

### Criteria (1) (movie A vs B):
- If the number of reviews for movie A && number of reviews for movie B are both greater then threshold or both smaller than threshold then we see which one has higher Average Relevance Score.

## Get top 10 movie recommendations: 
- We then sort the selected list via Criteria (2). And we have top 10.
### Criteria (2) (movie A vs B):
- If the number of reviews for movie A && number of reviews for movie B are both greater then threshold or both smaller than threshold then we see which one has higher score/maxScore + relevanceScore/maxRelevanceScore.

# How to run our program by Java command line:
  - Clone the project: git clone https://github.com/realInt2k/CSE364_dn.git
  - Navigate to your cloned folder: `cd */CSE364_dn`
  - Given user data, on Terminal, type: 
    `mvn install && java -cp target/cse364-project-1.0-SNAPSHOT-jar-with-dependencies.jar com.unist.App arg1 arg2 arg3`. Here, we substitute arg1 by gender, arg2 by age, arg3 by occupation. The arguments may be empty.
  - Given categories, on Terminal, type:
    `mvn install && java -cp target/cse364-project-1.0-SNAPSHOT-jar-with-dependencies.jar com.unist.App arg1 arg2 arg3 arg4`. Here, we substitute arg1 by gender, arg2 by age, arg3 by occupation, arg4 by genre(s) of the film (if more than one we use | as delimiter and wrap them with ""). The arguments may be empty, except for arg4.

# Milestone 1 highlights:

## Argument handling explain:
- 0 argument => simply compile the project, nothing happens, a message will be prompt to let user know what to input.
- 1 arguments => no occupation specified, select "other" as default.
- 2 arguments => normal calculation cases.
- ≥ 2 arguments => warning prompt, only use first 2.

## Cases handling
- If input strings have white spaces i.e  java .. "ad venture| comedy" "docto r " => still accepted and be calculated normally.
- If you query a genre more than one i.e java .. "adventure|adventurE|AdVeNtUrE|" => only one is selected and displayed in the result.
- If occupation input is incorrect => warning prompt + hints and terminate.
- If any of the genres' input is incorect => warning prompt + hints and terminate.
- If arguments are in wrong order then a prompt will let you known it's in wrong order and terminate.
- If you're happy and you know it => clap your hand and terminate.

## Works from team members <--- edit please
- Dung: make working environments, work on main implementations, a few fixes here and there in corner cases, improve visualization.
- Luong: refactor source code, fix bugs & handle corner cases, create unit tests.
- Bao:  review code, develop test plan and evaluate the dockerfile, POM file and application.
- Bekatan: testing Milestone2 corner cases, OccupationGapScore and ensuring >90 branch coverage

# Set up your Dockerfile (ver 1)

```dockerfile
# Getting base image
FROM ubuntu:20.04
MAINTAINER int2k pal <int2k@unist.ac.kr>
WORKDIR /root/project
COPY run.sh /root/project
RUN chmod +x run.sh
# Execute during building
RUN apt-get update
RUN apt-get install -y
RUN apt-get install -y software-properties-common
RUN apt-get install -y openssh-server
RUN apt-get install -y git gcc g++ python3 vim python3-pip
RUN pip3 install essential_generators
RUN apt-get install -y openjdk-11-jdk
RUN apt install -y  maven
# Execute after building
CMD ["/bin/bash"]
```

- How to run docker:
  - Create a file name: "Dockerfile" on your folder, copy the contents. 
  - Create another file name: "run.sh" on the same folder, leave it be. 
  - Run "docker build -t mile1:1.0 ." on your terminal, the 'mile1:1.0' is your image's name, which can be changed depending your liking. 
  - Run the image in container by executing: docker run -it mile1:1.0

- How to run our program by Java command line:
  - Clone the project: git clone https://github.com/realInt2k/CSE364_dn.git
  - Navigate to your cloned folder: `cd */CSE364_dn`
  - On Terminal, type: 
    `mvn install && java -cp target/cse364-project-1.0-SNAPSHOT-jar-with-dependencies.jar com.unist.App arg1 arg2`. Here, we substitute arg1 by genre(s) of the film (if more than one we use | as delimiter and wrap them with ""), and arg2 by occupation. 

# 31 Mar 2021: We need your test cases
- Suggestion: implement your own score calculation & compare, test the validity of milestone1.initialize().
- Can sb validate the dockerfile?

# 30 Mar 2021: How to run skeleton code:
- Navigate to your cloned folder (i.e: ~/CSE364_dn)
- On Linux, type: `mvn install && java -jar target/CSE364_dn-1.0-SNAPSHOT.jar arg1arg2 ...`

- Will add this implementation to run.sh later

- Create your branch when you wanna change stuffs so it doesn't mess the skeleton up :) (i.e: git checkout -b baotruong; when pushing: git push --set-upstream origin baotruong)
- Updated skeleton, we have array objects: ratings, reviewers, movies declared and defined in Milestone1.java, which is called by App.java for conveniences.

- String.split('char') returns a bare String when char isn't found, fixed that.

- A basic solve function is provided, can be wrong, idk, please make test to check implementation (or read the code itself).

- On Windows: Click on Maven, click on "m", choose goal "install", wait til it's done. Then type in terminal at the working folder: java -jar .\target\cse364-project-1.0-SNAPSHOT-jar-with-dependencies.jar arg1 arg2 ...
