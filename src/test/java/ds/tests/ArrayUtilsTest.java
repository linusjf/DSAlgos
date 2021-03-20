package ds.tests;

import static ds.ArrayUtils.*;
import static ds.tests.TestConstants.*;
import static org.joor.Reflect.*;
import static org.junit.jupiter.api.Assertions.*;

import ds.ArrayUtils;
import java.util.Optional;
import java.util.Random;
import java.util.stream.LongStream;
import org.joor.ReflectException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

@DisplayName("ArrayUtilsTest")
@TestInstance(Lifecycle.PER_CLASS)
@Execution(ExecutionMode.SAME_THREAD)
@SuppressWarnings({"PMD.LawOfDemeter", "PMD.DataflowAnomalyAnalysis"})
class ArrayUtilsTest {
  private static final String EXCEPTION_EXPECTED = "Exception expected.";

  @Test
  @DisplayName("ArrayUtilsTest.testPrivateConstructor")
  void testPrivateConstructor() {
    assertThrows(
        ReflectException.class,
        () -> on(ArrayUtils.class).create(),
        "Private constructor throws exception.");
  }

  @Test
  @DisplayName("ArrayUtilsTest.testSorted")
  void testSorted() {
    long[] arr = LongStream.rangeClosed(1, HUNDRED).toArray();
    assertTrue(isSorted(arr, HUNDRED >> 1), "Array is sorted!");
  }

  @DisplayName("ArrayUtilsTest.testUnSorted")
  @Test
  void testUnSorted() {
    Random random = new Random();
    long[] arr = random.longs().limit(HUNDRED).toArray();
    assertFalse(isSorted(arr, HUNDRED), "Array is unsorted!");
  }

  @Test
  @DisplayName("ArrayUtilsTest.testEmptyArray")
  void testEmptyArray() {
    long[] arr = new long[0];
    assertTrue(isSorted(arr), "Empty array is sorted!");
  }

  @DisplayName("ArrayUtilsTest.testFullArray")
  @Test
  void testFullArray() {
    long[] arr = LongStream.rangeClosed(1, HUNDRED).toArray();
    assertTrue(isSorted(arr), "Full array is sorted!");
  }

  @DisplayName("ArrayUtilsTest.testFullArrayUnsorted")
  @Test
  void testFullArrayUnsorted() {
    Random random = new Random();
    long[] arr = random.longs().limit(HUNDRED).toArray();
    assertFalse(isSorted(arr), "Full array is unsorted!");
  }

  @DisplayName("ArrayUtilsTest.testLessThanFullArray")
  @Test
  void testLessThanFullArray() {
    long[] arr = LongStream.rangeClosed(1, HUNDRED).toArray();
    int length = HUNDRED >> 1;
    assertTrue(isSorted(arr, length), "Less than full array is sorted!");
  }

  @DisplayName("ArrayUtilsTest.testLengthZero")
  @Test
  void testLengthZero() {
    long[] arr = new long[TEN];
    int length = 0;
    assertTrue(isSorted(arr, length), "Length zero array is sorted!");
  }

  @DisplayName("ArrayUtilsTest.testLengthOne")
  @Test
  void testLengthOne() {
    long[] arr = new long[TEN];
    arr[0] = 1;
    int length = 1;
    assertTrue(isSorted(arr, length), "One element array is sorted!");
  }

  @DisplayName("ArrayUtilsTest.testSizeOne")
  @Test
  void testSizeOne() {
    long[] arr = new long[1];
    int length = 1;
    arr[0] = 1;
    assertTrue(isSorted(arr, length), "Size one array is sorted!");
  }

  @SuppressWarnings("PMD.JUnitTestContainsTooManyAsserts")
  @Test
  @DisplayName("ArrayUtilsTest.testNegativeLength")
  void testNegativeLength() {
    long[] arr = new long[TEN];
    int length = -1;
    IllegalArgumentException iae =
        assertThrows(
            IllegalArgumentException.class, () -> isSorted(arr, length), EXCEPTION_EXPECTED);
    Optional<String> msg = Optional.ofNullable(iae.getMessage());
    String errMsg = msg.orElse("");
    int val = Integer.parseInt(errMsg.replaceAll("[A-Za-z. ]", ""));
    assertTrue(val < 0 || val > arr.length, " -1 expected.");
  }

  @Test
  @DisplayName("ArrayUtilsTest.testExcessiveLength")
  void testExcessiveLength() {
    long[] arr = new long[TEN];
    int length = TEN + 1;
    IllegalArgumentException iae =
        assertThrows(
            IllegalArgumentException.class, () -> isSorted(arr, length), EXCEPTION_EXPECTED);
    Optional<String> msg = Optional.ofNullable(iae.getMessage());
    String errMsg = msg.orElse("");
    int val = Integer.parseInt(errMsg.replaceAll("[\\D]", ""));
    assertTrue(val < 0 || val > arr.length, (TEN + 1) + " expected.");
  }

  @Test
  @DisplayName("ArrayUtilsTest.testDoubleCapacity")
  void testDoubleCapacity() {
    long[] arr = ArrayUtils.getDoubleCapacity(TEN);
    assertEquals(SCORE, arr.length, "Double the value expected.");
  }

  @Test
  @DisplayName("ArrayUtilsTest.testDoubleCapacityOdd")
  void testDoubleCapacityOdd() {
    long[] arr = ArrayUtils.getDoubleCapacity(TEN + 1);
    assertEquals(SCORE + 2, arr.length, "Double the value expected.");
  }

  @Test
  @DisplayName("ArrayUtilsTest.testDoubleCapacityZero")
  void testDoubleCapacityZero() {
    long[] arr = ArrayUtils.getDoubleCapacity(0);
    assertEquals(0, arr.length, "Zero value expected.");
  }

  @Test
  @DisplayName("ArrayUtilsTest.testMaxInt")
  void testMaxInt() {
    assertThrows(
        IllegalStateException.class,
        () -> ArrayUtils.getDoubleCapacity(Integer.MAX_VALUE),
        EXCEPTION_EXPECTED);
  }

  @Test
  @DisplayName("ArrayUtilsTest.testNegativeSize")
  void testNegativeSize() {
    assertThrows(
        IllegalStateException.class,
        () -> ArrayUtils.getDoubleCapacity(Integer.MIN_VALUE),
        EXCEPTION_EXPECTED);
  }

  @Test
  @DisplayName("ArrayUtilsTest.testNegativeSize")
  void testNegativeSizeScore() {
    assertThrows(
        IllegalStateException.class,
        () -> ArrayUtils.getDoubleCapacity(-1 * SCORE),
        EXCEPTION_EXPECTED);
  }

  @Test
  @DisplayName("ArrayUtilsTest.testSwapLessThanTrue")
  void testSwapLessThanTrue() {
    long[] a = {1, 2, 3};
    assertTrue(swapIfLessThan(a, 1, 2));
  }

  @Test
  @DisplayName("ArrayUtilsTest.testSwapLessThanFalse")
  void testSwapLessThanFalse() {
    long[] a = {1, 2, 2};
    assertFalse(swapIfLessThan(a, 1, 2));
  }
}
