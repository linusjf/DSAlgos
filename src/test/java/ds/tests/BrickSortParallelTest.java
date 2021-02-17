package ds.tests;

import static ds.ArrayUtils.*;
import static ds.BrickSortParallel.*;
import static ds.tests.TestConstants.*;
import static org.junit.jupiter.api.Assertions.*;

import ds.AbstractSort;
import ds.BrickSortParallel;
import ds.HighArray;
import ds.IArray;
import ds.ISort;
import ds.OrdArray;
import ds.RandomUtils;
import java.util.concurrent.CompletionException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.stream.LongStream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

@TestInstance(Lifecycle.PER_CLASS)
@Execution(ExecutionMode.SAME_THREAD)
@SuppressWarnings({"PMD.LawOfDemeter", "checkstyle:classfanoutcomplexity"})
@DisplayName("BrickSortParallelTest")
class BrickSortParallelTest implements SortProvider {

  private static final String MUST_BE_EQUAL = "Must be equal";
  private static final String ZERO_TASKS_EXPECTED = "Zero tasks expected.";
  private static final String ONE_TASK_EXPECTED = "One task expected.";
  private static final String HALF_TASKS_EXPECTED = "Half tasks expected.";
  private static final String ILLEGAL_LENGTH_EXPECTED = "Illegal length expected.";

  @Test
  @DisplayName("BrickSortParallelTest.testReverseSortedOdd255")
  void testReverseSortedOdd255() {
    IArray high = new HighArray(255);
    revRange(1, 255).forEach(i -> high.insert(i));
    BrickSortComplex sorter = new BrickSortComplex();
    IArray sorted = sorter.sort(high);
    final int innerLoopCount = sorter.getInnerLoopCount();
    final int outerLoopCount = sorter.getOuterLoopCount();
    int length = high.count();
    final int oddTaskCount = computeOddTaskCount(length);
    final int evenTaskCount = computeEvenTaskCount(length);
    assertEquals(
        (oddTaskCount + evenTaskCount) * (outerLoopCount - 1) + oddTaskCount,
        innerLoopCount,
        MUST_BE_EQUAL);
    assertEquals(
        sorter.getSwapCount(),
        sorter.getComparisonCount(),
        "Comparison count must be same as swap count in reverse ordered array.");
    assertTrue(isSorted(sorted), "Array must be sorted");
    assertTrue(sorter.isSorted(), SORTED_MUST_BE_SET);
  }

  @Test
  @DisplayName("BrickSortParallelTest.testToStringClass")
  void testToStringClass() {
    AbstractSort sorter = new BrickSortParallel();
    String className = BrickSortParallel.class.getName();
    assertTrue(
        sorter.toString().startsWith(className), () -> "ToString must start with " + className);
  }

  @Test
  @DisplayName("BrickSortParallelTest.testZeroTimeComplexity")
  void testZeroTimeComplexity() {
    BrickSortComplex bsc = new BrickSortComplex();
    bsc.sortZeroLengthArray();
    assertEquals(0, bsc.getTimeComplexity(), "Time Complexity must be zero.");
    assertTrue(bsc.isSorted(), SORTED_MUST_BE_SET);
  }

  @Test
  @DisplayName("BrickSortParallelTest.testOneTimeComplexity")
  void testOneTimeComplexity() {
    BrickSortComplex bsc = new BrickSortComplex();
    bsc.sortOneLengthArray();
    assertEquals(0, bsc.getTimeComplexity(), "Time Complexity must be zero.");
    assertTrue(bsc.isSorted(), SORTED_MUST_BE_SET);
  }

  @Test
  @DisplayName("BrickSortParallelTest.testNMinusOneTimeComplexity")
  void testNMinusOneTimeComplexity() {
    BrickSortComplex bsc = new BrickSortComplex();
    bsc.sortNMinusOneLengthArray();
    assertEquals(3, bsc.getTimeComplexity(), "Time Complexity must be three.");
    assertTrue(bsc.isSorted(), SORTED_MUST_BE_SET);
  }

  @Test
  @DisplayName("BrickSortParallelTest.testReset")
  void testReset() {
    BrickSortComplex bsc = new BrickSortComplex();
    bsc.resetInternals();
    assertEquals(0, bsc.getTimeComplexity(), "Time Complexity must be reset.");
    assertEquals(0, bsc.getComparisonCount(), "Comparison count must be reset.");
    assertEquals(0, bsc.getSwapCount(), "Swap count must be reset.");
    assertEquals(0, bsc.getCopyCount(), "Copy count must be reset.");
    assertFalse(bsc.isSorted(), "sorted must be reset.");
  }

