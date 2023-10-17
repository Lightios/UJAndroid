# syntax=docker/dockerfile:1

FROM ubuntu:22.04
RUN apt-get update && apt-get upgrade -y
RUN apt-get install software-properties-common -y
RUN add-apt-repository ppa:deadsnakes/ppa
# 3.0
RUN DEBIAN_FRONTEND=noninteractive apt-get install -y --no-install-recommends \
        python3.8-venv

# 3.5
RUN apt-get install openjdk-8-jdk -y
RUN apt -y install kotlin

# próby na 4.0
# RUN apt install snapd -y
# RUN snap install gradle --classic -y
# ENV GRADLE_VERSION=8.4
# RUN curl -L https://services.gradle.org/distributions/gradle-${GRADLE_VERSION}-bin.zip -o /tmp/gradle.zip \
#     && unzip /tmp/gradle.zip -d /opt \
#     && rm /tmp/gradle.zip
# ENV GRADLE_HOME /opt/gradle-${GRADLE_VERSION}
# ENV PATH $PATH:/opt/gradle-${GRADLE_VERSION}/bin


