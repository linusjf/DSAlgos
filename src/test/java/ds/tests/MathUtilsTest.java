package ds.tests;

import static ds.MathUtils.*;
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

  private static final String MUST_RETURN_TRUE = "Must return true.";
  private static final String MUST_RETURN_FALSE = "Must return false.";
  private static final String EXCEPTION_EXPECTED = "Exception expected.";
  private static final String EXCEPTION_NOT_EXPECTED = "Exception not expected.";

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
  @DisplayName("MathUtilsTest.testIsInRangeLeft")
  void testIsInRangeLeft() {
    assertTrue(
        isInRange(Integer.MIN_VALUE, Integer.MAX_VALUE, Integer.MIN_VALUE), MUST_RETURN_TRUE);
  }

  @Test
  @DisplayName("MathUtilsTest.testIsInRangeRight")
  void testIsInRangeRight() {
    assertTrue(
        isInRange(Integer.MIN_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE - 1), MUST_RETURN_TRUE);
  }

  @Test
  @DisplayName("MathUtilsTest.testIsNotInRangeRight")
  void testIsNotInRangeRight() {
    assertFalse(
        isInRange(Integer.MIN_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE), MUST_RETURN_FALSE);
  }

  @Test
  @DisplayName("MathUtilsTest.testIsNotInRangeRight")
  void testIsNotInRangeLeft() {
    assertFalse(
        isInRangeInclusive(Integer.MIN_VALUE + 1, Integer.MAX_VALUE, Integer.MIN_VALUE),
        MUST_RETURN_FALSE);
  }

  @Test
  @DisplayName("MathUtilsTest.testIsInRangeMid")
  void testIsInRangeMid() {
    assertTrue(isInRange(Integer.MIN_VALUE, Integer.MAX_VALUE, 0), MUST_RETURN_TRUE);
  }

  @Test
  @DisplayName("MathUtilsTest.testIsInRangeInclusiveMid")
  void testIsInRangeInclusiveMid() {
    assertTrue(isInRangeInclusive(Integer.MIN_VALUE, Integer.MAX_VALUE, 0), MUST_RETURN_TRUE);
  }

  @Test
  @DisplayName("MathUtilsTest.testIsInRangeInclusiveLeft")
  void testIsInRangeInclusiveLeft() {
    assertTrue(
        isInRangeInclusive(Integer.MIN_VALUE, Integer.MAX_VALUE, Integer.MIN_VALUE),
        MUST_RETURN_TRUE);
  }

  @Test
  @DisplayName("MathUtilsTest.testIsInRangeInclusiveRight")
  void testIsInRangeInclusiveRight() {
    assertTrue(
        isInRangeInclusive(Integer.MIN_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE),
        MUST_RETURN_TRUE);
  }

  @Test
  @DisplayName("MathUtilsTest.testGreaterThan")
  void testGreaterThan() {
    assertTrue(isGreaterThan(Integer.MIN_VALUE, Integer.MAX_VALUE), MUST_RETURN_TRUE);
  }

  @Test
  @DisplayName("MathUtilsTest.testNotGreaterThan")
  void testNotGreaterThan() {
    assertFalse(isGreaterThan(Integer.MAX_VALUE, Integer.MIN_VALUE), MUST_RETURN_FALSE);
  }

  @Test
  @DisplayName("MathUtilsTest.testNotLessThan")
  void testNotLessThan() {
    assertFalse(isLessThan(Integer.MIN_VALUE, Integer.MAX_VALUE), MUST_RETURN_FALSE);
  }

  @Test
  @DisplayName("MathUtilsTest.testLessThan")
  void testLessThan() {
    assertTrue(isLessThan(Integer.MAX_VALUE, Integer.MIN_VALUE), MUST_RETURN_TRUE);
  }

  @Test
  @DisplayName("MathUtilsTest.testRequireNotGreaterThan")
  void testRequireNotGreaterThan() {
    assertThrows(
        IllegalArgumentException.class,
        () -> requireGreaterThan(Integer.MAX_VALUE, Integer.MIN_VALUE),
        EXCEPTION_EXPECTED);
  }

  @Test
  @DisplayName("MathUtilsTest.testRequireGreaterThan")
  void testRequireGreaterThan() {
    assertDoesNotThrow(
        () -> requireGreaterThan(Integer.MIN_VALUE, Integer.MAX_VALUE), EXCEPTION_NOT_EXPECTED);
  }

  @Test
  @DisplayName("MathUtilsTest.testRequireNotLessThan")
  void testRequireNotLessThan() {
    assertThrows(
        IllegalArgumentException.class,
        () -> requireLessThan(Integer.MIN_VALUE, Integer.MAX_VALUE),
        EXCEPTION_EXPECTED);
  }

  @Test
  @DisplayName("MathUtilsTest.testRequireGreaterThan")
  void testRequireLessThan() {
    assertDoesNotThrow(
        () -> requireLessThan(Integer.MAX_VALUE, Integer.MIN_VALUE), EXCEPTION_NOT_EXPECTED);
  }

  @Test
  @DisplayName("MathUtilsTest.testRequireIsInRange")
  void testRequireIsInRange() {
    assertDoesNotThrow(
      () ->  requireInRange(Integer.MIN_VALUE, Integer.MAX_VALUE, Integer.MIN_VALUE),
        EXCEPTION_NOT_EXPECTED);
  }

  @Test
  @DisplayName("MathUtilsTest.testRequireNotInRange")
  void testRequireNotInRange() {
    assertThrows(
        IllegalArgumentException.class,
        () -> requireInRange(Integer.MIN_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE),
        EXCEPTION_EXPECTED);
  }

  @Test
  @DisplayName("MathUtilsTest.testRequireIsInRangeInclusive")
  void testRequireIsInRangeInclusive() {
    assertDoesNotThrow(
  () ->      requireInRangeInclusive(Integer.MIN_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE),
        EXCEPTION_NOT_EXPECTED);
  }

  @Test
  @DisplayName("MathUtilsTest.testRequireNotInRangeInclusive")
  void testRequireNotInRangeInclusive() {
    assertThrows(
        IllegalArgumentException.class,
        () -> requireInRangeInclusive(Integer.MIN_VALUE, Integer.MAX_VALUE - 1, Integer.MAX_VALUE),
        EXCEPTION_EXPECTED);
  }
}
