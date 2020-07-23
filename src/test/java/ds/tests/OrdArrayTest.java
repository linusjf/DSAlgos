package ds.tests;

import static ds.ArrayUtils.*;
import static ds.tests.TestUtils.*;
import static ds.tests.TestConstants.NOT_AVAILABLE;
import static org.joor.Reflect.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import ds.OrdArray;
import java.util.Arrays;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

@TestInstance(Lifecycle.PER_CLASS)
@Execution(ExecutionMode.SAME_THREAD)
@SuppressWarnings("PMD.LawOfDemeter")
class OrdArrayTest {
  private static final Logger LOGGER = Logger.getLogger(OrdArrayTest.class.getName());


  private static final String SORTED = "sorted";

  private static final String DIRTY = "dirty";

  OrdArray insertElements() {
    OrdArray arr = new OrdArray(100);
    // insert 10 items
    arr.insert(77L);
    arr.insert(99L);
    arr.insert(44L);
    arr.insert(55L);
    arr.insert(22L);
    arr.insert(88L);
    arr.insert(11L);
    arr.insert(00L);
    arr.insert(66L);
    arr.insert(33L);
    return arr;
  }

  OrdArray insertSequentialElements() {
    OrdArray arr = new OrdArray(100);
    // insert 10 items
    arr.insert(11L);
    arr.insert(12L);
    arr.insert(13L);
    arr.insert(14L);
    arr.insert(15L);
    arr.insert(16L);
    arr.insert(17L);
    arr.insert(18L);
    arr.insert(19L);
    arr.insert(20L);
    return arr;
  }

  private void insertElements(OrdArray arr) {
    // insert 10 items
    arr.insert(77L);
    arr.insert(99L);
    arr.insert(44L);
    arr.insert(55L);
    arr.insert(22L);
    arr.insert(88L);
    arr.insert(11L);
    arr.insert(00L);
    arr.insert(66L);
    arr.insert(33L);
  }

  @Test
  void insertDuplicate() {
    OrdArray arr = insertElements();
    assertTrue(6 == arr.insert(66L) && isSorted(arr), "Index 6 expected");
  }

  @Test
  void insertSyncDuplicate() {
    OrdArray arr = insertElements();
    assertTrue(6 == arr.syncInsert(66L) && isSorted(arr), "7 elements expected");
  }

  @Test
  void insertDuplicateElements() {
    OrdArray arr = new OrdArray(100);
    // insert 21 items
    arr.insert(77L);
    arr.insert(77L);
    arr.insert(99L);
    arr.insert(77L);
    arr.insert(99L);
    arr.insert(44L);
    arr.insert(55L);
    arr.insert(22L);
    arr.insert(22L);
    arr.insert(88L);
    arr.insert(88L);
    arr.insert(11L);
    arr.insert(11L);
    arr.insert(11L);
    arr.insert(00L);
    arr.insert(00L);
    arr.insert(00L);
    arr.insert(00L);
    arr.insert(66L);
    arr.insert(33L);
    arr.insert(33L);
    assertTrue(21 == arr.count() && isSorted(arr), "21 elements expected");
  }

  @Test
  void testConstructorParameterNegative() {
    IllegalArgumentException iae =
        assertThrows(
            IllegalArgumentException.class,
            () -> new OrdArray(-1),
            "IllegalArgumentException expected for " + -1);
    Optional<String> msg = Optional.ofNullable(iae.getMessage());
    String val = msg.orElse("");
    assertTrue(val.contains("-1"), "Parameter -1 expected");
  }

  @Test
  void testConstructorParameterOK() {
    OrdArray arr = new OrdArray(10);
    assertEquals(10, arr.get().length, "Length 10 expected");
  }

  @Test
  void testEmptyConstructor() {
    OrdArray arr = new OrdArray();
    boolean strict = (boolean) on(arr).get("strict");
    assertTrue(arr.get().length == 100 && !strict, "Length 100 and strict false expected");
  }

  @Test
  void testConstructorParameterZero() {
    IllegalArgumentException iae =
        assertThrows(
            IllegalArgumentException.class,
            () -> new OrdArray(0),
            "IllegalArgumentException expected for " + 0);
    Optional<String> msg = Optional.ofNullable(iae.getMessage());
    String val = msg.orElse("");
    assertTrue(val.contains("0"), "Parameter 0 expected");
  }

