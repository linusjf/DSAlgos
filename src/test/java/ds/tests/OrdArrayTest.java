package ds.tests;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import ds.OrdArray;
import java.util.ConcurrentModificationException;
// import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;
import java.util.stream.LongStream;
import java.util.stream.Stream;
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
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

@TestInstance(Lifecycle.PER_CLASS)
@Execution(ExecutionMode.CONCURRENT)
@SuppressWarnings("PMD.LawOfDemeter")
class OrdArrayTest {
  private static final Logger LOGGER = Logger.getLogger(OrdArrayTest.class.getName());
  OrdArray array;

  OrdArrayTest() {
    array = new OrdArray(1000, true);
    LongStream nos = LongStream.rangeClosed(1L, 1000L).unordered();
    nos.forEach(i -> array.insert(i));
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

  @Test
  void testInsert() {
    OrdArray arr = insertElements();
    assertEquals(arr.count(), 10, "10 elements not inserted.");
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
  void testDeleteFalse() {
    OrdArray arr = insertElements();
    assertFalse(
        arr.delete(12L) || arr.delete(6L) || arr.delete(5L), "Elements 12, 6, 5 found and deleted");
  }

  @Test
  void testFindIndexFalse() {
    OrdArray arr = insertElements();
    long searchKey = 35L;
    assertEquals(arr.count(), arr.findIndex(searchKey), () -> searchKey + " available");
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
    assertTrue(arr.findIndex(searchKey) >= 0, () -> searchKey + " not available");
  }

  @Test
  void testFindTrue() {
    OrdArray arr = insertElements();
    long searchKey = 11L;
    assertTrue(arr.find(searchKey), () -> searchKey + " not available");
  }

  @Test
  void testClear() {
    OrdArray arr = insertElements();
    arr.clear();
    // for code coverage
    arr.clear();
    assertEquals(arr.count(), 0, () -> "Array not cleared");
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

  @ParameterizedTest
  @MethodSource("provideInsertArraySize")
  void testConcurrentInserts(int size) {
    AtomicInteger excCount = new AtomicInteger();
    OrdArray ordArray = new OrdArray(size, true);
    LongStream.rangeClosed(1L, (long) size)
        .unordered()
        .parallel()
        .forEach(
            i -> {
              Thread thread =
                  new Thread(
                      () -> {
                        try {
                          ordArray.insert(i);
                        } catch (ConcurrentModificationException cme) {
                          excCount.incrementAndGet();
                        }
                      });
              thread.start();
            });
    assertNotEquals(0, excCount.get(), () -> excCount + " is number of concurrent exceptions.");
  }

  private Stream<Integer> provideInsertArraySize() {
    Runtime rt = Runtime.getRuntime();
    int processors = rt.availableProcessors();
    long memory = rt.totalMemory();
    LOGGER.info(() -> String.format("Memory: %d Processors: %d %n", memory, processors));
    if (processors <= 2) return Stream.of(20_000);
    else return Stream.of(5000);
  }

  /***
   * @ParameterizedTest
   * @MethodSource("provideArraySize")
   * void testConcurrentInsertsLatch(int size) {
   * CountDownLatch cdl = new CountDownLatch(1);
   * CountDownLatch done = new CountDownLatch(size);
   * AtomicInteger excCount = new AtomicInteger();
   * OrdArray ordArray = new OrdArray(size, true);
   * LongStream.rangeClosed(1L, (long) size)
   * .unordered()
   * .parallel()
   * .forEach(
   * i -> {
   * Thread thread =
   * new Thread(
   * () -> {
   * try {
   * cdl.await();
   * ordArray.insert(i);
   * done.countDown();
   * } catch (InterruptedException ie) {
   * Thread.currentThread().interrupt();
   * done.countDown();
   * } catch (ConcurrentModificationException cme) {
   * System.err.println(cme.getMessage());
   * excCount.incrementAndGet();
   * done.countDown();
   * }
   * });
   * thread.start();
   * });
   * try {
   * cdl.countDown();
   * done.await();
   * } catch (InterruptedException ie) {
   * Thread.currentThread().interrupt();
   * }
   * assertNotEquals(0, excCount.get(), () -> excCount + " is number of concurrent exceptions.");
   * }
   */

  private Stream<Integer> provideArraySize() {
    Runtime rt = Runtime.getRuntime();
    int processors = rt.availableProcessors();
    long memory = rt.totalMemory();
    LOGGER.info(() -> String.format("Memory: %d Processors: %d %n", memory, processors));
    if (processors <= 2) return Stream.of(10_000);
    else return Stream.of(5000);
  }

  @Test
  void testSyncInserts() {
    OrdArray ordArray = new OrdArray(10_000, true);
    LongStream.rangeClosed(1L, 10_000L)
        .unordered()
        .parallel()
        .forEach(
            i -> {
              Thread thread = new Thread(() -> ordArray.syncInsert(i));
              thread.start();
              try {
                thread.join(0);
              } catch (InterruptedException ie) {
                Thread.currentThread().interrupt();
              }
            });
    assertEquals(10_000, ordArray.count(), "10_000 elements not inserted.");
  }

  @ParameterizedTest
  @MethodSource("provideInsertArraySize")
  void testConcurrentDeletes(int size) {
    AtomicInteger excCount = new AtomicInteger();
    OrdArray ordArray = new OrdArray(size, true);
    LongStream nos = LongStream.rangeClosed(1L, (long) size).unordered();
    nos.forEach(i -> ordArray.insert(i));
    LongStream nosParallel = LongStream.rangeClosed(1L, (long) size).unordered().parallel();
    nosParallel.forEach(
        i ->
            new Thread(
                    () -> {
                      try {
                        ordArray.delete(i);
                      } catch (ConcurrentModificationException cme) {
                        LOGGER.severe(() -> "Error deleting " + i);
                        excCount.incrementAndGet();
                      }
                    })
                .start());
    assertNotEquals(0, excCount.get(), () -> excCount + " is number of concurrent exceptions.");
  }

  @Test
  void testSequentialDeletes() {
    OrdArray ordArray = new OrdArray(10_000, true);
    LongStream nos = LongStream.rangeClosed(1L, 10_000L);
    nos.forEach(
        i -> {
          ordArray.insert(i);
          ordArray.delete(i);
        });
    assertEquals(0, ordArray.count(), () -> "10,000 elements not deleted: " + ordArray.toString());
  }

  @Test
  void testConcurrentSyncDeletes() {
    OrdArray ordArray = new OrdArray(100);
    LongStream nos = LongStream.rangeClosed(1L, 10_000L);
    nos.forEach(
        i -> {
          ordArray.insert(i);
          ordArray.syncDelete(i);
        });
    assertEquals(0, ordArray.count(), () -> "100 elements not deleted: " + ordArray.toString());
  }

  @Test
  void testConcurrentSyncInsertsDeletes() {
    OrdArray ordArray = new OrdArray(100);
    LongStream nos = LongStream.rangeClosed(1L, 100L).parallel();
    nos.forEach(
        i -> {
          ordArray.insert(i);
          ordArray.syncDelete(i);
        });
    assertEquals(0, ordArray.count(), () -> "Elements not cleared");
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
    EqualsVerifier.forClass(OrdArray.class)
        .withIgnoredFields("modCount", "lock", "strict")
        .withRedefinedSuperclass()
        .withRedefinedSubclass(OrdArrayExt.class)
        .withIgnoredAnnotations(NonNull.class)
        .verify();
  }

  @Test
  @SuppressWarnings("PMD.JUnitTestsShouldIncludeAssert")
  void leafNodeEquals() {
    EqualsVerifier.forClass(OrdArray.class)
        .withIgnoredFields("modCount", "lock", "strict")
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
