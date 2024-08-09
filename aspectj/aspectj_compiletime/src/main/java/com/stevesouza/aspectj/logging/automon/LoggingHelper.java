package com.stevesouza.aspectj.logging.automon;

import com.stevesouza.aspectj.nativestyle.utils.ParameterExtractor;
import org.aspectj.lang.JoinPoint;
import org.slf4j.MDC;
import org.slf4j.NDC;

import java.util.UUID;

public class LoggingHelper {

    private static final String PARAMETERS = "parameters";
    private static final String EXECUTION_TIME_MS = "executionTimeMs";
    private static final String REQUEST_ID = "requestId";
    private static final String KIND = "kind";
    private static final String THIS = "this";
    private static final String TARGET = "target";

    public void putParameters(JoinPoint thisJoinPoint) {
        MDC.put(PARAMETERS, ParameterExtractor.toMap(thisJoinPoint).toString());
    }

    public void removeParameters() {
        MDC.remove(PARAMETERS);
    }

    public void putSignature(JoinPoint.StaticPart thisJoinPointStaticPart) {
//       orriginally i used  enclosing JoinPointStaticPart not sure what to use
//            System.out.println(thisJoinPointStaticPart);
//            System.out.println(thisEnclosingJoinPointStaticPart);
// also not sure if i should just use method name or make it more generic?
        NDC.push(thisJoinPointStaticPart.getSignature().toShortString());
    }

    public void removeSignature() {
        NDC.pop();
    }

    public void putEnclosingSignature(JoinPoint.StaticPart thisJoinPointStaticPart) {
        MDC.put("enclosingSignature", thisJoinPointStaticPart.getSignature().toShortString());
    }

    public void removeEnclosingSignature() {
        NDC.pop();
    }

    public void putExecutionTime() {
        MDC.put(EXECUTION_TIME_MS, String.valueOf((int) (Math.random() * 1000)));
    }

    public void removeExecutionTime() {
        MDC.remove(EXECUTION_TIME_MS);
    }

    public void putRequestId() {
        MDC.put(REQUEST_ID, UUID.randomUUID().toString());
    }

    public void removeRequestId() {
        MDC.remove(REQUEST_ID);
    }

    public void putKind(JoinPoint.StaticPart thisJoinPointStaticPart) {
        MDC.put(KIND, thisJoinPointStaticPart.getKind());
    }

    public void removeKind() {
        MDC.remove(KIND);
    }

    public void putThis(JoinPoint thisJoinPoint) {
        Object obj = thisJoinPoint.getThis();
        String valueString = obj == null ? "null" : obj.toString();
        MDC.put(THIS, valueString);
    }

    public void removeThis() {
        MDC.remove(THIS);
    }

    public void putTarget(JoinPoint thisJoinPoint) {
        Object obj = thisJoinPoint.getTarget();
        String valueString = obj == null ? "null" : obj.toString();
        MDC.put(TARGET, valueString);
    }

    public void removeTarget() {
        MDC.remove(TARGET);
    }
}
