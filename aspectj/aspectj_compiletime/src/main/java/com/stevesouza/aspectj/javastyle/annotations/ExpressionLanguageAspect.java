package com.stevesouza.aspectj.javastyle.annotations;

import java.lang.annotation.Annotation;
import java.lang.reflect.Parameter;
import java.text.MessageFormat;
import java.util.Arrays;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;


@Aspect
public class ExpressionLanguageAspect {

    //  this(MyClass) is done to exclude public static main(String[] args)
    @Around("this(MyClass) && execution(* *(..))")
    public Object doProfiling(ProceedingJoinPoint pjp) throws Throwable {

        MethodSignature ms = (MethodSignature) pjp.getSignature();
        Annotation[] annotations = ms.getMethod().getAnnotations();
        ExpressionLanguageAnnotation expressionAnnotation = ms.getMethod().getAnnotation(ExpressionLanguageAnnotation.class);
        Parameter[] parameters = ms.getMethod().getParameters();
        Annotation[][] paramAnnotations = ms.getMethod().getParameterAnnotations();
        /* 
        
        sample output.
        
**************void com.stevesouza.aspectj.annotations.MyClass.deliver1(String)
  annotations=[@com.stevesouza.aspectj.annotations.ExpressionLanguageAnnotation(value=deliver1.annotation)]
  getMethod().getAnnotation(...)=@com.stevesouza.aspectj.annotations.ExpressionLanguageAnnotation(value=deliver1.annotation)
  getMethod().getAnnotation(...).value()=deliver1.annotation
  method parameters=[java.lang.String arg0]
  pjp.getArgs()=[hi ]
  param annotations:
    null
hi 
**************void com.stevesouza.aspectj.annotations.MyClass.deliver2(String, String)
  annotations=[@com.stevesouza.aspectj.annotations.ExpressionLanguageAnnotation(value=deliver2.annotation)]
  getMethod().getAnnotation(...)=@com.stevesouza.aspectj.annotations.ExpressionLanguageAnnotation(value=deliver2.annotation)
  getMethod().getAnnotation(...).value()=deliver2.annotation
  method parameters=[java.lang.String arg0, java.lang.String arg1]
  pjp.getArgs()=[mom, hi]
  param annotations:
    @com.stevesouza.aspectj.annotations.ParamName(value=person)
    null
mom, hi 
         */


        System.out.println("  **************" + ms);

        System.out.println("    annotations=" + Arrays.asList(annotations));
        System.out.println("    getMethod().getAnnotation(...)=" + expressionAnnotation);
        System.out.println("    getMethod().getAnnotation(...).value()=" + expressionAnnotation.value());
        System.out.println("    method parameters=" + Arrays.asList(parameters));
        System.out.println("    method values=pjp.getArgs()=" + Arrays.asList(pjp.getArgs()));
        String message = MessageFormat.format(expressionAnnotation.label(), pjp.getArgs());
        System.out.println("    annotation populated with values (MessageFormat)=" + message);

        System.out.println("    param annotations:");
        for (Parameter param : parameters) {
            System.out.println("    " + param.getAnnotation(ParamName.class));
        }

        Object retVal = pjp.proceed();
        return retVal;
    }


}
