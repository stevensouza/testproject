# docker build -t=“jamonapi/jetty_jamon” .
FROM jetty
MAINTAINER Steve Souza < steve@stevesouza.com >
ENV REFRESHED_AT 2016-08-26
RUN apt-get -yqq update
RUN apt-get -yqq install wget
RUN java -jar "$JETTY_HOME/start.jar" --add-to-startd=jmx,stats
# COPY jamon/jamon-2.81.jar lib/ext/jamon-2.81.jar
# COPY jamon/jamon.war webapps/jamon.war
# COPY jamon/jamonapi.properties resources/jamonapi.properties
# ./jetty-distribution-9.2.1.v20140609/lib/ext/jamon-2.81.jar
# ./jetty-distribution-9.2.1.v20140609/resources/jamonapi.properties
# ./jetty-distribution-9.2.1.v20140609/webapps/jamon.war
# VOLUME [ "/ var/ lib/ tomcat7/ webapps/" ]
# WORKDIR /var/ lib/ tomcat7/ webapps/
CMD [ "/bin/bash" ]
