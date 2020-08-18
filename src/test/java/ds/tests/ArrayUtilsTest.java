package ds.tests;

import static ds.ArrayUtils.isSorted;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;
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

  @Test
  @DisplayName("ArrayUtilsTest.testSorted")
  void testSorted() {
    long[] arr = {1L, 2L, 5L, 7L, 8L, 0L, 0L, 0L, 0L, 0L};
    int length = 5;
    assertTrue(isSorted(arr, length), "Array is sorted!");
  }

  @DisplayName("ArrayUtilsTest.testUnSorted")
  @Test
  void testUnSorted() {
    long[] arr = {1L, 2L, 5L, 7L, 6L, 0L, 0L, 0L, 0L, 0L};
    int length = 7;
    assertFalse(isSorted(arr, length), "Array is unsorted!");
  }

  @Test
  @DisplayName("ArrayUtilsTest.testEmptyArray")
  void testEmptyArray() {
    long[] arr = new long[0];
    int length = 0;
    assertTrue(isSorted(arr, length), "Empty array is sorted!");
  }

  @DisplayName("ArrayUtilsTest.testFullArray")
  @Test
  void testFullArray() {
    long[] arr = {1L, 2L, 5L, 7L, 8L, 15L, 18L, 20L, 20L, 20L};
    int length = 10;
    assertTrue(isSorted(arr, length), "Full array is sorted!");
  }

  @DisplayName("ArrayUtilsTest.testLessThanFullArray")
  @Test
  void testLessThanFullArray() {
    long[] arr = {1L, 2L, 5L, 7L, 8L, 15L, 18L, 20L, 20L, 0L};
    int length = 9;
    assertTrue(isSorted(arr, length), "Less than full array is sorted!");
  }

  @DisplayName("ArrayUtilsTest.testLengthZero")
  @Test
  void testLengthZero() {
    long[] arr = new long[10];
    int length = 0;
    assertTrue(isSorted(arr, length), "Length zero array is sorted!");
  }

  @DisplayName("ArrayUtilsTest.testLengthOne")
  @Test
  void testLengthOne() {
    long[] arr = new long[10];
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
    long[] arr = new long[10];
    int length = -1;
    IllegalArgumentException iae =
        assertThrows(
            IllegalArgumentException.class, () -> isSorted(arr, length), "Exception expected!");
    Optional<String> msg = Optional.ofNullable(iae.getMessage());
    String errMsg = msg.orElse("");
    int val = Integer.parseInt(errMsg.replaceAll("[A-Za-z. ]", ""));
    assertTrue(val < 0 || val > arr.length, " -1 expected.");
  }

  @SuppressWarnings("PMD.JUnitTestContainsTooManyAsserts")
  @Test
  @DisplayName("ArrayUtilsTest.testExcessiveLength")
  void testExcessiveLength() {
    long[] arr = new long[10];
    int length = 11;
    IllegalArgumentException iae =
        assertThrows(
            IllegalArgumentException.class, () -> isSorted(arr, length), "Exception expected!");
    Optional<String> msg = Optional.ofNullable(iae.getMessage());
    String errMsg = msg.orElse("");
    int val = Integer.parseInt(errMsg.replaceAll("[\\D]", ""));
    assertTrue(val < 0 || val > arr.length, " 11 expected.");
  }
}
