package com.stevesouza.aspectj.javastyle.context;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.CodeSignature;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class MethodArgumentExtractor {
    /**
     * This method extracts method arguments and their values from the JoinPoint
     * and returns a read-only map containing them.
     *
     * @param thisJoinPoint The JoinPoint object containing the execution context
     * @return A read-only map of argument names to their corresponding values
     */
    public static Map<String, Object> extractArguments(JoinPoint thisJoinPoint) {
        CodeSignature signature = ((CodeSignature) thisJoinPoint.getSignature());
        String[] argNames = signature.getParameterNames();
        Object[] argValues = thisJoinPoint.getArgs();

        // Create a HashMap and populate it with argument names and values
        Map<String, Object> argumentsMap = new HashMap<>(argNames.length);
        for (int i = 0; i < argNames.length; i++) {
            argumentsMap.put(argNames[i], argValues[i]);
        }

        // Return a read-only version of the map for efficiency
        return Collections.unmodifiableMap(argumentsMap);
    }
}