  @Test
  @DisplayName("BrickSortParallelTest.testResetAfterSort")
  void testResetAfterSort() {
    BrickSortComplex bsc = new BrickSortComplex();
    bsc.resetInternalsAfterSort();
    assertEquals(0, bsc.getTimeComplexity(), "Time Complexity must be reset.");
    assertEquals(0, bsc.getComparisonCount(), "Comparison count must be reset.");
    assertEquals(0, bsc.getSwapCount(), "Swap count must be reset.");
    assertEquals(0, bsc.getCopyCount(), "Copy count must be reset.");
    assertFalse(bsc.isSorted(), "sorted must be reset.");
  }

  @Test
  @DisplayName("BrickSortParallelTest.testStateAfterReset")
  void testStateAfterReset() {
    BrickSortComplex bsc = new BrickSortComplex();
    bsc.sortOdd();
    final int oldInnerLoopCount = bsc.getInnerLoopCount();
    final int oldOuterLoopCount = bsc.getOuterLoopCount();
    bsc.sortEven();
    final int innerLoopCount = bsc.getInnerLoopCount();
    final int outerLoopCount = bsc.getOuterLoopCount();
    assertNotEquals(oldInnerLoopCount, innerLoopCount, "Inner loop count must not be same.");
    assertEquals(oldOuterLoopCount, outerLoopCount, "Outer loop count must be same.");
  }

  @Nested
  class MyriadTests {
    @Test
    @DisplayName("BrickSortParallelTest.testSortRandom")
    void testSortRandom() {
      HighArray arr = new HighArray(MYRIAD);
      try (LongStream stream = RandomUtils.longStream().limit(MYRIAD)) {
        stream.forEach(i -> arr.insert(i));
      }
      BrickSortComplex sorter = new BrickSortComplex();
      IArray sorted = sorter.sort(arr);
      assertTrue(isSorted(sorted), "Array must be sorted.");
    }

    @Test
    @DisplayName("BrickSortParallelTest.testStreamUnSorted")
    void testStreamUnSorted() {
      IArray high = new HighArray(MYRIAD);
      IArray ord = new OrdArray(MYRIAD);
      LongStream.rangeClosed(1, MYRIAD)
          .unordered()
          .forEach(
              i -> {
                high.insert(i);
                ord.insert(i);
              });
      BrickSortComplex sorter = new BrickSortComplex();
      IArray sorted = sorter.sort(high);
      long[] extentSorted = sorted.getExtentArray();
      long[] extent = ord.getExtentArray();
      final int innerLoopCount = sorter.getInnerLoopCount();
      final int outerLoopCount = sorter.getOuterLoopCount();
      int length = high.count();
      final int oddTaskCount = computeOddTaskCount(length);
      final int evenTaskCount = computeEvenTaskCount(length);
      assertEquals((oddTaskCount + evenTaskCount) * outerLoopCount, innerLoopCount, MUST_BE_EQUAL);
      assertArrayEquals(extentSorted, extent, "Elements must be sorted and equal.");
      assertTrue(sorter.isSorted(), SORTED_MUST_BE_SET);
    }

    @Test
    @DisplayName("BrickSortParallelTest.testStreamSorted")
    void testStreamSorted() {
      IArray high = new HighArray(MYRIAD);
      IArray ord = new OrdArray(MYRIAD);
      LongStream.rangeClosed(1, MYRIAD)
          .forEach(
              i -> {
                high.insert(i);
                ord.insert(i);
              });
      BrickSortComplex sorter = new BrickSortComplex();
      IArray sorted = sorter.sort(high);
      long[] extentSorted = sorted.getExtentArray();
      long[] extent = ord.getExtentArray();
      final int innerLoopCount = sorter.getInnerLoopCount();
      final int outerLoopCount = sorter.getOuterLoopCount();
      int length = high.count();
      final int oddTaskCount = computeOddTaskCount(length);
      final int evenTaskCount = computeEvenTaskCount(length);
      assertEquals((oddTaskCount + evenTaskCount) * outerLoopCount, innerLoopCount, MUST_BE_EQUAL);
      assertArrayEquals(extentSorted, extent, "Elements must be sorted and equal.");
      assertTrue(sorter.isSorted(), SORTED_MUST_BE_SET);
    }

