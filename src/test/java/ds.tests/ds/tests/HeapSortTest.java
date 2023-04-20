package ds.tests;

import static ds.ArrayUtils.*;
import static ds.tests.TestConstants.*;
import static ds.tests.TestData.*;
import static java.lang.Math.*;
import static org.junit.jupiter.api.Assertions.*;

import ds.AbstractSort;
import ds.HeapSort;
import ds.HighArray;
import ds.IArray;
import ds.ISort;
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
@DisplayName("HeapSortTest")
@SuppressWarnings("PMD.LawOfDemeter")
class HeapSortTest implements SortProvider {

  @ParameterizedTest
  @CsvSource(INIT_DATA)
  @DisplayName("HeapSortTest.testSort")
  void testSort(@AggregateWith(HighArrayArgumentsAggregator.class) IArray arr) {
    long[] a = {00, 11, 22, 33, 44, 55, 66, 77, 88, 99};
    ISort sorter = new HeapSort();
    IArray sorted = sorter.sort(arr);
    long[] extent = sorted.getExtentArray();
    assertArrayEquals(a, extent, ELEMENTS_SORTED_EQUAL);
  }

  @Test
  @DisplayName("HeapSortTest.testSortRandom")
  void testSortRandom() {
    Random random = new Random();
    LongStream stream = random.longs().limit(MYRIAD);
    HighArray high = new HighArray(MYRIAD);
    try (stream; ) {
      stream.forEach(i -> high.insert(i));
    }
    ISort sorter = new HeapSort();
    IArray sorted = sorter.sort(high);
    assertTrue(isSorted(sorted), "Array must be sorted.");
  }

  @ParameterizedTest
  @CsvSource(INIT_DUPLICATE_DATA)
  @DisplayName("HeapSortTest.testSortDuplicates")
  void testSortDuplicates(@AggregateWith(HighArrayArgumentsAggregator.class) IArray arr) {
    long[] a = {00, 00, 00, 00, 11, 11, 11, 22, 22, 33, 33, 44, 55, 66, 77, 77, 77, 88, 88, 99, 99};
    ISort sorter = new HeapSort();
    IArray sorted = sorter.sort(arr);
    long[] extent = sorted.getExtentArray();
    assertArrayEquals(a, extent, ELEMENTS_SORTED_EQUAL);
  }

  @ParameterizedTest
  @CsvSource(INIT_INSERTION_SORT_DATA)
  @DisplayName("HeapSortTest.testSortSmallData")
  void testSortSmallData(@AggregateWith(HighArrayArgumentsAggregator.class) IArray arr) {
    ISort sorter = new HeapSort();
    OrdArray ord = new OrdArray();
    long[] a = arr.getExtentArray();
    for (int i = 0; i < arr.count(); i++) ord.insert(a[i]);
    a = ord.getExtentArray();
    IArray sorted = sorter.sort(arr);
    long[] internal = sorted.getExtentArray();
    assertArrayEquals(a, internal, "Arrays must be sorted and equal.");
    assertTrue(isSorted(sorted), "Array must be sorted.");
  }

  @ParameterizedTest
  @CsvSource(INIT_ALL_SAME_DATA)
  @DisplayName("HeapSortTest.testSortAllSame")
  void testSortAllSame(@AggregateWith(HighArrayArgumentsAggregator.class) IArray arr) {
    long[] a = {43, 43, 43, 43, 43, 43, 43, 43, 43, 43};
    ISort sorter = new HeapSort();
    IArray sorted = sorter.sort(arr);
    long[] extent = sorted.getExtentArray();
    assertArrayEquals(a, extent, ELEMENTS_SORTED_EQUAL);
    assertEquals(0, sorter.getSwapCount(), "Swap count must be zero.");
  }

  @Test
  @DisplayName("HeapSortTest.testReset")
  void testReset() {
    IArray high = new HighArray();
    IArray ord = new OrdArray();
    LongStream.rangeClosed(1, SCORE)
        .forEach(
            i -> {
              high.insert(i);
              ord.insert(i);
            });
    ISort sorter = new HeapSort();
    sorter.sort(high);
    sorter.sort(ord);
    int comparisonCount = sorter.getComparisonCount();
    int lowerBound = (int) (SCORE * log(SCORE) / log(2));
    int upperBound = 1 << lowerBound;
    assertTrue(
        lowerBound <= comparisonCount && comparisonCount <= upperBound,
        "Comparison count must be in n-1.");
  }

