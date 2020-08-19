package ds.tests;

import static ds.ArrayUtils.*;
import static org.junit.jupiter.api.Assertions.*;

import ds.IArray;
import ds.OrdArray;
import java.util.ConcurrentModificationException;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;
import java.util.stream.LongStream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

@TestInstance(Lifecycle.PER_CLASS)
@Execution(ExecutionMode.SAME_THREAD)
@SuppressWarnings("PMD.LawOfDemeter")
@DisplayName("OrdArrayConcurrencyTest")
class OrdArrayConcurrencyTest implements ConcurrencyProvider {
  private static final Logger LOGGER = Logger.getLogger(OrdArrayConcurrencyTest.class.getName());

  @SuppressWarnings({"PMD.JUnitTestContainsTooManyAsserts", "PMD.DataflowAnomalyAnalysis"})
  @ParameterizedTest
  @MethodSource("provideArraySize")
  @DisplayName("OrdArrayConcurrencyTest.testConcurrent")
  void testConcurrent(int size) {
    IArray ordArray = new OrdArray(size, true);
    LongStream.rangeClosed(1L, size / 2)
        .unordered()
        .parallel()
        .forEach(i -> ordArray.syncInsert(i));
    CountDownLatch cdl = new CountDownLatch(1);
    CountDownLatch done = new CountDownLatch(size / 2);
    AtomicInteger excCount = new AtomicInteger();
    Random random = new Random();
    ExecutorService service = Executors.newFixedThreadPool(10);
    LongStream.rangeClosed(1L, (long) size / 2)
        .unordered()
        .parallel()
        .forEach(
            i ->
                service.execute(
                    () -> {
                      try {
                        cdl.await();
                        if (random.nextBoolean()) ordArray.insert(i);
                        else ordArray.delete(i);
                        done.countDown();
                      } catch (InterruptedException ie) {
                        Thread.currentThread().interrupt();
                        done.countDown();
                      } catch (ConcurrentModificationException cme) {
                        LOGGER.fine(() -> "Error: " + cme.getMessage());
                        excCount.incrementAndGet();
                        done.countDown();
                      }
                    }));
    try {
      cdl.countDown();
      done.await();
      service.shutdown();
      service.awaitTermination(1, TimeUnit.MINUTES);
    } catch (InterruptedException ie) {
      Thread.currentThread().interrupt();
    }
    int excs = excCount.get();
    assertFalse(isSorted(ordArray), "Array is sorted!");
    assertNotEquals(0, excs, () -> excs + " is number of concurrent exceptions.");
  }

  @SuppressWarnings({"PMD.JUnitTestContainsTooManyAsserts", "PMD.DataflowAnomalyAnalysis"})
  @ParameterizedTest
  @MethodSource("provideArraySize")
  @DisplayName("OrdArrayConcurrencyTest.testConcurrentInserts")
  void testConcurrentInserts(int size) {
    CountDownLatch cdl = new CountDownLatch(1);
    CountDownLatch done = new CountDownLatch(size);
    AtomicInteger excCount = new AtomicInteger();
    IArray ordArray = new OrdArray(size, true);
    ExecutorService service = Executors.newFixedThreadPool(10);
    LongStream.rangeClosed(1L, (long) size)
        .unordered()
        .parallel()
        .forEach(
            i ->
                service.execute(
                    () -> {
                      try {
                        cdl.await();
                        ordArray.insert(i);
                        done.countDown();
                      } catch (InterruptedException ie) {
                        Thread.currentThread().interrupt();
                        done.countDown();
                      } catch (ConcurrentModificationException cme) {
                        LOGGER.fine(() -> "Error: " + cme.getMessage());
                        excCount.incrementAndGet();
                        done.countDown();
                      }
                    }));
    try {
      cdl.countDown();
      done.await();
      service.shutdown();
      service.awaitTermination(1, TimeUnit.MINUTES);
    } catch (InterruptedException ie) {
      Thread.currentThread().interrupt();
    }
    int excs = excCount.get();
    assertFalse(isSorted(ordArray), "Array is sorted!");
    assertNotEquals(0, excs, () -> excs + " is number of concurrent exceptions.");
  }