    @Test
    @DisplayName("BrickSortParallelTest.testComparisonCountSorted")
    void testComparisonCountSorted() {
      IArray high = new HighArray(MYRIAD);
      LongStream.rangeClosed(1, MYRIAD).forEach(i -> high.insert(i));
      BrickSortComplex sorter = new BrickSortComplex();
      sorter.sort(high);
      int compCount = sorter.getComparisonCount();
      final int innerLoopCount = sorter.getInnerLoopCount();
      final int outerLoopCount = sorter.getOuterLoopCount();
      int length = high.count();
      final int oddTaskCount = computeOddTaskCount(length);
      final int evenTaskCount = computeEvenTaskCount(length);
      assertEquals((oddTaskCount + evenTaskCount) * outerLoopCount, innerLoopCount, MUST_BE_EQUAL);
      assertEquals(MYRIAD - 1, compCount, "Comparison count must be " + (MYRIAD - 1));
      assertTrue(sorter.isSorted(), SORTED_MUST_BE_SET);
    }

    @Test
    @DisplayName("BrickSortParallelTest.testComparisonCountUnsorted")
    void testComparisonCountUnsorted() {
      IArray high = new HighArray(MYRIAD);
      LongStream.rangeClosed(1, MYRIAD).parallel().unordered().forEach(i -> high.insert(i));
      BrickSortComplex sorter = new BrickSortComplex();
      sorter.sort(high);
      int compCount = sorter.getComparisonCount();
      final int innerLoopCount = sorter.getInnerLoopCount();
      final int outerLoopCount = sorter.getOuterLoopCount();
      int length = high.count();
      final int oddTaskCount = computeOddTaskCount(length);
      final int evenTaskCount = computeEvenTaskCount(length);
      assertEquals((oddTaskCount + evenTaskCount) * outerLoopCount, innerLoopCount, MUST_BE_EQUAL);
      assertTrue(
          MYRIAD - 1 <= compCount && compCount <= (MYRIAD * MYRIAD - 1),
          "Comparison count must be in range " + (MYRIAD - 1) + " and " + MYRIAD * (MYRIAD - 1));
      assertTrue(sorter.isSorted(), SORTED_MUST_BE_SET);
    }

    @Test
    @DisplayName("BrickSortParallelTest.testReverseSorted")
    void testReverseSorted() {
      IArray high = new HighArray(MYRIAD);
      revRange(1, MYRIAD).forEach(i -> high.insert(i));
      BrickSortComplex sorter = new BrickSortComplex();
      IArray sorted = sorter.sort(high);
      final int innerLoopCount = sorter.getInnerLoopCount();
      final int outerLoopCount = sorter.getOuterLoopCount();
      int length = high.count();
      final int oddTaskCount = computeOddTaskCount(length);
      final int evenTaskCount = computeEvenTaskCount(length);
      assertEquals((oddTaskCount + evenTaskCount) * outerLoopCount, innerLoopCount, MUST_BE_EQUAL);
      assertEquals(
          sorter.getSwapCount(),
          sorter.getComparisonCount(),
          "Comparison count must be same as swap count in reverse ordered array.");
      assertTrue(isSorted(sorted), "Array must be sorted");
      assertTrue(sorter.isSorted(), SORTED_MUST_BE_SET);
    }

    @Test
    @DisplayName("BrickSortParallelTest.testReverseSortedOdd")
    void testReverseSortedOdd() {
      IArray high = new HighArray(MYRIAD + 1);
      revRange(1, MYRIAD + 1).forEach(i -> high.insert(i));
      BrickSortComplex sorter = new BrickSortComplex();
      IArray sorted = sorter.sort(high);
      final int innerLoopCount = sorter.getInnerLoopCount();
      final int outerLoopCount = sorter.getOuterLoopCount();
      int length = high.count();
      final int oddTaskCount = computeOddTaskCount(length);
      final int evenTaskCount = computeEvenTaskCount(length);
      assertEquals(
          (oddTaskCount + evenTaskCount) * (outerLoopCount - 1) + oddTaskCount,
          innerLoopCount,
          MUST_BE_EQUAL);
      assertEquals(
          sorter.getSwapCount(),
          sorter.getComparisonCount(),
          "Comparison count must be same as swap count in reverse ordered array.");
      assertTrue(isSorted(sorted), "Array must be sorted");
      assertTrue(sorter.isSorted(), SORTED_MUST_BE_SET);
    }

    @Test
    @DisplayName("BrickSortParallelTest.testSwapCount")
    void testSwapCount() {
      IArray high = new HighArray(MYRIAD);
      LongStream.rangeClosed(1, MYRIAD).forEach(i -> high.insert(i));
      BrickSortComplex sorter = new BrickSortComplex();
      sorter.sort(high);
      final int innerLoopCount = sorter.getInnerLoopCount();
      final int outerLoopCount = sorter.getOuterLoopCount();
      int length = high.count();
      final int oddTaskCount = computeOddTaskCount(length);
      final int evenTaskCount = computeEvenTaskCount(length);
      assertEquals((oddTaskCount + evenTaskCount) * outerLoopCount, innerLoopCount, MUST_BE_EQUAL);
      assertEquals(0, sorter.getSwapCount(), "Swap count must be zero.");
      assertTrue(sorter.isSorted(), SORTED_MUST_BE_SET);
    }

