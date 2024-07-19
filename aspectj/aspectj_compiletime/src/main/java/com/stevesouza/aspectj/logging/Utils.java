package com.stevesouza.aspectj.logging;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.reflect.CodeSignature;
import java.util.*;

/**
 * Static utility methods used throughout Automon.
 */
public class Utils {

    public static final String LINE_SEPARATOR = "\n";
    /**
     * Returned when a method parameter is null
     */
    static final String NULL_STR = "<null>";

    /**
     * used when a value isn't returned, and yet we don't want to throw an exception (hey it's just monitoring)
     */
    static final String UNKNOWN = "???";


    /**
     * Maximum length for a parameter in the exception dump
     */
    static final int DEFAULT_ARG_STRING_MAX_LENGTH = 125;

    /**
     * Parameters kept in the details section are capped at a max length and this string is put at the end of
     * the string after the truncation point to indicate there is more data that is not shown.
     */
    static final String DEFAULT_MAX_STRING_ENDING = "...";



    /**
     * <p>If appropriate for the given {@link JoinPoint} return argument names and values
     * in a list of Strings.</p>
     *
     * <p>Example for the following method - printName(String fname, String lname)<br>
     * fname: Steve<br>
     * lname: Souza<br>
     * </p>
     *
     * <p>Under certain circumstances for example if the code was compiled without retaining parameters a
     * parameter name of the format arg0 or even 0 might appear.</p>
     *
     * @param jp
     * @return A list containing strings of the format: argName: argValue
     */
    public static List<String> getArgNameValuePairs(JoinPoint jp) {
        List<String> list = new ArrayList<String>();
        Object[] argValues = jp.getArgs();
        if (argValues == null || argValues.length == 0) {
            return list;
        }

        Object[] argNames = getParameterNames(argValues, jp);
        for (int i = 0; i < argValues.length; i++) {
            String argName = (argNames[i] == null) ? "" + i : argNames[i].toString();
            list.add("" + argName + ": " + toStringWithLimit(argValues[i]));
        }

        return list;
    }

    /**
     * Convert a list to a formatted string.
     *
     * @param args assumed to be parameter key value pairs
     * @return String representing the argName, argValue pairs
     */
    public static String argNameValuePairsToString(List<String> args) {
        if (args == null) {
            return UNKNOWN;
        }

        StringBuilder sb = new StringBuilder();
        sb.append("=== Parameters ===").append(LINE_SEPARATOR);
        for (String str : args) {
            sb.append(str).append(LINE_SEPARATOR);
        }
        return sb.append(LINE_SEPARATOR).toString();
    }

    /**
     * Turns a single method parameter into a string. To keep
     * the functionality safe we truncate overly long strings and
     * ignore any exceptions.
     */
    public static String toStringWithLimit(Object parameter) {
        if (parameter == null) {
            return NULL_STR;
        }

        String value = parameter.toString();
        try {
            if (value.length() > DEFAULT_ARG_STRING_MAX_LENGTH) {
                value = value.substring(0, DEFAULT_ARG_STRING_MAX_LENGTH) + DEFAULT_MAX_STRING_ENDING;
            }
            return value;
        } catch (Throwable e) {
            return UNKNOWN;
        }
    }




    // used to get method argvalues from the method signature.
    private static Object[] getParameterNames(Object[] argValues, JoinPoint jp) {
        Signature signature = jp.getSignature();
        if (signature instanceof CodeSignature) {
            return ((CodeSignature) signature).getParameterNames();
        } else {
            return new Object[argValues.length];
        }
    }


}
