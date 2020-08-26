package ds.tests;

import static ds.ArrayUtils.isSorted;
import static ds.tests.TestConstants.*;
import static ds.tests.TestData.*;
import static org.junit.jupiter.api.Assertions.*;

import ds.AbstractSort;
import ds.HighArray;
import ds.IArray;
import ds.ISort;
import ds.OrdArray;
import ds.QuickSortParallel;
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
@DisplayName("QuickSortParallelTest")
@SuppressWarnings("PMD.LawOfDemeter")
class QuickSortParallelTest implements SortProvider {

  private static final String SWAP_COUNT_ZERO = "Swap count must be zero.";
  @ParameterizedTest
  @CsvSource(INIT_DATA)
  @DisplayName("QuickSortParallelTest.testSort")
  void testSort(@AggregateWith(HighArrayArgumentsAggregator.class) IArray arr) {
    long[] a = {00, 11, 22, 33, 44, 55, 66, 77, 88, 99};
    ISort sorter = new QuickSortParallel();
    IArray sorted = sorter.sort(arr);
    long[] extent = sorted.getExtentArray();
    assertArrayEquals(a, extent, ELEMENTS_SORTED_EQUAL);
  }

  @Test
  @DisplayName("QuickSortParallelTest.testSortRandom")
  void testSortRandom() {
    Random random = new Random();
    LongStream stream = random.longs();
    stream = stream.limit(10_000);
    HighArray high = new HighArray(10_000);
    stream.forEach(i -> high.insert(i));
    ISort sorter = new QuickSortParallel();
    IArray sorted = sorter.sort(high);
    assertTrue(isSorted(sorted), "Array must be sorted.");
  }

  @Test
  @DisplayName("QuickSortParallelTest.testSortRandomMedium")
  void testSortRandomMedium() {
    Random random = new Random();
    LongStream stream = random.longs();
    stream = stream.limit(40);
    HighArray high = new HighArray(40);
    stream.forEach(i -> high.insert(i));
    ISort sorter = new QuickSortParallel();
    IArray sorted = sorter.sort(high);
    assertTrue(isSorted(sorted), "Array must be sorted.");
  }

  @ParameterizedTest
  @CsvSource(INIT_DUPLICATE_DATA)
  @DisplayName("QuickSortParallelTest.testSortDuplicates")
  void testSortDuplicates(@AggregateWith(HighArrayArgumentsAggregator.class) IArray arr) {
    long[] a = {00, 00, 00, 00, 11, 11, 11, 22, 22, 33, 33, 44, 55, 66, 77, 77, 77, 88, 88, 99, 99};
    ISort sorter = new QuickSortParallel();
    IArray sorted = sorter.sort(arr);
    long[] extent = sorted.getExtentArray();
    assertArrayEquals(a, extent, ELEMENTS_SORTED_EQUAL);
  }

  @ParameterizedTest
  @CsvSource(INIT_INSERTION_SORT_DATA)
  @DisplayName("QuickSortParallelTest.testSortSmallData")
  void testSortSmallData(@AggregateWith(HighArrayArgumentsAggregator.class) IArray arr) {
    ISort sorter = new QuickSortParallel();
    OrdArray ord = new OrdArray();
    long[] a = arr.getExtentArray();
    for (int i = 0; i < arr.count(); i++) ord.insert(a[i]);
    a = ord.getExtentArray();
    IArray sorted = sorter.sort(arr);
    long[] internal = sorted.getExtentArray();
    assertArrayEquals(a, internal, "Arrays must be sorted and equal.");
    assertTrue(isSorted(sorted), "Array must be sorted.");
  }

  @Test
  @DisplayName("QuickSortParallelTest.testSortAllSameBigData")
  void testSortAllSameBigData() {
    ISort sorter = new QuickSortParallel();
    HighArray arr = new HighArray(10_000);
    LongStream stream =
        LongStream.iterate(
            43L,
            val -> {
              return val;
            });
    stream = stream.limit(10_000);
    stream.forEach(i -> arr.insert(i));
    long[] a = arr.getExtentArray();
    IArray sorted = sorter.sort(arr);
    long[] extent = sorted.getExtentArray();
    assertArrayEquals(a, extent, ELEMENTS_SORTED_EQUAL);
    assertEquals(0, sorter.getSwapCount(), SWAP_COUNT_ZERO);
  }

  @ParameterizedTest
  @CsvSource(INIT_ALL_SAME_DATA)
  @DisplayName("QuickSortParallelTest.testSortAllSame")
  void testSortAllSame(@AggregateWith(HighArrayArgumentsAggregator.class) IArray arr) {
    long[] a = {43, 43, 43, 43, 43, 43, 43, 43, 43, 43};
    ISort sorter = new QuickSortParallel();
    IArray sorted = sorter.sort(arr);
    long[] extent = sorted.getExtentArray();
    assertArrayEquals(a, extent, ELEMENTS_SORTED_EQUAL);
    assertEquals(0, sorter.getSwapCount(), SWAP_COUNT_ZERO);
  }

  @Test
  @DisplayName("QuickSortParallelTest.testReset")
  void testReset() {
    IArray high = new HighArray();
    IArray ord = new OrdArray();
    LongStream.rangeClosed(1, 20)
        .forEach(
            i -> {
              high.insert(i);
              ord.insert(i);
            });
    ISort sorter = new QuickSortParallel();
    sorter.sort(high);
    sorter.sort(ord);
    int comparisonCount = sorter.getComparisonCount();
    assertTrue(
        20 * Math.log(20) < comparisonCount && comparisonCount <= 20 * 19,
        "Comparison count must be in range nlogn to n(n-1)");
  }

