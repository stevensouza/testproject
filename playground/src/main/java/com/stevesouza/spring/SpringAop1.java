package com.stevesouza.spring;

import org.apache.log4j.Logger;
import org.aspectj.lang.annotation.*;

/**
 * Created by stevesouza on 5/24/14.
 */
// Spring automatically finds.
@Aspect
public class SpringAop1 {
    private static final Logger LOG = Logger.getLogger(SpringAop1.class);


    @Pointcut("execution(* com.stevesouza.spring.MyAutowiredClass.getString(..))")
    // odd but must define empty method.  It isn't called.  it just lets us reuse the name 'theMethod' below
    // alternatively you could just put the above pointcut on each method.
    // note these annotations let this be a simple pojo.  cool
    public void theMethod() {
    }

     @Before("theMethod()")
     public void myFirstBeforeMethod() {
        System.out.println("myFirstBeforeMethod");
      }

     @Before("theMethod()")
      public void mySecondBeforeMethod() {
        System.out.println("mySecondBeforeMethod");
      }

    @AfterReturning("theMethod()")
    public void myAfterReturning() {
        System.out.println("myAfterReturning");
    }

    @AfterThrowing("theMethod()")
    public void myAfterThrowingException() {
        System.out.println("myAfterThrowingException");
    }

}
