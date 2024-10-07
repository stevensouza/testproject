package com.stevesouza.aspectj.nativestyle.annotations.declaration;

import io.micrometer.core.annotation.Counted;
import io.micrometer.core.annotation.Timed;
import io.micrometer.core.annotation.TimedSet;
import io.micrometer.observation.annotation.Observed;
/** assign annotations based on name type patterns.
 * Notes:
 * 1) Only @type, @method, @constructor and @field are supported (for example you can't assign an annotation to a method argument)
 * 2) You can only assign one annotation at a time
 * 3) Note pointcuts are not used but named type patterns are which is pretty much anything you woudl point inside say
 *      an execution(..) pointcut.  One downside of this is that if you want to assign multiple annotations to the same
 *      pointcut you must repeat the named pattern and not reuse it as you would with pointcuts (i.e. select() pointcut)
 */
public aspect DeclareAnnotationsAspect {

    // adding class annotations for micrometer
    declare @type: MyService+ : @Counted;
    declare @type: MyService+ : @Timed;
    declare @type: MyService+ : @Observed;


    // adding method annotations for micrometer
    declare @method: * MyService.process*() : @Timed;

    declare @method: * MyService.process*() :
        @Counted(
            value = "process.counter",
            description = "Number of times the counter is incremented"
        );

    declare @method: * MyService.timedSetMethod() :
        @TimedSet({
                @Timed(value = "method.execution.time", description = "Time taken for method execution"),
                @Timed(value = "method.execution.time.seconds", description = "Time taken for method execution in seconds", longTask = true),
                @Timed(value = "method.execution.time.percentiles", description = "Time taken for method execution with percentiles",
                        percentiles = {0.5, 0.95, 0.99}, histogram = true)
        });

    // you can use conditionals &&,||, ! (and, or, not)
    declare @method: (* MyService.aMethod() || * MyService.bMethod() ) :
    @Counted(
            value = "xMethod.counter",
            description = "Number of times the counter is incremented"
    );



}
