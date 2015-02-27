package com.stevesouza.sl4j;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *  Note you have to have a binding jar such as slf4j-log4j12-1.7.10.jar in your class path in addition to sl4j and
 * the logging framework (in this case log4j).  You switch bindings by putting different jars in your class path.
 * I believe logback-classic-x.x.x.jar is native implementation.
 *
 * Note there are currenty multiple bindings and so it seems to pick the first one.  That doesn't matter to me here as any are fine
 * to prove the concept.
 *
 * for more info http://www.slf4j.org/manual.html
 *
 *   The following prints out when I run the main method below indicating multiple bindings.
 *    SLF4J: Class path contains multiple SLF4J bindings.
 *    SLF4J: Found binding in [jar:file:/Users/stevesouza/.m2/repository/org/slf4j/slf4j-log4j12/1.7.10/slf4j-log4j12-1.7.10.jar!/org/slf4j/impl/StaticLoggerBinder.class]
 *    SLF4J: Found binding in [jar:file:/Users/stevesouza/.m2/repository/ch/qos/logback/logback-classic/1.1.2/logback-classic-1.1.2.jar!/org/slf4j/impl/StaticLoggerBinder.class]
 *    SLF4J: See http://www.slf4j.org/codes.html#multiple_bindings for an explanation.
 *    SLF4J: Actual binding is of type [org.slf4j.impl.Log4jLoggerFactory]
 */
public class Write {
    
    public static void main(String[] args) {
        Logger logger = LoggerFactory.getLogger(Write.class);
        logger.info("Hello World");
        logger.info("Hi {} & {}", "mom", "dad");
    }
}
