package ds.tests;

import static ds.ArrayUtils.*;
import static org.junit.jupiter.api.Assertions.*;

import ds.IArray;
import ds.OrdArrayLock;
import java.util.logging.Logger;
import java.util.stream.LongStream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

@TestInstance(Lifecycle.PER_CLASS)
@Execution(ExecutionMode.SAME_THREAD)
@SuppressWarnings("PMD.LawOfDemeter")
class OrdArrayLockConcurrencyTest implements ConcurrencyProvider {
  private static final Logger LOGGER =
      Logger.getLogger(OrdArrayLockConcurrencyTest.class.getName());

  @SuppressWarnings("PMD.JUnitTestContainsTooManyAsserts")
  @Test
  void testSyncInserts() {
    IArray ordArray = new OrdArrayLock(1000, true);
    LongStream.rangeClosed(1L, 1000L).unordered().parallel().forEach(i -> ordArray.syncInsert(i));
    assertTrue(isSorted(ordArray), "Array is unsorted!");
    assertEquals(1000, ordArray.count(), "1000 elements not inserted.");
  }

  @Test
  void testSequentialDeletes() {
    IArray ordArray = new OrdArrayLock(1000, true);
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
    IArray ordArray = new OrdArrayLock(100);
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
    IArray ordArray = new OrdArrayLock(100);
    LongStream nos = LongStream.rangeClosed(1L, 100L).unordered().parallel();
    nos.forEach(
        i -> {
          ordArray.syncInsert(i);
          ordArray.syncDelete(i);
        });
    assertEquals(0, ordArray.count(), () -> "Elements not cleared");
  }
}
