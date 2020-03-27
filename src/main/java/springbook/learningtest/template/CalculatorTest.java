package springbook.learningtest.template;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class CalculatorTest {
    Calculator calculator;
    String numFilePath;

    @Before
    public void setUp(){
        calculator = new Calculator();
        numFilePath = getClass().getResource("numbers.txt").getPath();
    }
    @Test
    public void sumOfNumbers() throws Exception {
        assertEquals(Integer.valueOf(10), calculator.calcSum(numFilePath));
    }

    @Test
    public void multiplyOfNumbers() throws Exception {
        assertEquals(Integer.valueOf(24), calculator.calcMultiply(numFilePath));
    }

    @Test
    public void concatenateString() throws IOException {
        assertEquals("1234", calculator.concatenate(numFilePath));
    }
}