    @Test
    @DisplayName("BrickSortParallelTest.testTimeComplexity")
    void testTimeComplexity() {
      IArray high = new HighArray(MYRIAD);
      LongStream.rangeClosed(1, MYRIAD).forEach(i -> high.insert(i));
      BrickSortComplex sorter = new BrickSortComplex();
      sorter.sort(high);
      final int innerLoopCount = sorter.getInnerLoopCount();
      final int outerLoopCount = sorter.getOuterLoopCount();
      int length = high.count();
      final int oddTaskCount = computeOddTaskCount(length);
      final int evenTaskCount = computeEvenTaskCount(length);
      assertEquals((oddTaskCount + evenTaskCount) * outerLoopCount, innerLoopCount, MUST_BE_EQUAL);
      assertEquals(MYRIAD - 1, sorter.getTimeComplexity(), "Time complexity must be twenty.");
      assertTrue(sorter.isSorted(), SORTED_MUST_BE_SET);
    }

    @Test
    @DisplayName("BrickSortParallelTest.testReverseSortedOddException")
    void testReverseSortedOddException() {
      IArray high = new HighArray(MYRIAD + 1);
      revRange(1, MYRIAD + 1).forEach(i -> high.insert(i));
      ISort sorter = new BrickSortExceptionable();
      assertThrows(
          CompletionException.class, () -> sorter.sort(high), "CompletionException expected.");
    }

    @Test
    @DisplayName("BrickSortParallelTest.testReverseSortedOddInterruption")
    void testReverseSortedOddInterruption() throws InterruptedException, ExecutionException {
      IArray high = new HighArray(MYRIAD + 1);
      revRange(1, MYRIAD + 1).forEach(i -> high.insert(i));
      BrickSortInterruptible sorter = new BrickSortInterruptible();
      assertThrows(
          CompletionException.class, () -> sorter.sort(high), "CompletionException expected.");
    }
  }

  @Nested
  class CornerCasesTest {
    @Test
    @DisplayName("BrickSortParallelTest.CornerCasesTest.testEmptyArray")
    void testEmptyArray() {
      BrickSortComplex bsc = new BrickSortComplex();
      bsc.sortEmptyArray();
      assertTrue(bsc.isSorted(), SORTED);
    }

    @Test
    @DisplayName("BrickSortParallelTest.CornerCasesTest.testSingleElementArray")
    void testSingleElementArray() {
      BrickSortComplex bsc = new BrickSortComplex();
      bsc.sortSingleElementArray();
      assertTrue(bsc.isSorted(), SORTED);
    }
  }

  @Nested
  @DisplayName("BrickSortParallelTest.ComputeTaskCountTest")
  class ComputeTaskCountTest {

    @Test
    @DisplayName("BrickSortParallelTest.ComputeTaskCountTest.testZeroLength")
    void testZeroLength() {
      assertEquals(0, computeOddTaskCount(0), ZERO_TASKS_EXPECTED);
      assertEquals(0, computeEvenTaskCount(0), ZERO_TASKS_EXPECTED);
    }

    @Test
    @DisplayName("BrickSortParallelTest.ComputeTaskCountTest.testMinusOneLength")
    void testMinusOneLength() {
      assertThrows(
          IllegalArgumentException.class, () -> computeOddTaskCount(-1), ILLEGAL_LENGTH_EXPECTED);
      assertThrows(
          IllegalArgumentException.class, () -> computeEvenTaskCount(-1), ILLEGAL_LENGTH_EXPECTED);
    }

    @Test
    @DisplayName("BrickSortParallelTest.ComputeTaskCountTest.testMinusTwoLength")
    void testMinusTwoLength() {
      assertThrows(
          IllegalArgumentException.class, () -> computeOddTaskCount(-2), ILLEGAL_LENGTH_EXPECTED);
      assertThrows(
          IllegalArgumentException.class, () -> computeEvenTaskCount(-2), ILLEGAL_LENGTH_EXPECTED);
    }

