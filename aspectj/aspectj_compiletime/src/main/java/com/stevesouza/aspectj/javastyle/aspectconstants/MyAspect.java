package com.stevesouza.aspectj.javastyle.aspectconstants;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

/**
 * Aspect that tests Class+ and Class
 *

 */
@Aspect
public class MyAspect  {

    // Note even though myOtherPointCut() is not static it can be referenced that way.  Note defining the pointcut as a static
    // method also seemed to work.
     @Before("within(MyClass) && com.stevesouza.aspectj.javastyle.aspectconstants.pointcuts.ReusablePointcuts.myOtherMethodPointcut()")
    public void myAdvice1(JoinPoint.StaticPart joinPoint) {
        System.out.println("  MyClass "+joinPoint.getKind()+": "+joinPoint);
    }


}
