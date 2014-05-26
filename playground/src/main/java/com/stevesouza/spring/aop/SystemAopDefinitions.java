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
public class SystemAopDefinitions {

    @Pointcut("execution(public * *(..))")
    public void anyPublicOperation() {}

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

    @Pointcut("anyPublicOperation() && inSteveSouza()")
    public void steveSouzaOperation() {}

    @Pointcut("anyPublicOperation() && inCamel()")
    public void camelOperation() {}

    @Pointcut("anyPublicOperation() && inCassandra()")
    public void cassandraOperation() {}

    @Pointcut("anyPublicOperation() && inElasticSearch()")
    public void elasticSearchOperation() {}
}
