package ds.tests;

import static ds.ArrayUtils.*;
import static ds.tests.TestConstants.*;
import static ds.tests.TestData.*;
import static org.junit.jupiter.api.Assertions.*;

import ds.AbstractSort;
import ds.BrickSort;
import ds.HighArray;
import ds.IArray;
import ds.OrdArray;
import java.util.Random;
import java.util.stream.LongStream;
import org.junit.jupiter.api.DisplayName;
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
@DisplayName("BrickSortTest")
class BrickSortTest implements SortProvider {


  @ParameterizedTest
  @CsvSource(INIT_DATA)
  @DisplayName("BrickSortTest.testSort")
  void testSort(@AggregateWith(HighArrayArgumentsAggregator.class) IArray arr) {
    long[] a = {00, 11, 22, 33, 44, 55, 66, 77, 88, 99};
    BrickSort sorter = new BrickSort();
    IArray sorted = sorter.sort(arr);
    long[] extent = sorted.getExtentArray();
    assertArrayEquals(a, extent, ELEMENTS_SORTED_EQUAL);
    assertTrue(sorter.isSorted(), SORTED_MUST_BE_SET);
  }

  @ParameterizedTest
  @CsvSource(INIT_DUPLICATE_DATA)
  @DisplayName("BrickSortTest.testSortDuplicates")
  void testSortDuplicates(@AggregateWith(HighArrayArgumentsAggregator.class) IArray arr) {
    long[] a = {00, 00, 00, 00, 11, 11, 11, 22, 22, 33, 33, 44, 55, 66, 77, 77, 77, 88, 88, 99, 99};
    BrickSort sorter = new BrickSort();
    IArray sorted = sorter.sort(arr);
    long[] extent = sorted.getExtentArray();
    assertArrayEquals(a, extent, ELEMENTS_SORTED_EQUAL);
    assertTrue(sorter.isSorted(), SORTED_MUST_BE_SET);
  }

  @ParameterizedTest
  @CsvSource(INIT_ALL_SAME_DATA)
  @DisplayName("BrickSortTest.testSortAllSame")
  void testSortAllSame(@AggregateWith(HighArrayArgumentsAggregator.class) IArray arr) {
    long[] a = {43, 43, 43, 43, 43, 43, 43, 43, 43, 43};
    BrickSort sorter = new BrickSort();
    IArray sorted = sorter.sort(arr);
    long[] extent = sorted.getExtentArray();
    assertArrayEquals(a, extent, ELEMENTS_SORTED_EQUAL);
    assertEquals(0, sorter.getSwapCount(), "Swap count will be zero.");
    assertTrue(sorter.isSorted(), SORTED_MUST_BE_SET);
  }

  @ParameterizedTest
  @CsvSource(INIT_BRICK_SORT_DATA)
  @DisplayName("BrickSortTest.testSortSmallData")
  void testSortSmallData(@AggregateWith(HighArrayArgumentsAggregator.class) IArray arr) {
    BrickSort sorter = new BrickSort();
    OrdArray ord = new OrdArray();
    long[] a = arr.getExtentArray();
    for (int i = 0; i < arr.count(); i++) ord.insert(a[i]);
    a = ord.getExtentArray();
    IArray sorted = sorter.sort(arr);
    long[] internal = sorted.getExtentArray();
    assertArrayEquals(a, internal, "Arrays must be sorted and equal.");
    assertEquals(13, sorter.getSwapCount(), "Swap count will be thirteen.");
    assertTrue(isSorted(sorted), "Array must be sorted.");
    assertTrue(sorter.isSorted(), SORTED_MUST_BE_SET);
  }

  @Test
  @DisplayName("BrickSortTest.testStreamUnSorted")
  void testStreamUnSorted() {
    IArray high = new HighArray();
    IArray ord = new OrdArray();
    LongStream.rangeClosed(1, SCORE)
        .unordered()
        .forEach(
            i -> {
              high.insert(i);
              ord.insert(i);
            });
    BrickSort sorter = new BrickSort();
    IArray sorted = sorter.sort(high);
    IArray sortedOrd = sorter.sort(ord);
    long[] extentSorted = sorted.getExtentArray();
    long[] extent = sortedOrd.getExtentArray();
    assertArrayEquals(extentSorted, extent, ELEMENTS_SORTED_EQUAL);
    assertTrue(sorter.isSorted(), SORTED_MUST_BE_SET);
  }

  @Test
  @DisplayName("BrickSortTest.testStreamSorted")
  void testStreamSorted() {
    IArray high = new HighArray();
    IArray ord = new OrdArray();
    LongStream.rangeClosed(1, SCORE)
        .forEach(
            i -> {
              high.insert(i);
              ord.insert(i);
            });
    BrickSort sorter = new BrickSort();
    IArray sorted = sorter.sort(high);
    IArray sortedOrd = sorter.sort(ord);
    long[] extentSorted = sorted.getExtentArray();
    long[] extent = sortedOrd.getExtentArray();
    assertArrayEquals(extentSorted, extent, ELEMENTS_SORTED_EQUAL);
    assertTrue(sorter.isSorted(), SORTED_MUST_BE_SET);
  }

