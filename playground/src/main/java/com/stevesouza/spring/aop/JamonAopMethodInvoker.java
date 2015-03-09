package com.stevesouza.spring.aop;

import com.jamonapi.*;

/**
 * Created by stevesouza on 6/10/14.
 */
public abstract class JamonAopMethodInvoker<T> {

    private JamonAopKeyHelperInt<T> keyHelper;

    public JamonAopMethodInvoker(JamonAopKeyHelperInt keyHelper) {
        this.keyHelper = keyHelper;
    }

    public Object monitor(T invoker) throws Throwable {
        Object retVal = null;
        String label = keyHelper.getLabel(invoker);
        String details = keyHelper.getDetails(invoker);
        MonKeyImp key = new MonKeyImp(label, details, "ms.");
        Monitor mon = MonitorFactory.start(key);
        try {
            retVal = proceed(invoker);
        } catch (Throwable t) {
            String exceptionDetails = keyHelper.getDetails(invoker, t);
            key.setDetails(exceptionDetails);
            trackException(t, exceptionDetails);
            throw t;
        } finally {
            mon.stop();
        }

        return retVal;
    }

    /** The normal proceed method.  This would have a different implementation for say a MethodInvocation or JoinPoint */
    abstract protected Object proceed(T invoker) throws Throwable;

    // add monitors for the thrown exception and also put the stack trace in the details portion of the key
    private void trackException(Throwable exception, String exceptionDetails) {
        MonitorFactory.add(new MonKeyImp(keyHelper.getExceptionLabel(exception), exceptionDetails, "Exception"), 1);
        MonitorFactory.add(new MonKeyImp(MonitorFactory.EXCEPTIONS_LABEL, exceptionDetails, "Exception"), 1);
    }

    public void setKeyHelper(JamonAopKeyHelperInt<T> keyHelper) {
        this.keyHelper = keyHelper;
    }

    public void setExceptionBufferListener(boolean enable) {
        MonKey key = new MonKeyImp(MonitorFactory.EXCEPTIONS_LABEL, "Exception");
        boolean hasBufferListener = MonitorFactory.getMonitor(key).hasListener("value", "FIFOBuffer");

        if (enable && !hasBufferListener) {
            MonitorFactory.getMonitor(key).addListener("value", JAMonListenerFactory.get("FIFOBuffer"));
        } else if (!enable && hasBufferListener) {
            MonitorFactory.getMonitor(key).removeListener("value", "FIFOBuffer");
        }
    }

}
