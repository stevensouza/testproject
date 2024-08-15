package com.stevesouza.aspectj.nativestyle.utils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.reflect.CodeSignature;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ParameterExtractor {
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
    public static Map<String, Object> toMap(JoinPoint joinPoint) {
        Map<String, Object> argsMap = new HashMap<>();
        Signature signature = joinPoint.getSignature();
        Object[] argValues = joinPoint.getArgs();

        // https://javadoc.io/doc/org.aspectj/aspectjweaver/1.8.11/org/aspectj/lang/reflect/CodeSignature.html
        if (signature instanceof CodeSignature) {
            CodeSignature codeSignature = (CodeSignature) signature;
            String[] parameterNames = codeSignature.getParameterNames();

            for (int i = 0; i < argValues.length; i++) {
                argsMap.put(parameterNames[i], argValues[i]);
            }
        } else {
            for (int i = 0; i < argValues.length; i++) {
                argsMap.put("param" + i, argValues[i]);
            }
        }

        // Return a read-only version of the map for efficiency
        return Collections.unmodifiableMap(argsMap);
    }

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static String toJson(JoinPoint thisJoinPoint) {
        try {
            return objectMapper.writeValueAsString(toMap(thisJoinPoint));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}
