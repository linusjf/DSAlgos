package ds.tests;

import static org.joor.Reflect.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import ds.OrdArray;
import java.util.Arrays;
import java.util.Optional;
import java.util.logging.Logger;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

@TestInstance(Lifecycle.PER_CLASS)
@Execution(ExecutionMode.CONCURRENT)
@SuppressWarnings("PMD.LawOfDemeter")
class OrdArrayTest {
  private static final Logger LOGGER = Logger.getLogger(OrdArrayTest.class.getName());

  static {
  }

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
    assertEquals(6, arr.insert(66L), "Index 6  expected");
  }

  @Test
  void insertSyncDuplicate() {
    OrdArray arr = insertElements();
    assertEquals(6, arr.syncInsert(66L), "7 elements expected");
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
    assertEquals(21, arr.count(), "21 elements expected");
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
    on(arr).set("dirty", true);
    arr.insert(10L);
    assertEquals(arr.count(), 11, "11 elements expected.");
  }

  @Test
  void testInsertOnDirtyEmpty() {
    OrdArray arr = new OrdArray(10);
    on(arr).set("dirty", true);
    arr.insert(10L);
    assertEquals(arr.count(), 1, "1 element expected.");
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
    on(arr).set("dirty", true);
    assertThrows(
        ArrayIndexOutOfBoundsException.class, () -> arr.insert(10L), "Array should be full.");
  }

  @Test
  void testInsert() {
    OrdArray arr = insertElements();
    assertEquals(arr.count(), 10, "10 elements not inserted.");
  }

  @Test
  void testInsertSorted() {
    OrdArray arr = insertElements();
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
    on(arr).set("dirty", true);
    int res = arr.insert(99L);
    boolean sorted = (boolean) on(arr).get("sorted");
    assertTrue(res == -1 && !sorted, "Insert must fail on unsorted");
  }

  @Test
  void testInsertAllSameSorted() {
    OrdArray arr = insertElements();
    long[] unsorted = new long[100];
    unsorted[0] = 43L;
    unsorted[1] = 43L;
    unsorted[2] = 43L;
    unsorted[3] = 43L;
    unsorted[4] = 43L;
    unsorted[5] = 43L;
    unsorted[6] = 43L;
    unsorted[7] = 43L;
    unsorted[8] = 43L;
    unsorted[9] = 43L;
    on(arr).set("a", unsorted);
    on(arr).set("sorted", false);
    on(arr).set("dirty", true);
    int res = arr.insert(99L);
    boolean sorted = (boolean) on(arr).get("sorted");
    assertTrue(res == 10 && sorted, "Insert must succeed.");
  }

  @Test
  void testInsertModCount() {
    OrdArray arr = new OrdArray(100);
    int modCount = (int) on(arr).get("modCount");
    arr.insert(10L);
    int newModCount = (int) on(arr).get("modCount");
    assertTrue(modCount < newModCount && newModCount > 0, "modcount not incremented.");
  }

  @Test
  void testClearModCount() {
    OrdArray arr = new OrdArray(100);
    arr.insert(10L);
    int modCount = (int) on(arr).get("modCount");
    arr.clear();
    int newModCount = (int) on(arr).get("modCount");
    assertTrue(modCount < newModCount && newModCount == 2, "modcount not incremented.");
  }

  @Test
  void testClearEmptyModCount() {
    OrdArray arr = new OrdArray(100);
    int modCount = (int) on(arr).get("modCount");
    arr.clear();
    int newModCount = (int) on(arr).get("modCount");
    assertTrue(modCount == newModCount && modCount == 0, "modcount must not be incremented.");
  }

  @Test
  void testDeleteModCount() {
    OrdArray arr = new OrdArray(100);
    arr.insert(10L);
    int modCount = (int) on(arr).get("modCount");
    arr.delete(10L);
    int newModCount = (int) on(arr).get("modCount");
    assertTrue(modCount < newModCount && newModCount == 2, "modcount not incremented.");
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
    assertTrue(
        arr.delete(00L) && arr.delete(55L) && arr.delete(99L), "Elements 00, 55, 99 not found.");
  }

  @Test
  @SuppressWarnings("PMD.DataflowAnomalyAnalysis")
  void testSyncDeleteTrue() {
    OrdArray arr = insertElements();
    assertTrue(
        arr.syncDelete(00L) && arr.syncDelete(55L) && arr.syncDelete(99L),
        "Elements 00, 55, 99 not found.");
  }

  @Test
  @SuppressWarnings("PMD.DataflowAnomalyAnalysis")
  void testSyncDeleteTrueIndividual() {
    OrdArray arr = insertElements();
    assertTrue(arr.syncDelete(00L), "Element 0 not found.");
  }

  @Test
  @SuppressWarnings("PMD.DataflowAnomalyAnalysis")
  void testDeleteFalse() {
    OrdArray arr = insertElements();
    assertFalse(
        arr.delete(12L) || arr.delete(6L) || arr.delete(5L), "Elements 12, 6, 5 found and deleted");
  }

  @Test
  @SuppressWarnings("PMD.DataflowAnomalyAnalysis")
  void testSyncDeleteFalse() {
    OrdArray arr = insertElements();
    assertFalse(
        arr.syncDelete(12L) || arr.syncDelete(6L) || arr.syncDelete(5L),
        "Elements 12, 6, 5 found and deleted");
  }

  @Test
  @SuppressWarnings("PMD.DataflowAnomalyAnalysis")
  void testSyncDeleteFalseIndividual() {
    OrdArray arr = insertElements();
    assertFalse(arr.syncDelete(12L), "Elements 12 found and deleted");
  }

  @Test
  void testFindIndexFalse() {
    OrdArray arr = insertElements();
    long searchKey = 35L;
    System.out.println(arr);
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
    assertEquals(1, arr.findIndex(searchKey), () -> searchKey + " not available");
  }

  @Test
  void testFindIndexStart() {
    OrdArray arr = insertElements();
    long searchKey = 0L;
    assertEquals(0, arr.findIndex(searchKey), () -> searchKey + " not available");
  }

  @Test
  void testFindIndexStartTrue() {
    OrdArray arr = insertElements();
    long searchKey = 0L;
    assertTrue(
        arr.find(searchKey) && arr.findIndex(searchKey) == 0, () -> searchKey + " not available");
  }

  @Test
  void testFindIndexEndTrue() {
    OrdArray arr = insertElements();
    long searchKey = 99L;
    assertTrue(
        arr.find(searchKey) && arr.findIndex(searchKey) == 9, () -> searchKey + " not available");
  }

  @Test
  void testFindIndexEnd() {
    OrdArray arr = insertElements();
    long searchKey = 99L;
    assertEquals(9, arr.findIndex(searchKey), () -> searchKey + " not available");
  }

  @Test
  void testFindTrue() {
    OrdArray arr = insertElements();
    long searchKey = 11L;
    assertTrue(arr.find(searchKey), () -> searchKey + " not available");
  }

  @Test
  void testDeleteStart() {
    OrdArray arr = insertElements();
    long searchKey = 00L;
    assertTrue(arr.delete(searchKey), () -> searchKey + " not available");
  }

  @Test
  void testDeleteEnd() {
    OrdArray arr = insertElements();
    long searchKey = 33L;
    assertTrue(arr.delete(searchKey), () -> searchKey + " not available");
  }

  @Test
  void testDeleteEndArray() {
    OrdArray arr = new OrdArray(10);
    insertElements(arr);
    long searchKey = 33L;
    assertTrue(arr.delete(searchKey), () -> searchKey + " not available");
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
    assertFalse(arr.delete(searchKey), () -> searchKey + " still available");
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
    StringBuilder sb = new StringBuilder();
    sb.append("nElems = ").append(3).append(System.lineSeparator()).append("44 77 99 ");
    assertEquals(sb.toString(), arr.toString(), "Strings not equal.");
  }

  @Test
  void testToStringEmpty() {
    OrdArray arr = insertElements();
    arr.clear();
    StringBuilder sb = new StringBuilder();
    sb.append("nElems = ").append(0).append(System.lineSeparator());
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
        .withIgnoredFields("modCount", "lock", "strict", "sorted", "dirty")
        .withRedefinedSuperclass()
        .withRedefinedSubclass(OrdArrayExt.class)
        .withIgnoredAnnotations(NonNull.class)
        .verify();
  }

  @Test
  @SuppressWarnings("PMD.JUnitTestsShouldIncludeAssert")
  void leafNodeEquals() {
    EqualsVerifier.forClass(OrdArray.class)
        .withIgnoredFields("modCount", "lock", "strict", "sorted", "dirty")
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