  @Test
  @DisplayName("HeapSortTest.testStreamUnsorted")
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
    ISort sorter = new HeapSort();
    IArray sorted = sorter.sort(high);
    long[] extentSorted = sorted.getExtentArray();
    long[] extent = ord.getExtentArray();
    assertArrayEquals(extentSorted, extent, ELEMENTS_SORTED_EQUAL);
  }

  @Test
  @DisplayName("HeapSortTest.testStreamSorted")
  void testStreamSorted() {
    IArray high = new HighArray();
    IArray ord = new OrdArray();
    LongStream.rangeClosed(1, SCORE)
        .forEach(
            i -> {
              high.insert(i);
              ord.insert(i);
            });
    ISort sorter = new HeapSort();
    IArray sorted = sorter.sort(high);
    long[] extentSorted = sorted.getExtentArray();
    long[] extent = ord.getExtentArray();
    int swapCount = sorter.getSwapCount();
    assertTrue(
        swapCount <= (SCORE * log(SCORE) / log(2)),
        "Swap count must be less than or equal to n * log n.");
    assertArrayEquals(extentSorted, extent, ELEMENTS_SORTED_EQUAL);
  }

  @Test
  @DisplayName("HeapSortTest.testSwapCount")
  void testSwapCount() {
    IArray high = new HighArray();
    IArray ord = new OrdArray();
    LongStream.rangeClosed(1, SCORE)
        .forEach(
            i -> {
              high.insert(i);
              ord.insert(i);
            });
    ISort sorter = new HeapSort();
    sorter.sort(high);
    int swapCount = sorter.getSwapCount();
    assertTrue(
        swapCount <= (SCORE * log(SCORE) / log(2)),
        "Swap count must be less than or equal to n * log n.");
  }

  @Test
  @DisplayName("HeapSortTest.testTimeComplexity")
  void testTimeComplexity() {
    IArray high = new HighArray();
    LongStream.rangeClosed(1, SCORE).forEach(i -> high.insert(i));
    ISort sorter = new HeapSort();
    sorter.sort(high);
    assertNotEquals(0, sorter.getTimeComplexity(), "Time complexity must be zero.");
  }

  @Test
  @DisplayName("HeapSortTest.testTimeComplexityReverseSorted")
  void testTimeComplexityReverseSorted() {
    IArray high = new HighArray();
    revRange(1, SCORE).forEach(i -> high.insert(i));
    ISort sorter = new HeapSort();
    sorter.sort(high);
    assertNotEquals(0, sorter.getTimeComplexity(), "Time complexity must not be zero.");
  }

  @Test
  @DisplayName("HeapSortTest.testReverseSorted")
  void testReverseSorted() {
    IArray high = new HighArray();
    revRange(1, SCORE).forEach(i -> high.insert(i));
    ISort sorter = new HeapSort();
    sorter.sort(high);
    int swapCount = sorter.getSwapCount();
    assertTrue(
        swapCount <= (SCORE * log(SCORE) / log(2)),
        "Swap count must be less than or equal to n * log n.");
  }

  @Test
  @DisplayName("HeapSortTest.testSingleElementArray")
  void testSingleElementArray() {
    IArray high = new HighArray(1);
    high.insert(1L);
    ISort sorter = new HeapSort();
    sorter.sort(high);
    assertTrue(isSorted(high), "Array is sorted.");
    assertEquals(0, sorter.getCopyCount(), "Copy count must be zero.");
    assertEquals(0, sorter.getTimeComplexity(), "Time complexity must be zero.");
    assertEquals(0, sorter.getComparisonCount(), "Comparison count must be zero.");
  }

  @Test
  @DisplayName("HeapSortTest.testEmptyArray")
  void testEmptyArray() {
    long[] a = {};
    HeapSortSub sorter = new HeapSortSub();
    sorter.sortArray(a, 0);
    assertTrue(isSorted(a), "Array is sorted.");
    assertEquals(0, sorter.getCopyCount(), "Copy count must be zero.");
    assertEquals(0, sorter.getTimeComplexity(), "Time complexity must be zero.");
    assertEquals(0, sorter.getComparisonCount(), "Comparison count must be zero.");
  }

  @Test
  @DisplayName("HeapSortTest.testIllegalArgumentException")
  void testIllegalArgumentException() {
    long[] a = {};
    HeapSortSub sorter = new HeapSortSub();
    assertThrows(
        IllegalArgumentException.class,
        () -> sorter.sortArray(a, -1),
        "IllegalArgumentException expected.");
  }

  @Test
  @DisplayName("HeapSortTest.testToStringClass")
  void testToStringClass() {
    AbstractSort sorter = new HeapSort();
    String className = HeapSort.class.getName();
    assertTrue(
        sorter.toString().startsWith(className),
        () -> "ToString must start with " + className + ".");
  }

  @Test
  @DisplayName("HeapSortTest.testPreReset")
  void testPreReset() {
    ISort sorter = new HeapSort();
    assertEquals(0, sorter.getComparisonCount(), INITIAL_VALUE_ZERO);
    assertEquals(0, sorter.getSwapCount(), INITIAL_VALUE_ZERO);
    assertEquals(0, sorter.getTimeComplexity(), INITIAL_VALUE_ZERO);
  }

  static final class HeapSortSub extends HeapSort {
    public void sortArray(long[] a, int length) {
      super.sort(a, length);
    }
  }
}