  @Test
  void testInsertOnDirty() {
    OrdArray arr = insertElements();
    on(arr).set(DIRTY, true);
    arr.insert(10L);
    assertTrue(arr.count() == 11 && isSorted(arr), "11 elements expected.");
  }

  @Test
  void testInsertOnDirtyEmpty() {
    OrdArray arr = new OrdArray(10);
    on(arr).set(DIRTY, true);
    arr.insert(10L);
    assertEquals(1, arr.count(), "1 element expected.");
  }

  @Test
  void testInsertOnDirtyFull() {
    OrdArray arr = new OrdArray(10);
    // insert 10 items
    arr.insert(77L);
    arr.insert(99L);
    arr.insert(44L);
    arr.insert(55L);
    arr.insert(22L);
    arr.insert(88L);
    arr.insert(11L);
    arr.insert(00L);
    arr.insert(66L);
    arr.insert(33L);
    on(arr).set(DIRTY, true);
    assertThrows(
        ArrayIndexOutOfBoundsException.class, () -> arr.insert(10L), "Array should be full.");
  }

  @Test
  void testInsert() {
    OrdArray arr = insertElements();
    assertTrue(10 == arr.count() && isSorted(arr), "10 elements not inserted.");
  }

  @Test
  void testInsertAtStartExists() {
    OrdArray arr = insertElements();
    int count = arr.count();
    long val = 0L;
    int index = arr.findIndex(val);
    int insertIndex = arr.insert(val);
    assertTrue(
        insertIndex >= index
            && insertIndex <= index + 1
            && arr.count() == count + 1
            && isSorted(arr),
        "11 elements expected, indexes 0 or 1 expected.");
  }

  @Test
  void testInsertAtEndExists() {
    OrdArray arr = insertElements();
    int count = arr.count();
    long val = 99L;
    int index = arr.findIndex(val);
    int insertIndex = arr.insert(val);
    assertTrue(
        insertIndex >= index
            && insertIndex <= index + 1
            && arr.count() == count + 1
            && isSorted(arr),
        "11 elements expected, indexes 9 or 10 expected.");
  }

  @Test
  void testInsertAtEnd() {
    OrdArray arr = insertElements();
    int count = arr.count();
    long val = 100L;
    int insertIndex = arr.insert(val);
    assertTrue(
        insertIndex == count && arr.count() == count + 1 && isSorted(arr),
        () -> (count + 1) + " elements expected, index " + count + " expected.");
  }

  @Test
  void testInsertAtStart() {
    OrdArray arr = insertElements();
    int count = arr.count();
    long val = -1L;
    int insertIndex = arr.insert(val);
    assertTrue(
        insertIndex == 0 && arr.count() == count + 1 && isSorted(arr),
        "11 elements expected, index 0 expected.");
  }

  @SuppressWarnings("PMD.DataflowAnomalyAnalysis")
  @Test
  void testInsertUnSorted() {
    OrdArray arr = new OrdArray(100);
    long[] unsorted = new long[100];
    unsorted[0] = 43L;
    unsorted[1] = 61L;
    unsorted[2] = 2L;
    unsorted[3] = 9L;
    unsorted[4] = 25L;
    unsorted[5] = 47L;
    unsorted[6] = 87L;
    unsorted[7] = 12L;
    unsorted[8] = 21L;
    unsorted[9] = 10L;
    on(arr).set("a", unsorted);
    on(arr).set("nElems", new AtomicInteger(10));
    on(arr).set(DIRTY, true);
    int count = arr.count();
    int res = arr.insert(99L);
    boolean sorted = getSorted(arr);
    assertTrue(res == -1 && !sorted && arr.count() == count, "Insert must fail on unsorted");
  }

  @SuppressWarnings("PMD.DataflowAnomalyAnalysis")
  @Test
  void testInsertSorted() {
    OrdArray arr = new OrdArray(100);
    long[] sortedArray = new long[100];
    sortedArray[0] = 43L;
    sortedArray[1] = 61L;
    sortedArray[2] = 61L;
    sortedArray[3] = 69L;
    sortedArray[4] = 72L;
    sortedArray[5] = 75L;
    sortedArray[6] = 87L;
    sortedArray[7] = 92L;
    sortedArray[8] = 101L;
    sortedArray[9] = 102L;
    on(arr).set("a", sortedArray);
    on(arr).set("nElems", new AtomicInteger(10));
    on(arr).set(DIRTY, true);
    int count = arr.count();
    int res = arr.insert(99L);
    boolean sorted = getSorted(arr);
    assertTrue(res == 8 && arr.count() == count + 1 && sorted, "Sorted and insert at 8 expected.");
  }

