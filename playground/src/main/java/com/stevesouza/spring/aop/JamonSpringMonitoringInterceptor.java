package com.stevesouza.spring.aop;

import com.jamonapi.MonKeyImp;
import com.jamonapi.Monitor;
import com.jamonapi.MonitorFactory;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.logging.Log;
import org.springframework.aop.interceptor.AbstractMonitoringInterceptor;

/**
 * Created by stevesouza on 6/10/14.
 */
public class JamonSpringMonitoringInterceptor extends AbstractMonitoringInterceptor {
    private JamonAopKeyHelperInt<MethodInvocation> keyHelper;

    @Override
    protected Object invokeUnderTrace(MethodInvocation invocation, Log logger) throws Throwable {
        Object retVal = null;
        String label = keyHelper.getLabel(invocation);
        String details = keyHelper.getDetails(invocation);
        MonKeyImp key = new MonKeyImp(label, details, "ms.");
        Monitor mon = MonitorFactory.start(key);
        try {
            retVal = invocation.proceed();
        } catch (Throwable t) {
            String exceptionDetails = keyHelper.getDetails(invocation, t);
            key.setDetails(exceptionDetails);
            trackException(t, exceptionDetails);
            throw t;
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
}
