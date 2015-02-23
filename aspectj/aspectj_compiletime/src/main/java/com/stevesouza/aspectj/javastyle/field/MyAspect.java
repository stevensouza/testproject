package com.stevesouza.aspectj.javastyle.field;

import com.stevesouza.aspectj.javastyle.AbstractAspect;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
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

    // Some other interesting field pointcuts
    //     @After("set(public * MyClass+.*)")
    //     @After("set(public double MyClass+.*)")
    //     @After("set(public double MyClass+.balance*)")
    //     @After("set(public double *.balance)")

    @After("set(* com.stevesouza.aspectj.javastyle.field.MyClass+.*)")
    public void setAdvice(JoinPoint joinPoint) {
        System.out.println("  After set: joinPoint.toLongString()="+joinPoint.toLongString());
        System.out.println("  After set: joinPoint.getTarget()="+joinPoint.getTarget());
        System.out.println("  After set: joinPoint.getArgs()[0] (value assigning)="+joinPoint.getArgs()[0]);
    }


    @After("get(* com.stevesouza.aspectj.javastyle.field.MyClass+.*)")
    public void getAdvice(JoinPoint joinPoint) {
        System.out.println("  After get: joinPoint.toLongString()="+joinPoint.toLongString());
        System.out.println("  After get: joinPoint.getTarget()="+joinPoint.getTarget());
    }


}
