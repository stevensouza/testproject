/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.stevesouza.expressionlanguage;

import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;

/**
 *
 * @author stevesouza
 * todo:
 *  - use @Value annotation
 * -  put code in test
 * - custom annotations for automon
 * 
 * 
 *  SpELExpressionParser - The parseExpression( ) method on the SpELExpressionParser returns a SpELExpression object.  
 *  SpELExpression - This object can then be used to evaluate the expression with a call to getValue( ).
 *  EvaluationContext - An EvaluationContext allows objects, variables, functions, etc. to be used in the evaluation of the expression
 */
public class Spel {
    public static void main(String[] args) {
        ExpressionParser parser = new SpelExpressionParser();  
  
        Expression exp = parser.parseExpression("'Hello SPEL'");  
        String message = (String) exp.getValue();  
        System.out.println(message);  
        
        exp = parser.parseExpression("10*55");  
        System.out.println("10*55="+exp.getValue(Integer.class));  
    }
    
}
