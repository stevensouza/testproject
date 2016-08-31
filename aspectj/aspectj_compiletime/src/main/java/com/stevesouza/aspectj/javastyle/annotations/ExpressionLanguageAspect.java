package com.stevesouza.annotations;

import com.fdsapi.Utils;
import java.lang.annotation.Annotation;
import java.lang.reflect.Parameter;
import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;


@Aspect
public class ExpressionLanguageAspect {
    private static final Logger LOG = Logger.getLogger(ExpressionLanguageAspect.class);

    @Pointcut("execution(* com.stevesouza.spring.MyAutowiredClass.get*(..))")

    public void advisedMethod() {
    }


    // note this is also used to advise another method defined in camelSpringApplicationContext.xml
    @Around("execution(* com.stevesouza.spring.MyAutowiredClass.getSlowMethod(..))")
    public Object doProfiling(ProceedingJoinPoint pjp) throws Throwable {
        
        MethodSignature ms = (MethodSignature) pjp.getSignature();
        Annotation[] annotations = ms.getMethod().getAnnotations();
        ExpressionLanguageAnnotation expressionAnnotation = ms.getMethod().getAnnotation(ExpressionLanguageAnnotation.class);
        Parameter[] parameters = ms.getMethod().getParameters();
        Annotation[][] paramAnnotations = ms.getMethod().getParameterAnnotations();
        /* 
        
obj.myAnnotatedMethod2("steve", "souza");

2016-07-25 21:42:41 INFO  ExpressionLanguageAspect:47 - **************
2016-07-25 21:42:41 INFO  ExpressionLanguageAspect:48 -   annotations=@com.stevesouza.annotations.ExpressionLanguageAnnotation(value=mymethod.annotation2)
2016-07-25 21:42:41 INFO  ExpressionLanguageAspect:49 -   getMethod().getAnnotation(...)=@com.stevesouza.annotations.ExpressionLanguageAnnotation(value=mymethod.annotation2)
2016-07-25 21:42:41 INFO  ExpressionLanguageAspect:50 -   getMethod().getAnnotation(...).value()=mymethod.annotation2
        
note to get actual parameter names (fname, lname instead of arg0, arg1) you have to compile with  javac  -g argument. 
2016-07-25 21:42:41 INFO  ExpressionLanguageAspect:51 -   method parameters=java.lang.String arg0,java.lang.String arg1
2016-07-25 21:42:41 INFO  ExpressionLanguageAspect:52 -   pjp.getArgs()=steve,souza
2016-07-25 21:42:41 INFO  ExpressionLanguageAspect:54 -   param annotations:
2016-07-25 21:42:41 INFO  ExpressionLanguageAspect:56 -     @com.stevesouza.annotations.ParamName(value=fname)
2016-07-25 21:42:41 INFO  ExpressionLanguageAspect:56 -     null   
         */
                    
        LOG.info("**************");
        LOG.info("  annotations="+Utils.delimit(",",annotations));
        LOG.info("  getMethod().getAnnotation(...)="+expressionAnnotation);
        LOG.info("  getMethod().getAnnotation(...).value()="+expressionAnnotation.value());
        LOG.info("  method parameters="+Utils.delimit(",",parameters));
        LOG.info("  pjp.getArgs()="+Utils.delimit(",",pjp.getArgs()));
        
        LOG.info("  param annotations:");
        for (Parameter param : parameters) {
            LOG.info("    "+param.getAnnotation(ParamName.class));
        }

        Object retVal = pjp.proceed();
        return retVal;
    }


}