  @SuppressWarnings("PMD.JUnitTestContainsTooManyAsserts")
  @Test
  @DisplayName("OrdArrayConcurrencyTest.testSyncInserts")
  void testSyncInserts() {
    IArray ordArray = new OrdArray(10_000, true);
    LongStream.rangeClosed(1L, 10_000L).unordered().parallel().forEach(i -> ordArray.syncInsert(i));
    assertTrue(isSorted(ordArray), "Array is unsorted!");
    assertEquals(10_000, ordArray.count(), "10_000 elements not inserted.");
  }

  @SuppressWarnings({"PMD.JUnitTestContainsTooManyAsserts", "PMD.DataflowAnomalyAnalysis"})
  @ParameterizedTest
  @MethodSource("provideArraySize")
  @DisplayName("OrdArrayConcurrencyTest.testConcurrentDeletes")
  void testConcurrentDeletes(int size) {
    CountDownLatch cdl = new CountDownLatch(1);
    CountDownLatch done = new CountDownLatch(size);
    AtomicInteger excCount = new AtomicInteger();
    IArray ordArray = new OrdArray(size, true);
    LongStream nos = LongStream.rangeClosed(1L, (long) size).unordered();
    nos.forEach(i -> ordArray.insert(i));
    LongStream nosParallel = LongStream.rangeClosed(1L, (long) size).unordered().parallel();
    ExecutorService service = Executors.newFixedThreadPool(10);
    nosParallel.forEach(
        i ->
            service.execute(
                () -> {
                  try {
                    cdl.await();
                    ordArray.delete(i);
                    done.countDown();
                  } catch (ConcurrentModificationException cme) {
                    LOGGER.fine(() -> "Error deleting " + i);
                    excCount.incrementAndGet();
                    done.countDown();
                  } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                    done.countDown();
                  }
                }));
    try {
      cdl.countDown();
      done.await();
      service.shutdown();
      service.awaitTermination(1, TimeUnit.MINUTES);
    } catch (InterruptedException ie) {
      Thread.currentThread().interrupt();
    }
    int excs = excCount.get();
    assertFalse(isSorted(ordArray), "Array is sorted!");
    assertNotEquals(0, excs, () -> excs + " is number of concurrent exceptions.");
  }

  @Test
  @DisplayName("OrdArrayConcurrencyTest.testSequentialDeletes")
  void testSequentialDeletes() {
    IArray ordArray = new OrdArray(10_000, true);
    LongStream nos = LongStream.rangeClosed(1L, 10_000L);
    nos.forEach(
        i -> {
          ordArray.insert(i);
          ordArray.delete(i);
        });
    assertEquals(0, ordArray.count(), () -> "10,000 elements not deleted: " + ordArray.toString());
  }

  @Test
  @DisplayName("OrdArrayConcurrencyTest.testConcurrentSyncDeletes")
  void testConcurrentSyncDeletes() {
    IArray ordArray = new OrdArray(100);
    LongStream nos = LongStream.rangeClosed(1L, 10_000L);
    nos.forEach(
        i -> {
          ordArray.insert(i);
          ordArray.syncDelete(i);
        });
    assertEquals(0, ordArray.count(), () -> "100 elements not deleted: " + ordArray.toString());
  }

  @Test
  @DisplayName("OrdArrayConcurrencyTest.testConcurrentSyncInsertsDeletes")
  void testConcurrentSyncInsertsDeletes() {
    IArray ordArray = new OrdArray(100);
    LongStream nos = LongStream.rangeClosed(1L, 100L).unordered().parallel();
    nos.forEach(
        i -> {
          ordArray.syncInsert(i);
          ordArray.syncDelete(i);
        });
    assertEquals(0, ordArray.count(), () -> "Elements not cleared");
  }
}
