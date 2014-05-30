package com.stevesouza.spring.aop;

import com.jamonapi.Monitor;
import com.jamonapi.MonitorFactory;
import com.stevesouza.spring.HelloSpringBean;
import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;

/**
 * Created by stevesouza on 5/24/14.
 * Samples of aspectj pointcuts...
 *
 * Other examples
 *  execution(* com.aspects.pointcut.DemoClass.*(..)) :
 *      This advice will be applied to all the methods of DemoClass.
 *  execution(* DemoClass.*(..)):
 *      You can omit the package if the DemoClass and the advice is in the same package.
 *  execution(public * DemoClass.*(..)):
 *      This advice will be applied to the public methods of DemoClass.
 *  execution(public int DemoClass.*(..)):
 *      This advice will be applied to the public methods of DemoClass and returning an int.
 *  execution(public int DemoClass.*(int, ..)):
 *      This advice will be applied to the public methods of DemoClass and returning an int and having first parameter as int.
 *  execution(public int DemoClass.*(int, int)):
 *      This advice will be applied to the public methods of DemoClass and returning an int and having both parameters as int.
 * dataAccessOperation was defined as a method with args .. and so the following would also require Account to be the first
 *    arg and it would be passed to the validateAccount method.
 *   @Before("com.xyz.myapp.SystemArchitecture.dataAccessOperation() && args(account,..)")
 *   public void accountDataAccessOperation(Account account) {    ...
 * Cool, can later reuse like this...
 *   @Before("accountDataAccessOperation(account)")
 *   public void validateAccount(Account account) {...
 */
// Spring automatically finds
// By default each aspect is a singleton within the applicationContext
@Aspect
public class MySpringAspect {
    private static final Logger LOG = Logger.getLogger(MySpringAspect.class);

//    @Pointcut("execution(* com.stevesouza.spring.MyAutowiredClass.getString(..))")

    // can remove class if the the class we are advising is in same package
    // @Pointcut("execution(* MyAutowiredClass.getString(..))")

    // Wildcards - all methods starting with get.
    @Pointcut("execution(* com.stevesouza.spring.MyAutowiredClass.get*(..))")
    // odd but must define empty method.  It isn't called.  it just lets us reuse the name 'advisedMethod' below
    // alternatively you could just put the above pointcut on each method.
    // note these annotations let this be a simple pojo.  cool
    public void advisedMethod() {
    }

     @Before("advisedMethod()")
     // note use of JoinPoint to get further info here
     public void myFirstBeforeMethod(JoinPoint pjp) {
        LOG.info("myFirstBeforeMethod() (@Before). Target method ivoked: "+pjp.toLongString());
      }

    // JoinPoint is not required.  Can have access to other args too.
    @Before("advisedMethod()")
      public void mySecondBeforeMethod() {
        LOG.info("mySecondBeforeMethod() (@Before)");
      }

    // other options
    // @After()  - always runs.  after finally
    // @AfterThrowing - runs when an exception was thron
    // @AfterReturning - after successful run
    // retVal has the return value of the invoked method
    @AfterReturning(pointcut = "advisedMethod()", returning = "retVal")
    public void myAfterReturning(String retVal) {
        LOG.info("myAfterReturning() (@AfterReturning): "+retVal);
    }

    @AfterThrowing("advisedMethod()")
    public void myAfterThrowingException() {
        LOG.info("myAfterThrowingException() (@AfterThrowing)");
    }

    @AfterThrowing(pointcut = "advisedMethod()",
            throwing="exception")
    public void myAfterThrowingException2(Throwable exception) {
        LOG.info("myAfterThrowingException2() and exception="+exception);
    }

    @Around("execution(* com.stevesouza.spring.MyAutowiredClass.getSlowMethod(..))")
    public Object doProfiling(ProceedingJoinPoint pjp) throws Throwable {
        Monitor mon = MonitorFactory.start(pjp.toLongString());
        System.out.println(pjp.getSignature());
        System.out.println(pjp.toShortString());
        System.out.println(pjp.getTarget()); // the advised object
        Object retVal = pjp.proceed();
        mon.stop();
        LOG.info(mon);
        return retVal;
    }

    @Around("com.stevesouza.spring.aop.SystemAopPointcutDefinitions.camelOperation()")
    public Object jamon(ProceedingJoinPoint pjp) throws Throwable {
        Monitor mon = MonitorFactory.start(pjp.toLongString());
        try {
            return pjp.proceed();
        } finally {
            mon.stop();
        }
    }

    /** use '..' in the args expression if you have zero or more parameters at that point
     * The values in args don't have to match the the advised methods argument names however they must match
     * the ones defined in doProfilingWithArgs.  Intellij catches the error if they don't match.
     * This is an easy way to get access to argments being passed into the original
     * method.  you can also call getArgs().
     *
     * Note because this method also starts with 'get*' it is also matched by the @Before annotation above.  Cool!
     */
    @Around("execution(* com.stevesouza.spring.MyAutowiredClass.getPassArgs(..)) && args(myString, helloSpringBean, ..)")
    public Object doProfilingWithArgs(ProceedingJoinPoint proceedingJoinPoint, String myString, HelloSpringBean helloSpringBean) throws Throwable {
        System.out.println("myString="+myString);
        System.out.println("helloSpringBean="+helloSpringBean);
        for (Object arg : proceedingJoinPoint.getArgs()) {
            LOG.info("arg="+arg);
        }

        // can also call  proceedingJoinPoint.proceed(proceedingJoinPoint.getArgs());
        // might be useful if you wanted change the arguments.
        Object retVal = proceedingJoinPoint.proceed();

        return retVal;
    }


}
