package ds.tests;

import static ds.ArrayUtils.*;
import static ds.MathUtils.*;
import static ds.tests.TestConstants.*;
import static org.junit.jupiter.api.Assertions.*;

import ds.AbstractSort;
import ds.BrickSortMaxMinParallel;
import ds.HighArray;
import ds.IArray;
import ds.ISort;
import ds.OrdArray;
import ds.RandomUtils;
import java.util.concurrent.CompletionException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.stream.LongStream;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

@Disabled
@TestInstance(Lifecycle.PER_CLASS)
@Execution(ExecutionMode.SAME_THREAD)
@SuppressWarnings({"PMD.LawOfDemeter", "checkstyle:classfanoutcomplexity"})
@DisplayName("BrickSortMaxMinParallelTest")
class BrickSortMaxMinParallelTest implements SortProvider {

  private static final String ZERO_TASKS_EXPECTED = "Zero tasks expected.";
  private static final String ONE_TASK_EXPECTED = "One task expected.";
  private static final String HALF_TASKS_EXPECTED = "Half tasks expected.";
  private static final String ILLEGAL_LENGTH_EXPECTED = "Illegal length expected.";

  @Test
  @DisplayName("BrickSortMaxMinParallelTest.testReverseSortedOdd255")
  void testReverseSortedOdd255() {
    IArray high = new HighArray(255);
    revRange(1, 255).forEach(i -> high.insert(i));
    BrickSortMaxMinParallelComplex sorter = new BrickSortMaxMinParallelComplex();
    IArray sorted = sorter.sort(high);
    assertTrue(isSorted(sorted), "Array must be sorted");
    assertTrue(sorter.isSorted(), SORTED_MUST_BE_SET);
  }

  @Test
  @DisplayName("BrickSortMaxMinParallelTest.testToStringClass")
  void testToStringClass() {
    AbstractSort sorter = new BrickSortMaxMinParallel();
    String className = BrickSortMaxMinParallel.class.getName();
    assertTrue(
        sorter.toString().startsWith(className), () -> "ToString must start with " + className);
  }

  @Test
  @DisplayName("BrickSortMaxMinParallelTest.testZeroTimeComplexity")
  void testZeroTimeComplexity() {
    BrickSortMaxMinParallelComplex bsc = new BrickSortMaxMinParallelComplex();
    bsc.sortZeroLengthArray();
    assertEquals(0, bsc.getTimeComplexity(), "Time Complexity must be zero.");
    assertTrue(bsc.isSorted(), SORTED_MUST_BE_SET);
  }

  @Test
  @DisplayName("BrickSortMaxMinParallelTest.testOneTimeComplexity")
  void testOneTimeComplexity() {
    BrickSortMaxMinParallelComplex bsc = new BrickSortMaxMinParallelComplex();
    bsc.sortOneLengthArray();
    assertEquals(0, bsc.getTimeComplexity(), "Time Complexity must be zero.");
    assertTrue(bsc.isSorted(), SORTED_MUST_BE_SET);
  }

  @Test
  @DisplayName("BrickSortMaxMinParallelTest.testNMinusOneTimeComplexity")
  void testNMinusOneTimeComplexity() {
    BrickSortMaxMinParallelComplex bsc = new BrickSortMaxMinParallelComplex();
    bsc.sortNMinusOneLengthArray();
    assertEquals(3, bsc.getTimeComplexity(), "Time Complexity must be three.");
    assertTrue(bsc.isSorted(), SORTED_MUST_BE_SET);
  }

  @Test
  @DisplayName("BrickSortMaxMinParallelTest.testReset")
  void testReset() {
    BrickSortMaxMinParallelComplex bsc = new BrickSortMaxMinParallelComplex();
    bsc.resetInternals();
    assertEquals(0, bsc.getTimeComplexity(), "Time Complexity must be reset.");
    assertEquals(0, bsc.getComparisonCount(), "Comparison count must be reset.");
    assertEquals(0, bsc.getSwapCount(), "Swap count must be reset.");
    assertEquals(0, bsc.getCopyCount(), "Copy count must be reset.");
    assertFalse(bsc.isSorted(), "sorted must be reset.");
  }

