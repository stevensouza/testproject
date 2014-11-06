package com.stevesouza.jmx;


import com.jamonapi.Monitor;
import com.jamonapi.MonitorFactory;

import java.util.*;
import java.util.concurrent.atomic.*;

public class SynchronizedLong implements Runnable {

    private static long LOOPCOUNT = 10000000000L;
    private static int NUMTHREADS=8;
    private static long aLong = 0;
    private static AtomicLong atomicLong=new AtomicLong(0);
    private static Object obj;
    private static List l=Collections.synchronizedList(new ArrayList());
    private static List l1=Collections.synchronizedList(new ArrayList());

    public static void main(String[] args) throws Exception {

        Monitor mon=MonitorFactory.start();
        Thread[] threadPool = new Thread[NUMTHREADS];
        for(int i = 0; i<NUMTHREADS; i++) {
            threadPool[i] = new Thread(new SynchronizedLong());
        }

        for(int i = 0; i<NUMTHREADS; i++) {
            threadPool[i].start();
        }

        for(int i =0;i<NUMTHREADS;i++)        {
            threadPool[i].join();
        }


        System.out.println("And the final answer is: "+aLong);
        System.out.println("Final time is: "+mon.stop());
    }



    public static void jamontest()  {
        for (int i = 1; i <= LOOPCOUNT; i++) 
          //  unprotectedIncr();
            atomicIncr();
         //   synchronizedIncr();
    }
    
    public static void unprotectedIncr() {
        aLong++;
    }
    public static synchronized void synchronizedIncr() {
            aLong++;
    }
    
    public static void atomicIncr() {
        atomicLong.incrementAndGet();
        if (atomicLong.intValue()%10000==0)
          l.add(new Object());
        
        if (atomicLong.intValue()%50000==0)
            l1.add(new Object());
        
        obj=new Object();
     }



    public void run() {
            jamontest();
    }

}
