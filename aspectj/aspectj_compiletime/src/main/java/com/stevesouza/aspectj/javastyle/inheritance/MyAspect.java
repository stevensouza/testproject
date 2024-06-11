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

    // The following will work for any methods defined in MyClass and children but not any methods
    // defined only in the children (new methods in children).
    // Note it will include static methods such as 'main'
    // Note MyClass works for only MyClass in this package and so the full classpath is
    // not required.
    @Before("execution(* MyClass.*(..))")
    public void myAdvice1(JoinPoint.StaticPart joinPoint) {
        System.out.println("  MyClass "+joinPoint.getKind()+": "+joinPoint);
    }

    // The following will work for both methods defined in MyClass as well as any new methods defined in
    // its children (MyClass+). Note it will include static methods.
    // The commented out @Before gets rid of static ie. main()
    //   @Before("execution(* MyClass+.*(..)) && this(MyClass)")
    @Before("execution(* MyClass+.*(..))")
    public void myAdvice2(JoinPoint.StaticPart joinPoint) {
        System.out.println("  MyClass+ "+joinPoint.getKind()+": "+joinPoint);
    }

}
