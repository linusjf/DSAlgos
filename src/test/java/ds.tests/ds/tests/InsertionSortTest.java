package ds.tests;

import static ds.ArrayUtils.*;
import static ds.tests.TestConstants.*;
import static ds.tests.TestData.*;
import static org.junit.jupiter.api.Assertions.*;

import ds.AbstractSort;
import ds.HighArray;
import ds.IArray;
import ds.ISort;
import ds.InsertionSort;
import ds.OrdArray;
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
@DisplayName("InsertionSortTest")
class InsertionSortTest implements SortProvider {

  @ParameterizedTest
  @CsvSource(INIT_DATA)
  @DisplayName("InsertionSortTest.testSort")
  void testSort(@AggregateWith(HighArrayArgumentsAggregator.class) IArray arr) {
    long[] a = {00, 11, 22, 33, 44, 55, 66, 77, 88, 99};
    ISort sorter = new InsertionSort();
    IArray sorted = sorter.sort(arr);
    long[] extent = sorted.getExtentArray();
    assertArrayEquals(a, extent, ELEMENTS_SORTED_EQUAL);
  }

  @ParameterizedTest
  @CsvSource(INIT_DUPLICATE_DATA)
  @DisplayName("InsertionSortTest.testSortDuplicates")
  void testSortDuplicates(@AggregateWith(HighArrayArgumentsAggregator.class) IArray arr) {
    long[] a = {00, 00, 00, 00, 11, 11, 11, 22, 22, 33, 33, 44, 55, 66, 77, 77, 77, 88, 88, 99, 99};
    ISort sorter = new InsertionSort();
    IArray sorted = sorter.sort(arr);
    long[] extent = sorted.getExtentArray();
    assertArrayEquals(a, extent, ELEMENTS_SORTED_EQUAL);
  }

  @ParameterizedTest
  @CsvSource(INIT_INSERTION_SORT_DATA)
  @DisplayName("InsertionSortTest.testSortSmallData")
  void testSortSmallData(@AggregateWith(HighArrayArgumentsAggregator.class) IArray arr) {
    ISort sorter = new InsertionSort();
    OrdArray ord = new OrdArray();
    long[] a = arr.getExtentArray();
    for (int i = 0; i < arr.count(); i++) ord.insert(a[i]);
    a = ord.getExtentArray();
    IArray sorted = sorter.sort(arr);
    long[] internal = sorted.getExtentArray();
    assertArrayEquals(a, internal, "Arrays must be sorted and equal.");
    assertEquals(12, sorter.getCopyCount(), "Copy count will be twelve.");
    assertTrue(isSorted(sorted), "Array must be sorted.");
  }

  @ParameterizedTest
  @CsvSource(INIT_ALL_SAME_DATA)
  @DisplayName("InsertionSortTest.testSortAllSame")
  void testSortAllSame(@AggregateWith(HighArrayArgumentsAggregator.class) IArray arr) {
    long[] a = {43, 43, 43, 43, 43, 43, 43, 43, 43, 43};
    ISort sorter = new InsertionSort();
    IArray sorted = sorter.sort(arr);
    long[] extent = sorted.getExtentArray();
    assertArrayEquals(a, extent, ELEMENTS_SORTED_EQUAL);
    assertEquals(0, sorter.getSwapCount(), "Swap count will be zero.");
  }

  @Test
  @DisplayName("InsertionSortTest.testReset")
  void testReset() {
    IArray high = new HighArray();
    IArray ord = new OrdArray();
    LongStream.rangeClosed(1, SCORE)
        .forEach(
            i -> {
              high.insert(i);
              ord.insert(i);
            });
    ISort sorter = new InsertionSort();
    sorter.sort(high);
    sorter.sort(ord);
    assertEquals(
        SCORE - 1, sorter.getComparisonCount(), "Comparison count must be " + (SCORE - 1) + ".");
  }

