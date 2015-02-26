package com.stevesouza.openmon;

import com.jamonapi.Monitor;

/**
 * Created by stevesouza on 2/26/15.
 */
public class MyAspect {
    private OpenMon openMon;

    public OpenMon getOpenMon() {
        return openMon;
    }

    public void setOpenMon(OpenMon openMon) {
        this.openMon = openMon;
    }


    public void aroundAdvice() throws Exception {
        Object mon = openMon.start("com.stevesouza.myMethod");
        Thread.sleep(100);
        openMon.stop(mon);
    }

    public void afterThrowingAdvice() throws Exception {
        openMon.exception("com.stevesouza.myException");
    }

    public static void main(String[] args) throws Exception {
        MyAspect myAspect=new MyAspect();

        System.out.println("\nJamon");
        myAspect.setOpenMon(new JamonImp());
        myAspect.aroundAdvice();
        myAspect.afterThrowingAdvice();

        System.out.println("\nJavasimon");
        myAspect.setOpenMon(new SimonImp());
        myAspect.aroundAdvice();
        myAspect.afterThrowingAdvice();

        System.out.println("\nMetrics");
        myAspect.setOpenMon(new MetricsImp());
        myAspect.aroundAdvice();
        myAspect.afterThrowingAdvice();

        System.out.println("\nNull Implementation (noop)");
        myAspect.setOpenMon(new NullImp());
        myAspect.aroundAdvice();
        myAspect.afterThrowingAdvice();
    }
}
