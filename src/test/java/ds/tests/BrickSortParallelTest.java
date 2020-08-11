package ds.tests;

import static ds.ArrayUtils.*;
import static ds.tests.TestData.*;
import static org.junit.jupiter.api.Assertions.*;

import ds.AbstractSort;
import ds.BrickSortParallel;
import ds.HighArray;
import ds.IArray;
import ds.ISort;
import ds.OrdArray;
import java.util.concurrent.CompletionException;
import java.util.concurrent.ExecutionException;
import java.util.stream.LongStream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.AggregateWith;
import org.junit.jupiter.params.provider.CsvSource;

@TestInstance(Lifecycle.PER_CLASS)
@Execution(ExecutionMode.SAME_THREAD)
@SuppressWarnings("PMD.LawOfDemeter")
class BrickSortParallelTest implements SortProvider {

  private static final String MUST_BE_EQUAL = "Must be equal";
  private static final String SORTED_MUST_BE_SET = "Sorted must be set.";
  @ParameterizedTest
  @CsvSource(INIT_DATA)
  void testSort(@AggregateWith(HighArrayArgumentsAggregator.class) IArray arr) {
    long[] a = {00, 11, 22, 33, 44, 55, 66, 77, 88, 99};
    BrickSortComplex sorter = new BrickSortComplex();
    IArray sorted = sorter.sort(arr);
    long[] extent = sorted.getExtentArray();
    final int innerLoopCount = sorter.getInnerLoopCount();
    final int outerLoopCount = sorter.getOuterLoopCount();
    final int oddTaskCount = sorter.getOddTaskCount();
    final int evenTaskCount = sorter.getEvenTaskCount();
    assertEquals((oddTaskCount + evenTaskCount) * outerLoopCount, innerLoopCount, MUST_BE_EQUAL);
    assertArrayEquals(a, extent, "Elements must be sorted and equal.");
    assertTrue(sorter.isSorted(), SORTED_MUST_BE_SET);
  }

  @ParameterizedTest
  @CsvSource(INIT_BRICK_SORT_DATA)
  void testSortSmallData(@AggregateWith(HighArrayArgumentsAggregator.class) IArray arr) {
    BrickSortComplex sorter = new BrickSortComplex();
    sorter.sort(arr);
    final int innerLoopCount = sorter.getInnerLoopCount();
    final int outerLoopCount = sorter.getOuterLoopCount();
    final int oddTaskCount = sorter.getOddTaskCount();
    final int evenTaskCount = sorter.getEvenTaskCount();
    assertEquals((oddTaskCount + evenTaskCount) * outerLoopCount, innerLoopCount, MUST_BE_EQUAL);
    assertEquals(13, sorter.getSwapCount(), "Swap count will be five.");
    assertTrue(sorter.isSorted(), SORTED_MUST_BE_SET);
  }

  @Test
  void testStreamUnSorted() {
    IArray high = new HighArray();
    IArray ord = new OrdArray();
    LongStream.rangeClosed(1, 20)
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
    final int oddTaskCount = sorter.getOddTaskCount();
    final int evenTaskCount = sorter.getEvenTaskCount();
    assertEquals((oddTaskCount + evenTaskCount) * outerLoopCount, innerLoopCount, MUST_BE_EQUAL);
    assertArrayEquals(extentSorted, extent, "Elements must be sorted and equal.");
    assertTrue(sorter.isSorted(), SORTED_MUST_BE_SET);
  }

  @Test
  void testStreamSorted() {
    IArray high = new HighArray();
    IArray ord = new OrdArray();
    LongStream.rangeClosed(1, 20)
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
    final int oddTaskCount = sorter.getOddTaskCount();
    final int evenTaskCount = sorter.getEvenTaskCount();
    assertEquals((oddTaskCount + evenTaskCount) * outerLoopCount, innerLoopCount, MUST_BE_EQUAL);
    assertArrayEquals(extentSorted, extent, "Elements must be sorted and equal.");
    assertTrue(sorter.isSorted(), SORTED_MUST_BE_SET);
  }

  @Test
  void testComparisonCountSorted() {
    IArray high = new HighArray();
    LongStream.rangeClosed(1, 20).forEach(i -> high.insert(i));
    BrickSortComplex sorter = new BrickSortComplex();
    sorter.sort(high);
    int compCount = sorter.getComparisonCount();
    final int innerLoopCount = sorter.getInnerLoopCount();
    final int outerLoopCount = sorter.getOuterLoopCount();
    final int oddTaskCount = sorter.getOddTaskCount();
    final int evenTaskCount = sorter.getEvenTaskCount();
    assertEquals((oddTaskCount + evenTaskCount) * outerLoopCount, innerLoopCount, MUST_BE_EQUAL);
    assertEquals(19, compCount, "Comparison count must be 19.");
    assertTrue(sorter.isSorted(), SORTED_MUST_BE_SET);
  }

