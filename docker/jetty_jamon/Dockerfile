# docker build -t="jamonapi/jetty_jamon" .
# docker run -d -p 80:8080 jamonapi/jetty_jamon
# http:\\localhost

FROM jetty
MAINTAINER Steve Souza < steve@stevesouza.com >
ENV REFRESHED_AT 2016-08-27a
RUN apt-get -yqq update
RUN apt-get -yqq install wget
RUN java -jar "$JETTY_HOME/start.jar" --add-to-startd=jmx,stats
# currently this installs jamon 2.79 (with listener that tracks page hits)
RUN printf 'Y' | java -jar "$JETTY_HOME/start.jar" --add-to-startd=jamon

# this would do the most recent jamon however it doesn't alter jetty.xml for tracking page hits.
#COPY jamon/jamon-2.81.jar lib/ext/jamon-2.81.jar
#COPY jamon/jamon.war webapps/jamon.war
#COPY jamon/jamonapi.properties resources/jamonapi.properties

# VOLUME [ "/ var/ lib/ tomcat7/ webapps/" ]
# WORKDIR /var/ lib/ tomcat7/ webapps/
# CMD [ "/bin/bash" ]
