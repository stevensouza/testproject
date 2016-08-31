package com.stevesouza.aspectj.javastyle.afterthrowing;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Aspect that advises exceptions being thrown
 *
 */

@Aspect
public class MyAspect  {

  private ThreadLocal<Throwable> lastLoggedException = new ThreadLocal<Throwable>();
    private Map m = Collections.synchronizedMap(new LinkedHashMap());

  //@Pointcut("(execution (* *.*(..)) || execution (*.new(..)) || initialization(*.new(..)) ||  staticinitialization(*))  && !within(MyAspect+)  && !cflow(adviceexecution())")
  // note preinitialization gives bytecode errors and that is why it is excluded.
    @Pointcut("!preinitialization(*.new(..))")
  public void all() {

  }

    @Pointcut("all()  && !within(MyAspect+) && !cflow(adviceexecution())")
    public void myExceptions() {
    }


    // threadlocal won't work as it never removes the stack trace for a given thread.
    // p 275 aspectj book
    @AfterThrowing(pointcut = "myExceptions()", throwing = "e")
    public void myAfterThrowing(JoinPoint joinPoint, Throwable e) {
        if (lastLoggedException.get()!=e) {
            lastLoggedException.set(e);
            System.out.println();
            System.out.println("Exception: "+e);
            System.out.println("Exception: "+e.getClass().getName());

            System.out.println(" jp.getKind()=" + joinPoint.getKind());
            System.out.println(" jp.getStaticPart()="+joinPoint.getStaticPart());
            Object[] argValues = joinPoint.getArgs();
            Signature signature = joinPoint.getSignature();
            System.out.println(" jp.getSignature().getClass()="+signature.getClass());
            // Note would have to look at all the special cases here.
            if (signature instanceof MethodSignature) {
                MethodSignature methodSignature =  (MethodSignature) signature;
                String[] argNames = methodSignature.getParameterNames();
                for (int i = 0; i < argNames.length; i++) {
                    printMe("  argName, argValue", argNames[i] + ", " + argValues[i]);
                }
            }
        }

    }



    private void printMe(String type, Object message) {
        System.out.println(" "+type + " : " + message);
    }




}
