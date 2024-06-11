package com.stevesouza.aspectj.nativestyle.method;

/**
 * Tracks calls to ALL aspectj events.  That is because pointcut traced doesn't have limitations such as
 * execution, call etc.  cool!
 *
 * from location where these files are:
 * ajc com/stevesouza/aspectj/*.javastyle com/stevesouza/aspectj/*.aj
 * javastyle com.stevesouza.aspectj.MessageCommunicator
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
 Before: get(PrintStream javastyle.lang.System.out)
 After: get(PrintStream javastyle.lang.System.out)
 Before: call(void javastyle.io.PrintStream.println(String))
 hi
 */
public aspect NativeAspect {
    // The following line will give a compilation warning (by using 'error' and error/failed compilation would be thrown
    declare warning : call(void System.gc()) && within(MyClass):  "Don't call System.gc() from code. (AspectJ Custom warning)";

    // The following would catch any calls to System.out.println("badcall") within the specified class. However, it would
    // also catch any calls to a PrintStream in general from this class.  I couldn't figrue out the pointcut for
    // System.out.println(..) only. Note this would be a good error to check for in your code.
    declare warning : call(* java.io.PrintStream.println(..)) && within(MyClass): "Don't call System.out.println(..). Use a logger. (AspectJ Custom warning)";

    private int callDepth;
    // The following would do all methods in base class and all added methods in children
    //   pointcut traced() : within(com.stevesouza.aspectj.MyClassBase+)  && execution(* *(..));

    // Only traces methods introduced in MyClass.  Not any parent classes.  Includes
    // static methods too.
    //pointcut traced() : within(MyClass)  && execution(* *(..));

    // The following traces  any instance methods defined in this class which includes parent methods that
    // are inherited.  However it doesn't include any static methods.
    pointcut traced() : this(MyClass)  && execution(* *(..));

    before() : traced() {
        print("Before: ", thisJoinPoint);
        callDepth++;
    }

    after() : traced() {
        callDepth--;
        print("After: ", thisJoinPoint);
    }
    private void print(String prefix, Object message) {
        for (int i=0; i<callDepth;i++) {
            System.out.print("  ");
        }

        System.out.println(prefix+"("+getClass()+ "):"+message);
    }
}
