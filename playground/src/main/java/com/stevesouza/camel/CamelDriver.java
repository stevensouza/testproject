package com.stevesouza.camel;

import com.stevesouza.Utils;
import com.stevesouza.spring.MyAutowiredClass;
import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.StartupListener;
import org.apache.camel.impl.DefaultCamelContextNameStrategy;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Program that launches various camel tests.
 */
public class CamelDriver {
    private static final Logger LOG = Logger.getLogger(CamelDriver.class);


    public static void main(String[] ags) throws Exception {
        ApplicationContext context = new ClassPathXmlApplicationContext("camelSpringApplicationContext.xml");
        CamelContext camel = configureCamelContext(context);
        ProducerTemplate template = camel.createProducerTemplate();
        LOG.info("");
        LOG.info("***** Start actual app *****");

        // if camel wasn't started in the spring config xml file with autoStartup="true" then we would do the following:
        //  camel.start();

        // Send to a specific queue
        LOG.info("1 ***********");
        template.sendBodyAndHeader("direct:messagetofile", "<hello>world!</hello>", "filename", "helloworld.txt");
        LOG.info("2 ***********");
        template.sendBodyAndHeader("direct:messagetofile", "<hello>steve!</hello>", "filename", "hellosteve.txt");
        LOG.info("3 ***********");
        template.sendBody("direct:personsname", new PersonsName("steve","souza"));
        LOG.info("4 ***********");
        template.sendBody("direct:personsname_xstream", new PersonsName("joel","souza"));
        LOG.info("5 ***********");
        template.sendBodyAndHeader("direct:messageusingbean", "<hello>world!</hello>", "filename", "messageusingbean.txt");

        LOG.info("* Start - aop calls on myAutoWiredClass.getSlowerMethod()");
        MyAutowiredClass myAutowiredClass = context.getBean("myComponent", MyAutowiredClass.class);
        // aop is defined on this method in MySpringAspect
        myAutowiredClass.getSlowerMethod();
        LOG.info("* End - aop calls on myAutoWiredClass.getSlowerMethod()");

        Thread.sleep(5000);
        camel.stop();
        Utils.displayJamon();

    }

    // should also be able to do this in spring xml?
    private static CamelContext configureCamelContext(ApplicationContext context) throws Exception {
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
                LOG.info("Seeing when startup strategy is called.  CamelContext=" + context);
            }
         });

        return camel;
    }


}
