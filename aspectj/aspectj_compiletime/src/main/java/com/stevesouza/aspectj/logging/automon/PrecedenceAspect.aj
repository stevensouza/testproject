package com.stevesouza.aspectj.logging.automon;

public aspect PrecedenceAspect {
    declare precedence: RequestIdAutomon, *; // All other aspects
}