  @Test
  void testInsertAllSameSorted() {
    OrdArray arr = new OrdArray(100);
    long[] unsorted = new long[100];
    for (int i = 0; i < 10; i++) unsorted[i] = 43L;
    on(arr).set("a", unsorted);
    on(arr).set(SORTED, false);
    on(arr).set(DIRTY, true);
    on(arr).set("nElems", new AtomicInteger(10));
    int count = arr.count();
    int res = arr.insert(99L);
    boolean sorted = getSorted(arr);
    assertTrue(res == 10 && sorted && arr.count() == count + 1, "Insert must succeed.");
  }

  @Test
  void testInsertModCount() {
    OrdArray arr = new OrdArray(100);
    int count = arr.count();
    int modCount = getModCount(arr);
    arr.insert(10L);
    int newModCount = getModCount(arr);
    assertTrue(
        modCount < newModCount && newModCount == 1 && arr.count() == count + 1,
        "modcount not incremented.");
  }

  @Test
  void testClearModCount() {
    OrdArray arr = new OrdArray(100);
    arr.insert(10L);
    int modCount = getModCount(arr);
    arr.clear();
    int newModCount = getModCount(arr);
    assertTrue(
        modCount < newModCount && newModCount == 2 && arr.count() == 0,
        "modcount not incremented.");
  }

  @Test
  void testClearEmptyModCount() {
    OrdArray arr = new OrdArray(100);
    int modCount = getModCount(arr);
    arr.clear();
    int newModCount = getModCount(arr);
    assertTrue(
        modCount == newModCount && modCount == 0 && arr.count() == 0,
        "modcount must not be incremented.");
  }

  @Test
  void testDeleteModCount() {
    OrdArray arr = new OrdArray(100);
    arr.insert(10L);
    int modCount = getModCount(arr);
    arr.delete(10L);
    int newModCount = getModCount(arr);
    assertTrue(
        modCount < newModCount && newModCount == 2 && arr.count() == 0,
        "modcount not incremented.");
  }

  @Test
  void testDeleteNotFoundModCount() {
    OrdArray arr = new OrdArray(100);
    int count = arr.count();
    int modCount = getModCount(arr);
    arr.delete(10L);
    int newModCount = getModCount(arr);
    assertTrue(
        modCount == newModCount && modCount == 0 && arr.count() == count,
        "modcount must not be incremented.");
  }

  @Test
  void testGet() {
    OrdArray arr = insertElements();
    long[] vals = arr.get();
    assertTrue(vals != null && vals.length == 100, "Null array or length incorrect");
  }

  @Test
  void testCountZero() {
    OrdArray arr = new OrdArray(10, true);
    assertEquals(0, arr.count(), "Count must be zero!");
  }

  @Test
  void testCountPositive() {
    OrdArray arr = insertElements();
    assertEquals(10, arr.count(), "Count must be 10!");
  }

  @Test
  @SuppressWarnings("PMD.DataflowAnomalyAnalysis")
  void testDeleteTrue() {
    OrdArray arr = insertElements();
    int count = arr.count();
    assertTrue(
        arr.syncDelete(00L)
            && arr.syncDelete(55L)
            && arr.syncDelete(99L)
            && arr.count() == count - 3
            && isSorted(arr),
        "Elements 00, 55, 99 not found.");
  }

  @Test
  @SuppressWarnings("PMD.DataflowAnomalyAnalysis")
  void testSyncDeleteTrue() {
    OrdArray arr = insertElements();
    int count = arr.count();
    assertTrue(
        arr.syncDelete(00L)
            && arr.syncDelete(55L)
            && arr.syncDelete(99L)
            && arr.count() == count - 3
            && isSorted(arr),
        "Elements 00, 55, 99 not found.");
  }

  @Test
  @SuppressWarnings("PMD.DataflowAnomalyAnalysis")
  void testSyncDeleteTrueIndividual() {
    OrdArray arr = insertElements();
    int count = arr.count();
    assertTrue(
        arr.syncDelete(00L) && arr.count() == count - 1 && isSorted(arr), "Element 0 not found.");
  }

