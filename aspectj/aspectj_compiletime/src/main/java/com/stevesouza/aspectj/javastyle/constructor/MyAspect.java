package com.stevesouza.aspectj.javastyle.constructor;

import com.stevesouza.aspectj.javastyle.AbstractAspect;
import org.aspectj.lang.annotation.Aspect;
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
public class MyAspect extends AbstractAspect {

    // constructors within the given class and protected
    //  @Pointcut("within(com.stevesouza.aspectj.javastyle.constructor.MyClass) && protectedConstructor()")

    // constructors within the given class and of any protection level
    @Pointcut("within(com.stevesouza.aspectj.javastyle.constructor.MyClass) && constructor()")
    public void traced() {
    }


}
