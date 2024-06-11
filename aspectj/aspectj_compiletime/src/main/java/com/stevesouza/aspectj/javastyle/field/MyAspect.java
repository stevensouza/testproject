package com.stevesouza.aspectj.javastyle.field;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;

import static java.lang.System.out;

/**
 * Aspect that advise field sets and gets.
 * <p>
 * For example:
 * set:  obj.var="hi";
 * get:  System.out.println(obj.var);
 * get&set:  obj.var1=obj.var;
 */
@Aspect
public class MyAspect {

    // Some other interesting field pointcuts
    //     @After("set(public * MyClass+.*)")
    //     @After("set(public double MyClass+.*)")
    //     @After("set(public double MyClass+.balance*)")
    //     @After("set(public double *.balance)")

    @After("set(* com.stevesouza.aspectj.javastyle.field.MyClass+.*)")
    public void setAdvice(JoinPoint joinPoint) {
        out.println();
        out.println("  After set: joinPoint.getKind()=" + joinPoint.getKind());
        out.println("  After set: joinPoint.toLongString()=" + joinPoint.toLongString());
        out.println("  After set: joinPoint.getTarget()=" + joinPoint.getTarget());
        out.println("  After set: joinPoint.getArgs()[0] (value assigning)=" + joinPoint.getArgs()[0]);
    }


    @After("get(* com.stevesouza.aspectj.javastyle.field.MyClass+.*)")
    public void getAdvice(JoinPoint joinPoint) {
        out.println();
        out.println("  After get: joinPoint.getKind()=" + joinPoint.getKind());
        out.println("  After get: joinPoint.toLongString()=" + joinPoint.toLongString());
        out.println("  After get: joinPoint.getTarget()=" + joinPoint.getTarget());
    }


}