  @Test
  @SuppressWarnings("PMD.DataflowAnomalyAnalysis")
  void testDeleteFalse() {
    OrdArray arr = insertElements();
    int count = arr.count();
    assertFalse(
        arr.delete(12L) || arr.delete(6L) || arr.delete(5L) && arr.count() != count,
        "Elements 12, 6, 5 found and deleted");
  }

  @Test
  @SuppressWarnings("PMD.DataflowAnomalyAnalysis")
  void testSyncDeleteFalse() {
    OrdArray arr = insertElements();
    int count = arr.count();
    assertFalse(
        arr.syncDelete(12L) || arr.syncDelete(6L) || arr.syncDelete(5L) && arr.count() != count,
        "Elements 12, 6, 5 found and deleted");
  }

  @Test
  @SuppressWarnings("PMD.DataflowAnomalyAnalysis")
  void testSyncDeleteFalseIndividual() {
    OrdArray arr = insertElements();
    int count = arr.count();
    assertFalse(arr.syncDelete(12L) && arr.count() != count, "Elements 12 found and deleted");
  }

  @Test
  void testFindIndexFalse() {
    OrdArray arr = insertElements();
    long searchKey = 35L;
    assertEquals(-4, arr.findIndex(searchKey) + 1, () -> searchKey + " available");
  }

  @Test
  void testFindFalse() {
    OrdArray arr = insertElements();
    long searchKey = 35L;
    assertFalse(arr.find(searchKey), () -> searchKey + " available");
  }

  @Test
  void testFindIndexTrue() {
    OrdArray arr = insertElements();
    long searchKey = 11L;
    assertEquals(1, arr.findIndex(searchKey), () -> String.format(NOT_AVAILABLE, searchKey));
  }

  @Test
  void testFindIndexStart() {
    OrdArray arr = insertElements();
    long searchKey = 0L;
    assertEquals(0, arr.findIndex(searchKey), () -> String.format(NOT_AVAILABLE, searchKey));
  }

  @Test
  void testFindIndexStartTrue() {
    OrdArray arr = insertElements();
    long searchKey = 0L;
    assertTrue(
        arr.find(searchKey) && arr.findIndex(searchKey) == 0,
        () -> String.format(NOT_AVAILABLE, searchKey));
  }

  @Test
  void testFindIndexEndTrue() {
    OrdArray arr = insertElements();
    long searchKey = 99L;
    assertTrue(
        arr.find(searchKey) && arr.findIndex(searchKey) == 9,
        () -> String.format(NOT_AVAILABLE, searchKey));
  }

  @Test
  void testFindIndexEnd() {
    OrdArray arr = insertElements();
    long searchKey = 99L;
    assertEquals(9, arr.findIndex(searchKey), () -> String.format(NOT_AVAILABLE, searchKey));
  }

  @Test
  void testFindTrue() {
    OrdArray arr = insertElements();
    long searchKey = 11L;
    assertTrue(arr.find(searchKey), () -> String.format(NOT_AVAILABLE, searchKey));
  }

  @Test
  void testFindSeqBefore() {
    OrdArray arr = insertSequentialElements();
    long searchKey = 14L;
    assertTrue(
        arr.find(searchKey) && arr.findIndex(searchKey) == 3,
        () -> String.format(NOT_AVAILABLE, searchKey));
  }

  @Test
  void testFindSeqAfter() {
    OrdArray arr = insertSequentialElements();
    long searchKey = 16L;
    assertTrue(
        arr.find(searchKey) && arr.findIndex(searchKey) == 5,
        () -> String.format(NOT_AVAILABLE, searchKey));
  }

  @Test
  void testDeleteStart() {
    OrdArray arr = insertElements();
    int count = arr.count();
    long searchKey = 00L;
    assertTrue(
        arr.delete(searchKey) && arr.count() == count - 1 && isSorted(arr),
        () -> String.format(NOT_AVAILABLE, searchKey));
  }

  @Test
  void testDeleteEnd() {
    OrdArray arr = insertElements();
    int count = arr.count();
    long searchKey = 33L;
    assertTrue(
        arr.delete(searchKey) && arr.count() == count - 1 && isSorted(arr),
        () -> String.format(NOT_AVAILABLE, searchKey));
  }

  @Test
  void testDeleteEndArray() {
    OrdArray arr = new OrdArray(10);
    insertElements(arr);
    int count = arr.count();
    long searchKey = 33L;
    assertTrue(
        arr.delete(searchKey) && arr.count() == count - 1 && isSorted(arr),
        () -> String.format(NOT_AVAILABLE, searchKey));
  }

