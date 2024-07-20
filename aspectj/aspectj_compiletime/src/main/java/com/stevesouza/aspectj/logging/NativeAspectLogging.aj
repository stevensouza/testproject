package com.stevesouza.aspectj.logging;

import com.stevesouza.aspectj.nativestyle.utils.MethodArgumentExtractor;
import org.slf4j.*;


/**
 Tests call, withincode (any pointcut directly in a method), cflow (any pointcuts directly in the method as well as any pointcuts in the
 code that any of the methods call too).
 */
public aspect NativeAspectLogging {

    // Note within does all pointcuts within the class including this and static and constructors etc.
    // this() would not do static methods but only instance ones.
    pointcut loggingInfo(String message): within(MyLoggerClass) && call(* org.slf4j.Logger.info(String)) && args(message);


    before(String message): loggingInfo(message) {
        NDC.push(thisEnclosingJoinPointStaticPart.getSignature().toShortString());
        MDC.put("loggingMessage", MethodArgumentExtractor.toJson(thisJoinPoint));
        System.out.println("\nBefore Logger.info call: " + thisJoinPoint);
        // this will display info on what method called call(myMethod3())
        System.out.println(" thisEnclosingJoinPointStaticPart: " + thisEnclosingJoinPointStaticPart);
        System.out.println(" thisEnclosingJoinPointStaticPart.getSignature().toShortString(): " + thisEnclosingJoinPointStaticPart.getSignature().toShortString());
        System.out.println(" loggingMessage="+message);
    }

    after(String message): loggingInfo(message) {
        NDC.pop();
        MDC.remove("loggingMessage");
    }
}
