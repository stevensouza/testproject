package com.stevesouza.aspectj.logging.automon;


import org.slf4j.MDC;

import java.util.UUID;

public aspect RequestIdAutomon {
    private static final LoggingHelper helper = new LoggingHelper();
    // Note within does all pointcuts within the class including this and static and constructors etc.
    // this() would not do static methods but only instance ones.
    pointcut requestStart(): execution(* com.stevesouza.aspectj.logging.automon.MyLoggerClass.main(..));

    before(): requestStart() {
        helper.putRequestId();
    }

    after(): requestStart() {
        helper.removeRequestId();
    }
}
