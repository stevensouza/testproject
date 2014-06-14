package com.stevesouza.spring.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;

/**
 * Created by stevesouza on 5/24/14.
 * This method tracks the performance of a method:
 *   JAMon Label=void com.stevesouza.spring.MonitorMe3.anotherMethod2(), Units=ms.: (LastValue=0.0, Hits=10.0, Avg=0.3, Total=3.0, Min=0.0, Max=1.0, Active=0.0, Avg Active=1.0, Max Active=1.0, First Access=Tue Jun 03 21:11:21 CEST 2014, Last Access=Tue Jun 03 21:11:26 CEST 2014)
 *
 * And Exceptions if the method throws one.  To jamon monitors are incremented if an exception is thrown.  One for the specific exception
 * and one for the general class of all exceptions.  An example follows:
 *   JAMon Label=java.io.IOException, Units=Exception: (LastValue=1.0, Hits=10.0, Avg=1.0, Total=10.0, Min=1.0, Max=1.0, Active=0.0, Avg Active=0.0, Max Active=0.0, First Access=Tue Jun 03 21:11:21 CEST 2014, Last Access=Tue Jun 03 21:11:26 CEST 2014)
 *   JAMon Label=com.jamonapi.Exceptions, Units=Exception: (LastValue=1.0, Hits=10.0, Avg=1.0, Total=10.0, Min=1.0, Max=1.0, Active=0.0, Avg Active=0.0, Max Active=0.0, First Access=Tue Jun 03 21:11:21 CEST 2014, Last Access=Tue Jun 03 21:11:26 CEST 2014)
 *
 * And finally the detailed stacktrace is kept if an exception is thrown.  This can be viewed in the jamon war.
 */
// Spring automatically finds
// By default each aspect is a singleton within the applicationContext
@Aspect
public class JamonAspect {

    // The pointcut could be defined here, but it is more flexibly defined in applicationContext.xml
    //     @Around("com.stevesouza.spring.aop.SystemAopPointcutDefinitions.camelOperation()")
    // or  @Around("com.stevesouza.spring.aop.SystemAopPointcutDefinitions.monitorAnnotatedClass()")
  //  private JamonAopKeyHelperInt keyHelper;
    private JamonAopMethodInvoker<ProceedingJoinPoint> invoker = new JamonAopMethodInvoker<ProceedingJoinPoint>(new JamonAopKeyHelper()) {
        @Override
        protected Object proceed(ProceedingJoinPoint invoker) throws Throwable {
            return invoker.proceed();
        }
    };

    public JamonAspect() {
    }

    public JamonAspect(JamonAopKeyHelperInt keyHelper) {
       setKeyHelper(keyHelper);
    }

    public Object monitor(ProceedingJoinPoint pjp) throws Throwable {
        return invoker.monitor(pjp);
    }


    public void setKeyHelper(JamonAopKeyHelperInt keyHelper) {
        invoker.setKeyHelper(keyHelper);
    }

    public void setExceptionBufferListener(boolean enable) {
        invoker.setExceptionBufferListener(enable);
    }


}