  @Test
  @DisplayName("BrickSortTest.testReset")
  void testReset() {
    IArray high = new HighArray();
    IArray ord = new OrdArray();
    LongStream.rangeClosed(1, SCORE)
        .forEach(
            i -> {
              high.insert(i);
              ord.insert(i);
            });
    BrickSort sorter = new BrickSort();
    sorter.sort(high);
    sorter.sort(ord);
    assertEquals(SCORE - 1, sorter.getComparisonCount(), "Comparison count must be n -1.");
    assertTrue(sorter.isSorted(), SORTED_MUST_BE_SET);
  }

  @Test
  @DisplayName("BrickSortTest.testComparisonCountSorted")
  void testComparisonCountSorted() {
    IArray high = new HighArray();
    LongStream.rangeClosed(1, SCORE).forEach(i -> high.insert(i));
    BrickSort sorter = new BrickSort();
    sorter.sort(high);
    int compCount = sorter.getComparisonCount();
    assertEquals(SCORE - 1, compCount, "Comparison count must be " + (SCORE - 1) + ".");
    assertTrue(sorter.isSorted(), SORTED_MUST_BE_SET);
  }

  @Test
  @DisplayName("BrickSortTest.testComparisonCountUnsorted")
  void testComparisonCountUnsorted() {
    IArray high = new HighArray();
    LongStream.rangeClosed(1, SCORE).parallel().unordered().forEach(i -> high.insert(i));
    BrickSort sorter = new BrickSort();
    sorter.sort(high);
    int compCount = sorter.getComparisonCount();
    assertTrue(
        SCORE - 1 <= compCount && compCount <= ((SCORE * (SCORE - 1)) >> 1),
        "Comparison count must be in range "
            + (SCORE - 1)
            + " and "
            + ((SCORE * (SCORE - 1)) >> 1)
            + ".");
    assertTrue(sorter.isSorted(), SORTED_MUST_BE_SET);
  }

  @SuppressWarnings("PMD.JUnitTestContainsTooManyAsserts")
  @DisplayName("BrickSortTest.testReverseSorted")
  @Test
  void testReverseSorted() {
    IArray high = new HighArray();
    revRange(1, SCORE).forEach(i -> high.insert(i));
    BrickSort sorter = new BrickSort();
    IArray sorted = sorter.sort(high);
    assertEquals(
        sorter.getSwapCount(),
        sorter.getComparisonCount(),
        "Comparison count must be same as swap count in reverse ordered array.");
    assertTrue(isSorted(sorted), "Array must be sorted.");
    assertTrue(sorter.isSorted(), SORTED_MUST_BE_SET);
  }

  @SuppressWarnings("PMD.JUnitTestContainsTooManyAsserts")
  @Test
  @DisplayName("BrickSortTest.testReverseSortedOdd")
  void testReverseSortedOdd() {
    IArray high = new HighArray();
    revRange(1, SCORE + 1).forEach(i -> high.insert(i));
    BrickSort sorter = new BrickSort();
    IArray sorted = sorter.sort(high);
    assertEquals(
        sorter.getSwapCount(),
        sorter.getComparisonCount(),
        "Comparison count must be same as swap count in reverse ordered array.");
    assertTrue(isSorted(sorted), "Array must be sorted.");
    assertTrue(sorter.isSorted(), SORTED_MUST_BE_SET);
  }

  @Test
  @DisplayName("BrickSortTest.testSwapCount")
  void testSwapCount() {
    IArray high = new HighArray();
    LongStream.rangeClosed(1, SCORE).forEach(i -> high.insert(i));
    BrickSort sorter = new BrickSort();
    sorter.sort(high);
    assertEquals(0, sorter.getSwapCount(), "Swap count must be zero.");
    assertTrue(sorter.isSorted(), SORTED_MUST_BE_SET);
  }

  @Test
  @DisplayName("BrickSortTest.testTimeComplexity")
  void testTimeComplexity() {
    IArray high = new HighArray();
    LongStream.rangeClosed(1, SCORE).forEach(i -> high.insert(i));
    BrickSort sorter = new BrickSort();
    sorter.sort(high);
    assertEquals(
        SCORE - 1, sorter.getTimeComplexity(), "Time complexity must be " + (SCORE - 1) + ".");
    assertTrue(sorter.isSorted(), SORTED_MUST_BE_SET);
  }

  @Test
  @DisplayName("BrickSortTest.testToStringClass")
  void testToStringClass() {
    AbstractSort sorter = new BrickSort();
    String className = BrickSort.class.getName();
    assertTrue(
        sorter.toString().startsWith(className),
        () -> "ToString must start with " + className + ".");
  }

  @Test
  @DisplayName("BrickSortTest.testPreReset")
  void testPreReset() {
    BrickSort sorter = new BrickSort();
    assertEquals(0, sorter.getComparisonCount(), INITIAL_VALUE_ZERO);
    assertEquals(0, sorter.getSwapCount(), INITIAL_VALUE_ZERO);
    assertEquals(0, sorter.getTimeComplexity(), INITIAL_VALUE_ZERO);
    assertEquals(0, sorter.getCopyCount(), INITIAL_VALUE_ZERO);
    assertFalse(sorter.isSorted(), "Sorted must not be set.");
  }

