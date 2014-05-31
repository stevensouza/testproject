package com.stevesouza.camel;

import org.apache.camel.Body;
import org.apache.camel.Handler;
import org.apache.camel.Header;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

/**
 * Created by stevesouza on 5/31/14.
 */
@Component
public class MessagePeekerBean {
    private final Logger LOG = Logger.getLogger(getClass());

    /** Note because this class has one method @Handler is not required.  Also @Body is not required
     * as it will see the message is a String and if the class has only 1 method where the message matches
     * the body it would be picked.
     *
     * @param message
     * @return
     */
    @Handler
    public String peek(@Body String message, @Header("filename") String fileName) {
        LOG.info("The message is: "+message+", fileName="+fileName);
        return getClass()+", fileName="+fileName+", changed message: "+message;  // new body
    }
}
