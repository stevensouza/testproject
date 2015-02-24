package com.stevesouza.aspectj.javastyle.context;

import com.stevesouza.aspectj.javastyle.AbstractAspect;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

/**
 * Aspect that shows various ways to do context.
 *
 *
 */
@Aspect
public class MyAspect {

    @AfterReturning(value = "execution(* com.stevesouza.aspectj.javastyle.context.MyClass.myMethod(int, String))")
    public void afterReturning(JoinPoint thisJoinPoint, JoinPoint.StaticPart joinPointStaticPart, JoinPoint.EnclosingStaticPart joinPointEnclosingStaticPart) {
        // Example: thisJoinPoint.toString() : execution(void com.stevesouza.aspectj.javastyle.context.MyClass.myMethod(int, String))
        printMe("thisJoinPoint.toString()", thisJoinPoint.toString());
        printMe("thisJoinPoint.toLongString()", thisJoinPoint.toLongString());
        printMe("thisJoinPoint.toShortString()", thisJoinPoint.toShortString());

        System.out.println();
        printMe("thisJoinPoint.getKind()", thisJoinPoint.getKind());

        System.out.println();
        System.out.println(" Method args");
        // to have argNames on you have to compile with -g
        // I hear in maven it is by default:  using the compiler maven plugin, the debug is on by default
        MethodSignature signature = ((MethodSignature) thisJoinPoint.getSignature());

        Object[] argValues = thisJoinPoint.getArgs();
        String[] argNames = signature.getParameterNames();
        for (int i=0;i<argNames.length;i++) {
            printMe("  argName, argValue", argNames[i]+", "+argValues[i]);
        }

        System.out.println();
        printMe("thisJoinPoint.getSignature()", thisJoinPoint.getSignature());
        printMe("thisJoinPoint.getSignature().getDeclaringType()", thisJoinPoint.getSignature().getDeclaringType());
        printMe("thisJoinPoint.getSignature().getDeclaringTypeName()", thisJoinPoint.getSignature().getDeclaringTypeName());
        printMe("thisJoinPoint.getSignature().getModifiers()", thisJoinPoint.getSignature().getModifiers());
        printMe("thisJoinPoint.getSignature().getName()", thisJoinPoint.getSignature().getName());


        System.out.println();
        printMe("thisJoinPoint.getSourceLocation()", thisJoinPoint.getSourceLocation());
        printMe("thisJoinPoint.getSourceLocation().getFileName()", thisJoinPoint.getSourceLocation().getFileName());
        printMe("thisJoinPoint.getSourceLocation().getLine()", thisJoinPoint.getSourceLocation().getLine());
        printMe("thisJoinPoint.getSourceLocation()..getWithinType()", thisJoinPoint.getSourceLocation().getWithinType());

        System.out.println();
        printMe("joinPointStaticPart.toString()", joinPointStaticPart);
        printMe("joinPointStaticPart.getSignature()", joinPointStaticPart.getSignature());
        printMe("joinPointStaticPart.getId()", joinPointStaticPart.getId());
        printMe("joinPointStaticPart.getSourceLocation()", joinPointStaticPart.getSourceLocation());

        System.out.println();
        printMe("joinPointEnclosingStaticPart.toString()", joinPointEnclosingStaticPart);

    }

    private void printMe(String type, Object message) {
        System.out.println(" "+type + " : " + message);
    }

}
