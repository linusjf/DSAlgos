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
import ds.SelectionSort;
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

  private static final String SORTED_AND_EQUAL = "Elements must be sorted and equal.";

  @ParameterizedTest
  @CsvSource(INIT_DATA)
  void testSort(@AggregateWith(HighArrayArgumentsAggregator.class) IArray arr) {
    long[] a = {00, 11, 22, 33, 44, 55, 66, 77, 88, 99};
    ISort sorter = new SelectionSort();
    IArray sorted = sorter.sort(arr);
    long[] extent = sorted.getExtentArray();
    assertArrayEquals(a, extent, SORTED_AND_EQUAL);
  }

  @ParameterizedTest
  @CsvSource(INIT_DUPLICATE_DATA)
  void testSortDuplicates(@AggregateWith(HighArrayArgumentsAggregator.class) IArray arr) {
    long[] a = {00, 00, 00, 00, 11, 11, 11, 22, 22, 33, 33, 44, 55, 66, 77, 77, 77, 88, 88, 99, 99};
    ISort sorter = new SelectionSort();
    IArray sorted = sorter.sort(arr);
    long[] extent = sorted.getExtentArray();
    assertArrayEquals(a, extent, SORTED_AND_EQUAL);
  }

  @ParameterizedTest
  @CsvSource(INIT_ALL_SAME_DATA)
  void testSortAllSame(@AggregateWith(HighArrayArgumentsAggregator.class) IArray arr) {
    long[] a = {43, 43, 43, 43, 43, 43, 43, 43, 43, 43};
    ISort sorter = new SelectionSort();
    IArray sorted = sorter.sort(arr);
    long[] extent = sorted.getExtentArray();
    assertArrayEquals(a, extent, SORTED_AND_EQUAL);
    assertEquals(0, sorter.getSwapCount(), "Swap count will be zero.");
  }

  @ParameterizedTest
  @CsvSource(INIT_SELECTION_SORT_DATA)
  void testSortSmallData(@AggregateWith(HighArrayArgumentsAggregator.class) IArray arr) {
    ISort sorter = new SelectionSort();
    OrdArray ord = new OrdArray();
    long[] a = arr.getExtentArray();
    for (int i = 0; i < arr.count(); i++) ord.insert(a[i]);
    a = ord.getExtentArray();
    IArray sorted = sorter.sort(arr);
    long[] internal = sorted.getExtentArray();
    assertArrayEquals(a, internal, SORTED_AND_EQUAL);
    assertEquals(5, sorter.getSwapCount(), "Swap count will be five.");
    assertTrue(isSorted(sorted), "Array must be sorted.");
  }

  @Test
  void testReset() {
    IArray high = new HighArray(20);
    IArray ord = new OrdArray();
    LongStream.rangeClosed(1, 20)
        .forEach(
            i -> {
              high.insert(i);
              ord.insert(i);
            });
    ISort sorter = new SelectionSort();
    sorter.sort(high);
    sorter.sort(ord);
    assertEquals(190, sorter.getComparisonCount(), "Comparison count must be (n * n -1) / 2.");
    assertEquals(0, sorter.getSwapCount(), "Swap count must be 0.");
  }

  @Test
  void testStreamUnSorted() {
    IArray high = new HighArray(20);
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
    assertArrayEquals(extentSorted, extent, SORTED_AND_EQUAL);
  }

  @Test
  void testStreamSorted() {
    IArray high = new HighArray(20);
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
    assertArrayEquals(extentSorted, extent, SORTED_AND_EQUAL);
  }

  @Test
  void testReverseSorted() {
    IArray high = new HighArray(20);
    revRange(1, 20).forEach(i -> high.insert(i));
    ISort sorter = new SelectionSort();
    IArray sorted = sorter.sort(high);
    long[] a = sorted.get();
    assertEquals(
        (20 * 19) / 2, sorter.getTimeComplexity(), "Time complexity must be same as n squared.");
    assertTrue(a[0] == 1 && a[19] == 20, "First element must be 1 and last element must be 20.");
  }

  @Test
  void testSortedTimeComplexity() {
    IArray high = new HighArray(20);
    LongStream.rangeClosed(1, 20).forEach(i -> high.insert(i));
    ISort sorter = new SelectionSort();
    sorter.sort(high);
    assertEquals(
        (20 * 19) / 2, sorter.getTimeComplexity(), "Time complexity must be same as n squared.");
    assertEquals(0, sorter.getSwapCount(), "Swap count must be zero.");
  }

  @Test
  void testComparisonCountUnsorted() {
    IArray high = new HighArray(20);
    LongStream.rangeClosed(1, 20).unordered().parallel().forEach(i -> high.insert(i));
    ISort sorter = new SelectionSort();
    IArray sorted = sorter.sort(high);
    long[] a = sorted.get();
    assertEquals(190, sorter.getComparisonCount(), "Comparison count must be 190.");
    assertTrue(a[0] == 1 && a[19] == 20, "First element must be 1 and last element must be 20.");
  }

  @Test
  void testSwapCountUnsorted() {
    IArray high = new HighArray(20);
    LongStream.rangeClosed(1, 20).unordered().parallel().forEach(i -> high.insert(i));
    ISort sorter = new SelectionSort();
    sorter.sort(high);
    assertTrue(sorter.getSwapCount() < 20, "Swap count must be less than array length.");
  }

  @Test
  void testSwapCountSorted() {
    IArray high = new HighArray(20);
    LongStream.rangeClosed(1, 20).forEach(i -> high.insert(i));
    ISort sorter = new SelectionSort();
    sorter.sort(high);
    assertEquals(0, sorter.getSwapCount(), "Swap count must be zero.");
  }

  @Test
  void testToStringClass() {
    AbstractSort sorter = new SelectionSort();
    String className = SelectionSort.class.getName();
    assertTrue(
        sorter.toString().startsWith(className), () -> "ToString must start with " + className);
  }

  @Test
  void testPreReset() {
    ISort sorter = new SelectionSort();
    assertEquals(0, sorter.getComparisonCount(), INITIAL_VALUE_ZERO);
    assertEquals(0, sorter.getSwapCount(), INITIAL_VALUE_ZERO);
    assertEquals(0, sorter.getTimeComplexity(), INITIAL_VALUE_ZERO);
    assertEquals(0, sorter.getCopyCount(), INITIAL_VALUE_ZERO);
  }

  @Test
  void testFullArraySort() {
    SelectionSub sorter = new SelectionSub();
    sorter.sortFullArray();
    System.out.println(sorter.toString());
    assertEquals(10, sorter.getComparisonCount(), INITIAL_VALUE_ZERO);
    assertEquals(2, sorter.getSwapCount(), INITIAL_VALUE_ZERO);
    assertEquals(10, sorter.getTimeComplexity(), INITIAL_VALUE_ZERO);
  }

  @Test
  void testNotFullArraySort() {
    SelectionSub sorter = new SelectionSub();
    sorter.sortNotFullArray();
    System.out.println(sorter.toString());
    assertEquals(6, sorter.getComparisonCount(), INITIAL_VALUE_ZERO);
    assertEquals(3, sorter.getSwapCount(), INITIAL_VALUE_ZERO);
    assertEquals(6, sorter.getTimeComplexity(), INITIAL_VALUE_ZERO);
  }

  @Test
  void testEmptyArraySort() {
    SelectionSub sorter = new SelectionSub();
    sorter.sortEmptyArray();
    System.out.println(sorter.toString());
    assertEquals(0, sorter.getComparisonCount(), INITIAL_VALUE_ZERO);
    assertEquals(0, sorter.getSwapCount(), INITIAL_VALUE_ZERO);
    assertEquals(0, sorter.getTimeComplexity(), INITIAL_VALUE_ZERO);
  }

  @Test
  void testSingleElementArraySort() {
    SelectionSub sorter = new SelectionSub();
    sorter.sortSingleElementArray();
    System.out.println(sorter.toString());
    assertEquals(0, sorter.getComparisonCount(), INITIAL_VALUE_ZERO);
    assertEquals(0, sorter.getSwapCount(), INITIAL_VALUE_ZERO);
    assertEquals(0, sorter.getTimeComplexity(), INITIAL_VALUE_ZERO);
  }

  @Test
  void testNegativeLengthArraySort() {
    SelectionSub sorter = new SelectionSub();
    assertThrows(
        IllegalArgumentException.class,
        () -> sorter.sortNegativeLengthArray(),
        "Invalid length expected.");
  }

  static class SelectionSub extends SelectionSort {

    void sortFullArray() {
      long[] a = {12, 23, 12, 5, 6};
      sort(a, 5);
    }

    void sortNotFullArray() {
      long[] a = {12, 23, 12, 5, 0};
      sort(a, 4);
    }

    void sortEmptyArray() {
      long[] a = {};
      sort(a, 0);
    }

    void sortSingleElementArray() {
      long[] a = {0};
      sort(a, 0);
    }

    void sortNegativeLengthArray() {
      long[] a = null;
      sort(a, -2);
    }
  }
}