  @Test
  @DisplayName("BrickSortTest.testZeroTimeComplexity")
  void testZeroTimeComplexity() {
    BrickSortComplex bsc = new BrickSortComplex();
    bsc.sortZeroLengthArray();
    assertEquals(0, bsc.getTimeComplexity(), "Time Complexity must be zero.");
    assertTrue(bsc.isSorted(), SORTED_MUST_BE_SET);
  }

  @Test
  @DisplayName("BrickSortTest.testOneTimeComplexity")
  void testOneTimeComplexity() {
    BrickSortComplex bsc = new BrickSortComplex();
    bsc.sortOneLengthArray();
    assertEquals(0, bsc.getTimeComplexity(), "Time Complexity must be zero.");
    assertTrue(bsc.isSorted(), SORTED_MUST_BE_SET);
  }

  @Test
  @DisplayName("BrickSortTest.testResetSubClass")
  void testResetSubClass() {
    BrickSortComplex bsc = new BrickSortComplex();
    bsc.resetInternals();
    assertEquals(0, bsc.getTimeComplexity(), "Time Complexity must be reset.");
    assertEquals(0, bsc.getComparisonCount(), "Comparison count must be reset.");
    assertEquals(0, bsc.getSwapCount(), "Swap count must be reset.");
    assertEquals(0, bsc.getCopyCount(), "Copy count must be reset.");
    assertFalse(bsc.isSorted(), "sorted must be reset.");
  }

  @Test
  @DisplayName("BrickSortTest.testResetAfterSort")
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
  @DisplayName("BrickSortTest.testInternalsAfterSort")
  void testInternalsAfterSort() {
    BrickSortComplex bsc = new BrickSortComplex();
    bsc.sortAndSetInternals();
    assertNotEquals(0, bsc.getTimeComplexity(), "Time Complexity must be reset.");
    assertNotEquals(0, bsc.getComparisonCount(), "Comparison count must be reset.");
    assertNotEquals(0, bsc.getSwapCount(), "Swap count must be reset.");
    assertEquals(0, bsc.getCopyCount(), "Copy count must be reset.");
    assertTrue(bsc.isSorted(), "sorted must be reset.");
  }

  @Test
  @DisplayName("BrickSortTest.testInnerLoopAfterOddSort")
  void testInnerLoopAfterOddSort() {
    BrickSortComplex bsc = new BrickSortComplex();
    bsc.sortOdd();
    assertTrue(bsc.isSorted(), SORTED);
  }

  @Test
  @DisplayName("BrickSortTest.testInnerLoopAfterEvenSort")
  void testInnerLoopAfterEvenSort() {
    BrickSortComplex bsc = new BrickSortComplex();
    bsc.sortEven();
    assertTrue(bsc.isSorted(), SORTED);
  }

  @Test
  @DisplayName("BrickSortTest.testStateAfterReset")
  void testStateAfterReset() {
    BrickSortComplex bsc = new BrickSortComplex();
    bsc.sortOdd();
    final int oldInnerLoopCount = bsc.getInnerLoopCount();
    bsc.sortEven();
    final int innerLoopCount = bsc.getInnerLoopCount();
    assertNotEquals(oldInnerLoopCount, innerLoopCount, "Inner loop count must not be same.");
  }

  @Test
  @DisplayName("BrickSortTest.testEmptyArray")
  void testEmptyArray() {
    BrickSortComplex bsc = new BrickSortComplex();
    bsc.sortEmptyArray();
    assertTrue(bsc.isSorted(), SORTED);
  }

  @Test
  @DisplayName("BrickSortTest.testSingleElementArray")
  void testSingleElementArray() {
    BrickSortComplex bsc = new BrickSortComplex();
    bsc.sortSingleElementArray();
    assertTrue(bsc.isSorted(), SORTED);
  }

  static class BrickSortComplex extends BrickSort {
    Random random = new Random();

    void sortZeroLengthArray() {
      long[] a = random.longs().limit(TEN).toArray();
      sort(a, 0);
    }

    void sortOneLengthArray() {
      long[] a = random.longs().limit(TEN).toArray();
      sort(a, 1);
    }

    void sortNMinusOneLengthArray() {
      long[] a = random.longs().limit(TEN).toArray();
      sort(a, TEN - 1);
    }

    void sortEmptyArray() {
      long[] a = {};
      sort(a, 0);
    }

    void sortSingleElementArray() {
      long[] a = {TEN};
      sort(a, 1);
    }

    void resetInternals() {
      reset();
    }

    void sortAndSetInternals() {
      long[] a = random.longs().limit(TEN).toArray();
      sort(a, a.length);
    }

    void resetInternalsAfterSort() {
      sortAndSetInternals();
      reset();
    }

    void sortOdd() {
      long[] a = random.longs().limit(TEN - 1).toArray();
      sort(a, a.length);
    }

    void sortEven() {
      long[] a = random.longs().limit(TEN).toArray();
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
