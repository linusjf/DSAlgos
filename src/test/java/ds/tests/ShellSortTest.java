package ds.tests;

import static ds.ArrayUtils.*;
import static ds.tests.TestConstants.*;
import static ds.tests.TestData.*;
import static org.junit.jupiter.api.Assertions.*;

import ds.AbstractSort;
import ds.HighArray;
import ds.IArray;
import ds.ISort;
import ds.OrdArray;
import ds.RandomUtils;
import ds.ShellSort;
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
@DisplayName("ShellSortTest")
class ShellSortTest implements SortProvider {

  private static final String COPY_COUNT_ZERO = "Copy count must be zero.";

  @ParameterizedTest
  @CsvSource(INIT_DATA)
  @DisplayName("ShellSortTest.testSort")
  void testSort(@AggregateWith(HighArrayArgumentsAggregator.class) IArray arr) {
    long[] a = {00, 11, 22, 33, 44, 55, 66, 77, 88, 99};
    ISort sorter = new ShellSort();
    IArray sorted = sorter.sort(arr);
    long[] extent = sorted.getExtentArray();
    assertArrayEquals(a, extent, ELEMENTS_SORTED_EQUAL);
  }

  @Test
  @DisplayName("ShellSortTest.testSortRandom")
  void testSortRandom() {
    HighArray arr = new HighArray(MYRIAD);
    OrdArray ord = new OrdArray(MYRIAD);
    try (LongStream stream = RandomUtils.longStream().limit(MYRIAD)) {
      stream.forEach(
          i -> {
            arr.insert(i);
            ord.insert(i);
          });
    }
    ISort sorter = new ShellSort();
    IArray sorted = sorter.sort(arr);
    long[] extent = sorted.getExtentArray();
    long[] a = ord.getExtentArray();
    assertArrayEquals(a, extent, ELEMENTS_SORTED_EQUAL);
  }

  @ParameterizedTest
  @CsvSource(INIT_DUPLICATE_DATA)
  @DisplayName("ShellSortTest.testSortDuplicates")
  void testSortDuplicates(@AggregateWith(HighArrayArgumentsAggregator.class) IArray arr) {
    long[] a = {00, 00, 00, 00, 11, 11, 11, 22, 22, 33, 33, 44, 55, 66, 77, 77, 77, 88, 88, 99, 99};
    ISort sorter = new ShellSort();
    IArray sorted = sorter.sort(arr);
    long[] extent = sorted.getExtentArray();
    assertArrayEquals(a, extent, ELEMENTS_SORTED_EQUAL);
  }

  @ParameterizedTest
  @CsvSource(INIT_INSERTION_SORT_DATA)
  @DisplayName("ShellSortTest.testSortSmallData")
  void testSortSmallData(@AggregateWith(HighArrayArgumentsAggregator.class) IArray arr) {
    ISort sorter = new ShellSort();
    OrdArray ord = new OrdArray();
    long[] a = arr.getExtentArray();
    for (int i = 0; i < arr.count(); i++) ord.insert(a[i]);
    a = ord.getExtentArray();
    IArray sorted = sorter.sort(arr);
    long[] internal = sorted.getExtentArray();
    assertArrayEquals(a, internal, "Arrays must be sorted and equal.");
    assertEquals(8, sorter.getCopyCount(), "Copy count will be eight.");
    assertTrue(isSorted(sorted), "Array must be sorted.");
  }

  @ParameterizedTest
  @CsvSource(INIT_ALL_SAME_DATA)
  @DisplayName("ShellSortTest.testSortAllSame")
  void testSortAllSame(@AggregateWith(HighArrayArgumentsAggregator.class) IArray arr) {
    long[] a = {43, 43, 43, 43, 43, 43, 43, 43, 43, 43};
    ISort sorter = new ShellSort();
    IArray sorted = sorter.sort(arr);
    long[] extent = sorted.getExtentArray();
    assertArrayEquals(a, extent, ELEMENTS_SORTED_EQUAL);
    assertEquals(0, sorter.getSwapCount(), "Swap count will be zero.");
  }

