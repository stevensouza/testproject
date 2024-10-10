package com.stevesouza.opentelemetry;

import io.micrometer.common.KeyValues;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tag;
import io.micrometer.observation.annotation.Observed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@RestController
@Observed (
        lowCardinalityKeyValues = {
                "shoppingcart_tag", "true",
                "performance_tag", "true"
        }
)
public class HelloController {
    private static final Logger logger = LoggerFactory.getLogger(HelloController.class);

    private final MeterRegistry meterRegistry;

    //    private final Tracer tracer;
    HelloController(MeterRegistry meterRegistry) {
        List<Tag> list = new LinkedList<>();
        list.add(Tag.of("company", "amazon"));
        list.add(Tag.of("developer", "steve"));
        meterRegistry.config().commonTags(list);
        meterRegistry.config().commonTags("website", "shoppingcart", "env", "prod");

//    HelloController(MeterRegistry meterRegistry, Tracer tracer) {
        this.meterRegistry = meterRegistry;
//        this.tracer = tracer;
    }

    //    @WithSpan
    @GetMapping("/bye")
//    @Timed(value = "bye.time", description = "Time taken to return hello")
    public String goodBye() {
        logger.info("in goodBye()");
        return "Goodbye, World!";
    }

    @GetMapping("/hello")
//    @Timed(value = "hello.time", description = "Time taken to return hello")
//    @WithSpan
    @Observed(
            name = "my.service.hello",
            contextualName = "my-service-hello",
            lowCardinalityKeyValues = {
                    "method", "hello()",
                    "region", "us-west-2"  // This overrides the class-level "region" tag
            }
    )
    public String hello() {
        logger.info("in hello()");
        meterRegistry.counter("hello.counter").increment();
        return "Hello World!";
//
//        Span span = tracer.spanBuilder("manual-span").startSpan();
//        try {
//            span.addEvent("Processing hello request");
//            return "Hello, World!";
//        } finally {
//            span.end();
//        }
    }
}
