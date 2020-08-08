package ds.tests;

import static ds.tests.TestData.*;
import static org.junit.jupiter.api.Assertions.*;

import ds.AbstractSort;
import ds.CombSort;
import ds.HighArray;
import ds.IArray;
import ds.ISort;
import ds.OrdArray;
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
class CombSortTest {

  @ParameterizedTest
  @CsvSource(INIT_DATA)
  void testSort(@AggregateWith(HighArrayArgumentsAggregator.class) IArray arr) {
    long[] a = {00, 11, 22, 33, 44, 55, 66, 77, 88, 99};
    ISort sorter = new CombSort();
    IArray sorted = sorter.sort(arr);
    long[] extent = sorted.getExtentArray();
    assertArrayEquals(a, extent, "Elements must be sorted and equal.");
  }

  @ParameterizedTest
  @CsvSource(INIT_DUPLICATE_DATA)
  void testSortDuplicates(@AggregateWith(HighArrayArgumentsAggregator.class) IArray arr) {
    long[] a = {00, 00, 00, 00, 11, 11, 11, 22, 22, 33, 33, 44, 55, 66, 77, 77, 77, 88, 88, 99, 99};
    ISort sorter = new CombSort();
    IArray sorted = sorter.sort(arr);
    long[] extent = sorted.getExtentArray();
    assertArrayEquals(a, extent, "Elements must be sorted and equal.");
  }

  @ParameterizedTest
  @CsvSource(INIT_ALL_SAME_DATA)
  void testSortAllSame(@AggregateWith(HighArrayArgumentsAggregator.class) IArray arr) {
    long[] a = {43, 43, 43, 43, 43, 43, 43, 43, 43, 43};
    ISort sorter = new CombSort();
    IArray sorted = sorter.sort(arr);
    long[] extent = sorted.getExtentArray();
    assertArrayEquals(a, extent, "Elements must be sorted and equal.");
    assertEquals(0, sorter.getSwapCount(), "Swap count will be zero.");
  }

  @ParameterizedTest
  @CsvSource(INIT_COMB_SORT_DATA)
  void testSortSmallData(@AggregateWith(HighArrayArgumentsAggregator.class) IArray arr) {
    ISort sorter = new CombSort();
    IArray sorted = sorter.sort(arr);
    assertEquals(16, sorter.getSwapCount(), "Swap count will be sixteen.");
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
    ISort sorter = new CombSort();
    sorter.sort(high);
    sorter.sort(ord);
    assertEquals(110, sorter.getComparisonCount(), "Comparison count must be n -1.");
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
    ISort sorter = new CombSort();
    IArray sorted = sorter.sort(high);
    long[] extentSorted = sorted.getExtentArray();
    long[] extent = ord.getExtentArray();
    assertArrayEquals(extentSorted, extent, "Elements must be sorted and equal.");
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
    ISort sorter = new CombSort();
    IArray sorted = sorter.sort(high);
    long[] extentSorted = sorted.getExtentArray();
    long[] extent = ord.getExtentArray();
    assertArrayEquals(extentSorted, extent, "Elements must be sorted and equal.");
  }

  @Test
  void testSwapCount() {
    IArray high = new HighArray();
    IArray ord = new OrdArray();
    LongStream.rangeClosed(1, 20)
        .forEach(
            i -> {
              high.insert(i);
              ord.insert(i);
            });
    ISort sorter = new CombSort();
    sorter.sort(high);
    assertEquals(0, sorter.getSwapCount(), "Swap count must be zero.");
  }

  @Test
  void testToStringClass() {
    AbstractSort sorter = new CombSort();
    String className = CombSort.class.getName();
    assertTrue(
        sorter.toString().startsWith(className), () -> "ToString must start with " + className);
  }

  @Test
  void testPreReset() {
    ISort sorter = new CombSort();
    assertEquals(0, sorter.getComparisonCount(), "Initial value must be zero.");
    assertEquals(0, sorter.getSwapCount(), "Initial value must be zero.");
    assertEquals(0, sorter.getTimeComplexity(), "Initial value must be zero.");
    assertEquals(0, sorter.getCopyCount(), "Initial value must be zero.");
  }
}
