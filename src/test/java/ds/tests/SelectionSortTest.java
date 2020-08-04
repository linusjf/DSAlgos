package ds.tests;

import static ds.tests.TestData.*;
import static org.junit.jupiter.api.Assertions.*;

import ds.HighArray;
import ds.IArray;
import ds.ISort;
import ds.OrdArray;
import ds.SelectionSort;
import java.util.logging.Logger;
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
class SelectionSortTest implements SortProvider {

  private static final Logger LOGGER = Logger.getLogger(SelectionSortTest.class.getName());

  @ParameterizedTest
  @CsvSource(INIT_DATA)
  void testSort(@AggregateWith(HighArrayArgumentsAggregator.class) IArray arr) {
    long[] a = {00, 11, 22, 33, 44, 55, 66, 77, 88, 99};
    ISort sorter = new SelectionSort();
    IArray sorted = sorter.sort(arr);
    long[] extent = sorted.getExtentArray();
    assertArrayEquals(a, extent, "Elements must be sorted and equal.");
  }

  @Test
  void testStreamUnSorted() {
    IArray high = new HighArray();
    IArray ord = new OrdArray();
    LongStream.rangeClosed(1, 20)
        .parallel()
        .unordered()
        .forEach(
            i -> {
              high.insert(i);
              ord.syncInsert(i);
            });
    ISort sorter = new SelectionSort();
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
              ord.syncInsert(i);
            });
    ISort sorter = new SelectionSort();
    IArray sorted = sorter.sort(high);
    long[] extentSorted = sorted.getExtentArray();
    long[] extent = ord.getExtentArray();
    assertArrayEquals(extentSorted, extent, "Elements must be sorted and equal.");
  }

  @Test
  void testReverseSorted() {
    IArray high = new HighArray();
    revRange(1, 20).forEach(i -> high.insert(i));
    ISort sorter = new SelectionSort();
    LOGGER.info(() -> high.toString());
    IArray sorted = sorter.sort(high);
    assertEquals(
        (20 * 19) / 2, sorter.getTimeComplexity(), "Time complexity must be same as n squared.");
  }

  @Test
  void testComparisonCountUnsorted() {
    IArray high = new HighArray();
    LongStream.rangeClosed(1, 20).unordered().parallel().forEach(i -> high.insert(i));
    ISort sorter = new SelectionSort();
    LOGGER.info(() -> high.toString());
    IArray sorted = sorter.sort(high);
    assertEquals(190, sorter.getComparisonCount(), "Comparison count must be 190.");
  }

  @Test
  void testSwapCountUnsorted() {
    IArray high = new HighArray();
    LongStream.rangeClosed(1, 20).unordered().parallel().forEach(i -> high.insert(i));
    ISort sorter = new SelectionSort();
    IArray sorted = sorter.sort(high);
    assertTrue(sorter.getSwapCount() < 20, "Swap count must be less than array length.");
  }

  @Test
  void testSwapCountSorted() {
    IArray high = new HighArray();
    LongStream.rangeClosed(1, 20).forEach(i -> high.insert(i));
    ISort sorter = new SelectionSort();
    IArray sorted = sorter.sort(high);
    assertEquals(0, sorter.getSwapCount(), "Swap count must be zero.");
  }
}