  @Test
  @DisplayName("BrickSortMaxMinParallelTest.testResetAfterSort")
  void testResetAfterSort() {
    BrickSortMaxMinParallelComplex bsc = new BrickSortMaxMinParallelComplex();
    bsc.resetInternalsAfterSort();
    assertEquals(0, bsc.getTimeComplexity(), "Time Complexity must be reset.");
    assertEquals(0, bsc.getComparisonCount(), "Comparison count must be reset.");
    assertEquals(0, bsc.getSwapCount(), "Swap count must be reset.");
    assertEquals(0, bsc.getCopyCount(), "Copy count must be reset.");
    assertFalse(bsc.isSorted(), "sorted must be reset.");
  }

  @Test
  @DisplayName("BrickSortMaxMinParallelTest.testStateAfterReset")
  void testStateAfterReset() {
    BrickSortMaxMinParallelComplex bsc = new BrickSortMaxMinParallelComplex();
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
    @DisplayName("BrickSortMaxMinParallelTest.testSortRandom")
    void testSortRandom() {
      HighArray arr = new HighArray(MYRIAD);
      try (LongStream stream = RandomUtils.longStream().limit(MYRIAD)) {
        stream.forEach(i -> arr.insert(i));
      }
      BrickSortMaxMinParallelComplex sorter = new BrickSortMaxMinParallelComplex();
      IArray sorted = sorter.sort(arr);
      assertTrue(isSorted(sorted), "Array must be sorted.");
    }

    @Test
    @DisplayName("BrickSortMaxMinParallelTest.testStreamUnSorted")
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
      BrickSortMaxMinParallelComplex sorter = new BrickSortMaxMinParallelComplex();
      IArray sorted = sorter.sort(high);
      long[] extentSorted = sorted.getExtentArray();
      long[] extent = ord.getExtentArray();
      assertArrayEquals(extentSorted, extent, "Elements must be sorted and equal.");
      assertTrue(sorter.isSorted(), SORTED_MUST_BE_SET);
    }

    @Test
    @DisplayName("BrickSortMaxMinParallelTest.testStreamSorted")
    void testStreamSorted() {
      IArray high = new HighArray(MYRIAD);
      IArray ord = new OrdArray(MYRIAD);
      LongStream.rangeClosed(1, MYRIAD)
          .forEach(
              i -> {
                high.insert(i);
                ord.insert(i);
              });
      BrickSortMaxMinParallelComplex sorter = new BrickSortMaxMinParallelComplex();
      IArray sorted = sorter.sort(high);
      long[] extentSorted = sorted.getExtentArray();
      long[] extent = ord.getExtentArray();
      assertArrayEquals(extentSorted, extent, "Elements must be sorted and equal.");
      assertTrue(sorter.isSorted(), SORTED_MUST_BE_SET);
    }

    @Test
    @DisplayName("BrickSortMaxMinParallelTest.testComparisonCountSorted")
    void testComparisonCountSorted() {
      IArray high = new HighArray(MYRIAD);
      LongStream.rangeClosed(1, MYRIAD).forEach(i -> high.insert(i));
      BrickSortMaxMinParallelComplex sorter = new BrickSortMaxMinParallelComplex();
      sorter.sort(high);
      int compCount = sorter.getComparisonCount();
      assertEquals(MYRIAD - 1, compCount, "Comparison count must be " + (MYRIAD - 1));
      assertTrue(sorter.isSorted(), SORTED_MUST_BE_SET);
    }

    @Test
    @DisplayName("BrickSortMaxMinParallelTest.testComparisonCountUnsorted")
    void testComparisonCountUnsorted() {
      IArray high = new HighArray(MYRIAD);
      LongStream.rangeClosed(1, MYRIAD).parallel().unordered().forEach(i -> high.insert(i));
      BrickSortMaxMinParallelComplex sorter = new BrickSortMaxMinParallelComplex();
      sorter.sort(high);
      int compCount = sorter.getComparisonCount();
      assertTrue(
          MYRIAD - 1 <= compCount && compCount <= (MYRIAD * MYRIAD - 1),
          "Comparison count must be in range " + (MYRIAD - 1) + " and " + MYRIAD * (MYRIAD - 1));
      assertTrue(sorter.isSorted(), SORTED_MUST_BE_SET);
    }

