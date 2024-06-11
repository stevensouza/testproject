package com.stevesouza.aspectj.javastyle.method;

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
public class MyAspect extends AbstractAspect {

    // Wildcards - constructor methods starting with get.
    //    @Pointcut("within(com.stevesouza.aspectj.javastyle.method.MyClass)  && method()")
    //    @Pointcut("within(com.stevesouza.aspectj.javastyle.method.MyClass)  && privateMethod()")
    //    @Pointcut("within(com.stevesouza.aspectj.javastyle.method.MyClass)  && protectedMethod()")

    // traces methods defined in the specified class only (both static and instance, but not inherited) and is further limited to
    //  public functions.
    // @Pointcut("within(com.stevesouza.aspectj.javastyle.method.MyClass)  && publicMethod()")

    // The following would do all instance methods both inherited and defined in this class as well
    // as with any class in the inheritance heirarchy.
    //  @Pointcut("within(com.stevesouza.aspectj.MyClassBase+)  && publicMethod()")

    // The following would do all methods in base class and all added methods in children including statics
    //  @Pointcut("within(com.stevesouza.aspectj.MyClassBase+)  && publicMethod()")

    // Only traces methods introduced in MyClass.  Not any parent mehtods.  Includes
    // static methods too (within()).   This would include main(..) for example.
    // @Pointcut("within(com.stevesouza.aspectj.javastyle.method.MyClass)  && publicMethod()")

    //pointcut traced() : within(MyClass)  && execution(* *(..));

    // The following traces  any instance methods defined in this class which includes parent methods that
    // are inherited.  However it doesn't include any static methods.

    // The following would do all instance methods both inherited and defined in this class
    @Override
    @Pointcut("this(com.stevesouza.aspectj.javastyle.method.MyClass)  && publicMethod()")
    public void traced() {
    }


}
