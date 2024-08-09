package com.stevesouza.aspectj.nativestyle.context;


/**
 Tests context related standard aspectj variables to help understand what their values are.
 */
public aspect NativeAspectContext {
    private static int id = 0;

    before(): within(com.stevesouza.aspectj.nativestyle.context.MyClass) {
        System.out.println(String.format("%d thisJoinPointStaticPart.getKind(): %s", id, thisJoinPointStaticPart.getKind()));
        System.out.println(String.format("%d thisJoinPoint: %s", id, thisJoinPoint));
        System.out.println(String.format("%d thisJoinPointStaticPart: %s", id, thisJoinPointStaticPart));
        System.out.println(String.format("%d thisJoinPointStaticPart.toShortString(): %s", id, thisJoinPointStaticPart.toShortString()));
        System.out.println(String.format("%d thisEnclosingJoinPointStaticPart: %s", id, thisEnclosingJoinPointStaticPart));
        System.out.println(String.format("%d getThis(): %s", id, thisJoinPoint.getThis()));
        System.out.println(String.format("%d getTarget(): %s", id, thisJoinPoint.getTarget()));
        System.out.println(String.format("%d thisJoinPoint.getSignature(): %s", id, thisJoinPoint.getSignature()));
        System.out.println(String.format("%d thisJoinPoint.getSignature().getClass(): %s", id, thisJoinPoint.getSignature().getClass()));
        System.out.println(String.format("%d thisJoinPoint.getSignature().toShortString(): %s", id, thisJoinPoint.getSignature().toShortString()));
        // repeat this at the end for better understanding.
        System.out.println(String.format("%d thisJoinPointStaticPart.getKind(): %s", id, thisJoinPointStaticPart.getKind()));
        System.out.println();
        id++;


//        NDC.push(thisEnclosingJoinPointStaticPart.getSignature().toShortString());
//        MDC.put("loggingMessageJson", MethodArgumentExtractor.toJson(thisJoinPoint));
//        MDC.put("loggingMessageMap", MethodArgumentExtractor.toMap(thisJoinPoint).toString());
//        System.out.println("\nBefore Logger.info call: " + thisJoinPoint);
//        // this will display info on what method called call(myMethod3())
//        System.out.println(" thisEnclosingJoinPointStaticPart: " + thisEnclosingJoinPointStaticPart);
//        System.out.println(" thisEnclosingJoinPointStaticPart.getSignature().toShortString(): " + thisEnclosingJoinPointStaticPart.getSignature().toShortString());
//        System.out.println(" loggingMessage="+message);
    }


}