    @Test
    @DisplayName("BrickSortMaxMinParallelTest.testReverseSorted")
    void testReverseSorted() {
      IArray high = new HighArray(MYRIAD);
      revRange(1, MYRIAD).forEach(i -> high.insert(i));
      BrickSortMaxMinParallelComplex sorter = new BrickSortMaxMinParallelComplex();
      IArray sorted = sorter.sort(high);
      assertTrue(isSorted(sorted), "Array must be sorted");
      assertTrue(sorter.isSorted(), SORTED_MUST_BE_SET);
    }

    @Test
    @DisplayName("BrickSortMaxMinParallelTest.testReverseSortedOdd")
    void testReverseSortedOdd() {
      IArray high = new HighArray(MYRIAD + 1);
      revRange(1, MYRIAD + 1).forEach(i -> high.insert(i));
      BrickSortMaxMinParallelComplex sorter = new BrickSortMaxMinParallelComplex();
      IArray sorted = sorter.sort(high);
      assertTrue(isSorted(sorted), "Array must be sorted");
      assertTrue(sorter.isSorted(), SORTED_MUST_BE_SET);
    }

    @Test
    @DisplayName("BrickSortMaxMinParallelTest.testSwapCount")
    void testSwapCount() {
      IArray high = new HighArray(MYRIAD);
      LongStream.rangeClosed(1, MYRIAD).forEach(i -> high.insert(i));
      BrickSortMaxMinParallelComplex sorter = new BrickSortMaxMinParallelComplex();
      sorter.sort(high);
      assertEquals(0, sorter.getSwapCount(), "Swap count must be zero.");
      assertTrue(sorter.isSorted(), SORTED_MUST_BE_SET);
    }

    @Test
    @DisplayName("BrickSortMaxMinParallelTest.testTimeComplexity")
    void testTimeComplexity() {
      IArray high = new HighArray(MYRIAD);
      LongStream.rangeClosed(1, MYRIAD).forEach(i -> high.insert(i));
      BrickSortMaxMinParallelComplex sorter = new BrickSortMaxMinParallelComplex();
      sorter.sort(high);
      assertTrue(sorter.isSorted(), SORTED_MUST_BE_SET);
    }

    @Test
    @DisplayName("BrickSortMaxMinParallelTest.testReverseSortedOddException")
    void testReverseSortedOddException() {
      IArray high = new HighArray(MYRIAD + 1);
      revRange(1, MYRIAD + 1).forEach(i -> high.insert(i));
      ISort sorter = new BrickSortExceptionable();
      assertThrows(
          CompletionException.class, () -> sorter.sort(high), "CompletionException expected.");
    }

    @Test
    @DisplayName("BrickSortMaxMinParallelTest.testReverseSortedOddInterruption")
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
    @DisplayName("BrickSortMaxMinParallelTest.CornerCasesTest.testEmptyArray")
    void testEmptyArray() {
      BrickSortMaxMinParallelComplex bsc = new BrickSortMaxMinParallelComplex();
      bsc.sortEmptyArray();
      assertTrue(bsc.isSorted(), SORTED);
    }