  @Test
  @DisplayName("ShellSortTest.testReset")
  void testReset() {
    IArray high = new HighArray();
    IArray ord = new OrdArray();
    LongStream.rangeClosed(1, SCORE)
        .forEach(
            i -> {
              high.insert(i);
              ord.insert(i);
            });
    ISort sorter = new ShellSort();
    sorter.sort(high);
    sorter.sort(ord);
    assertEquals(62, sorter.getComparisonCount(), "Comparison count must be 62.");
  }

  @Test
  @DisplayName("ShellSortTest.testStresmUnSorted")
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
    ISort sorter = new ShellSort();
    IArray sorted = sorter.sort(high);
    long[] extentSorted = sorted.getExtentArray();
    long[] extent = ord.getExtentArray();
    assertArrayEquals(extentSorted, extent, ELEMENTS_SORTED_EQUAL);
  }

  @Test
  @DisplayName("ShellSortTest.testStresmSorted")
  void testStreamSorted() {
    IArray high = new HighArray();
    IArray ord = new OrdArray();
    LongStream.rangeClosed(1, SCORE)
        .forEach(
            i -> {
              high.insert(i);
              ord.insert(i);
            });
    ISort sorter = new ShellSort();
    IArray sorted = sorter.sort(high);
    long[] extentSorted = sorted.getExtentArray();
    long[] extent = ord.getExtentArray();
    assertArrayEquals(extentSorted, extent, ELEMENTS_SORTED_EQUAL);
  }

  @Test
  @DisplayName("ShellSortTest.testCopyCount")
  void testCopyCount() {
    IArray high = new HighArray();
    IArray ord = new OrdArray();
    LongStream.rangeClosed(1, SCORE)
        .forEach(
            i -> {
              high.insert(i);
              ord.insert(i);
            });
    ISort sorter = new ShellSort();
    sorter.sort(high);
    assertEquals(0, sorter.getCopyCount(), COPY_COUNT_ZERO);
  }

  @Test
  @DisplayName("ShellSortTest.testTimeComplexity")
  void testTimeComplexity() {
    IArray high = new HighArray();
    LongStream.rangeClosed(1, SCORE).forEach(i -> high.insert(i));
    ISort sorter = new ShellSort();
    sorter.sort(high);
    assertEquals(62, sorter.getTimeComplexity(), "Time complexity must be 248.");
  }

  @Test
  @DisplayName("ShellSortTest.testTimeComplexityReverseSorted")
  void testTimeComplexityReverseSorted() {
    IArray high = new HighArray();
    revRange(1, SCORE).forEach(i -> high.insert(i));
    ISort sorter = new ShellSort();
    sorter.sort(high);
    assertEquals(36, sorter.getTimeComplexity(), "Time complexity must be thirty six.");
  }

  @Test
  @DisplayName("ShellSortTest.testReverseSorted")
  void testReverseSorted() {
    IArray high = new HighArray();
    revRange(1, SCORE).forEach(i -> high.insert(i));
    ISort sorter = new ShellSort();
    sorter.sort(high);
    assertEquals(36, sorter.getCopyCount(), "Copy count must be 36 in reverse ordered array.");
  }

  @Test
  @DisplayName("ShellSortTest.testSingleElementArray")
  void testSingleElementArray() {
    IArray high = new HighArray(1);
    high.insert(1L);
    ISort sorter = new ShellSort();
    sorter.sort(high);
    assertTrue(isSorted(high), "Array is sorted.");
    assertEquals(0, sorter.getCopyCount(), COPY_COUNT_ZERO);
    assertEquals(0, sorter.getTimeComplexity(), "Time complexity must be zero.");
    assertEquals(0, sorter.getComparisonCount(), "Comparison count must be zero.");
  }

  @Test
  @DisplayName("ShellSortTest.testEmptyArray")
  void testEmptyArray() {
    long[] a = {};
    ShellSortSub sorter = new ShellSortSub();
    sorter.sortArray(a, 0);
    assertTrue(isSorted(a), "Array is sorted.");
    assertEquals(0, sorter.getCopyCount(), COPY_COUNT_ZERO);
    assertEquals(0, sorter.getTimeComplexity(), "Time complexity must be zero.");
    assertEquals(0, sorter.getComparisonCount(), "Comparison count must be zero.");
  }

  @Test
  @DisplayName("ShellSortTest.testToStringClass")
  void testToStringClass() {
    AbstractSort sorter = new ShellSort();
    String className = ShellSort.class.getName();
    assertTrue(
        sorter.toString().startsWith(className), () -> "ToString must start with " + className);
  }

  @Test
  @DisplayName("ShellSortTest.testPreReset")
  void testPreReset() {
    ISort sorter = new ShellSort();
    assertEquals(0, sorter.getComparisonCount(), INITIAL_VALUE_ZERO);
    assertEquals(0, sorter.getCopyCount(), INITIAL_VALUE_ZERO);
    assertEquals(0, sorter.getTimeComplexity(), INITIAL_VALUE_ZERO);
    assertEquals(0, sorter.getCopyCount(), INITIAL_VALUE_ZERO);
  }

  @Test
  @DisplayName("ShellSortTest.testEmptyArraySort")
  void testEmptyArraySort() {
    ShellSortSub sorter = new ShellSortSub();
    sorter.sortEmptyArray();
    assertEquals(0, sorter.getComparisonCount(), INITIAL_VALUE_ZERO);
    assertEquals(0, sorter.getSwapCount(), INITIAL_VALUE_ZERO);
    assertEquals(0, sorter.getTimeComplexity(), INITIAL_VALUE_ZERO);
  }

  @Test
  @DisplayName("ShellSortTest.testSingleElementArraySort")
  void testSingleElementArraySort() {
    ShellSortSub sorter = new ShellSortSub();
    sorter.sortSingleElementArray();
    assertEquals(0, sorter.getComparisonCount(), INITIAL_VALUE_ZERO);
    assertEquals(0, sorter.getSwapCount(), INITIAL_VALUE_ZERO);
    assertEquals(0, sorter.getTimeComplexity(), INITIAL_VALUE_ZERO);
  }

  @Test
  @DisplayName("ShellSortTest.testSortTwoElementArraySorted")
  void testSortTwoElementArraySorted() {
    ShellSortSub sorter = new ShellSortSub();
    sorter.sortTwoElementArraySorted();
    assertEquals(1, sorter.getComparisonCount(), "Comparison count must be 2.");
    assertEquals(0, sorter.getCopyCount(), COPY_COUNT_ZERO);
    assertEquals(1, sorter.getTimeComplexity(), "Time complexity must be one.");
  }

  @Test
  @DisplayName("ShellSortTest.testSortTwoElementArrayUnsorted")
  void testSortTwoElementArrayUnsorted() {
    ShellSortSub sorter = new ShellSortSub();
    sorter.sortTwoElementArrayUnsorted();
    assertEquals(1, sorter.getComparisonCount(), "Comparison count must be 2.");
    assertEquals(1, sorter.getCopyCount(), "Copy count must be one.");
    assertEquals(1, sorter.getTimeComplexity(), "Time complexity must be one.");
  }

  static final class ShellSortSub extends ShellSort {
    void sortArray(long[] a, int length) {
      super.sort(a, length);
    }

    void sortEmptyArray() {
      long[] a = {};
      sort(a, 0);
    }

    void sortSingleElementArray() {
      long[] a = {0};
      sort(a, 0);
    }

    void sortTwoElementArraySorted() {
      long[] a = {1L, 2L};
      sort(a, 2);
    }

    void sortTwoElementArrayUnsorted() {
      long[] a = {2L, 1L};
      sort(a, 2);
    }
  }
}