  @Test
  void testComparisonCountUnsorted() {
    IArray high = new HighArray();
    LongStream.rangeClosed(1, 20).parallel().unordered().forEach(i -> high.insert(i));
    BrickSortComplex sorter = new BrickSortComplex();
    sorter.sort(high);
    int compCount = sorter.getComparisonCount();
    final int innerLoopCount = sorter.getInnerLoopCount();
    final int outerLoopCount = sorter.getOuterLoopCount();
    final int oddTaskCount = sorter.getOddTaskCount();
    final int evenTaskCount = sorter.getEvenTaskCount();
    assertEquals((oddTaskCount + evenTaskCount) * outerLoopCount, innerLoopCount, MUST_BE_EQUAL);
    assertTrue(
        19 <= compCount && compCount <= 400, "Comparison count must be in range 19 and 400.");
    assertTrue(sorter.isSorted(), SORTED_MUST_BE_SET);
  }

  @Test
  void testReverseSorted() {
    IArray high = new HighArray();
    revRange(1, 20).forEach(i -> high.insert(i));
    BrickSortComplex sorter = new BrickSortComplex();
    IArray sorted = sorter.sort(high);
    final int innerLoopCount = sorter.getInnerLoopCount();
    final int outerLoopCount = sorter.getOuterLoopCount();
    final int oddTaskCount = sorter.getOddTaskCount();
    final int evenTaskCount = sorter.getEvenTaskCount();
    assertEquals((oddTaskCount + evenTaskCount) * outerLoopCount, innerLoopCount, MUST_BE_EQUAL);
    assertEquals(
        sorter.getSwapCount(),
        sorter.getComparisonCount(),
        "Comparison count must be same as swap count in reverse ordered array.");
    assertTrue(isSorted(sorted), "Array must be sorted");
    assertTrue(sorter.isSorted(), SORTED_MUST_BE_SET);
  }

