package ds.tests;

import static ds.ArrayUtils.*;
import static org.junit.jupiter.api.Assertions.*;

import ds.IArray;
import ds.OrdArrayRecursive;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;
import java.util.stream.LongStream;
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
class OrdArrayRecursiveConcurrencyTest implements ConcurrencyProvider {
  private static final Logger LOGGER =
      Logger.getLogger(OrdArrayRecursiveConcurrencyTest.class.getName());

  @ParameterizedTest
  @MethodSource("provideSyncArraySize")
  void testSyncConcurrent(int size) {
    IArray ordArray = new OrdArrayRecursive(size, true);
    LongStream.rangeClosed(1L, size / 2)
        .unordered()
        .parallel()
        .forEach(i -> ordArray.syncInsert(i));
    CountDownLatch cdl = new CountDownLatch(1);
    CountDownLatch done = new CountDownLatch(size / 2);
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
                        if (random.nextBoolean()) ordArray.syncInsert(i);
                        else ordArray.syncDelete(i);
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
    assertTrue(isSorted(ordArray), "Array is sorted!");
  }

  @ParameterizedTest
  @MethodSource("provideSyncArraySize")
  void testConcurrent(int size) {
    IArray ordArray = new OrdArrayRecursive(size, true);
    LongStream.rangeClosed(1L, size / 2)
        .unordered()
        .parallel()
        .forEach(i -> ordArray.syncInsert(i));
    CountDownLatch cdl = new CountDownLatch(1);
    CountDownLatch done = new CountDownLatch(size / 2);
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
    LOGGER.info(() -> ordArray.toString());
    assertTrue(isSorted(ordArray), "Array is sorted!");
  }

  @ParameterizedTest
  @MethodSource("provideSyncArraySize")
  void testConcurrentInserts(int size) {
    CountDownLatch cdl = new CountDownLatch(1);
    CountDownLatch done = new CountDownLatch(size);
    IArray ordArray = new OrdArrayRecursive(size, true);
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
    LOGGER.info(() -> ordArray.toString());
    assertTrue(isSorted(ordArray), "Array is sorted!");
  }

  @SuppressWarnings("PMD.JUnitTestContainsTooManyAsserts")
  @Test
  void testSyncInserts() {
    IArray ordArray = new OrdArrayRecursive(1000, true);
    LongStream.rangeClosed(1L, 1000L).unordered().parallel().forEach(i -> ordArray.syncInsert(i));
    assertTrue(isSorted(ordArray), "Array is unsorted!");
    assertEquals(1000, ordArray.count(), "1000 elements not inserted.");
  }

  @ParameterizedTest
  @MethodSource("provideArraySize")
  void testConcurrentDeletes(int size) {
    CountDownLatch cdl = new CountDownLatch(1);
    CountDownLatch done = new CountDownLatch(size);
    IArray ordArray = new OrdArrayRecursive(size, true);
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
    LOGGER.info(() -> ordArray.toString());
    assertTrue(isSorted(ordArray), "Array is sorted!");
  }

  @Test
  void testSequentialDeletes() {
    IArray ordArray = new OrdArrayRecursive(1000, true);
    LongStream nos = LongStream.rangeClosed(1L, 1000L);
    nos.forEach(
        i -> {
          ordArray.insert(i);
          ordArray.delete(i);
        });
    assertEquals(0, ordArray.count(), () -> "10,000 elements not deleted: " + ordArray.toString());
  }

  @Test
  void testConcurrentSyncDeletes() {
    IArray ordArray = new OrdArrayRecursive(100);
    LongStream nos = LongStream.rangeClosed(1L, 1000L);
    nos.forEach(
        i -> {
          ordArray.insert(i);
          ordArray.syncDelete(i);
        });
    assertEquals(0, ordArray.count(), () -> "100 elements not deleted: " + ordArray.toString());
  }

  @Test
  void testConcurrentSyncInsertsDeletes() {
    IArray ordArray = new OrdArrayRecursive(100);
    LongStream nos = LongStream.rangeClosed(1L, 100L).unordered().parallel();
    nos.forEach(
        i -> {
          ordArray.syncInsert(i);
          ordArray.syncDelete(i);
        });
    assertEquals(0, ordArray.count(), () -> "Elements not cleared");
  }
}
