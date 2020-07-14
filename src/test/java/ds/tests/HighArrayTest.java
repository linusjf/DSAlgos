package ds.tests;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import ds.HighArray;
import java.util.ConcurrentModificationException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;
import java.util.stream.LongStream;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.checkerframework.checker.nullness.qual.NonNull;
// import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.RepetitionInfo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.junit.jupiter.api.parallel.ResourceAccessMode;
import org.junit.jupiter.api.parallel.ResourceLock;

@TestInstance(Lifecycle.PER_CLASS)
@Execution(ExecutionMode.CONCURRENT)
@SuppressWarnings("PMD.LawOfDemeter")
class HighArrayTest {
  private static final Logger LOGGER = Logger.getLogger(HighArrayTest.class.getName());
  HighArray array;

  HighArrayTest() {
    array = new HighArray(1000, true);
    LongStream nos = LongStream.rangeClosed(1L, 1000L);
    nos.forEach(i -> array.insert(i));
  }

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
  void testInsert() {
    HighArray arr = insertElements();
    assertEquals(arr.count(), 10, "10 elements not inserted.");
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
  void testDeleteFalse() {
    HighArray arr = insertElements();
    assertFalse(
        arr.delete(12L) || arr.delete(6L) || arr.delete(5L), "Elements 12, 6, 5 found and deleted");
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
  void testFindIndexTrue() {
    HighArray arr = insertElements();
    long searchKey = 11L;
    assertTrue(arr.findIndex(searchKey) >= 0, () -> searchKey + " not available");
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
    assertEquals(arr.count(), 0, () -> "Array not cleared");
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

  @Test
  void testConcurrentSyncInsertsDeletes() {
    HighArray highArray = new HighArray(100);
    LongStream nos = LongStream.rangeClosed(1L, 100L).parallel();
    nos.forEach(
        i -> {
          highArray.insert(i);
          highArray.syncDelete(i);
        });
    assertEquals(0, highArray.count(), () -> "Elements not cleared");
  }

  @RepeatedTest(1000)
  @ResourceLock(value = "hello", mode = ResourceAccessMode.READ_WRITE)
  void repeatedTestWithRepetitionInfo(RepetitionInfo repetitionInfo) {
    int current = repetitionInfo.getCurrentRepetition();
    if (!array.delete(current)) fail(() -> "Element " + current + " not found.");
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
