# Use the openjdk 17 image as the base image
#FROM openjdk:17
#FROM maven:3.5.0-jdk-8

FROM openjdk:17-alpine

# Install the missing X11 library
RUN apk --no-cache add libxrender libxtst libxi

# Install xvfb
#RUN apt-get update && apt-get install -y xvfb

# Install the missing X11 library
#RUN apt-get update && apt-get install -y libxext6

#Create a new app directory for my application files(that directory live inside your container not on your host machine)
#it applies for image
RUN mkdir /Snake_Game

#Set directory for excuting future commands \
WORKDIR /Snake_Game

#Copy the app files from host machine to image filesystem \
#hier first argument applies for host machine and 2nd for image
COPY ./ /Snake_Game

# Compile the Java code
RUN javac SnakeGame.java

#Run the main class
#used when i want to rum my main class when my container starts.I'll use CMD
#CMD for specify the command i would run if i was trying to run my main class from terminal
CMD ["java", "SnakeGame"]
#CMD tail -f /dev/null