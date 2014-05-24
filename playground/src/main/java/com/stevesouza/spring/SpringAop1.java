package com.stevesouza.spring;

import org.apache.log4j.Logger;
import org.aspectj.lang.annotation.*;

/**
 * Created by stevesouza on 5/24/14.
 * Samples of aspectj pointcuts...
 *
 * Other examples
 *  execution(* com.aspects.pointcut.DemoClass.*(..)) :
 *      This advice will be applied to all the methods of DemoClass.
 *  execution(* DemoClass.*(..)):
 *      You can omit the package if the DemoClass and the advice is in the same package.
 *  execution(public * DemoClass.*(..)):
 *      This advice will be applied to the public methods of DemoClass.
 *  execution(public int DemoClass.*(..)):
 *      This advice will be applied to the public methods of DemoClass and returning an int.
 *  execution(public int DemoClass.*(int, ..)):
 *      This advice will be applied to the public methods of DemoClass and returning an int and having first parameter as int.
 *  execution(public int DemoClass.*(int, int)):
 *      This advice will be applied to the public methods of DemoClass and returning an int and having both parameters as int.
 */
// Spring automatically finds.
@Aspect
public class SpringAop1 {
    private static final Logger LOG = Logger.getLogger(SpringAop1.class);

//    @Pointcut("execution(* com.stevesouza.spring.MyAutowiredClass.getString(..))")

    // can remove class if the the class we are advising is in same package
    // @Pointcut("execution(* MyAutowiredClass.getString(..))")

    // Wildcards - all methods starting with get.
    @Pointcut("execution(* MyAutowiredClass.get*(..))")
    // odd but must define empty method.  It isn't called.  it just lets us reuse the name 'anyMethod' below
    // alternatively you could just put the above pointcut on each method.
    // note these annotations let this be a simple pojo.  cool
    public void anyMethod() {
    }

     @Before("anyMethod()")
     public void myFirstBeforeMethod() {
        System.out.println("myFirstBeforeMethod");
      }

     @Before("anyMethod()")
      public void mySecondBeforeMethod() {
        System.out.println("mySecondBeforeMethod");
      }

    @AfterReturning("anyMethod()")
    public void myAfterReturning() {
        System.out.println("myAfterReturning");
    }

    @AfterThrowing("anyMethod()")
    public void myAfterThrowingException() {
        System.out.println("myAfterThrowingException");
    }

}
