package com.stevesouza.aspectj.javastyle.context;

import com.stevesouza.aspectj.nativestyle.utils.MethodArgumentExtractor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import java.text.MessageFormat;
import java.util.Map;


/**
 * Aspect that shows various ways to do context. Context means getting the values from
 * various variables such as instance (this()), target(), args()
 */
@Aspect
public class MyAspect {

    // Note in general a package is required for @AspectJ style pointbuts, but not when it is
    // in the same package as the aspect. Note this is an alternative to the anonymous pointcut
    // defined in the afterReturning1 advice.
    @Pointcut("execution(* MyClass.myMethod(int, String)) && this(myClass) && args(myInt, myString)")
    public void myMethodPointcut(MyClass myClass, int myInt, String myString) {
    }

    // afterReturning0 and afterReturning1 match the same pointcut however one uses a named pointcut while the
    // other uses an anonymous pointcut.
    @AfterReturning(value = "myMethodPointcut(myClass, myInt, myString)")
    public void afterReturning0(MyClass myClass, int myInt, String myString) {
        System.out.println("advice AfterReturning0 using myMethodPointcut(..)");
        String message = MessageFormat.format("MyClass instance={0}, myIntArg={1}, myStringArg={2}",
                myClass, myInt, myString);
        printMe(" this() and args() context:",message);
        System.out.println();
    }

    @AfterReturning(value = "execution(* com.stevesouza.aspectj.javastyle.context.MyClass.myMethod(int, String)) && this(myClass) && args(myInt, myString)")
    public void afterReturning1(MyClass myClass, int myInt, String myString) {
        System.out.println("advice AfterReturning1");
        String message = MessageFormat.format("MyClass instance={0}, myIntArg={1}, myStringArg={2}",
                myClass, myInt, myString);
        printMe(" this() and args() context:",message);
        System.out.println();
    }

    // same as above but uses the more dynamic approach.
    @AfterReturning(value = "execution(* com.stevesouza.aspectj.javastyle.context.MyClass.myMethod(int, String))")
    public void afterReturning2(JoinPoint thisJoinPoint, JoinPoint.StaticPart joinPointStaticPart, JoinPoint.EnclosingStaticPart joinPointEnclosingStaticPart) {
        // Example: thisJoinPoint.toString() : execution(void com.stevesouza.aspectj.javastyle.context.MyClass.myMethod(int, String))
        System.out.println();
        System.out.println("advice AfterReturning2");

        printMe("thisJoinPoint.toString()", thisJoinPoint.toString());
        printMe("thisJoinPoint.toLongString()", thisJoinPoint.toLongString());
        printMe("thisJoinPoint.toShortString()", thisJoinPoint.toShortString());

        printMe("thisJoinPoint.getKind()", thisJoinPoint.getKind());
        System.out.println(" -");

        System.out.println(" Method args/values");
        // to have argNames on you have to compile with -g
        // I hear in maven it is by default:  using the compiler maven plugin, the debug is on by default
        Map<String, Object> argMap = MethodArgumentExtractor.toMap(thisJoinPoint);
        System.out.println("  "+argMap);

        System.out.println(" -");

        printMe("thisJoinPoint.getSignature()", thisJoinPoint.getSignature());
        printMe("thisJoinPoint.getSignature().getDeclaringType()", thisJoinPoint.getSignature().getDeclaringType());
        printMe("thisJoinPoint.getSignature().getDeclaringTypeName()", thisJoinPoint.getSignature().getDeclaringTypeName());
        printMe("thisJoinPoint.getSignature().getModifiers()", thisJoinPoint.getSignature().getModifiers());
        printMe("thisJoinPoint.getSignature().getName()", thisJoinPoint.getSignature().getName());
        System.out.println(" -");

        printMe("thisJoinPoint.getSourceLocation()", thisJoinPoint.getSourceLocation());
        printMe("thisJoinPoint.getSourceLocation().getFileName()", thisJoinPoint.getSourceLocation().getFileName());
        printMe("thisJoinPoint.getSourceLocation().getLine()", thisJoinPoint.getSourceLocation().getLine());
        printMe("thisJoinPoint.getSourceLocation()..getWithinType()", thisJoinPoint.getSourceLocation().getWithinType());
        System.out.println(" -");

        printMe("joinPointStaticPart.toString()", joinPointStaticPart);
        printMe("joinPointStaticPart.getSignature()", joinPointStaticPart.getSignature());
        printMe("joinPointStaticPart.getId()", joinPointStaticPart.getId());
        printMe("joinPointStaticPart.getSourceLocation()", joinPointStaticPart.getSourceLocation());
        System.out.println(" -");

        printMe("joinPointEnclosingStaticPart.toString()", joinPointEnclosingStaticPart);

    }

    private void printMe(String type, Object message) {
        System.out.println(" " + type + " : " + message);
    }

}
