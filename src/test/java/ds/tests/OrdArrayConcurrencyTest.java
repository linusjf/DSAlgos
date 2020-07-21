package ds.tests;

import static ds.tests.TestUtils.*;
import static org.junit.jupiter.api.Assertions.*;

import ds.OrdArray;
import java.util.ConcurrentModificationException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
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
class OrdArrayConcurrencyTest {
  private static final Logger LOGGER = Logger.getLogger(OrdArrayConcurrencyTest.class.getName());

  @ParameterizedTest
  @MethodSource("provideArraySize")
  void testConcurrentInsertsLatch(int size) {
    ExecutorService service = Executors.newFixedThreadPool(10);
    CountDownLatch cdl = new CountDownLatch(1);
    CountDownLatch done = new CountDownLatch(size);
    AtomicInteger excCount = new AtomicInteger();
    OrdArray ordArray = new OrdArray(size, true);
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
                        LOGGER.severe(() -> "Error: " + cme.getMessage());
                        excCount.incrementAndGet();
                        done.countDown();
                      }
                    }));
    try {
      cdl.countDown();
      done.await();
    } catch (InterruptedException ie) {
      Thread.currentThread().interrupt();
    }
    assertNotEquals(0, excCount.get(), () -> excCount + " is number of concurrent exceptions.");
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
  @MethodSource("provideArraySize")
  void testConcurrentDeletes(int size) {
    ExecutorService service = Executors.newFixedThreadPool(10);
    CountDownLatch cdl = new CountDownLatch(1);
    CountDownLatch done = new CountDownLatch(size);
    AtomicInteger excCount = new AtomicInteger();
    OrdArray ordArray = new OrdArray(size, true);
    LongStream nos = LongStream.rangeClosed(1L, (long) size).unordered();
    nos.forEach(i -> ordArray.insert(i));
    LongStream nosParallel = LongStream.rangeClosed(1L, (long) size).unordered().parallel();
    nosParallel.forEach(
        i ->
            service.execute(
                () -> {
                  try {
                    cdl.await();
                    ordArray.delete(i);
                    done.countDown();
                  } catch (ConcurrentModificationException cme) {
                    LOGGER.severe(() -> "Error deleting " + i);
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
    } catch (InterruptedException ie) {
      Thread.currentThread().interrupt();
    }
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
    LongStream nos = LongStream.rangeClosed(1L, 100L).unordered().parallel();
    nos.forEach(
        i -> {
          ordArray.syncInsert(i);
          ordArray.syncDelete(i);
        });
    assertEquals(0, ordArray.count(), () -> "Elements not cleared");
  }
}
