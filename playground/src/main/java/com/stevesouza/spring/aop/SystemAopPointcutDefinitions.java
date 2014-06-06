package com.stevesouza.spring.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

/**
 * It is good to have a class that has common aop pointcuts for your application.  These can be reused and built
 * upon elsewhere.
 *
 * Created by stevesouza on 5/26/14.
 */
@Aspect
public class SystemAopPointcutDefinitions {

    @Pointcut("execution(public * *(..))")
    public void anyPublicMethod() {}

    // com.stevesouza..* - .. means stevesouza and any of its subpackages.
    // com.stevesouza.*  - means only in com.stevesouza and none of its subpackages
    @Pointcut("within(com.stevesouza..*)")
    public void inSteveSouza() {}

    @Pointcut("within(com.stevesouza.camel..*)")
    public void inCamel() {}

    @Pointcut("within(com.stevesouza.cassandra..*)")
    public void inCassandra() {}

    @Pointcut("within(com.stevesouza.elasticsearch..*)")
    public void inElasticSearch() {}

    // The pointcut below will find all methods in all types marked with @MonitorAnnotation
    @Pointcut("within(@com.stevesouza.spring.aop.MonitorAnnotation *)")
    public void monitorAnnotatedClass() {}

    // The pointcut below will find all methods marked in with @MonitorAnnotation regardless of whether or not
    // class is marked that way or not.
    @Pointcut("anyPublicMethod() && @annotation(com.stevesouza.spring.aop.MonitorAnnotation)")
    public void monitorAnnotatedMethod() {}

    @Pointcut("anyPublicMethod() && inSteveSouza()")
    public void steveSouzaOperation() {}

    @Pointcut("anyPublicMethod() && inCamel()")
    public void camelOperation() {}

    @Pointcut("anyPublicMethod() && inCassandra()")
    public void cassandraOperation() {}

    @Pointcut("anyPublicMethod() && inElasticSearch()")
    public void elasticSearchOperation() {}
}
