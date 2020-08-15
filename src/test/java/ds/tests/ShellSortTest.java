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
import ds.ShellSort;
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
class ShellSortTest implements SortProvider {

  @ParameterizedTest
  @CsvSource(INIT_DATA)
  void testSort(@AggregateWith(HighArrayArgumentsAggregator.class) IArray arr) {
    long[] a = {00, 11, 22, 33, 44, 55, 66, 77, 88, 99};
    ISort sorter = new ShellSort();
    IArray sorted = sorter.sort(arr);
    long[] extent = sorted.getExtentArray();
    assertArrayEquals(a, extent, ELEMENTS_SORTED_EQUAL);
  }

  @ParameterizedTest
  @CsvSource(INIT_DUPLICATE_DATA)
  void testSortDuplicates(@AggregateWith(HighArrayArgumentsAggregator.class) IArray arr) {
    long[] a = {00, 00, 00, 00, 11, 11, 11, 22, 22, 33, 33, 44, 55, 66, 77, 77, 77, 88, 88, 99, 99};
    ISort sorter = new ShellSort();
    IArray sorted = sorter.sort(arr);
    long[] extent = sorted.getExtentArray();
    assertArrayEquals(a, extent, ELEMENTS_SORTED_EQUAL);
  }

  @ParameterizedTest
  @CsvSource(INIT_INSERTION_SORT_DATA)
  void testSortSmallData(@AggregateWith(HighArrayArgumentsAggregator.class) IArray arr) {
    ISort sorter = new ShellSort();
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
  void testSortAllSame(@AggregateWith(HighArrayArgumentsAggregator.class) IArray arr) {
    long[] a = {43, 43, 43, 43, 43, 43, 43, 43, 43, 43};
    ISort sorter = new ShellSort();
    IArray sorted = sorter.sort(arr);
    long[] extent = sorted.getExtentArray();
    assertArrayEquals(a, extent, ELEMENTS_SORTED_EQUAL);
    assertEquals(0, sorter.getSwapCount(), "Swap count will be zero.");
  }

  @Test
  void testReset() {
    IArray high = new HighArray();
    IArray ord = new OrdArray();
    LongStream.rangeClosed(1, 20)
        .forEach(
            i -> {
              high.insert(i);
              ord.insert(i);
            });
    ISort sorter = new ShellSort();
    sorter.sort(high);
    sorter.sort(ord);
    assertEquals(19, sorter.getComparisonCount(), "Comparison count must be n -1.");
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
    ISort sorter = new ShellSort();
    IArray sorted = sorter.sort(high);
    long[] extentSorted = sorted.getExtentArray();
    long[] extent = ord.getExtentArray();
    assertArrayEquals(extentSorted, extent, ELEMENTS_SORTED_EQUAL);
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
    ISort sorter = new ShellSort();
    IArray sorted = sorter.sort(high);
    long[] extentSorted = sorted.getExtentArray();
    long[] extent = ord.getExtentArray();
    assertArrayEquals(extentSorted, extent, ELEMENTS_SORTED_EQUAL);
  }

  @Test
  void testCopyCount() {
    IArray high = new HighArray();
    IArray ord = new OrdArray();
    LongStream.rangeClosed(1, 20)
        .forEach(
            i -> {
              high.insert(i);
              ord.insert(i);
            });
    ISort sorter = new ShellSort();
    sorter.sort(high);
    assertEquals(0, sorter.getCopyCount(), "Copy count must be zero.");
  }

  @Test
  void testTimeComplexity() {
    IArray high = new HighArray();
    LongStream.rangeClosed(1, 20).forEach(i -> high.insert(i));
    ISort sorter = new ShellSort();
    sorter.sort(high);
    assertEquals(19, sorter.getTimeComplexity(), "Time complexity must be twenty.");
  }

  @Test
  void testTimeComplexityReverseSorted() {
    IArray high = new HighArray();
    revRange(1, 20).forEach(i -> high.insert(i));
    ISort sorter = new ShellSort();
    sorter.sort(high);
    assertEquals(190, sorter.getTimeComplexity(), "Time complexity must be twenty.");
  }

  @Test
  void testReverseSorted() {
    IArray high = new HighArray();
    revRange(1, 20).forEach(i -> high.insert(i));
    ISort sorter = new ShellSort();
    sorter.sort(high);
    assertEquals(
        sorter.getCopyCount(),
        sorter.getComparisonCount(),
        "Comparison count must be same as copy count in reverse ordered array.");
  }

  @Test
  void testToStringClass() {
    AbstractSort sorter = new ShellSort();
    String className = ShellSort.class.getName();
    assertTrue(
        sorter.toString().startsWith(className), () -> "ToString must start with " + className);
  }

  @Test
  void testPreReset() {
    ISort sorter = new ShellSort();
    assertEquals(0, sorter.getComparisonCount(), INITIAL_VALUE_ZERO);
    assertEquals(0, sorter.getCopyCount(), INITIAL_VALUE_ZERO);
    assertEquals(0, sorter.getTimeComplexity(), INITIAL_VALUE_ZERO);
    assertEquals(0, sorter.getCopyCount(), INITIAL_VALUE_ZERO);
  }
}
