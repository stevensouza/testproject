package com.stevesouza.aspectj.javastyle.inheritance;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

/**
 * Aspect that tests Class+ and Class
 *

 */
@Aspect
public class MyAspect  {

     @Before("execution(* MyClass.*(..))")
    public void myAdvice1(JoinPoint.StaticPart joinPoint) {
        System.out.println("  MyClass "+joinPoint.getKind()+": "+joinPoint);
    }

    @Before("execution(* MyClass+.*(..))")
    public void myAdvice2(JoinPoint.StaticPart joinPoint) {
        System.out.println("  MyClass+ "+joinPoint.getKind()+": "+joinPoint);
    }

}
