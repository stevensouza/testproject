package com.stevesouza.annotations;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by stevesouza on 5/24/14.
 * <p>
 * To run from maven: mvn  exec:java -Dexec.mainClass="com.stevesouza.annotations.SpringMain" -Dexec.classpathScope=runtime -X
 */
public class SpringMain {
    private static final Logger LOG = Logger.getLogger(SpringMain.class);

    public static void main(String[] args) throws Exception {
        ApplicationContext context = new ClassPathXmlApplicationContext("annotationApplicationContext.xml");
        AnnotationExperiments obj = (AnnotationExperiments) context.getBean("annotationExperiments");
        obj.myAnnotatedMethod();
        obj.myAnnotatedMethod2("steve", "souza");
    }
}
