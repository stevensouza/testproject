package com.stevesouza.aspectj.javastyle.afterthrowing;

import com.stevesouza.aspectj.nativestyle.utils.ParameterExtractor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;


/**
 * Aspect that advises exceptions being thrown
 */

@Aspect
public class MyAspect {

    private final ThreadLocal<Throwable> lastLoggedException = new ThreadLocal<>();

    //@Pointcut("(execution (* *.*(..)) || execution (*.new(..)) || initialization(*.new(..)) ||  staticinitialization(*))  && !within(MyAspect+)  && !cflow(adviceexecution())")
    // note preinitialization gives bytecode errors and that is why it was excluded.
    @Pointcut("within(com.stevesouza.aspectj.javastyle.afterthrowing.MyClass)")
    public void all() {

    }

//    @Pointcut("all()  && !within(com.stevesouza.aspectj.javastyle.afterthrowing.MyAspect+) && !cflow(adviceexecution()) && !handler(*)")
    @Pointcut("all() && !handler(*)")
    public void myExceptions() {
    }


    // threadlocal won't work as it never removes the stack trace for a given thread. But the concept is to
    // store the most recent exception and not store it again.
    // @AfterThrowing is called only after an exception is thrown.
    //  p 275 aspectj book
    // note this aspect will catch any exceptions thrown in
    //  Constructors
    //  In method calls
    //  NPE in code above with info such as what arguments were passed
    @AfterThrowing(pointcut = "myExceptions()", throwing = "e")
    public void myAfterThrowing(JoinPoint joinPoint, Throwable e) {
        if (lastLoggedException.get() != e) {
            lastLoggedException.set(e);
            System.out.println("In aspect/advice:");
            System.out.println(" Exception: " + e);
            System.out.println(" Exception: " + e.getClass().getName());

            System.out.println(" jp.getKind()=" + joinPoint.getKind());
            System.out.println(" jp.getStaticPart()=" + joinPoint.getStaticPart());
            Signature signature = joinPoint.getSignature();
            System.out.println(" jp.getSignature().getClass()=" + signature.getClass());
            // Note would have to look at all the special cases here.
            if (signature instanceof MethodSignature) {
                System.out.println(" method param/value:"+ ParameterExtractor.toJson(joinPoint));
            }
        }

    }


}
