package com.stevesouza.misc;

import com.jamonapi.*;
import com.jamonapi.proxy.MonProxyFactory;

/**
 * Created by stevesouza on 9/2/15.
 */
public class JamonPlay {
    public  JamonPlay() throws Exception {
        setup();
    }



    private IHelloWorld helloworld;
    private HelloWorldAspectJImpl helloworldAspectJ;

    private IHelloWorld helloworldProxy;

    public void setup() throws Exception {
        helloworld = new HelloWorldImpl();
        helloworldAspectJ = new HelloWorldAspectJImpl();

        helloworldProxy = (IHelloWorld) (MonProxyFactory.monitor(helloworld, IHelloWorld.class));
    }

    private String getDirectResponseWithAspectJ(int count) {
        String response = null;
        Monitor mon = MonitorFactory.start("getDirectResponseWithAspectJ");


        for (int i = 0; i < count; i++) {
            response = helloworldAspectJ.getResponse(String.valueOf(i));
        }

        mon.stop();
        return response;
    }

    private String getDirectResponse(int count) {
        String response = null;
        Monitor mon = MonitorFactory.start("getDirectResponse");

        for (int i = 0; i < count; i++) {
            response = helloworld.getResponse(String.valueOf(i));
        }

        mon.stop();
        return response;
    }

    private String getDirectResponseWithMonitoring(int count) {
        String response = null;
        Monitor mon = MonitorFactory.start("getDirectResponseWithMonitoring");

        for (int i = 0; i < count; i++) {
            Monitor mon1 = MonitorFactory.start("directMonitoring.getResponse");
            response = helloworld.getResponse(String.valueOf(i));

            mon1.stop();
        }

        mon.stop();
        return response;
    }

    private String getProxyResponse(int count) {
        String response = null;
        Monitor mon = MonitorFactory.start("getProxyResponse");


        for (int i = 0; i < count; i++) {
            response = helloworldProxy.getResponse(String.valueOf(i));
        }

        mon.stop();

        return response;
    }

    private String getProxyDisableResponse(int count) {
        Monitor mon = MonitorFactory.start("getProxyDisableResponse");

        // <tr><td>local</td><td>getProxyDisableResponse, ms.</td><td>1.0</td><td>744.0</td><td>744.0</td><td>0.0</td><td>744.0</td><td>744.0</td><td>744.0</td><td>0.0</td><td>1.0</td><td>1.0</td><td>Wed Sep 09 13:41:33 CEST 2015</td><td>Wed Sep 09 13:41:33 CEST 2015</td><td>true</td><td>false</td><td>false</td><td>local</td></tr>
        //MonitorFactory.setEnabled(false);
        // direct calls - 49.1
        // direct response with monitoring in code - 353.6
        // proxy and enabled monitoring - 1487
        // MonitorFactory.setEnabled(false) - 732.1 disable only jamon but keep proxy enabled 732.1.  here proxy is called and a noop monitor.
        // MonProxyFactory.enableAll(false) - 54.8 disable only proxy then time goes back to what you would expect. for 10 iterations avg 54.8
        //  note only methods that are called directly via the proxy are monitored.  if a proxied method calls another proxied method internally it is not
        //  monitored.

        // try automon?  general comment is that with monitoring and proxying you get an overhead of 1.4 seconds for monitoring 10,000,000 method calls.
        // this includes tracking min, max, avg, total, concurrency and more. as well as exceptions.
        /// direct measurement of serial calls may be of limited value anyway. note also disabled then the performance difference goes to near 0.
        MonProxyFactory.enableAll(false);


        String response = null;

        for (int i = 0; i < count; i++) {
            response = helloworldProxy.getResponse(String.valueOf(i));
        }

        //MonitorFactory.setEnabled(true);
        MonProxyFactory.enableAll(true);

        mon.stop();

        return response;
    }



    public static void main(String[] args) throws Exception {
        int loops = (args==null || args.length==0) ? 1 : Integer.valueOf(args[0]);
        JamonPlay play = new JamonPlay();
        int ITERATIONS=1000;
        for (int i=0;i<loops;i++) {
            play.getDirectResponse(ITERATIONS);
            play.getDirectResponseWithAspectJ(ITERATIONS);
            play.getProxyResponse(ITERATIONS);
            play.getDirectResponseWithMonitoring(ITERATIONS);
            play.getProxyDisableResponse(ITERATIONS);
        }

        System.out.println(MonitorFactory.getReport());
    }
//
//    public interface IHelloWorld {
//
//        String getResponse(String hello);
//    }
//
//    public class HelloWorldImpl implements IHelloWorld {
//
//        @Override
//        public String getResponse(String hello) {
//           // return "say hello:" + hello + ",at:" + new Date();
//            return "say hello:";
//
//        }
//
//    }


}
