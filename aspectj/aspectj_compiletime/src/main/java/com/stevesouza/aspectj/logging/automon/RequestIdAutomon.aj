package com.stevesouza.aspectj.logging.automon;


public aspect RequestIdAutomon {
    private static final LogTracingHelper helper = LogTracingHelper.getInstance();
    // Note within does all pointcuts within the class including this and static and constructors etc.
    // this() would not do static methods but only instance ones.
    pointcut requestStart(): execution(* com.stevesouza.aspectj.logging.automon.basic.MyLoggerClassBasic.main(..));

    before(): requestStart() {
        helper.withRequestId();
    }

    after(): requestStart() {
        helper.removeRequestId();
    }
}
