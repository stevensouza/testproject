package com.stevesouza.aspectj.nativestyle.complex;

/**
 Tests call, withincode (any pointcut directly in a method), cflow (any pointcuts directly in the method as well as any pointcuts in the
 code that any of the methods call too).
 */
public aspect NativeAspect {

    // Note within does all pointcuts within the class including this and static and constructors etc.
    // this() would not do static methods but only instance ones.
    pointcut traced() : within(MyClass)  && call(* MyClass.myMethod3());
    // withincode has any direct pointcuts within myMethod1 body included, but not any pointcuts that are in
    // the methods invoked from it. cflow would also include the entire flow below that too.  It is not efficient
    // though withincode is according at least in aspectj book.
    pointcut withinMethod() : withincode(* MyClass.myMethod1(..));
    pointcut cflowMethod() : cflow(call(* MyClass.myMethod4(..))) && !within(NativeAspect);

    before() : traced() {
        System.out.println("\nBefore call(): "+ thisJoinPoint);
        // this will display info on what method called call(myMethod3())
        System.out.println(" thisEnclosingJoinPointStaticPart: "+thisEnclosingJoinPointStaticPart);
    }

    before() : withinMethod() {
        System.out.println("\nBefore withincode(): "+ thisJoinPoint);
        // this will display info on what method called call(myMethod3())
        System.out.println(" thisEnclosingJoinPointStaticPart: "+thisEnclosingJoinPointStaticPart);
    }

    before() : cflowMethod() {
        System.out.println("\nBefore cflow(): "+ thisJoinPoint);
        // this will display info on what method called call(myMethod3())
        System.out.println(" thisEnclosingJoinPointStaticPart: "+thisEnclosingJoinPointStaticPart);
    }

    after() : traced()  {
        System.out.println("After call(): "+thisJoinPoint);
        System.out.println(" thisEnclosingJoinPointStaticPart: "+thisEnclosingJoinPointStaticPart);
    }

}
