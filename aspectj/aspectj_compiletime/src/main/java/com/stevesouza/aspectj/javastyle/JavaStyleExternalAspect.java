package com.stevesouza.aspectj.javastyle;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

/**
 * aspectj compile time weaving using ajc
 *  cd /Users/stevesouza/gitrepo/testproject/playground/src/main/javastyle
 *  ajc -source 5 com/stevesouza/aspectj/*.javastyle
 *  javastyle com.stevesouza.aspectj.MessageCommunicator
 *
 * for nonaspectj version:
 *  javac com/stevesouza/aspectj/*.javastyle
 *
 */
@Aspect
public class JavaStyleExternalAspect {
    private int callDepth;

    // Wildcards - constructor methods starting with get.
    // @Pointcut("!within(JavaStyleAspect) && execution(* *(..))")
    @Pointcut("!within(com.stevesouza..*)")
    public void traced() {
    }

    @Before("traced()")
    // note use of JoinPoint to get further info here
    public void before(JoinPoint thisJoinPoint) {
        print("Before", thisJoinPoint);
        callDepth++;
    }


    @After("traced()")
    // note use of JoinPoint to get further info here
    public void after(JoinPoint thisJoinPoint) {
        print("After", thisJoinPoint);
        callDepth--;
    }


    private void print(String prefix, Object message) {
        for (int i=0; i<callDepth;i++) {
            System.out.print(" ");
        }
        System.out.println(prefix+"("+getClass().getSimpleName()+ "):"+message);
    }
}
