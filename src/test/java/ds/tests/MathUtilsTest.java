package ds.tests;

import static ds.MathUtils.isOdd;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

@TestInstance(Lifecycle.PER_CLASS)
@Execution(ExecutionMode.SAME_THREAD)
@SuppressWarnings({"PMD.LawOfDemeter", "PMD.DataflowAnomalyAnalysis"})
class MathUtilsTest {

  @Test
  void testZero() {
    assertFalse(isOdd(0), "Zero is even!");
  }

  @Test
  void testOne() {
    assertTrue(isOdd(1), "One is odd!");
  }

  @Test
  void testMinusOne() {
    assertTrue(isOdd(-1), "Minus One is odd!");
  }

  @Test
  void testTwo() {
    assertFalse(isOdd(2), "Two is even!");
  }

  @Test
  void testMinusTwo() {
    assertFalse(isOdd(-2), "Minus Two is even!");
  }

  @Test
  void testMaxValue() {
    assertTrue(isOdd(Integer.MAX_VALUE), () -> Integer.MAX_VALUE + " is odd!");
  }

  @Test
  void testMinValue() {
    assertFalse(isOdd(Integer.MIN_VALUE), () -> Integer.MIN_VALUE + " is even!");
  }
}
