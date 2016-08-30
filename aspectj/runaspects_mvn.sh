#!/bin/bash

cd aspectj_compiletime/

echo '******* afterthrowing'
mvn  exec:java -Dexec.mainClass="com.stevesouza.aspectj.javastyle.afterthrowing.MyClass" -Dexec.classpathScope=runtime -X

echo '******* aspectconstants'
mvn  exec:java -Dexec.mainClass="com.stevesouza.aspectj.javastyle.aspectconstants.MyClass" -Dexec.classpathScope=runtime -X

echo '******* constructor'
mvn  exec:java -Dexec.mainClass="com.stevesouza.aspectj.javastyle.constructor.MyClass" -Dexec.classpathScope=runtime -X

echo '******* afterthrowing'
mvn  exec:java -Dexec.mainClass="com.stevesouza.aspectj.javastyle.context.MyClass" -Dexec.classpathScope=runtime -X

echo '******* field'
mvn  exec:java -Dexec.mainClass="com.stevesouza.aspectj.javastyle.field.MyClass" -Dexec.classpathScope=runtime -X

echo '******* inheritance'
mvn  exec:java -Dexec.mainClass="com.stevesouza.aspectj.javastyle.inheritance.MyClass" -Dexec.classpathScope=runtime -X

echo '******* method'
mvn  exec:java -Dexec.mainClass="com.stevesouza.aspectj.javastyle.method.MyClass" -Dexec.classpathScope=runtime -X

echo '******* method.setters'
mvn  exec:java -Dexec.mainClass="com.stevesouza.aspectj.javastyle.method.setters.MyClass" -Dexec.classpathScope=runtime -X

echo '******* unkinded'
mvn  exec:java -Dexec.mainClass="com.stevesouza.aspectj.javastyle.unkinded.MyClass" -Dexec.classpathScope=runtime -X

echo '******* nativestyle.method'
mvn  exec:java -Dexec.mainClass="com.stevesouza.aspectj.nativestyle.method.MyClass" -Dexec.classpathScope=runtime -X

cd ..
