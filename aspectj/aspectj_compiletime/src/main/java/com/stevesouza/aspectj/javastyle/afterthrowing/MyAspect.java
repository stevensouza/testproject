package com.stevesouza.aspectj.javastyle.afterthrowing;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Aspect that advises exceptions being thrown
 *
 */

@Aspect
public class MyAspect  {

  private ThreadLocal<Throwable> lastLoggedException = new ThreadLocal<Throwable>();
    private Map m = Collections.synchronizedMap(new LinkedHashMap());

  //@Pointcut("(execution (* *.*(..)) || execution (*.new(..)) || initialization(*.new(..)) ||  staticinitialization(*))  && !within(MyAspect+)  && !cflow(adviceexecution())")
  // note preinitialization gives bytecode errors and that is why it is excluded.
    @Pointcut("!preinitialization(*.new(..))")
  public void all() {

  }

    @Pointcut("all()  && !within(MyAspect+) && !cflow(adviceexecution())")
    public void myExceptions() {
    }

    // linkedHashMap removeEldestEntry - http://docs.oracle.com/javase/6/docs/api/java/util/LinkedHashMap.html#removeEldestEntry(java.util.Map.Entry)
    // must be threadsafe
    // key is exception, value could be timestamp
    // or key is thread id, and value is exception and timestamp
//    protected boolean removeEldestEntry(Map.Entry eldest){
//        return size() > MAX_SIZE;
//    }
    // also this may need to be done to have last element tracked and if so i wouldn' tneed to add a date
//    public LinkedHashMap(int initialCapacity,
//                         float loadFactor,
//                         boolean accessOrder)
   // Map m = Collections.synchronizedMap(new LinkedHashMap(...));

    // from eldest documentation - note i don't think i can get away with removing just eldest but say ALL older than a specified time.
    // note there is only ever 1 entry per thread.
//    * @param    eldest The least recently inserted entry in the map, or if
//            *           this is an access-ordered map, the least recently accessed
//    *           entry.  This is the entry that will be removed it this
//            *           method returns <tt>true</tt>.  If the map was empty prior
//    *           to the <tt>put</tt> or <tt>putAll</tt> invocation resulting
//    *           in this invocation, this will be the entry that was just
//    *           inserted; in other words, if the map contains a single
//    *           entry, the eldest entry is also the newest.
//    * @return   <tt>true</tt> if the eldest entry should be removed
//    *           from the map; <tt>false</tt> if it should be retained.
//            */

    // **** SOLUTION ***maybe check past in entry.  if it is older than specified time iterate full collection removing all older than specified time
    // and return false.  Note removeEldest is called from put which will be synchronized. Map m = Collections.synchronizedMap(new LinkedHashMap(...));
  //  This method typically does not modify the map in any way, instead allowing the map to modify itself as directed by its return value. It is permitted for this method to modify the map directly, but if it does so, it must return false (indicating that the map should not attempt any further modification). The effects of returning true after modifying the map from within this method are unspecified.

    // look at the following methods javadoc by pasting this in the code and opening in intellij
    //         LinkedHashMap l = new LinkedHashMap();

//    protected boolean removeEldestEntry(Map.Entry<K,V> eldest) {
//        return false;
//    }


    // threadlocal won't work as it never removes the stack trace for a given thread.
    // p 275 aspectj book
    @AfterThrowing(pointcut = "myExceptions()", throwing = "e")
    public void myAfterThrowing(JoinPoint joinPoint, Throwable e) {
        if (lastLoggedException.get()!=e) {
            lastLoggedException.set(e);
            System.out.println();
            System.out.println("Exception: "+e);
            System.out.println(" jp.getKind()=" + joinPoint.getKind());
            System.out.println(" jp.getStaticPart()="+joinPoint.getStaticPart());
            Object[] argValues = joinPoint.getArgs();
            Signature signature = joinPoint.getSignature();
            System.out.println(" jp.getSignature().getClass()="+signature.getClass());
            // Note would have to look at all the special cases here.
            if (signature instanceof MethodSignature) {
                MethodSignature methodSignature =  (MethodSignature) signature;
                String[]argNames = methodSignature.getParameterNames();
                for (int i = 0; i < argNames.length; i++) {
                    printMe("  argName, argValue", argNames[i] + ", " + argValues[i]);
                }
            }
        }

    }



    private void printMe(String type, Object message) {
        System.out.println(" "+type + " : " + message);
    }




}
