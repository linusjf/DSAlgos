package ds.tests;

import static ds.ArrayUtils.*;
import static ds.BrickSortParallel.*;
import static ds.tests.TestConstants.*;
import static org.junit.jupiter.api.Assertions.*;

import ds.BrickSort;
import ds.HighArray;
import ds.IArray;
import ds.OrdArray;
import ds.RandomUtils;
import java.util.stream.LongStream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

@TestInstance(Lifecycle.PER_CLASS)
@Execution(ExecutionMode.SAME_THREAD)
@SuppressWarnings({"PMD.LawOfDemeter", "checkstyle:classfanoutcomplexity"})
@DisplayName("MyriadBrickSortTest")
class MyriadBrickSortTest implements SortProvider {

  private static final String MUST_BE_EQUAL = "Must be equal";

  @Test
  @DisplayName("MyriadBrickSortTest.testSortRandom")
  void testSortRandom() {
    HighArray arr = new HighArray(MYRIAD);
    try (LongStream stream = RandomUtils.longStream().limit(MYRIAD)) {
      stream.forEach(i -> arr.insert(i));
    }
    BrickSort sorter = new BrickSort();
    IArray sorted = sorter.sort(arr);
    assertTrue(isSorted(sorted), "Array must be sorted.");
  }

  @Test
  @DisplayName("MyriadBrickSortTest.testStreamUnSorted")
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
  @DisplayName("MyriadBrickSortTest.testStreamSorted")
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
  @DisplayName("MyriadBrickSortTest.testComparisonCountSorted")
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
  @DisplayName("MyriadBrickSortTest.testComparisonCountUnsorted")
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
  @DisplayName("MyriadBrickSortTest.testReverseSorted")
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
  @DisplayName("MyriadBrickSortTest.testReverseSortedOdd")
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
  @DisplayName("MyriadBrickSortTest.testSwapCount")
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
  @DisplayName("MyriadBrickSortTest.testTimeComplexity")
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

  // CPD-OFF
  static class BrickSortComplex extends BrickSort {

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
