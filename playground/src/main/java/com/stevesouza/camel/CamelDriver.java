package com.stevesouza.camel;

import com.stevesouza.Utils;
import com.stevesouza.camel.json.jackson.FileToJsonToPojoRouteBuilder;
import com.stevesouza.camel.json.xstream.FileToJsonToPojoRouteBuilderXstream;
import com.stevesouza.camel.json.jackson.PojoToJsonToFileRouteBuilder;
import com.stevesouza.camel.json.xstream.PojoToJsonToFileRouteBuilderXstream;
import com.stevesouza.spring.HelloSpringBean;
import com.stevesouza.spring.MyAutowiredClass;
import org.apache.camel.*;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.impl.DefaultCamelContextNameStrategy;
import org.apache.camel.spring.SpringCamelContext;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Program that launches various camel tests.
 */
public class CamelDriver {

    public static void main(String[] ags) throws Exception {
        ApplicationContext context = new ClassPathXmlApplicationContext("camelSpringApplicationContext.xml");
        //normal way of getting camel context but i have it defined in camelSpringApplicationContext.xml
        // CamelContext camel = new DefaultCamelContext();
        // CamelContext camel = new SpringCamelContext(context);
        CamelContext camel = (CamelContext) context.getBean("myCamelContext");

        // shows up in jmx with this context name instead of camel-1
        camel.setNameStrategy(new DefaultCamelContextNameStrategy("steves_camel_driver"));
        // camel.setTracing(true);  // set in camelSpringApplicationContext.xml
        camel.addStartupListener(new StartupListener() {
            @Override
            public void onCamelContextStarted(CamelContext context, boolean alreadyStarted) throws Exception {
                System.out.println("Seeing when startup strategy is called.  CamelContext=" + context);
            }
       });

        ProducerTemplate template = camel.createProducerTemplate();

        // if camel wasn't started in the spring config xml file with autoStartup="true" then we would do the following:
        //  camel.start();
        MyAutowiredClass myAutowiredClass = (MyAutowiredClass) context.getBean("myComponent");
        myAutowiredClass.getSlowerMethod();


        // Send to a specific queue
        template.sendBodyAndHeader("direct:messagetofile", "<hello>world!</hello>", "filename", "helloworld.txt");
        template.sendBodyAndHeader("direct:messagetofile", "<hello>steve!</hello>", "filename", "hellosteve.txt");
        template.sendBody("direct:personsname", new PersonsName("steve","souza"));
        template.sendBody("direct:personsname_xstream", new PersonsName("joel","souza"));


        Thread.sleep(5000);
        camel.stop();
        Utils.displayJamon();

    }


}
