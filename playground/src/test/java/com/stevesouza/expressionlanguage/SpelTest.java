/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.stevesouza.expressionlanguage;

import java.util.HashMap;
import java.util.Map;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;


/**
 *
 * @author stevesouza
 */
public class SpelTest {
    private ExpressionParser parser;
    private StandardEvaluationContext context;
    
    public SpelTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        parser = new SpelExpressionParser();  
        context = new StandardEvaluationContext();
    }
    
    @After
    public void tearDown() {
    }

    
    @Test
    public void testMath() {       
        Expression exp = parser.parseExpression("10*55");  
        assertThat(exp.getValue(Integer.class)).isEqualTo(550);
    }
    
    @Test
    public void testString() {
        Expression exp = parser.parseExpression("'Hello SPEL'");  
        String message = (String) exp.getValue();  
        assertThat(message).isEqualTo("Hello SPEL");
    }
    
    @Test
    public void testEvalContext1() {
        context.setVariable("myInt", 10);
        Expression exp = parser.parseExpression("#myInt * #myInt - 10");
        int value =  exp.getValue(context, Integer.class);  

        assertThat(value).isEqualTo(90);

    }
    
        @Test
    public void testEvalContext2() {
        Map<String, Object> map = new HashMap();
        map.put("myInt", 10);
        context.setVariables(map);
        Expression exp = parser.parseExpression("#myInt * #myInt - 10");
        int value =  exp.getValue(context, Integer.class);  

        assertThat(value).isEqualTo(90);
    }

}