  @Test
  void testFindIndexOverflow() {
    OrdArray arr = insertElements();
    long searchKey = 0L;
    arr.delete(searchKey);
    assertEquals(-1, arr.findIndex(searchKey), () -> searchKey + " still available");
  }

  @Test
  void testDeleteOverflow() {
    OrdArray arr = insertElements();
    long searchKey = 0L;
    arr.delete(searchKey);
    int count = arr.count();
    assertFalse(
        arr.delete(searchKey) && arr.count() != count, () -> searchKey + " still available");
  }

  @Test
  void testFindEmpty() {
    OrdArray arr = new OrdArray(10);
    long searchKey = 0L;
    assertEquals(-1, arr.findIndex(searchKey), () -> searchKey + " available");
  }

  @Test
  void testClear() {
    OrdArray arr = insertElements();
    int origCount = arr.count();
    arr.clear();
    long[] copy = new long[origCount];
    long[] origTrunc = Arrays.copyOf(arr.get(), origCount);
    assertTrue(0 == arr.count() && Arrays.equals(copy, origTrunc), () -> "Array not cleared");
  }

  @Test
  void testClearEmpty() {
    OrdArray arr = new OrdArray(100);
    arr.clear();
    long[] copy = new long[100];
    assertTrue(0 == arr.count() && Arrays.equals(arr.get(), copy), () -> "Array not cleared");
  }

  @Test
  void testToString() {
    OrdArray arr = insertElements();
    arr.clear();
    arr.insert(77L);
    arr.insert(99L);
    arr.insert(44L);
    String lineSeparator = System.lineSeparator();
    StringBuilder sb = new StringBuilder();
    sb.append(OrdArray.class.getName())
        .append(lineSeparator)
        .append("nElems = ")
        .append(3)
        .append(lineSeparator)
        .append("44 77 99 ");
    assertEquals(sb.toString(), arr.toString(), "Strings not equal.");
  }

  @Test
  void testToStringEmpty() {
    OrdArray arr = insertElements();
    arr.clear();
    String lineSeparator = System.lineSeparator();
    StringBuilder sb = new StringBuilder();
    sb.append(OrdArray.class.getName())
        .append(lineSeparator)
        .append("nElems = ")
        .append(0)
        .append(lineSeparator);
    assertEquals(sb.toString(), arr.toString(), "Strings not equal.");
  }

  @Test
  void testDisplay() {
    OrdArray arr = insertElements();
    OrdArray ordArray = spy(arr);

    doAnswer(
            i -> {
              i.callRealMethod();
              return null;
            })
        .when(ordArray)
        .display();
    ordArray.display();
    doCallRealMethod().when(ordArray).display();
    ordArray.display();
    verify(ordArray, times(2)).display();
  }

  @Test
  void testException() {
    OrdArray ordArray = new OrdArray(3);
    ordArray.insert(2L);
    ordArray.insert(11L);
    ordArray.insert(21L);
    assertThrows(
        ArrayIndexOutOfBoundsException.class,
        () -> {
          ordArray.insert(45L);
        });
  }

  /** Added tests for code coverage completeness. */
  @Test
  @SuppressWarnings("PMD.JUnitTestsShouldIncludeAssert")
  void equalsContract() {
    EqualsVerifier.forClass(OrdArray.class)
        .withIgnoredFields("modCount", "lock", "strict", SORTED, DIRTY)
        .withRedefinedSuperclass()
        .withRedefinedSubclass(OrdArrayExt.class)
        .withIgnoredAnnotations(NonNull.class)
        .verify();
  }

  @Test
  @SuppressWarnings("PMD.JUnitTestsShouldIncludeAssert")
  void leafNodeEquals() {
    EqualsVerifier.forClass(OrdArray.class)
        .withIgnoredFields("modCount", "lock", "strict", SORTED, DIRTY)
        .withRedefinedSuperclass()
        .withRedefinedSubclass(OrdArrayExt.class)
        .withIgnoredAnnotations(NonNull.class)
        .verify();
  }

  static class OrdArrayExt extends OrdArray {
    OrdArrayExt(int size) {
      super(size);
    }

    @Override
    public boolean canEqual(Object obj) {
      return obj instanceof OrdArrayExt;
    }
  }
}
