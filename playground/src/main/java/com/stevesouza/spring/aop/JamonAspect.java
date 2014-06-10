package com.stevesouza.spring.aop;

import com.jamonapi.JAMonListenerFactory;
import com.jamonapi.MonKeyImp;
import com.jamonapi.Monitor;
import com.jamonapi.MonitorFactory;
import com.jamonapi.utils.Misc;
import org.apache.log4j.Logger;
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
    private JamonAopKeyHelperInt keyHelper;

    public JamonAspect() {
        keyHelper = new JamonAopKeyHelper();
    }

    public JamonAspect(JamonAopKeyHelperInt keyHelper) {
        this.keyHelper = keyHelper;
    }

    public Object monitor(ProceedingJoinPoint pjp) throws Throwable {
        Object retVal = null;
        String label = keyHelper.getLabel(pjp);
        String details = keyHelper.getDetails(pjp);
        MonKeyImp key = new MonKeyImp(label, details, "ms.");
        Monitor mon = MonitorFactory.start(key);
        try {
            retVal = pjp.proceed();
        } catch (Throwable t) {
            String exceptionDetails = keyHelper.getDetails(pjp, t);
            key.setDetails(exceptionDetails);
            trackException(t, exceptionDetails);
        } finally {
            mon.stop();
        }

        return retVal;
    }

    // add monitors for the thrown exception and also put the stack trace in the details portion of the key
    private void trackException(Throwable exception, String exceptionDetails) {
        MonitorFactory.add(new MonKeyImp(keyHelper.getExceptionLabel(exception), exceptionDetails, "Exception"), 1);
        MonitorFactory.add(new MonKeyImp(MonitorFactory.EXCEPTIONS_LABEL, exceptionDetails, "Exception"), 1);
    }

    public void setKeyHelper(JamonAopKeyHelperInt keyHelper) {
        this.keyHelper = keyHelper;
    }

    public void setExceptionBufferListener(boolean enable) {
        if (enable) {
            MonitorFactory.getMonitor(MonitorFactory.EXCEPTIONS_LABEL, "Exception")
                    .addListener("value", JAMonListenerFactory.get("FIFOBuffer"));
        } else {
            MonitorFactory.getMonitor(MonitorFactory.EXCEPTIONS_LABEL, "Exception")
                    .removeListener("value", "FIFOBuffer");
        }
                /*
                private void addListeners(HttpServletRequest request, MonKey key) {
  String addListener=request.getParameter("addlistener");
  String listenerType=request.getParameter("listenertype");
  if (addListener!=null && listenerType!=null) {

   String[] add=request.getParameterValues("availablelistener");
   int rows=(add==null) ? 0 : add.length;

   if (MonitorFactory.exists(key)) {
     Monitor mon=MonitorFactory.getMonitor(key);
     for (int i=0;i<rows;i++) {

      if (!mon.hasListener(listenerType,add[i])) {
         JAMonListener listener=JAMonListenerFactory.get(add[i]);
         mon.addListener(listenerType,listener);
      }

                 */
    }

}
