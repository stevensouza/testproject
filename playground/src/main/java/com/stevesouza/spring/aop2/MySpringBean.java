/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.stevesouza.spring.aop2;

/**
 *
 * @author stevesouza
 * 
 * from command line: mvn exec:java -Dexec.mainClass="com.stevesouza.spring.aop2.SpringDriver" -Dexec.classpathScope=runtime -X
 * 
 */
public class MySpringBean {
     public void myMethod() throws Exception {
        Thread.sleep(250);
    }


    public void anotherMethod1() throws Exception {
        Thread.sleep(10);
    }

    public void methodThrowingException() throws Exception {
        throw new Exception("file io exception simulation");
    }   
}