  @Test
  @DisplayName("InsertionSortTest.testStreamUnSorted")
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
    ISort sorter = new InsertionSort();
    IArray sorted = sorter.sort(high);
    long[] extentSorted = sorted.getExtentArray();
    long[] extent = ord.getExtentArray();
    assertArrayEquals(extentSorted, extent, ELEMENTS_SORTED_EQUAL);
  }

  @Test
  @DisplayName("InsertionSortTest.testStreamSorted")
  void testStreamSorted() {
    IArray high = new HighArray();
    IArray ord = new OrdArray();
    LongStream.rangeClosed(1, SCORE)
        .forEach(
            i -> {
              high.insert(i);
              ord.insert(i);
            });
    ISort sorter = new InsertionSort();
    IArray sorted = sorter.sort(high);
    long[] extentSorted = sorted.getExtentArray();
    long[] extent = ord.getExtentArray();
    assertArrayEquals(extentSorted, extent, ELEMENTS_SORTED_EQUAL);
  }

  @Test
  @DisplayName("InsertionSortTest.testCopyCount")
  void testCopyCount() {
    IArray high = new HighArray();
    IArray ord = new OrdArray();
    LongStream.rangeClosed(1, SCORE)
        .forEach(
            i -> {
              high.insert(i);
              ord.insert(i);
            });
    ISort sorter = new InsertionSort();
    sorter.sort(high);
    assertEquals(0, sorter.getCopyCount(), "Copy count must be zero.");
  }

  @Test
  @DisplayName("InsertionSortTest.testTimeComplexity")
  void testTimeComplexity() {
    IArray high = new HighArray();
    LongStream.rangeClosed(1, SCORE).forEach(i -> high.insert(i));
    ISort sorter = new InsertionSort();
    sorter.sort(high);
    assertEquals(SCORE - 1, sorter.getTimeComplexity(), "Time complexity must be twenty.");
  }

  @Test
  @DisplayName("InsertionSortTest.testTimeComplexityReverseSorted")
  void testTimeComplexityReverseSorted() {
    IArray high = new HighArray();
    revRange(1, SCORE).forEach(i -> high.insert(i));
    ISort sorter = new InsertionSort();
    sorter.sort(high);
    assertEquals(
        (SCORE * (SCORE - 1)) >> 1, sorter.getTimeComplexity(), "Time complexity must be twenty.");
  }

  @Test
  @DisplayName("InsertionSortTest.testReverseSorted")
  void testReverseSorted() {
    IArray high = new HighArray();
    revRange(1, SCORE).forEach(i -> high.insert(i));
    ISort sorter = new InsertionSort();
    sorter.sort(high);
    assertEquals(
        sorter.getCopyCount(),
        sorter.getComparisonCount(),
        "Comparison count must be same as copy count in reverse ordered array.");
  }

  @Test
  @DisplayName("InsertionSortTest.testToStringClass")
  void testToStringClass() {
    AbstractSort sorter = new InsertionSort();
    String className = InsertionSort.class.getName();
    assertTrue(
        sorter.toString().startsWith(className), () -> "ToString must start with " + className);
  }

  @Test
  @DisplayName("InsertionSortTest.testPreReset")
  void testPreReset() {
    ISort sorter = new InsertionSort();
    assertEquals(0, sorter.getComparisonCount(), INITIAL_VALUE_ZERO);
    assertEquals(0, sorter.getCopyCount(), INITIAL_VALUE_ZERO);
    assertEquals(0, sorter.getTimeComplexity(), INITIAL_VALUE_ZERO);
    assertEquals(0, sorter.getCopyCount(), INITIAL_VALUE_ZERO);
  }

  @Test
  @DisplayName("InsertionSortTest.testEmptyArraySort")
  void testEmptyArraySort() {
    InsertionSub sorter = new InsertionSub();
    sorter.sortEmptyArray();
    assertEquals(0, sorter.getComparisonCount(), INITIAL_VALUE_ZERO);
    assertEquals(0, sorter.getCopyCount(), INITIAL_VALUE_ZERO);
    assertEquals(0, sorter.getTimeComplexity(), INITIAL_VALUE_ZERO);
  }

  @Test
  @DisplayName("InsertionSortTest.testSingleElementArraySort")
  void testSingleElementArraySort() {
    InsertionSub sorter = new InsertionSub();
    sorter.sortSingleElementArray();
    assertEquals(0, sorter.getComparisonCount(), INITIAL_VALUE_ZERO);
    assertEquals(0, sorter.getCopyCount(), INITIAL_VALUE_ZERO);
    assertEquals(0, sorter.getTimeComplexity(), INITIAL_VALUE_ZERO);
  }

  @Test
  @DisplayName("InsertionSortTest.testNegativeLengthArraySort")
  void testNegativeLengthArraySort() {
    InsertionSub sorter = new InsertionSub();
    assertThrows(
        IllegalArgumentException.class,
        () -> sorter.sortNegativeLengthArray(),
        "Invalid length expected.");
  }

  static class InsertionSub extends InsertionSort {

    void sortEmptyArray() {
      long[] a = {};
      sort(a, 0);
    }

    void sortSingleElementArray() {
      long[] a = {0};
      sort(a, 0);
    }

    void sortNegativeLengthArray() {
      long[] a = {};
      sort(a, -1 * TEN);
    }
  }
}
