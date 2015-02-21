package com.stevesouza.aspectj.javastyle.method.setters;

import com.stevesouza.aspectj.javastyle.AbstractAspect;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

/**
 * Aspect that allows you to all all methods, public, private, or protected
 *
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
public  class MyAspect extends AbstractAspect {

    // The following would do all instance methods both inherited and defined in this class
    @Pointcut("this(com.stevesouza.aspectj.javastyle.method.setters.MyClass)  && getterOrSetter()")
    public void traced() {
    }


}
