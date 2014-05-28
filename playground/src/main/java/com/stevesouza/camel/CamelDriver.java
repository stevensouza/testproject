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
        System.out.println("*****"+context);
        System.out.println("*****"+context.getClass());
        System.out.println("*****"+context.getParent());
        CamelContext camel = (CamelContext) context.getBean("myCamelContext");
        System.out.println("****routes="+camel.getRoutes());



        //CamelContext camel = new DefaultCamelContext();
 //       CamelContext camel = new SpringCamelContext(context);
        // shows up in jmx with this context name instead of camel-1
        camel.setNameStrategy(new DefaultCamelContextNameStrategy("steves_camel_driver"));
        camel.setTracing(true);
        camel.addStartupListener(new StartupListener() {
            @Override
            public void onCamelContextStarted(CamelContext context, boolean alreadyStarted) throws Exception {
                System.out.println("Seeing when startup strategy is called.  CamelContext=" + context);
            }
       });
//        camel.addRoutes(new FileCopyRouteBuilder());
//        camel.addRoutes(new MessageToFileRouteBuilder());
//        camel.addRoutes(new PojoToJsonToFileRouteBuilder());
//        camel.addRoutes(new FileToJsonToPojoRouteBuilder());
//        camel.addRoutes(new PojoToJsonToFileRouteBuilderXstream());
//        camel.addRoutes(new FileToJsonToPojoRouteBuilderXstream());

        ProducerTemplate template = camel.createProducerTemplate();

        camel.start();
        MyAutowiredClass myAutowiredClass = (MyAutowiredClass) context.getBean("myComponent");
        myAutowiredClass.getSlowerMethod();


        // Send to a specific queue
        template.sendBodyAndHeader("direct:messagetofile", "<hello>world!</hello>", "filename", "WOOHOOhelloworld.txt");
        template.sendBodyAndHeader("direct:messagetofile", "<hello>steve!</hello>", "filename", "WOOHOOhellosteve.txt");
    //    template.sendBody("direct:personsname", new PersonsName("steve","souza"));
        template.sendBody("direct:personsname_xstream", new PersonsName("joel","souza"));


        Thread.sleep(10000);
        camel.stop();
        Utils.displayJamon();

    }


}