    @Test
    @DisplayName("BrickSortMaxMinParallelTest.CornerCasesTest.testSingleElementArray")
    void testSingleElementArray() {
      BrickSortMaxMinParallelComplex bsc = new BrickSortMaxMinParallelComplex();
      bsc.sortSingleElementArray();
      assertTrue(bsc.isSorted(), SORTED);
    }
  }

  @Nested
  @DisplayName("BrickSortMaxMinParallelTest.ComputeCountTest")
  class ComputeTaskCountTest {

    @Test
    @DisplayName("BrickSortMaxMinParallelTest.ComputeTaskCountTest.testZeroLength")
    void testZeroLength() {
      assertEquals(0, computeOddCount(0), ZERO_TASKS_EXPECTED);
      assertEquals(0, computeEvenCount(0), ZERO_TASKS_EXPECTED);
    }

    @Test
    @DisplayName("BrickSortMaxMinParallelTest.ComputeTaskCountTest.testMinusOneLength")
    void testMinusOneLength() {
      assertThrows(
          IllegalArgumentException.class, () -> computeOddCount(-1), ILLEGAL_LENGTH_EXPECTED);
      assertThrows(
          IllegalArgumentException.class, () -> computeEvenCount(-1), ILLEGAL_LENGTH_EXPECTED);
    }

    @Test
    @DisplayName("BrickSortMaxMinParallelTest.ComputeTaskCountTest.testMinusTwoLength")
    void testMinusTwoLength() {
      assertThrows(
          IllegalArgumentException.class, () -> computeOddCount(-2), ILLEGAL_LENGTH_EXPECTED);
      assertThrows(
          IllegalArgumentException.class, () -> computeEvenCount(-2), ILLEGAL_LENGTH_EXPECTED);
    }

    @Test
    @DisplayName("BrickSortMaxMinParallelTest.ComputeTaskCountTest.testMinValueLength")
    void testMinValueLength() {
      assertThrows(
          IllegalArgumentException.class,
          () -> computeOddCount(Integer.MIN_VALUE),
          ILLEGAL_LENGTH_EXPECTED);
      assertThrows(
          IllegalArgumentException.class,
          () -> computeEvenCount(Integer.MIN_VALUE),
          ILLEGAL_LENGTH_EXPECTED);
    }

    @Test
    @DisplayName("BrickSortMaxMinParallelTest.ComputeTaskCountTest.testOneValueLength")
    void testOneValueLength() {
      assertEquals(0, computeOddCount(1), ZERO_TASKS_EXPECTED);
      assertEquals(0, computeEvenCount(1), ZERO_TASKS_EXPECTED);
    }

    @Test
    @DisplayName("BrickSortMaxMinParallelTest.ComputeTaskCountTest.testTwoValueLength")
    void testTwoValueLength() {
      assertEquals(0, computeOddCount(2), ZERO_TASKS_EXPECTED);
      assertEquals(1, computeEvenCount(2), ONE_TASK_EXPECTED);
    }

    @Test
    @DisplayName("BrickSortMaxMinParallelTest.ComputeTaskCountTest.testThreeValueLength")
    void testThreeValueLength() {
      assertEquals(1, computeOddCount(3), ONE_TASK_EXPECTED);
      assertEquals(1, computeEvenCount(3), ONE_TASK_EXPECTED);
    }

    @Test
    @DisplayName("BrickSortMaxMinParallelTest.ComputeTaskCountTest.testFourValueLength")
    void testFourValueLength() {
      assertEquals(1, computeOddCount(4), ONE_TASK_EXPECTED);
      assertEquals(2, computeEvenCount(4), "Two tasks expected");
    }

    @Test
    @DisplayName("BrickSortMaxMinParallelTest.ComputeTaskCountTest.testMaxValueLength")
    void testMaxValueLength() {
      assertEquals(Integer.MAX_VALUE / 2, computeOddCount(Integer.MAX_VALUE), HALF_TASKS_EXPECTED);
      assertEquals(Integer.MAX_VALUE / 2, computeEvenCount(Integer.MAX_VALUE), HALF_TASKS_EXPECTED);
    }

    @Test
    @DisplayName("BrickSortMaxMinParallelTest.ComputeTaskCountTest.testMaxValueLengthEven")
    void testMaxValueLengthEven() {
      assertEquals(
          (Integer.MAX_VALUE - 2) / 2, computeOddCount(Integer.MAX_VALUE - 1), HALF_TASKS_EXPECTED);
      assertEquals(
          (Integer.MAX_VALUE - 1) / 2,
          computeEvenCount(Integer.MAX_VALUE - 1),
          HALF_TASKS_EXPECTED);
    }
  }

  static class BrickSortExceptionable extends BrickSortMaxMinParallel {
    @Override
    protected void bubble(long[] ignored, int ignoredInt) {
      throw new IllegalStateException("Error in " + BrickSortExceptionable.class + ".bubble");
    }
  }

  static class BrickSortInterruptible extends BrickSortMaxMinParallel {
    @Override
    protected void sortInterruptibly(
        long[] ignoredArr, int ignoredLength, ExecutorService ignoredService)
        throws InterruptedException, ExecutionException {
      throw new InterruptedException(
          "Error in " + BrickSortInterruptible.class + ".sortInterruptible");
    }
  }

  // CPD-OFF
  static class BrickSortMaxMinParallelComplex extends BrickSortMaxMinParallel {

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
