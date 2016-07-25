package com.stevesouza.annotations;

import com.fdsapi.Utils;
import java.lang.annotation.Annotation;
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
        /* 
        
obj.myAnnotatedMethod2("steve", "souza");

2016-07-25 18:44:00 INFO  ExpressionLanguageAspect:37 - ***getMethod().getAnnotation(...).value()=mymethod.annotation
2016-07-25 18:44:00 INFO  ExpressionLanguageAspect:34 - ***pjp.getArgs()=steve,souza
2016-07-25 18:44:00 INFO  ExpressionLanguageAspect:35 - ***annotations=@com.stevesouza.annotations.ExpressionLanguageAnnotation(value=mymethod.annotation2)
2016-07-25 18:44:00 INFO  ExpressionLanguageAspect:36 - ***getMethod().getAnnotation(...)=@com.stevesouza.annotations.ExpressionLanguageAnnotation(value=mymethod.annotation2)
2016-07-25 18:44:00 INFO  ExpressionLanguageAspect:37 - ***getMethod().getAnnotation(...).value()=mymethod.annotation2
        
         */

                               
        LOG.info("***pjp.getArgs()="+Utils.delimit(",",pjp.getArgs()));
        LOG.info("***annotations="+Utils.delimit(",",annotations));
        LOG.info("***getMethod().getAnnotation(...)="+expressionAnnotation);
        LOG.info("***getMethod().getAnnotation(...).value()="+expressionAnnotation.value());

        Object retVal = pjp.proceed();
        return retVal;
    }




}
