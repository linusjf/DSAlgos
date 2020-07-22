package ds.tests;

import static ds.ArrayUtils.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.logging.Logger;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

@TestInstance(Lifecycle.PER_CLASS)
@Execution(ExecutionMode.SAME_THREAD)
@SuppressWarnings("PMD.LawOfDemeter")
class ArrayUtilsTest {
  private static final Logger LOGGER = Logger.getLogger(ArrayUtilsTest.class.getName());

  @Test
  void testSorted() {
    long[] arr = {1L, 2L, 5L, 7L, 8L, 0L, 0L, 0L, 0L, 0L};
    int length = 5;
    assertTrue(isSorted(arr, length), "Array is sorted!");
  }

  @Test
  void testUnSorted() {
    long[] arr = {1L, 2L, 5L, 7L, 6L, 0L, 0L, 0L, 0L, 0L};
    int length = 7;
    assertFalse(isSorted(arr, length), "Array is unsorted!");
  }

  @Test
  void testEmptyArray() {
    long[] arr = new long[10];
    int length = 0;
    assertTrue(isSorted(arr, length), "Empty array is sorted!");
  }

  @Test
  void testOneElementArray() {
    long[] arr = new long[10];
    int length = 1;
    arr[0] = 1;
    assertTrue(isSorted(arr, length), "One element array is sorted!");
  }

  @Test
  void testNegativeLength() {
    long[] arr = new long[10];
    int length = -1;
    arr[0] = 1;
    assertThrows(
        ArrayIndexOutOfBoundsException.class, () -> isSorted(arr, length), "Exception expected!");
  }

  @Test
  void testExcessiveLength() {
    long[] arr = new long[10];
    int length = 11;
    arr[0] = 1;
    assertThrows(
        ArrayIndexOutOfBoundsException.class, () -> isSorted(arr, length), "Exception expected!");
  }
}
