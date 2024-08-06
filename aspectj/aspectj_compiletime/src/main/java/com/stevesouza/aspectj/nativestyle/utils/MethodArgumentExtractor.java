package com.stevesouza.aspectj.nativestyle.utils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.CodeSignature;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class MethodArgumentExtractor {
    /**
     * This method extracts method arguments and their values from the JoinPoint
     * and returns a read-only map containing them.
     * Terminology Clarification:
     *
     * Parameter (Formal Parameter): A variable declared in the method signature that acts as a placeholder for the value that will be passed in during the method call.
     * Argument (Actual Parameter): The actual value (data) that is passed into the method when it is called. This value is assigned to the corresponding parameter.
     *
     * @param thisJoinPoint The JoinPoint object containing the execution context
     * @return A read-only map of argument names to their corresponding values
     */
    public static Map<String, Object> toMap(JoinPoint thisJoinPoint) {
        // https://javadoc.io/doc/org.aspectj/aspectjweaver/1.8.11/org/aspectj/lang/reflect/CodeSignature.html
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

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static String toJson(JoinPoint thisJoinPoint) {
        try {
            return objectMapper.writeValueAsString(toMap(thisJoinPoint));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    /*
    import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.CodeSignature;
import java.util.HashMap;
import java.util.Arrays;

public class ParameterMapper {
    **
     * Creates a HashMap mapping parameter names to their corresponding values,
     * handling varargs and potential mismatches in parameter/value lengths.
     *
     * @param joinPoint The JoinPoint representing the method execution.
     * @return A HashMap where keys are parameter names and values are the argument values.
     *
    public static HashMap<String, Object> mapParameters(JoinPoint joinPoint) {
        HashMap<String, Object> parameterMap = new HashMap<>();
        CodeSignature signature = (CodeSignature) joinPoint.getSignature();

        // Get parameter names
        String[] argNames = signature.getParameterNames();
        // Get argument values
        Object[] argValues = joinPoint.getArgs();

        // Handle potential mismatches in length due to varargs or proceeding join points.
        int numParamsToProcess = Math.min(argNames.length, argValues.length);

        // Iterate and populate the map, handling varargs specially
        for (int i = 0; i < numParamsToProcess; i++) {
            if (argValues[i] != null && argValues[i].getClass().isArray() && i == argNames.length - 1) {
                // Handle varargs as a list
                parameterMap.put(argNames[i], Arrays.asList((Object[]) argValues[i]));
            } else {
                parameterMap.put(argNames[i], argValues[i]);
            }
        }

        return parameterMap;
    }
}

     */
}
