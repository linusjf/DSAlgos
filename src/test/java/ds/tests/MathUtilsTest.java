package ds.tests;

import static ds.MathUtils.isOdd;
import static org.joor.Reflect.*;
import static org.junit.jupiter.api.Assertions.*;

import ds.MathUtils;
import org.joor.ReflectException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

@TestInstance(Lifecycle.PER_CLASS)
@Execution(ExecutionMode.SAME_THREAD)
@SuppressWarnings({"PMD.LawOfDemeter", "PMD.DataflowAnomalyAnalysis"})
@DisplayName("MathUtilsTest")
class MathUtilsTest {

  @Test
  @DisplayName("MathUtilsTest.testPrivateConstructor")
  void testPrivateConstructor() {
    assertThrows(
        ReflectException.class,
        () -> on(MathUtils.class).create(),
        "Private constructor throws exception.");
  }

  @Test
  @DisplayName("MathUtilsTest.testZero")
  void testZero() {
    assertFalse(isOdd(0), "Zero is even!");
  }

  @Test
  @DisplayName("MathUtilsTest.testOne")
  void testOne() {
    assertTrue(isOdd(1), "One is odd!");
  }

  @Test
  @DisplayName("MathUtilsTest.testMinusOne")
  void testMinusOne() {
    assertTrue(isOdd(-1), "Minus One is odd!");
  }

  @Test
  @DisplayName("MathUtilsTest.testTwo")
  void testTwo() {
    assertFalse(isOdd(2), "Two is even!");
  }

  @Test
  @DisplayName("MathUtilsTest.testMinusTwo")
  void testMinusTwo() {
    assertFalse(isOdd(-2), "Minus Two is even!");
  }

  @Test
  @DisplayName("MathUtilsTest.testMaxValue")
  void testMaxValue() {
    assertTrue(isOdd(Integer.MAX_VALUE), () -> Integer.MAX_VALUE + " is odd!");
  }

  @Test
  @DisplayName("MathUtilsTest.testMinValue")
  void testMinValue() {
    assertFalse(isOdd(Integer.MIN_VALUE), () -> Integer.MIN_VALUE + " is even!");
  }
  
  @Test
  @DisplayName("MathUtilsTest.testInRangeLeft")
  void testInRangeLeft() {
    assertTrue(inRange(Integer.MIN_VALUE, Integer.MAX_VALUE,
          Integer.MIN_VALUE));
  }
  
  @Test
  @DisplayName("MathUtilsTest.testInRangeRight")
  void testInRangeRight() {
    assertTrue(inRange(Integer.MIN_VALUE, Integer.MAX_VALUE,
          Integer.MAX_VALUE - 1));
  }
  
  @Test
  @DisplayName("MathUtilsTest.testInRangeMid")
  void testInRangeMid() {
    assertTrue(inRange(Integer.MIN_VALUE, Integer.MAX_VALUE,0));
  }
  
  @Test
  @DisplayName("MathUtilsTest.testInRangeInclusiveMid")
  void testInRangeInclusiveMid() {
    assertTrue(inRange(Integer.MIN_VALUE, Integer.MAX_VALUE,0));
  }
  
  @Test
  @DisplayName("MathUtilsTest.testInRangeInclusiveLeft")
  void testInRangeInclusiveLeft() {
    assertTrue(inRange(Integer.MIN_VALUE, Integer.MAX_VALUE,Integer.MIN_VALUE));
  }
  
  @Test
  @DisplayName("MathUtilsTest.testInRangeInclusiveRight")
  void testInRangeInclusiveRight() {
    assertTrue(inRange(Integer.MIN_VALUE, Integer.MAX_VALUE,Integer.MAX_VALUE));
  }
}
