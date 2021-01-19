package ds.tests;

import static org.junit.jupiter.api.Assertions.*;

import ds.Median;
import java.util.Arrays;
import java.util.Random;
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
    System.out.println("testEmptyArray");
    Median median = new Median(new long[0]);
    assertEquals(Double.NaN, median.find(), "NaN expected.");
  }

  @Test
  @DisplayName("MedianTest.testSingleElementArray")
  void testSingleElementArray() {
    System.out.println("testSingleElementArray");
    long[] arr = {23};
    Median median = new Median(arr);
    assertEquals(23, median.find(), "23 expected.");
  }

  @Test
  @DisplayName("MedianTest.testTwoElementArray")
  void testTwoElementArray() {
    System.out.println("testTwoElementArray");
    long[] arr = {23, 24};
    Median median = new Median(arr);
    assertEquals(23.5, median.find(), "23.5 expected.");
  }

  @Test
  @DisplayName("MedianTest.testThreeElementArray")
  void testThreeElementArray() {
    System.out.println("testThreeElementArray");
    long[] arr = {23, 21, 24};
    Median median = new Median(arr);
    assertEquals(23, median.find(), "23 expected.");
  }

  @Test
  @DisplayName("MedianTest.testMedianEven")
  void testMedianEven() {
    System.out.println("testMedianEven");
    long[] arr = {23, 21, 20, 18, 17, 16, 0, 10, 9, 10};
    Median median = new Median(arr.clone());
    Arrays.sort(arr);
    double expected = (arr[4] + arr[5]) / 2.0f;
    assertEquals(expected, median.find(), () -> expected + " expected.");
  }

  @Test
  @DisplayName("MedianTest.testMedianOdd")
  void testMedianOdd() {
    System.out.println("testMedianOdd");
    long[] arr = {23, 21, 20, 18, 18, 17, 16, 0, 10, 9, 10};
    Median median = new Median(arr.clone());
    Arrays.sort(arr);
    assertEquals(arr[5], median.find(), () -> arr[5] + " expected.");
  }

  @Test
  @DisplayName("MedianTest.testRandomEven")
  void testRandomEven() {
    System.out.println("testRandomEven");
    long[] arr = new long[10_000];
    Random random = new Random();
    for (int i = 0; i < 10_000; i++) arr[i] = random.nextInt();
    Median median = new Median(arr.clone());
    Arrays.sort(arr);
    double medianVal = 0.5 * (arr[4999] + arr[5000]);
    assertEquals(medianVal, median.find(), "Sorted array value must match calculated value.");
  }

  @Test
  @DisplayName("MedianTest.testRandomOdd")
  void testRandomOdd() {
    System.out.println("testRandomOdd");
    long[] arr = new long[10_001];
    Random random = new Random();
    for (int i = 0; i < 10_001; i++) arr[i] = random.nextInt();
    Median median = new Median(arr.clone());
    Arrays.sort(arr);
    double medianVal = arr[5000];
    assertEquals(medianVal, median.find(), "Sorted array value must match calculated value.");
  }
}
