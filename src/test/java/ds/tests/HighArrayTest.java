package ds.tests;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import ds.HighArray;
import java.util.ConcurrentModificationException;
import java.util.logging.Logger;
import java.util.stream.LongStream;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

@TestInstance(Lifecycle.PER_CLASS)
@Execution(ExecutionMode.SAME_THREAD)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SuppressWarnings("PMD.LawOfDemeter")
class HighArrayTest {
  private static final Logger LOGGER = Logger.getLogger(HighArrayTest.class.getName());
  HighArray arr;
  HighArray array;

  HighArrayTest() {
    arr = new HighArray(100);
    array = new HighArray(10_000, true);
    LongStream nos = LongStream.rangeClosed(1L, 10_000L);
    nos.forEach(i -> array.insert(i));
  }

  @Test
  @Order(1)
  void testInsert() {
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
    assertEquals(arr.count(), 10, "10 elements inserted.");
  }

  @Test
  @Order(2)
  void testDeleteTrue() {
    LOGGER.info(() -> arr.toString());
    assertTrue(
        () -> arr.delete(00L) && arr.delete(55L) && arr.delete(99L),
        () -> "Elements 00, 55, 99 deleted");
  }

  @Test
  @Order(3)
  void testDeleteFalse() {
    LOGGER.info(() -> arr.toString());
    assertFalse(
        () -> arr.delete(00L) || arr.delete(55L) || arr.delete(99L),
        () -> "Elements 00, 55, 99 deleted");
  }

  @Test
  @Order(4)
  void testFindIndexFalse() {
    long searchKey = 35L;
    assertEquals(arr.findIndex(searchKey), -1, () -> searchKey + " not available");
  }

  @Test
  @Order(4)
  void testFindFalse() {
    long searchKey = 35L;
    assertFalse(arr.find(searchKey), () -> searchKey + " not available");
  }

  @Test
  @Order(4)
  void testFindIndexTrue() {
    long searchKey = 11L;
    assertTrue(arr.findIndex(searchKey) >= 0, () -> searchKey + " available");
  }

  @Test
  @Order(4)
  void testFindTrue() {
    long searchKey = 11L;
    assertTrue(arr.find(searchKey), () -> searchKey + " available");
  }

  @Test
  @Order(5)
  void testClear() {
    arr.clear();
    // for code coverage
    arr.clear();
    assertEquals(arr.count(), 0, () -> "Array cleared");
  }

  @Test
  @Order(6)
  void testToString() {
    arr.insert(77L);
    arr.insert(99L);
    arr.insert(44L);
    StringBuilder sb = new StringBuilder();
    sb.append("nElems = ").append(3).append(System.lineSeparator()).append("77 99 44 ");
    assertEquals(arr.toString(), sb.toString(), "Strings equal.");
  }

  @Test
  @Order(7)
  void testDisplay() {
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
    HighArray array = new HighArray(3);
    array.insert(2L);
    array.insert(11L);
    array.insert(21L);
    assertThrows(
        ArrayIndexOutOfBoundsException.class,
        () -> {
          array.insert(45L);
        });
  }

  @Test
  void testConcurrentInserts() {
    HighArray array = new HighArray(10_000);
    LongStream.rangeClosed(1L, 10_000L).parallel().forEach(i -> array.insert(i));
    assertEquals(10_000, array.count(), () -> "10,000 elements filled: " + array.toString());
  }

  @Test
  void testConcurrentDeletes() {
    HighArray array = new HighArray(10_000, true);
    LongStream nos = LongStream.rangeClosed(1L, 10_000L);
    nos.forEach(i -> array.insert(i));
    LongStream nosParallel = LongStream.rangeClosed(1L, 10_000L).parallel();
    assertThrows(
        ConcurrentModificationException.class, () -> nosParallel.forEach(i -> array.delete(i)));
  }

  @Test
  void testSequentialDeletes() {
    HighArray array = new HighArray(10_000, true);
    LongStream nos = LongStream.rangeClosed(1L, 10_000L);
    nos.forEach(
        i -> {
          array.insert(i);
          array.delete(i);
        });
    assertEquals(0, array.count(), () -> "10,000 elements deleted: " + array.toString());
  }

  @Test
  void testConcurrentSyncDeletes() {
    HighArray array = new HighArray(100);
    LongStream nos = LongStream.rangeClosed(1L, 10_000L);
    nos.forEach(
        i -> {
          array.insert(i);
          array.syncDelete(i);
        });
    assertEquals(0, array.count(), () -> "100 elements deleted: " + array.toString());
  }

  @Test
  void testConcurrentInsertsDeletes() {
    HighArray array = new HighArray(1_000_000, true);
    assertThrows(
        ConcurrentModificationException.class,
        () -> {
          LongStream nos = LongStream.rangeClosed(1L, 1_000_000L).parallel();
          nos.forEach(
              i -> {
                array.insert(i);
                array.delete(i);
              });
        });
  }

  @Test
  void testConcurrentSyncInsertsDeletes() {
    HighArray array = new HighArray(100);
    LongStream nos = LongStream.rangeClosed(1L, 100L).parallel();
    nos.forEach(
        i -> {
          array.insert(i);
          array.syncDelete(i);
        });
    assertEquals(0, array.count(), () -> "Elements cleared");
  }

  /**
   * @RepeatedTest(10_000) void repeatedTestWithRepetitionInfo(RepetitionInfo repetitionInfo) {
   * array.delete(repetitionInfo.getCurrentRepetition()); assertEquals(10_000,
   * repetitionInfo.getTotalRepetitions()); }
   */

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
