package com.stevesouza.aspectj.javastyle.unkinded.various;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

/**
 * Aspect that advise field sets and gets.
 *
 * For example:
 *  set:  obj.var="hi";
 *  get:  System.out.println(obj.var);
 *  get&set:  obj.var1=obj.var;
 *
 */
@Aspect
public class MyAspect  {

    @Pointcut("!preinitialization(*.new(..))")
    public void all() {

    }
    // note should be able to do this with just various, however with jdk 8 it gives byte code errors.
    // I originally tried cflow(execution(* MyClass.myMethod(..))) however it gives stack errors.  The following
    // is a work around that does the same thing.
    @Before("within(MyClass) && all()")
    public void myAdvice1(JoinPoint.StaticPart joinPoint) {
        System.out.println("  Before myAdvice1 "+joinPoint.getKind()+": "+joinPoint);
    }

    // note the above will have main(..) executed whereas 'this' won't
    @Before("this(MyClass) && execution(* *(..))")
    public void myAdvice2(JoinPoint.StaticPart joinPoint) {
        System.out.println("  Before myAdvice2 "+joinPoint.getKind()+": "+joinPoint);
    }

    // traces any direct calls in myMethod().  it doesn't track methods called by the methods that it calls.
    @Before("withincode(public void MyClass.myMethod())")
    public void myAdvice3(JoinPoint.StaticPart joinPoint) {
        System.out.println("  Before myAdvice3 "+joinPoint.getKind()+": "+joinPoint);
    }



}
