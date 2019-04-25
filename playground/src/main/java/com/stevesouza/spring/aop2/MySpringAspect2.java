package com.stevesouza.spring.aop2;

import com.jamonapi.Monitor;
import com.jamonapi.MonitorFactory;
import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

/**
 * Created by stevesouza on 5/24/14.
 * Samples of aspectj pointcuts...
 * <p>
 * Other examples
 * execution(* DemoClass.*(..)):
 * You can omit the package if the DemoClass and the advice is in the same package.
 */
// Spring automatically finds
// By default each aspect is a singleton within the applicationContext
@Aspect
public class MySpringAspect2 {
    private static final Logger LOG = Logger.getLogger(MySpringAspect2.class);


    @Pointcut("execution(* MySpringBean.*(..))")
    // odd but must define empty method.  It isn't called.  it just lets us reuse the name 'advisedMethod' below
    // alternatively you could just put the above pointcut on each method.
    // note these annotations let this be a simple pojo.  cool
    public void profile() {
    }


    @AfterThrowing(pointcut = "profile()", throwing = "exception")
    public void myAfterThrowingException(Throwable exception) {
        LOG.info("**** aop myAfterThrowingException() and exception=" + exception);
    }

    // note this is also used to advise another method defined in camelSpringApplicationContext.xml
    @Around("profile()")
    public Object doProfiling(ProceedingJoinPoint pjp) throws Throwable {
        LOG.info("**** aop profiling");
        Monitor mon = MonitorFactory.start(pjp.getSignature().toString());
        Object retVal = pjp.proceed();
        mon.stop();
        LOG.info("**** done profiling.  jamon=" + mon);
        return retVal;
    }

}
