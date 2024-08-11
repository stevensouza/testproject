package com.stevesouza.aspectj.logging.automon;

import com.stevesouza.aspectj.nativestyle.utils.ParameterExtractor;
import org.aspectj.lang.JoinPoint;
import org.slf4j.MDC;
import org.slf4j.NDC;

import java.util.UUID;

/**
 * LogTracingHelper provides utility methods for managing logging context in AspectJ.
 * It uses SLF4J's MDC (Mapped Diagnostic Context) and NDC (Nested Diagnostic Context)
 * to store and remove various pieces of information related to application execution.
 *
 * Note this class is thread safe due to its use of the thread safe NDC, and MDC which
 * stores unique information for each logging thread. Note all values in NDC and MDC must be
 * cleared at the end of an invocation process.
 *
 * One singleton instance of this class will suffice for all loggers in the application due to the
 * use of MDC, and NDC and so this class uses the singleton pattern to disallow direct creation of an
 * instance.
 */
public class LogTracingHelper {

    private static final String PARAMETERS = "parameters";
    private static final String EXECUTION_TIME_MS = "executionTimeMs";
    private static final String REQUEST_ID = "requestId";
    private static final String KIND = "kind";
    private static final String THIS = "this";
    private static final String TARGET = "target";
    private static final String ENCLOSING_SIGNATURE = "enclosingSignature";

    // Private static instance of the class, initialized only once
    private static LogTracingHelper helper = new LogTracingHelper();

    /**
     * Private constructor to prevent external instantiation.
     */
    private LogTracingHelper() {
    }

    /**
     * Public static method to get the single instance of the class.
     * @return The single instance of LogTracingHelper.
     */
    public static LogTracingHelper getInstance() {
        return helper;
    }

    /**
     * Adds method parameters to the MDC.
     * Example MDC entry: "parameters" : "{arg0=John, arg1=30}"
     *
     * @param joinPoint The JoinPoint representing the intercepted method call
     * @return This LogTracingHelper instance
     */
    public LogTracingHelper withParameters(JoinPoint joinPoint) {
        MDC.put(PARAMETERS, ParameterExtractor.toMap(joinPoint).toString());
        return this;
    }


    /**
     * Pushes the method signature to the NDC stack.
     * Example NDC entry: "com.example.Service.doSomething(..)"
     *
     * @param joinPointStaticPart The static part of the JoinPoint
     * @return This LogTracingHelper instance
     */
    public LogTracingHelper withSignature(JoinPoint.StaticPart joinPointStaticPart) {
        NDC.push(joinPointStaticPart.getSignature().toShortString());
        return this;
    }


    /**
     * Adds the enclosing method signature to the MDC.
     * Example MDC entry: "enclosingSignature" : "com.example.Service.processRequest(..)"
     *
     * @param joinPointStaticPart The static part of the JoinPoint
     * @return This LogTracingHelper instance
     */
    public LogTracingHelper withEnclosingSignature(JoinPoint.StaticPart joinPointStaticPart) {
        MDC.put(ENCLOSING_SIGNATURE, joinPointStaticPart.getSignature().toShortString());
        return this;
    }

    /**
     * Adds execution time to the MDC.
     * Note: This method currently uses a random value and should be replaced with actual timing logic.
     * Example MDC entry: "executionTimeMs" : "42"
     *
     * @return This LogTracingHelper instance
     */
    public LogTracingHelper withExecutionTime(int milliseconds) {
        MDC.put(EXECUTION_TIME_MS, String.valueOf(milliseconds));
        return this;
    }

    /**
     * Adds a unique request ID to the MDC.
     * Example MDC entry: "requestId" : "550e8400-e29b-41d4-a716-446655440000"
     *
     * @return This LogTracingHelper instance
     */
    public LogTracingHelper withRequestId() {
        MDC.put(REQUEST_ID, UUID.randomUUID().toString());
        return this;
    }

    /**
     * Adds the join point kind to the MDC.
     * Example MDC entry: "kind" : "method-execution"
     *
     * @param joinPointStaticPart The static part of the JoinPoint
     * @return This LogTracingHelper instance
     */
    public LogTracingHelper withKind(JoinPoint.StaticPart joinPointStaticPart) {
        MDC.put(KIND, joinPointStaticPart.getKind());
        return this;
    }


    /**
     * Adds the string representation of 'this' object to the MDC.
     * Example MDC entry: "this" : "com.example.Service@3f8f9dd6"
     *
     * @param joinPoint The JoinPoint representing the intercepted method call
     * @return This LogTracingHelper instance
     */
    public LogTracingHelper withThis(JoinPoint joinPoint) {
        MDC.put(THIS, objectToString(joinPoint.getThis()));
        return this;
    }

    /**
     * Adds the string representation of the target object to the MDC.
     * Example MDC entry: "target" : "com.example.ServiceImpl@1a2b3c4d"
     *
     * @param joinPoint The JoinPoint representing the intercepted method call
     * @return This LogTracingHelper instance
     */
    public LogTracingHelper withTarget(JoinPoint joinPoint) {
        MDC.put(TARGET, objectToString(joinPoint.getTarget()));
        return this;
    }


    /**
     * Removes the target object representation from the MDC.
     *
     * @return This LogTracingHelper instance
     */
    public LogTracingHelper removeTarget() {
        MDC.remove(TARGET);
        return this;
    }

    /**
     * Removes method parameters from the MDC.
     *
     * @return This LogTracingHelper instance
     */
    public LogTracingHelper removeParameters() {
        MDC.remove(PARAMETERS);
        return this;
    }

    /**
     * Removes the top element from the NDC stack.
     *
     * @return This LogTracingHelper instance
     */
    public LogTracingHelper removeSignature() {
        NDC.pop();
        return this;
    }

    /**
     * Removes the enclosing method signature from the MDC.
     *
     * @return This LogTracingHelper instance
     */
    public LogTracingHelper removeEnclosingSignature() {
        MDC.remove(ENCLOSING_SIGNATURE);
        return this;
    }

    /**
     * Removes the execution time from the MDC.
     *
     * @return This LogTracingHelper instance
     */
    public LogTracingHelper removeExecutionTime() {
        MDC.remove(EXECUTION_TIME_MS);
        return this;
    }

    /**
     * Removes the request ID from the MDC.
     *
     * @return This LogTracingHelper instance
     */
    public LogTracingHelper removeRequestId() {
        MDC.remove(REQUEST_ID);
        return this;
    }

    /**
     * Removes the join point kind from the MDC.
     *
     * @return This LogTracingHelper instance
     */
    public LogTracingHelper removeKind() {
        MDC.remove(KIND);
        return this;
    }

    /**
     * Removes 'this' object representation from the MDC.
     *
     * @return This LogTracingHelper instance
     */
    public LogTracingHelper removeThis() {
        MDC.remove(THIS);
        return this;
    }


    private String objectToString(Object obj) {
        return obj == null ? "null" : obj.toString();
    }
}
