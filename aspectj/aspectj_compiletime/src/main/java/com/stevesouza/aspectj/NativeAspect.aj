package com.stevesouza.aspectj;

/**
 * Tracks calls to ALL aspectj events.  That is because pointcut traced doesn't have limitations such as
 * execution, call etc.  cool!
 *
 * from location where these files are:
 * ajc com/stevesouza/aspectj/*.java com/stevesouza/aspectj/*.aj
 * java com.stevesouza.aspectj.MessageCommunicator
 * output similar to the following:
 *
 * Before: staticinitialization(com.stevesouza.aspectj.MessageCommunicator.<clinit>)
 After: staticinitialization(com.stevesouza.aspectj.MessageCommunicator.<clinit>)
 Before: execution(void com.stevesouza.aspectj.MessageCommunicator.main(String[]))
 Before: call(com.stevesouza.aspectj.MessageCommunicator())
 Before: preinitialization(com.stevesouza.aspectj.MessageCommunicator())
 After: preinitialization(com.stevesouza.aspectj.MessageCommunicator())
 Before: initialization(com.stevesouza.aspectj.MessageCommunicator())
 Before: execution(com.stevesouza.aspectj.MessageCommunicator())
 After: execution(com.stevesouza.aspectj.MessageCommunicator())
 After: initialization(com.stevesouza.aspectj.MessageCommunicator())
 After: call(com.stevesouza.aspectj.MessageCommunicator())
 Before: call(void com.stevesouza.aspectj.MessageCommunicator.deliver(String))
 Before: execution(void com.stevesouza.aspectj.MessageCommunicator.deliver(String))
 Before: get(PrintStream java.lang.System.out)
 After: get(PrintStream java.lang.System.out)
 Before: call(void java.io.PrintStream.println(String))
 hi
 */
public aspect NativeAspect {
    private int callDepth;
  //  pointcut traced() : !within(NativeAspect);
    pointcut traced() : within(MessageCommunicator)  && execution(* *(..));

    before() : traced() {
        print("Before", thisJoinPoint);
        callDepth++;
    }

    after() : traced() {
        print("After", thisJoinPoint);
        callDepth--;
    }
    private void print(String prefix, Object message) {
        for (int i=0; i<callDepth;i++) {
            System.out.print("  ");
        }

        System.out.println(prefix+"("+getClass().getSimpleName()+ "):"+message);
    }
}
