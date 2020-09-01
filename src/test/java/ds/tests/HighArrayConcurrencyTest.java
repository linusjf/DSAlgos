package ds.tests;

import static org.junit.jupiter.api.Assertions.*;

import ds.HighArray;
import ds.IArray;
import java.util.ConcurrentModificationException;
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
@DisplayName("HighArrayConcurrencyTest")
class HighArrayConcurrencyTest implements ConcurrencyProvider {
  private static final Logger LOGGER = Logger.getLogger(HighArrayConcurrencyTest.class.getName());

  @Test
  @DisplayName("HighArrayConcurrencyTest.testConcurrentInserts")
  void testConcurrentInserts() {
    IArray highArray = new HighArray(10_000);
    LongStream.rangeClosed(1L, 10_000L).parallel().forEach(i -> highArray.insert(i));
    assertEquals(
        10_000, highArray.count(), () -> "10,000 elements not filled: " + highArray.toString());
  }

  @SuppressWarnings("PMD.DataflowAnomalyAnalysis")
  @ParameterizedTest
  @MethodSource("provideArraySize")
  @DisplayName("HighArrayConcurrencyTest.testConcurrentDeletes")
  void testConcurrentDeletes(int size) {
    CountDownLatch cdl = new CountDownLatch(1);
    CountDownLatch done = new CountDownLatch(size);
    AtomicInteger excCount = new AtomicInteger();
    IArray highArray = new HighArray(size, true);
    LongStream nos = LongStream.rangeClosed(1L, (long) size).unordered();
    nos.forEach(i -> highArray.insert(i));
    nos.close();
    LongStream nosParallel = LongStream.rangeClosed(1L, (long) size).unordered().parallel();
    ExecutorService service = Executors.newFixedThreadPool(10);
    nosParallel.forEach(
        i ->
            service.execute(
                () -> {
                  try {
                    cdl.await();
                    highArray.delete(i);
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
    nosParallel.close();
    try {
      cdl.countDown();
      done.await();
      service.shutdown();
      service.awaitTermination(1, TimeUnit.MINUTES);
    } catch (InterruptedException ie) {
      Thread.currentThread().interrupt();
    }

    assertNotEquals(0, excCount.get(), () -> excCount + " is number of concurrent exceptions.");
  }

  @Test
  @DisplayName("HighArrayConcurrencyTest.testSequentialDeletes")
  void testSequentialDeletes() {
    IArray highArray = new HighArray(10_000, true);
    LongStream nos = LongStream.rangeClosed(1L, 10_000L);
    nos.forEach(
        i -> {
          highArray.insert(i);
          highArray.delete(i);
        });
    nos.close();
    assertEquals(
        0, highArray.count(), () -> "10,000 elements not deleted: " + highArray.toString());
  }

  @Test
  @DisplayName("HighArrayConcurrencyTest.testConcurrentSyncDeletes")
  void testConcurrentSyncDeletes() {
    IArray highArray = new HighArray(100);
    LongStream nos = LongStream.rangeClosed(1L, 10_000L);
    nos.forEach(
        i -> {
          highArray.insert(i);
          highArray.syncDelete(i);
        });
    nos.close();
    assertEquals(0, highArray.count(), () -> "100 elements not deleted: " + highArray.toString());
  }
}
