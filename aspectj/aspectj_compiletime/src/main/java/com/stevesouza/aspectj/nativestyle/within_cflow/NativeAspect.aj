package com.stevesouza.aspectj.nativestyle.within_cflow;

import java.util.*;

/**
 Tests call, withincode (any pointcut directly in a method), cflow (any pointcuts directly in the method as well as any pointcuts in the
 code that any of the methods call too).
 */
public aspect NativeAspect {

    // Note within does all pointcuts within the class including this and static and constructors etc.
    // this() would not do static methods but only instance ones.
    pointcut traced(): within(MyClass)  && call(* MyClass.myMethod3());
    // withincode has any direct pointcuts within myMethod1 body included, but not any pointcuts that are in
    // the methods invoked from it. cflow would also include the entire flow below that too.  It is not efficient
    // though withincode is according at least in aspectj book.
    pointcut withinMethod(): withincode(* MyClass.myMethod1(..));
    pointcut cflowMethod(): cflow(call(* MyClass.myMethod4(..))) && !within(NativeAspect);
    pointcut logging(String logStr): withincode(* MyClass.myMethod5(..)) && call(* java.io.PrintStream.println(String)) && args(logStr);


    before(): traced() {
        System.out.println("\nBefore call(): " + thisJoinPoint);
        // this will display info on what method called call(myMethod3())
        System.out.println(" thisEnclosingJoinPointStaticPart: " + thisEnclosingJoinPointStaticPart);
    }

    before(): withinMethod() {
        System.out.println("\nBefore withincode(): " + thisJoinPoint);
        // this will display info on what method called call(myMethod3())
        System.out.println(" thisEnclosingJoinPointStaticPart: " + thisEnclosingJoinPointStaticPart);
    }

    before(): cflowMethod() {
        System.out.println("\nBefore cflow(): " + thisJoinPoint);
        // this will display info on what method called call(myMethod3())
        System.out.println(" thisEnclosingJoinPointStaticPart: " + thisEnclosingJoinPointStaticPart);
    }

    after(): traced()  {
        System.out.println("After call(): " + thisJoinPoint);
        System.out.println(" thisEnclosingJoinPointStaticPart: " + thisEnclosingJoinPointStaticPart);
    }

    /**
     * Experimentation of what a tracking/logging aspect might look like using MDC/NDC.  Note this could work with
     * logging that doesn't support MDC/NDC for example System.out.println
     *
     * <PatternLayout pattern="[%d{ISO8601}] [%t] %X{MDC_KEY} %NDC %level: %msg%n"/>
     * This pattern places timestamp, thread name, MDC, NDC, log level, and then the message.
     *
     * In practice, placing the MDC/NDC information before the message is more common
     * and generally considered best practice (from Claude AI)
     */
    Object around(String logStr): logging(logStr) {
        System.out.println("\naround logging(): " + thisJoinPoint);
        // this will display info on what method called call(myMethod3())
        System.out.println(" thisEnclosingJoinPointStaticPart: " + thisEnclosingJoinPointStaticPart);

        System.out.println(" original logStr value: " + logStr);
        logStr = String.format("%s <NDC:%s; MDC:%s>",
                logStr.trim(), getNDC(thisEnclosingJoinPointStaticPart.getSignature().toShortString()), getMDC());
        return proceed(logStr);
    }

    private Map<String, Object> getMDC() {
        Map<String, Object> map = new HashMap<>();
        map.put("session_uuid", UUID.randomUUID().toString());
        map.put("request_uuid", UUID.randomUUID().toString());
        map.put("thread_id", Thread.currentThread().threadId());
        map.put("user", "ssouza");
        return map;
    }

    private List<String> getNDC(String ndc) {
        List<String> list = new ArrayList<>();
        list.add(ndc);
        return list;
    }

}