  @Test
  @DisplayName("QuickSortParallelTest.testStreamUnsorted")
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
    ISort sorter = new QuickSortParallel();
    IArray sorted = sorter.sort(high);
    long[] extentSorted = sorted.getExtentArray();
    long[] extent = ord.getExtentArray();
    assertArrayEquals(extentSorted, extent, ELEMENTS_SORTED_EQUAL);
  }

  @Test
  @DisplayName("QuickSortParallelTest.testStreamSorted")
  void testStreamSorted() {
    IArray high = new HighArray();
    IArray ord = new OrdArray();
    LongStream.rangeClosed(1, 20)
        .forEach(
            i -> {
              high.insert(i);
              ord.insert(i);
            });
    ISort sorter = new QuickSortParallel();
    IArray sorted = sorter.sort(high);
    long[] extentSorted = sorted.getExtentArray();
    long[] extent = ord.getExtentArray();
    assertArrayEquals(extentSorted, extent, ELEMENTS_SORTED_EQUAL);
    assertEquals(0, sorter.getSwapCount(), SWAP_COUNT_ZERO);
  }

  @Test
  @DisplayName("QuickSortParallelTest.testSwapCount")
  void testSwapCount() {
    IArray high = new HighArray();
    IArray ord = new OrdArray();
    LongStream.rangeClosed(1, 20)
        .forEach(
            i -> {
              high.insert(i);
              ord.insert(i);
            });
    ISort sorter = new QuickSortParallel();
    sorter.sort(high);
    assertEquals(0, sorter.getSwapCount(), SWAP_COUNT_ZERO);
  }

  @Test
  @DisplayName("QuickSortParallelTest.testTimeComplexity")
  void testTimeComplexity() {
    IArray high = new HighArray();
    LongStream.rangeClosed(1, 20).forEach(i -> high.insert(i));
    ISort sorter = new QuickSortParallel();
    sorter.sort(high);
    assertNotEquals(0, sorter.getTimeComplexity(), "Time complexity must not be zero.");
  }

  @Test
  @DisplayName("QuickSortParallelTest.testTimeComplexityReverseSorted")
  void testTimeComplexityReverseSorted() {
    IArray high = new HighArray();
    revRange(1, 20).forEach(i -> high.insert(i));
    ISort sorter = new QuickSortParallel();
    sorter.sort(high);
    assertNotEquals(0, sorter.getTimeComplexity(), "Time complexity must not be zero.");
  }

  @Test
  @DisplayName("QuickSortParallelTest.testReverseSorted")
  void testReverseSorted() {
    IArray high = new HighArray(10_000);
    revRange(1, 10_000).forEach(i -> high.insert(i));
    ISort sorter = new QuickSortParallel();
    sorter.sort(high);
    assertNotEquals(0, sorter.getSwapCount(), "Swap count must not be zero.");
  }

  @Test
  @DisplayName("QuickSortParallelTest.testReverseSortedMedium")
  void testReverseSortedMedium() {
    IArray high = new HighArray();
    revRange(1, 40).forEach(i -> high.insert(i));
    ISort sorter = new QuickSortParallel();
    sorter.sort(high);
    assertNotEquals(0, sorter.getSwapCount(), "Swap count must not be zero.");
  }

  @Test
  @DisplayName("QuickSortParallelTest.testSingleElementArray")
  void testSingleElementArray() {
    IArray high = new HighArray(1);
    high.insert(1L);
    ISort sorter = new QuickSortParallel();
    sorter.sort(high);
    assertTrue(isSorted(high), "Array is sorted.");
    assertEquals(0, sorter.getCopyCount(), "Copy count must be zero.");
    assertEquals(0, sorter.getTimeComplexity(), "Time complexity must be zero.");
    assertEquals(0, sorter.getComparisonCount(), "Comparison count must be zero.");
  }

  @Test
  @DisplayName("QuickSortParallelTest.testEmptyArray")
  void testEmptyArray() {
    long[] a = {};
    QuickSortParallelSub sorter = new QuickSortParallelSub();
    sorter.sortArray(a, 0);
    assertTrue(isSorted(a), "Array is sorted.");
    assertEquals(0, sorter.getCopyCount(), "Copy count must be zero.");
    assertEquals(0, sorter.getTimeComplexity(), "Time complexity must be zero.");
    assertEquals(0, sorter.getComparisonCount(), "Comparison count must be zero.");
  }

  @Test
  @DisplayName("QuickSortParallelTest.testIllegalArgumentException")
  void testIllegalArgumentException() {
    long[] a = {};
    QuickSortParallelSub sorter = new QuickSortParallelSub();
    assertThrows(
        IllegalArgumentException.class,
        () -> sorter.sortArray(a, -1),
        "IllegalArgumentException expected.");
  }

  @Test
  @DisplayName("QuickSortParallelTest.testToStringClass")
  void testToStringClass() {
    AbstractSort sorter = new QuickSortParallel();
    String className = QuickSortParallel.class.getName();
    assertTrue(
        sorter.toString().startsWith(className), () -> "ToString must start with " + className);
  }

  @Test
  @DisplayName("QuickSortParallelTest.testPreReset")
  void testPreReset() {
    ISort sorter = new QuickSortParallel();
    assertEquals(0, sorter.getComparisonCount(), INITIAL_VALUE_ZERO);
    assertEquals(0, sorter.getCopyCount(), INITIAL_VALUE_ZERO);
    assertEquals(0, sorter.getTimeComplexity(), INITIAL_VALUE_ZERO);
    assertEquals(0, sorter.getSwapCount(), INITIAL_VALUE_ZERO);
  }

  static final class QuickSortParallelSub extends QuickSortParallel {
    public void sortArray(long[] a, int length) {
      super.sort(a, length);
    }
  }
}