    @Test
    @DisplayName("BrickSortParallelTest.ComputeTaskCountTest.testMinValueLength")
    void testMinValueLength() {
      assertThrows(
          IllegalArgumentException.class,
          () -> computeOddTaskCount(Integer.MIN_VALUE),
          ILLEGAL_LENGTH_EXPECTED);
      assertThrows(
          IllegalArgumentException.class,
          () -> computeEvenTaskCount(Integer.MIN_VALUE),
          ILLEGAL_LENGTH_EXPECTED);
    }

    @Test
    @DisplayName("BrickSortParallelTest.ComputeTaskCountTest.testOneValueLength")
    void testOneValueLength() {
      assertEquals(0, computeOddTaskCount(1), ZERO_TASKS_EXPECTED);
      assertEquals(0, computeEvenTaskCount(1), ZERO_TASKS_EXPECTED);
    }

    @Test
    @DisplayName("BrickSortParallelTest.ComputeTaskCountTest.testTwoValueLength")
    void testTwoValueLength() {
      assertEquals(0, computeOddTaskCount(2), ZERO_TASKS_EXPECTED);
      assertEquals(1, computeEvenTaskCount(2), ONE_TASK_EXPECTED);
    }

    @Test
    @DisplayName("BrickSortParallelTest.ComputeTaskCountTest.testThreeValueLength")
    void testThreeValueLength() {
      assertEquals(1, computeOddTaskCount(3), ONE_TASK_EXPECTED);
      assertEquals(1, computeEvenTaskCount(3), ONE_TASK_EXPECTED);
    }

    @Test
    @DisplayName("BrickSortParallelTest.ComputeTaskCountTest.testFourValueLength")
    void testFourValueLength() {
      assertEquals(1, computeOddTaskCount(4), ONE_TASK_EXPECTED);
      assertEquals(2, computeEvenTaskCount(4), "Two tasks expected");
    }

    @Test
    @DisplayName("BrickSortParallelTest.ComputeTaskCountTest.testMaxValueLength")
    void testMaxValueLength() {
      assertEquals(
          Integer.MAX_VALUE / 2, computeOddTaskCount(Integer.MAX_VALUE), HALF_TASKS_EXPECTED);
      assertEquals(
          Integer.MAX_VALUE / 2, computeEvenTaskCount(Integer.MAX_VALUE), HALF_TASKS_EXPECTED);
    }

    @Test
    @DisplayName("BrickSortParallelTest.ComputeTaskCountTest.testMaxValueLengthEven")
    void testMaxValueLengthEven() {
      assertEquals(
          (Integer.MAX_VALUE - 2) / 2,
          computeOddTaskCount(Integer.MAX_VALUE - 1),
          HALF_TASKS_EXPECTED);
      assertEquals(
          (Integer.MAX_VALUE - 1) / 2,
          computeEvenTaskCount(Integer.MAX_VALUE - 1),
          HALF_TASKS_EXPECTED);
    }
  }

  static class BrickSortExceptionable extends BrickSortParallel {
    @Override
    protected void bubble(long[] ignored, int ignoredInt) {
      throw new IllegalStateException("Error in " + BrickSortExceptionable.class + ".bubble");
    }
  }

  static class BrickSortInterruptible extends BrickSortParallel {
    @Override
    protected void sortInterruptibly(
        long[] ignoredArr, int ignoredLength, ExecutorService ignoredService)
        throws InterruptedException, ExecutionException {
      throw new InterruptedException(
          "Error in " + BrickSortInterruptible.class + ".sortInterruptible");
    }
  }

  // CPD-OFF
  static class BrickSortComplex extends BrickSortParallel {

    void sortZeroLengthArray() {
      long[] a = {2, 5, 6, 8, 0};
      sort(a, 0);
    }

    void sortOneLengthArray() {
      long[] a = {2, 5, 6, 8, 0};
      sort(a, 1);
    }

    void sortNMinusOneLengthArray() {
      long[] a = {2, 5, 6, 8, 0};
      sort(a, 4);
    }

    void sortEmptyArray() {
      long[] a = {};
      sort(a, 0);
    }

    void sortSingleElementArray() {
      long[] a = {10};
      sort(a, 1);
    }

    void resetInternals() {
      reset();
    }

    void sortAndSetInternals() {
      long[] a = {12, 9, 7, 22, 25};
      sort(a, a.length);
    }

    void resetInternalsAfterSort() {
      sortAndSetInternals();
      reset();
    }

    void sortOdd() {
      long[] a = {12, 9, 7, 22, 25};
      sort(a, a.length);
    }

    void sortEven() {
      long[] a = {12, 9, 7, 22, 25, 19};
      sort(a, a.length);
    }

    int getInnerLoopCount() {
      return innerLoopCount;
    }

    int getOuterLoopCount() {
      return outerLoopCount;
    }
  }
}
