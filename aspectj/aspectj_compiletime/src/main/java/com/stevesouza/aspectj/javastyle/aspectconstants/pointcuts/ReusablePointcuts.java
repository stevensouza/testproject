package com.stevesouza.aspectj.javastyle.aspectconstants.pointcuts;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

/**
 * Created by stevesouza on 2/28/15.
 */
@Aspect
public class ReusablePointcuts {
    // It can be referred to in other aspects as ...ReusablePointcuts.myOtherMethodPointcut() even though it isn't
    // a static method.
    @Pointcut("execution(* *.myOtherMethod(..))")
    public void myOtherMethodPointcut() {

    }
}
