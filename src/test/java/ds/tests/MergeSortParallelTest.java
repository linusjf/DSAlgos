package ds.tests;

import static ds.ArrayUtils.*;
import static ds.tests.TestConstants.*;
import static ds.tests.TestData.*;
import static org.junit.jupiter.api.Assertions.*;

import ds.AbstractSort;
import ds.HighArray;
import ds.IArray;
import ds.ISort;
import ds.MergeSortParallel;
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
@DisplayName("MergeSortParallelTest")
@SuppressWarnings("PMD.LawOfDemeter")
class MergeSortParallelTest implements SortProvider {

  @ParameterizedTest
  @CsvSource(INIT_DATA)
  @DisplayName("MergeSortParallelTest.testSort")
  void testSort(@AggregateWith(HighArrayArgumentsAggregator.class) IArray arr) {
    long[] a = {00, 11, 22, 33, 44, 55, 66, 77, 88, 99};
    ISort sorter = new MergeSortParallel();
    IArray sorted = sorter.sort(arr);
    long[] extent = sorted.getExtentArray();
    assertArrayEquals(a, extent, ELEMENTS_SORTED_EQUAL);
  }

  @ParameterizedTest
  @CsvSource(INIT_DUPLICATE_DATA)
  @DisplayName("MergeSortParallelTest.testSortDuplicates")
  void testSortDuplicates(@AggregateWith(HighArrayArgumentsAggregator.class) IArray arr) {
    long[] a = {00, 00, 00, 00, 11, 11, 11, 22, 22, 33, 33, 44, 55, 66, 77, 77, 77, 88, 88, 99, 99};
    ISort sorter = new MergeSortParallel();
    IArray sorted = sorter.sort(arr);
    long[] extent = sorted.getExtentArray();
    assertArrayEquals(a, extent, ELEMENTS_SORTED_EQUAL);
  }

  @ParameterizedTest
  @CsvSource(INIT_INSERTION_SORT_DATA)
  @DisplayName("MergeSortParallelTest.testSortSmallData")
  void testSortSmallData(@AggregateWith(HighArrayArgumentsAggregator.class) IArray arr) {
    ISort sorter = new MergeSortParallel();
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
  @DisplayName("MergeSortParallelTest.testSortAllSame")
  void testSortAllSame(@AggregateWith(HighArrayArgumentsAggregator.class) IArray arr) {
    long[] a = {43, 43, 43, 43, 43, 43, 43, 43, 43, 43};
    ISort sorter = new MergeSortParallel();
    IArray sorted = sorter.sort(arr);
    long[] extent = sorted.getExtentArray();
    assertArrayEquals(a, extent, ELEMENTS_SORTED_EQUAL);
    assertEquals(0, sorter.getCopyCount(), "Swap count must be zero.");
  }

  @Test
  @DisplayName("MergeSortParallelTest.testReset")
  void testReset() {
    IArray high = new HighArray(10_000);
    IArray ord = new OrdArray(10_000);
    LongStream.rangeClosed(1, 10_000)
        .forEach(
            i -> {
              high.insert(i);
              ord.insert(i);
            });
    ISort sorter = new MergeSortParallel();
    sorter.sort(high);
    sorter.sort(ord);
    int comparisonCount = sorter.getComparisonCount();
    assertEquals(9999, comparisonCount, "Comparison count must be n-1.");
  }

  @Test
  @DisplayName("MergeSortParallelTest.testStreamUnsorted")
  void testStreamUnSorted() {
    IArray high = new HighArray(10_000);
    IArray ord = new OrdArray(10_000);
    LongStream.rangeClosed(1, 10_000)
        .unordered()
        .forEach(
            i -> {
              high.insert(i);
              ord.insert(i);
            });
    ISort sorter = new MergeSortParallel();
    IArray sorted = sorter.sort(high);
    long[] extentSorted = sorted.getExtentArray();
    long[] extent = ord.getExtentArray();
    assertArrayEquals(extentSorted, extent, ELEMENTS_SORTED_EQUAL);
  }

  @Test
  @DisplayName("MergeSortParallelTest.testStreamSorted")
  void testStreamSorted() {
    IArray high = new HighArray(10_000);
    IArray ord = new OrdArray(10_000);
    LongStream.rangeClosed(1, 10_000)
        .forEach(
            i -> {
              high.insert(i);
              ord.insert(i);
            });
    ISort sorter = new MergeSortParallel();
    IArray sorted = sorter.sort(high);
    long[] extentSorted = sorted.getExtentArray();
    long[] extent = ord.getExtentArray();
    assertArrayEquals(extentSorted, extent, ELEMENTS_SORTED_EQUAL);
    assertEquals(0, sorter.getCopyCount(), "Swap count must be zero.");
  }

  @Test
  @DisplayName("MergeSortParallelTest.testSwapCount")
  void testCopyCount() {
    IArray high = new HighArray(10_000);
    IArray ord = new OrdArray(10_000);
    LongStream.rangeClosed(1, 10_000)
        .forEach(
            i -> {
              high.insert(i);
              ord.insert(i);
            });
    ISort sorter = new MergeSortParallel();
    sorter.sort(high);
    assertEquals(0, sorter.getCopyCount(), "Swap count must be zero.");
  }

  @Test
  @DisplayName("MergeSortParallelTest.testTimeComplexity")
  void testTimeComplexity() {
    IArray high = new HighArray(10_000);
    LongStream.rangeClosed(1, 10_000).forEach(i -> high.insert(i));
    ISort sorter = new MergeSortParallel();
    sorter.sort(high);
    assertEquals(0, sorter.getTimeComplexity(), "Time complexity must be zero.");
  }

  @Test
  @DisplayName("MergeSortParallelTest.testTimeComplexityReverseSorted")
  void testTimeComplexityReverseSorted() {
    IArray high = new HighArray(10_000);
    revRange(1, 10_000).forEach(i -> high.insert(i));
    ISort sorter = new MergeSortParallel();
    sorter.sort(high);
    assertNotEquals(0, sorter.getTimeComplexity(), "Time complexity must not be zero.");
  }

  @Test
  @DisplayName("MergeSortParallelTest.testReverseSorted")
  void testReverseSorted() {
    IArray high = new HighArray(10_000);
    revRange(1, 10_000).forEach(i -> high.insert(i));
    ISort sorter = new MergeSortParallel();
    sorter.sort(high);
    assertNotEquals(0, sorter.getCopyCount(), "Copy count must not be zero.");
  }

  @Test
  @DisplayName("MergeSortParallelTest.testToStringClass")
  void testToStringClass() {
    AbstractSort sorter = new MergeSortParallel();
    String className = MergeSortParallel.class.getName();
    assertTrue(
        sorter.toString().startsWith(className), () -> "ToString must start with " + className);
  }

  @Test
  @DisplayName("MergeSortParallelTest.testPreReset")
  void testPreReset() {
    ISort sorter = new MergeSortParallel();
    assertEquals(0, sorter.getComparisonCount(), INITIAL_VALUE_ZERO);
    assertEquals(0, sorter.getCopyCount(), INITIAL_VALUE_ZERO);
    assertEquals(0, sorter.getTimeComplexity(), INITIAL_VALUE_ZERO);
  }
}