  @Test
  void testReverseSortedOdd() {
    IArray high = new HighArray();
    revRange(1, 21).forEach(i -> high.insert(i));
    BrickSortComplex sorter = new BrickSortComplex();
    IArray sorted = sorter.sort(high);
    final int innerLoopCount = sorter.getInnerLoopCount();
    final int outerLoopCount = sorter.getOuterLoopCount();
    final int oddTaskCount = sorter.getOddTaskCount();
    final int evenTaskCount = sorter.getEvenTaskCount();
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
  void testReverseSortedOdd255() {
    IArray high = new HighArray(255);
    revRange(1, 255).forEach(i -> high.insert(i));
    BrickSortComplex sorter = new BrickSortComplex();
    IArray sorted = sorter.sort(high);
    final int innerLoopCount = sorter.getInnerLoopCount();
    final int outerLoopCount = sorter.getOuterLoopCount();
    final int oddTaskCount = sorter.getOddTaskCount();
    final int evenTaskCount = sorter.getEvenTaskCount();
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
  void testSwapCount() {
    IArray high = new HighArray();
    LongStream.rangeClosed(1, 20).forEach(i -> high.insert(i));
    BrickSortComplex sorter = new BrickSortComplex();
    sorter.sort(high);
    final int innerLoopCount = sorter.getInnerLoopCount();
    final int outerLoopCount = sorter.getOuterLoopCount();
    final int oddTaskCount = sorter.getOddTaskCount();
    final int evenTaskCount = sorter.getEvenTaskCount();
    assertEquals((oddTaskCount + evenTaskCount) * outerLoopCount, innerLoopCount, MUST_BE_EQUAL);
    assertEquals(0, sorter.getSwapCount(), "Swap count must be zero.");
    assertTrue(sorter.isSorted(), SORTED_MUST_BE_SET);
  }

  @Test
  void testTimeComplexity() {
    IArray high = new HighArray();
    LongStream.rangeClosed(1, 20).forEach(i -> high.insert(i));
    BrickSortComplex sorter = new BrickSortComplex();
    sorter.sort(high);
    final int innerLoopCount = sorter.getInnerLoopCount();
    final int outerLoopCount = sorter.getOuterLoopCount();
    final int oddTaskCount = sorter.getOddTaskCount();
    final int evenTaskCount = sorter.getEvenTaskCount();
    assertEquals((oddTaskCount + evenTaskCount) * outerLoopCount, innerLoopCount, MUST_BE_EQUAL);
    assertEquals(19, sorter.getTimeComplexity(), "Time complexity must be twenty.");
    assertTrue(sorter.isSorted(), SORTED_MUST_BE_SET);
  }

  @Test
  void testToStringClass() {
    AbstractSort sorter = new BrickSortParallel();
    String className = BrickSortParallel.class.getName();
    assertTrue(
        sorter.toString().startsWith(className), () -> "ToString must start with " + className);
  }

  @Test
  void testReverseSortedOddException() {
    IArray high = new HighArray();
    revRange(1, 21).forEach(i -> high.insert(i));
    ISort sorter = new BrickSortExceptionable();
    assertThrows(
        CompletionException.class, () -> sorter.sort(high), "CompletionException expected.");
  }

  @Test
  void testReverseSortedOddInterruption() throws InterruptedException, ExecutionException {
    IArray high = new HighArray();
    revRange(1, 21).forEach(i -> high.insert(i));
    BrickSortInterruptible sorter = new BrickSortInterruptible();
    assertThrows(
        CompletionException.class, () -> sorter.sort(high), "CompletionException expected.");
  }

  @Test
  void testZeroTimeComplexity() {
    BrickSortComplex bsc = new BrickSortComplex();
    bsc.sortZeroLengthArray();
    assertEquals(0, bsc.getTimeComplexity(), "Time Complexity must be zero.");
    assertTrue(bsc.isSorted(), SORTED_MUST_BE_SET);
  }

  @Test
  void testOneTimeComplexity() {
    BrickSortComplex bsc = new BrickSortComplex();
    bsc.sortOneLengthArray();
    assertEquals(0, bsc.getTimeComplexity(), "Time Complexity must be zero.");
    assertTrue(bsc.isSorted(), SORTED_MUST_BE_SET);
  }

  @Test
  void testNMinusOneTimeComplexity() {
    BrickSortComplex bsc = new BrickSortComplex();
    bsc.sortNMinusOneLengthArray();
    assertEquals(3, bsc.getTimeComplexity(), "Time Complexity must be three.");
    assertTrue(bsc.isSorted(), SORTED_MUST_BE_SET);
  }

  @Test
  void testReset() {
    BrickSortComplex bsc = new BrickSortComplex();
    bsc.resetInternals();
    assertEquals(0, bsc.getTimeComplexity(), "Time Complexity must be reset.");
    assertEquals(0, bsc.getComparisonCount(), "Comparison count must be reset.");
    assertEquals(0, bsc.getSwapCount(), "Swap count must be reset.");
    assertEquals(0, bsc.getCopyCount(), "Copy count must be reset.");
    assertEquals(false, bsc.isSorted(), "sorted must be reset.");
  }

  @Test
  void testResetAfterSort() {
    BrickSortComplex bsc = new BrickSortComplex();
    bsc.resetInternalsAfterSort();
    assertEquals(0, bsc.getTimeComplexity(), "Time Complexity must be reset.");
    assertEquals(0, bsc.getComparisonCount(), "Comparison count must be reset.");
    assertEquals(0, bsc.getSwapCount(), "Swap count must be reset.");
    assertEquals(0, bsc.getCopyCount(), "Copy count must be reset.");
    assertEquals(false, bsc.isSorted(), "sorted must be reset.");
  }

  @Test
  void testInternalsAfterSort() {
    BrickSortComplex bsc = new BrickSortComplex();
    bsc.sortAndSetInternals();
    assertNotEquals(0, bsc.getTimeComplexity(), "Time Complexity must be reset.");
    assertNotEquals(0, bsc.getComparisonCount(), "Comparison count must be reset.");
    assertNotEquals(0, bsc.getSwapCount(), "Swap count must be reset.");
    assertEquals(0, bsc.getCopyCount(), "Copy count must be reset.");
    assertNotEquals(false, bsc.isSorted(), "sorted must be reset.");
  }

  @Test
  void testInnerLoopAfterOddSort() {
    BrickSortComplex bsc = new BrickSortComplex();
    bsc.sortOdd();
    final int innerLoopCount = bsc.getInnerLoopCount();
    final int outerLoopCount = bsc.getOuterLoopCount();
    final int oddTaskCount = bsc.getOddTaskCount();
    final int evenTaskCount = bsc.getEvenTaskCount();
    assertEquals(12, innerLoopCount, "Inner loop count must be 4.");
    assertEquals(3, outerLoopCount, "Outer loop count must be 3.");
    assertEquals(bsc.getComparisonCount(), innerLoopCount, "Inner loop count must be 4.");
    assertEquals(
        (oddTaskCount + evenTaskCount) * outerLoopCount,
        innerLoopCount,
        "Inner loop count must be 4.");
    assertEquals(true, bsc.isSorted(), "Sorted.");
  }

  @Test
  void testInnerLoopAfterEvenSort() {
    BrickSortComplex bsc = new BrickSortComplex();
    bsc.sortEven();
    final int innerLoopCount = bsc.getInnerLoopCount();
    final int outerLoopCount = bsc.getOuterLoopCount();
    final int oddTaskCount = bsc.getOddTaskCount();
    final int evenTaskCount = bsc.getEvenTaskCount();
    assertEquals(15, innerLoopCount, "Inner loop count must be 4.");
    assertEquals(3, outerLoopCount, "Outer loop count must be 4.");
    assertEquals(bsc.getComparisonCount(), innerLoopCount, "Inner loop count must be 4.");
    assertEquals(
        (oddTaskCount + evenTaskCount) * outerLoopCount,
        innerLoopCount,
        "Inner loop count must be 4.");
    assertEquals(true, bsc.isSorted(), "Sorted.");
  }

  @Test
  void testStateAfterReset() {
    BrickSortComplex bsc = new BrickSortComplex();
    bsc.sortOdd();
    final int oldInnerLoopCount = bsc.getInnerLoopCount();
    final int oldOuterLoopCount = bsc.getOuterLoopCount();
    final int oldOddTaskCount = bsc.getOddTaskCount();
    final int oldEvenTaskCount = bsc.getEvenTaskCount();
    bsc.sortEven();
    final int innerLoopCount = bsc.getInnerLoopCount();
    final int outerLoopCount = bsc.getOuterLoopCount();
    final int oddTaskCount = bsc.getOddTaskCount();
    final int evenTaskCount = bsc.getEvenTaskCount();
    assertNotEquals(oldInnerLoopCount, innerLoopCount, "Inner loop count must not be same.");
    assertEquals(oldOuterLoopCount, outerLoopCount, "Outer loop count must be same.");
    assertEquals(oldOddTaskCount, oddTaskCount, "Odd task count must  be same.");
    assertNotEquals(oldEvenTaskCount, evenTaskCount, "Even task count must not be same.");
  }

  @Test
  void testEmptyArray() {
    BrickSortComplex bsc = new BrickSortComplex();
    bsc.sortEmptyArray();
    final int innerLoopCount = bsc.getInnerLoopCount();
    final int outerLoopCount = bsc.getOuterLoopCount();
    final int oddTaskCount = bsc.getOddTaskCount();
    final int evenTaskCount = bsc.getEvenTaskCount();
    assertEquals(0, oddTaskCount, "Odd task count zero");
    assertEquals(0, evenTaskCount, "Even task count zero");
    assertEquals(true, bsc.isSorted(), "Sorted.");
    assertEquals(
        (oddTaskCount + evenTaskCount) * outerLoopCount,
        innerLoopCount,
        "Inner loop count must be 0.");
  }

  @Test
  void testNegativeLengthArray() {
    BrickSortComplex bsc = new BrickSortComplex();
    bsc.sortNegativeLengthArray();
    final int innerLoopCount = bsc.getInnerLoopCount();
    final int outerLoopCount = bsc.getOuterLoopCount();
    final int oddTaskCount = bsc.getOddTaskCount();
    final int evenTaskCount = bsc.getEvenTaskCount();
    assertEquals(0, oddTaskCount, "Odd task count zero");
    assertEquals(0, evenTaskCount, "Even task count zero");
    assertEquals(true, bsc.isSorted(), "Sorted.");
    assertEquals(
        (oddTaskCount + evenTaskCount) * outerLoopCount,
        innerLoopCount,
        "Inner loop count must be 0.");
  }

  @Test
  void testSingleElementArray() {
    BrickSortComplex bsc = new BrickSortComplex();
    bsc.sortSingleElementArray();
    final int innerLoopCount = bsc.getInnerLoopCount();
    final int outerLoopCount = bsc.getOuterLoopCount();
    final int oddTaskCount = bsc.getOddTaskCount();
    final int evenTaskCount = bsc.getEvenTaskCount();
    assertEquals(0, oddTaskCount, "Odd task count zero");
    assertEquals(0, evenTaskCount, "Even task count zero");
    assertEquals(true, bsc.isSorted(), "Sorted.");
    assertEquals(
        (oddTaskCount + evenTaskCount) * outerLoopCount,
        innerLoopCount,
        "Inner loop count must be 0.");
  }

  static class BrickSortExceptionable extends BrickSortParallel {
    @Override
    protected void bubble(long[] a, int i) {
      throw new IllegalStateException("Error in " + BrickSortExceptionable.class + ".bubble");
    }
  }

  static class BrickSortInterruptible extends BrickSortParallel {
    @Override
    protected void sortInterruptibly(long[] a, int length)
        throws InterruptedException, ExecutionException {
      throw new InterruptedException(
          "Error in " + BrickSortInterruptible.class + ".sortInterruptible");
    }
  }

  static class BrickSortComplex extends BrickSortParallel {
    void sortNegativeLengthArray() {
      long[] a = {2, 5, 6, 8, 0};
      sort(a, Integer.MIN_VALUE);
    }

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
      reset(0);
    }

    void sortAndSetInternals() {
      long[] a = {12, 9, 7, 22, 25};
      sort(a, a.length);
    }

    void resetInternalsAfterSort() {
      sortAndSetInternals();
      reset(5);
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
