package com.stevesouza.aspectj.javastyle.unkinded;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

/**
 * Has good example of withincode
 *
 * Note kinded are pointcuts that match on signatures.  Unkinded are all that do not
 * match on signature.
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
    @Before("within(com.stevesouza.aspectj.javastyle.unkinded.MyClass) && all()")
    public void myAdvice1(JoinPoint.StaticPart joinPoint) {
        System.out.println();
        System.out.println("  Before myAdvice1 "+joinPoint.getKind()+": "+joinPoint);
    }

    // note the above will have main(..) executed whereas 'this' won't
    @Before("this(com.stevesouza.aspectj.javastyle.unkinded.MyClass) && execution(* *(..))")
    public void myAdvice2(JoinPoint.StaticPart joinPoint) {
        System.out.println("  Before myAdvice2 "+joinPoint.getKind()+": "+joinPoint);
    }

    // traces any direct calls in myMethod().  it doesn't track methods called by the methods that it calls.
    @Before("withincode(public void com.stevesouza.aspectj.javastyle.unkinded.MyClass.myMethod())")
    public void myAdvice3(JoinPoint.StaticPart joinPoint) {
        System.out.println("  Before myAdvice3 "+joinPoint.getKind()+": "+joinPoint);
    }



}
