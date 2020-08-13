package ds.tests;

import static ds.tests.TestConstants.*;
import static org.junit.jupiter.api.Assertions.*;

import ds.AbstractSort;
import ds.HighArray;
import ds.IArray;
import ds.ISort;
import java.util.stream.LongStream;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

@TestInstance(Lifecycle.PER_CLASS)
@Execution(ExecutionMode.SAME_THREAD)
@SuppressWarnings("PMD.LawOfDemeter")
class AbstractSortTest {

  private static final String SWAP_MUST_SUCCEED = "Swap must succeed";

  @Test
  void testConcreteSort() {
    IArray high = new HighArray();
    LongStream.rangeClosed(1, 20).parallel().unordered().forEach(i -> high.insert(i));
    long[] arr = high.get();
    ISort sorter = new ConcreteSort();
    IArray sorted = sorter.sort(high);
    long[] arr2 = sorted.get();
    assertArrayEquals(arr, arr2, "Arrays must be equal");
  }

  @Nested
  class SwapTests {
    ConcreteSort sorter;
    long[] arr;

    SwapTests() {
      IArray high = new HighArray();
      LongStream.rangeClosed(1, 20).forEach(i -> high.insert(i));
      arr = high.get();
      sorter = new ConcreteSort();
    }

    @Test
    void testSwapZeroIndex() {
      long[] a = arr.clone();
      sorter.swapZerothIndex(a);
      assertTrue(a[0] == 20 && a[19] == 1, SWAP_MUST_SUCCEED);
    }

    @Test
    void testSwapIndexOne() {
      long[] a = arr.clone();
      sorter.swapIndexOne(a);
      assertTrue(a[1] == 19 && a[18] == 2, SWAP_MUST_SUCCEED);
    }

    @Test
    void testSwapNthIndex() {
      long[] a = arr.clone();
      sorter.swapNthIndex(a);
      assertTrue(a[0] == 20 && a[19] == 1, SWAP_MUST_SUCCEED);
    }

    @Test
    void testSwapNMinusOneIndex() {
      long[] a = arr.clone();
      sorter.swapNthMinusOne(a);
      assertTrue(a[1] == 19 && a[18] == 2, SWAP_MUST_SUCCEED);
    }

    @Test
    void testSwapNegativeLeft() {
      assertThrows(
          IllegalArgumentException.class,
          () -> sorter.swapNegativeLeft(arr.clone()),
          () -> "Swap negative left must throw exception");
    }

    @Test
    void testSwapNegativeRight() {
      assertThrows(
          IllegalArgumentException.class,
          () -> sorter.swapNegativeRight(arr.clone()),
          () -> "Swap negative right must throw exception");
    }

    @Test
    void testSwapNegatives() {
      assertThrows(
          IllegalArgumentException.class,
          () -> sorter.swapNegatives(arr.clone()),
          () -> "Swap negatives must throw exception");
    }

    @Test
    void testSwapBeyondsLength() {
      assertThrows(
          IllegalArgumentException.class,
          () -> sorter.swapBeyondsLength(arr.clone()),
          () -> "Swap beyonds must throw exception");
    }

    @Test
    void testSwapBeyondsLengthPlusOne() {
      assertThrows(
          IllegalArgumentException.class,
          () -> sorter.swapBeyondsLengthPlusOne(arr.clone()),
          () -> "Swap beyonds must throw exception");
    }

    @Test
    void testSwapBeyondsLengthMinusOne() {
      long[] a = arr.clone();
      long val = a[a.length - 1];
      sorter.swapBeyondsLengthMinusOne(a);
      assertEquals(val, a[a.length - 1], "No swap occurs.");
    }

    @Test
    void testSwapBeyondLeftLength() {
      assertThrows(
          IllegalArgumentException.class,
          () -> sorter.swapBeyondLeftLength(arr.clone()),
          () -> "Swap beyond left must throw exception");
    }

    @Test
    void testSwapBeyondRightLength() {
      assertThrows(
          IllegalArgumentException.class,
          () -> sorter.swapBeyondRightLength(arr.clone()),
          () -> "Swap beyond right must throw exception");
    }

    @Test
    void testResetCounts() {
      sorter.setAndResetCounts();
      assertEquals(0, sorter.getComparisonCount(), INITIAL_VALUE_ZERO);
      assertEquals(0, sorter.getSwapCount(), INITIAL_VALUE_ZERO);
      assertEquals(0, sorter.getTimeComplexity(), INITIAL_VALUE_ZERO);
      assertEquals(0, sorter.getCopyCount(), INITIAL_VALUE_ZERO);
    }
  }

  static class ConcreteSort extends AbstractSort {
    @Override
    protected void sort(long[] a, int length) {
      for (int i = 0; i < length; i++) swap(a, i, i);
    }

    void swapZerothIndex(long... a) {
      swap(a, 0, 19);
    }

    void swapIndexOne(long... a) {
      swap(a, 1, 18);
    }

    void swapNthIndex(long... a) {
      swap(a, 19, 0);
    }

    void swapNthMinusOne(long... a) {
      swap(a, 18, 1);
    }

    void swapNegativeLeft(long... a) {
      swap(a, -10, 0);
    }

    void swapNegativeRight(long... a) {
      swap(a, 0, -10);
    }

    void swapNegatives(long... a) {
      swap(a, -5, -10);
    }

    void swapBeyondRightLength(long... a) {
      swap(a, 0, a.length);
    }

    void swapBeyondLeftLength(long... a) {
      swap(a, a.length, 0);
    }

    void swapBeyondsLength(long... a) {
      swap(a, a.length, a.length);
    }

    void swapBeyondsLengthPlusOne(long... a) {
      swap(a, a.length + 1, a.length + 1);
    }

    void swapBeyondsLengthMinusOne(long... a) {
      swap(a, a.length - 1, a.length - 1);
    }

    void setAndResetCounts() {
      innerLoopCount = outerLoopCount = comparisonCount = copyCount = swapCount = 190;
      resetCounts();
    }
  }
}
