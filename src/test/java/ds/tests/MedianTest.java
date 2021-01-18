package ds.tests;

import static org.junit.jupiter.api.Assertions.*;

import ds.Median;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

@DisplayName("MedianTest")
@TestInstance(Lifecycle.PER_CLASS)
@Execution(ExecutionMode.SAME_THREAD)
class MedianTest {

  @Test
  @DisplayName("MedianTest.testEmptyArray")
  void testEmptyArray() {
    Median median = new Median(new long[0]);
    assertEquals(Double.NaN, median.find(), "NaN expected.");
  }

  @Test
  @DisplayName("MedianTest.testSingleElementArray")
  void testSingleElementArray() {
    long[] arr = {23};
    Median median = new Median(arr);
    assertEquals(23, median.find(), "23 expected.");
  }

  @Test
  @DisplayName("MedianTest.testTwoElementArray")
  void testTwoElementArray() {
    long[] arr = {23, 24};
    Median median = new Median(arr);
    assertEquals(23.5, median.find(), "23.5 expected.");
  }

  @Test
  @DisplayName("MedianTest.testThreeElementArray")
  void testThreeElementArray() {
    long[] arr = {23, 21, 24};
    Median median = new Median(arr);
    assertEquals(23, median.find(), "23 expected.");
  }

  @Test
  @DisplayName("MedianTest.testMedianEven")
  void testMedianEven() {
    long[] arr = {23, 21, 20, 18, 17, 16, 0, 10, 9, 10};
    Median median = new Median(arr);
    assertEquals(16.5f, median.find(), "16.5 expected.");
  }

  @Test
  @DisplayName("MedianTest.testMedianOdd")
  void testMedianOdd() {
    long[] arr = {23, 21, 20, 18, 18, 17, 16, 0, 10, 9, 10};
    Median median = new Median(arr);
    assertEquals(17.0f, median.find(), "17 expected.");
  }
}
