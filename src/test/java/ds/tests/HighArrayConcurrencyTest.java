package ds.tests;

import static org.junit.jupiter.api.Assertions.*;

import ds.HighArray;
import java.util.ConcurrentModificationException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;
import java.util.stream.LongStream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

@TestInstance(Lifecycle.PER_CLASS)
@Execution(ExecutionMode.CONCURRENT)
@SuppressWarnings("PMD.LawOfDemeter")
class HighArrayConcurrencyTest {
  private static final Logger LOGGER = Logger.getLogger(HighArrayConcurrencyTest.class.getName());

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
                      int modCount = highArray.getModCount();
                      try {
                        highArray.delete(i);
                      } catch (ConcurrentModificationException cme) {
                        int newCount = highArray.getModCount();
                        boolean strict = highArray.isStrict();
                        if (strict && modCount < newCount) excCount.incrementAndGet();
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
}
