package ds.tests;

import static org.joor.Reflect.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import ds.HighArray;
import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;
import java.util.stream.LongStream;
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
class HighArrayTest {
  private static final Logger LOGGER = Logger.getLogger(HighArrayTest.class.getName());

  HighArray insertElements() {
    HighArray arr = new HighArray(100);
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

  @Test
  void testConstructorParameter() {
    assertThrows(
        IllegalArgumentException.class,
        () -> new HighArray(-1),
        "IllegalArgumentException expected for " + -1);
  }

  @Test
  void testConstructorParameterZero() {
    assertThrows(
        IllegalArgumentException.class,
        () -> new HighArray(0),
        "IllegalArgumentException expected for " + 0);
  }

  @Test
  void testInsert() {
    HighArray arr = insertElements();
    assertEquals(arr.count(), 10, "10 elements not inserted.");
  }

  @Test
  void testInsertModCount() {
    HighArray arr = new HighArray(100);
    int modCount = (int) on(arr).get("modCount");
    arr.insert(10L);
    int newModCount = (int) on(arr).get("modCount");
    assertTrue(modCount < newModCount, "modcount not incremented.");
  }

  @Test
  void testClearModCount() {
    HighArray arr = new HighArray(100);
    arr.insert(10L);
    int modCount = (int) on(arr).get("modCount");
    arr.clear();
    int newModCount = (int) on(arr).get("modCount");
    assertTrue(modCount < newModCount, "modcount not incremented.");
  }

  @Test
  @SuppressWarnings("PMD.DataflowAnomalyAnalysis")
  void testDeleteTrue() {
    HighArray arr = insertElements();
    assertTrue(
        arr.delete(00L) && arr.delete(55L) && arr.delete(99L), "Elements 00, 55, 99 not found.");
  }

  @Test
  @SuppressWarnings("PMD.DataflowAnomalyAnalysis")
  void testSyncDeleteTrue() {
    HighArray arr = insertElements();
    assertTrue(
        arr.syncDelete(00L) && arr.syncDelete(55L) && arr.syncDelete(99L),
        "Elements 00, 55, 99 not found.");
  }

  @Test
  @SuppressWarnings("PMD.DataflowAnomalyAnalysis")
  void testDeleteFalse() {
    HighArray arr = insertElements();
    assertFalse(
        arr.delete(12L) || arr.delete(6L) || arr.delete(5L), "Elements 12, 6, 5 found and deleted");
  }

  @Test
  @SuppressWarnings("PMD.DataflowAnomalyAnalysis")
  void testSyncDeleteFalse() {
    HighArray arr = insertElements();
    assertFalse(
        arr.syncDelete(12L) || arr.syncDelete(6L) || arr.syncDelete(5L),
        "Elements 12, 6, 5 found and deleted");
  }

  @Test
  void testFindIndexFalse() {
    HighArray arr = insertElements();
    long searchKey = 35L;
    assertEquals(arr.findIndex(searchKey), -1, () -> searchKey + " available");
  }

  @Test
  void testFindFalse() {
    HighArray arr = insertElements();
    long searchKey = 35L;
    assertFalse(arr.find(searchKey), () -> searchKey + " available");
  }

  @Test
  void testFindIndex() {
    HighArray arr = insertElements();
    long searchKey = 11L;
    assertEquals(6, arr.findIndex(searchKey), () -> searchKey + " not available");
  }

  @Test
  void testFindIndexStart() {
    HighArray arr = insertElements();
    long searchKey = 77L;
    assertEquals(0, arr.findIndex(searchKey), () -> searchKey + " not available");
  }

  @Test
  void testDeleteStart() {
    HighArray arr = insertElements();
    long searchKey = 77L;
    assertTrue(arr.delete(searchKey), () -> searchKey + " not available");
  }

  @Test
  void testFindIndexStartTrue() {
    HighArray arr = insertElements();
    long searchKey = 77L;
    assertTrue(arr.find(searchKey), () -> searchKey + " not available");
  }

  @Test
  void testFindIndexEnd() {
    HighArray arr = insertElements();
    long searchKey = 33L;
    assertEquals(9, arr.findIndex(searchKey), () -> searchKey + " not available");
  }

  @Test
  void testDeleteEnd() {
    HighArray arr = insertElements();
    long searchKey = 33L;
    assertTrue(arr.delete(searchKey), () -> searchKey + " not available");
  }

  @Test
  void testFindIndexEndTrue() {
    HighArray arr = insertElements();
    long searchKey = 33L;
    assertTrue(arr.find(searchKey), () -> searchKey + " not available");
  }

  @Test
  void testFindIndexOverflow() {
    HighArray arr = insertElements();
    long searchKey = 0L;
    arr.delete(0L);
    assertEquals(-1, arr.findIndex(searchKey), () -> searchKey + " still available");
  }

  @Test
  void testDeleteOverflow() {
    HighArray arr = insertElements();
    long searchKey = 0L;
    arr.delete(0L);
    assertFalse(arr.delete(searchKey), () -> searchKey + " still available");
  }

  @Test
  void testFindEmpty() {
    HighArray arr = new HighArray(10);
    long searchKey = 0L;
    assertEquals(-1, arr.findIndex(searchKey), () -> searchKey + " available");
  }

  @Test
  void testFindTrue() {
    HighArray arr = insertElements();
    long searchKey = 11L;
    assertTrue(arr.find(searchKey), () -> searchKey + " not available");
  }

  @Test
  void testClear() {
    HighArray arr = insertElements();
    arr.clear();
    // for code coverage
    arr.clear();
    long[] copy = new long[100];
    assertTrue(0 == arr.count() && Arrays.equals(arr.get(), copy), () -> "Array not cleared");
  }

  @Test
  void testToString() {
    HighArray arr = insertElements();
    arr.clear();
    arr.insert(77L);
    arr.insert(99L);
    arr.insert(44L);
    StringBuilder sb = new StringBuilder();
    sb.append("nElems = ").append(3).append(System.lineSeparator()).append("77 99 44 ");
    assertEquals(arr.toString(), sb.toString(), "Strings not equal.");
  }

  @Test
  void testDisplay() {
    HighArray arr = insertElements();
    HighArray highArray = spy(arr);

    doAnswer(
            i -> {
              i.callRealMethod();
              return null;
            })
        .when(highArray)
        .display();
    highArray.display();
    doCallRealMethod().when(highArray).display();
    highArray.display();
    verify(highArray, times(2)).display();
  }

  @Test
  void testException() {
    HighArray highArray = new HighArray(3);
    highArray.insert(2L);
    highArray.insert(11L);
    highArray.insert(21L);
    assertThrows(
        ArrayIndexOutOfBoundsException.class,
        () -> {
          highArray.insert(45L);
        });
  }

  @Test
  void testConcurrentInserts() {
    HighArray highArray = new HighArray(10_000);
    LongStream.rangeClosed(1L, 10_000L).parallel().forEach(i -> highArray.insert(i));
    assertEquals(
        10_000, highArray.count(), () -> "10,000 elements not filled: " + highArray.toString());
  }

  @Test
  void testConcurrentDeletes() {
    AtomicInteger excCount = new AtomicInteger();
    HighArray highArray = new HighArray(10_000, true);
    LongStream nos = LongStream.rangeClosed(1L, 10_000L);
    nos.forEach(i -> highArray.insert(i));
    LongStream nosParallel = LongStream.rangeClosed(1L, 10_000L).parallel();
    nosParallel.forEach(
        i ->
            new Thread(
                    () -> {
                      try {
                        highArray.delete(i);
                      } catch (ConcurrentModificationException cme) {
                        excCount.incrementAndGet();
                      }
                    })
                .start());
    assertNotEquals(0, excCount.get(), () -> excCount + " is number of concurrent exceptions.");
  }

  @Test
  void testSequentialDeletes() {
    HighArray highArray = new HighArray(10_000, true);
    LongStream nos = LongStream.rangeClosed(1L, 10_000L);
    nos.forEach(
        i -> {
          highArray.insert(i);
          highArray.delete(i);
        });
    assertEquals(
        0, highArray.count(), () -> "10,000 elements not deleted: " + highArray.toString());
  }

  @Test
  void testConcurrentSyncDeletes() {
    HighArray highArray = new HighArray(100);
    LongStream nos = LongStream.rangeClosed(1L, 10_000L);
    nos.forEach(
        i -> {
          highArray.insert(i);
          highArray.syncDelete(i);
        });
    assertEquals(0, highArray.count(), () -> "100 elements not deleted: " + highArray.toString());
  }

  /** Added tests for code coverage completeness. */
  @Test
  @SuppressWarnings("PMD.JUnitTestsShouldIncludeAssert")
  void equalsContract() {
    EqualsVerifier.forClass(HighArray.class)
        .withIgnoredFields("modCount", "lock", "strict")
        .withRedefinedSuperclass()
        .withRedefinedSubclass(HighArrayExt.class)
        .withIgnoredAnnotations(NonNull.class)
        .verify();
  }

  @Test
  @SuppressWarnings("PMD.JUnitTestsShouldIncludeAssert")
  void leafNodeEquals() {
    EqualsVerifier.forClass(HighArray.class)
        .withIgnoredFields("modCount", "lock", "strict")
        .withRedefinedSuperclass()
        .withRedefinedSubclass(HighArrayExt.class)
        .withIgnoredAnnotations(NonNull.class)
        .verify();
  }

  static class HighArrayExt extends HighArray {
    HighArrayExt(int size) {
      super(size);
    }

    @Override
    public boolean canEqual(Object obj) {
      return obj instanceof HighArrayExt;
    }
  }
}
