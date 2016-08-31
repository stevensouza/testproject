#!/bin/bash

echo '******* afterthrowing'
java -classpath $CLASSPATH:./aspectj_compiletime/target/aspectj_compiletime-1.0-SNAPSHOT.jar  com.stevesouza.aspectj.javastyle.afterthrowing.MyClass

echo '******* aspectconstants'
java -classpath $CLASSPATH:./aspectj_compiletime/target/aspectj_compiletime-1.0-SNAPSHOT.jar  com.stevesouza.aspectj.javastyle.aspectconstants.MyClass

echo '******* constructor'
java -classpath $CLASSPATH:./aspectj_compiletime/target/aspectj_compiletime-1.0-SNAPSHOT.jar  com.stevesouza.aspectj.javastyle.constructor.MyClass

echo '******* context'
java -classpath $CLASSPATH:./aspectj_compiletime/target/aspectj_compiletime-1.0-SNAPSHOT.jar  com.stevesouza.aspectj.javastyle.context.MyClass

echo '******* annotations'
java -classpath $CLASSPATH:./aspectj_compiletime/target/aspectj_compiletime-1.0-SNAPSHOT.jar  com.stevesouza.aspectj.javastyle.annotations.MyClass

echo '******* field'
java -classpath $CLASSPATH:./aspectj_compiletime/target/aspectj_compiletime-1.0-SNAPSHOT.jar  com.stevesouza.aspectj.javastyle.field.MyClass

echo '******* inheritance'
java -classpath $CLASSPATH:./aspectj_compiletime/target/aspectj_compiletime-1.0-SNAPSHOT.jar  com.stevesouza.aspectj.javastyle.inheritance.MyClass

echo '******* method'
java -classpath $CLASSPATH:./aspectj_compiletime/target/aspectj_compiletime-1.0-SNAPSHOT.jar  com.stevesouza.aspectj.javastyle.method.MyClass

echo '******* method.setters'
java -classpath $CLASSPATH:./aspectj_compiletime/target/aspectj_compiletime-1.0-SNAPSHOT.jar  com.stevesouza.aspectj.javastyle.method.setters.MyClass

echo '******* unkinded'
java -classpath $CLASSPATH:./aspectj_compiletime/target/aspectj_compiletime-1.0-SNAPSHOT.jar  com.stevesouza.aspectj.javastyle.unkinded.MyClass

echo '******* nativestyle.method'
java -classpath $CLASSPATH:./aspectj_compiletime/target/aspectj_compiletime-1.0-SNAPSHOT.jar  com.stevesouza.aspectj.nativestyle.method.MyClass
