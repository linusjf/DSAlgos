package ds.tests;

import static org.joor.Reflect.*;
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

@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class HighArrayTest {
  private static final Logger LOGGER = Logger.getLogger(HighArrayTest.class.getName());
  HighArray arr;

  @SuppressWarnings("checkstyle:magicnumber")
  HighArrayTest() {
    arr = new HighArray(100);
  }

  @Test
  @Order(1)
  @SuppressWarnings("checkstyle:magicnumber")
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
  @SuppressWarnings("checkstyle:magicnumber")
  void testDeleteTrue() {
    LOGGER.info(() -> arr.toString());
    assertTrue(
        () -> arr.delete(00L) && arr.delete(55L) && arr.delete(99L),
        () -> "Elements 00, 55, 99 deleted");
  }

  @Test
  @Order(3)
  @SuppressWarnings("checkstyle:magicnumber")
  void testDeleteFalse() {
    LOGGER.info(() -> arr.toString());
    assertFalse(
        () -> arr.delete(00L) || arr.delete(55L) || arr.delete(99L),
        () -> "Elements 00, 55, 99 deleted");
  }

  @Test
  @Order(4)
  @SuppressWarnings("checkstyle:magicnumber")
  void testFindIndexFalse() {
    long searchKey = 35L;
    assertEquals(arr.findIndex(searchKey), -1, () -> "Key " + searchKey + " not available");
  }

  @Test
  @Order(4)
  @SuppressWarnings("checkstyle:magicnumber")
  void testFindFalse() {
    long searchKey = 35L;
    assertFalse(arr.find(searchKey), () -> "Key " + searchKey + " not available");
  }

  @Test
  @Order(4)
  @SuppressWarnings("checkstyle:magicnumber")
  void testFindIndexTrue() {
    long searchKey = 11L;
    assertTrue(arr.findIndex(searchKey) >= 0, () -> "Key " + searchKey + " available");
  }

  @Test
  @Order(4)
  @SuppressWarnings("checkstyle:magicnumber")
  void testFindTrue() {
    long searchKey = 11L;
    assertTrue(arr.find(searchKey), () -> "Key " + searchKey + " available");
  }

  @Test
  @Order(5)
  @SuppressWarnings("checkstyle:magicnumber")
  void testClear() {
    arr.clear();
    // for code coverage
    arr.clear();
    assertEquals(arr.count(), 0, () -> "Array cleared");
  }

  @Test
  @Order(6)
  @SuppressWarnings("checkstyle:magicnumber")
  void testToString() {
    arr.insert(77L);
    arr.insert(99L);
    arr.insert(44L);
    StringBuilder sb = new StringBuilder();
    sb.append("nElems = ").append(3).append(System.lineSeparator());
    sb.append("77 99 44 ");
    assertEquals(arr.toString(), sb.toString(), "Strings equal.");
  }

  @Test
  @Order(7)
  @SuppressWarnings("checkstyle:magicnumber")
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
  @SuppressWarnings("checkstyle:magicnumber")
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
  @SuppressWarnings("checkstyle:magicnumber")
  void testConcurrentInserts() {
    HighArray array = new HighArray(10_000);
    LongStream.rangeClosed(1L, 10_000L).parallel().forEach(i -> array.insert(i));
    assertEquals(10_000, array.count(), () -> "10,000 elements filled: " + array.toString());
  }

  @Test
  @SuppressWarnings("checkstyle:magicnumber")
  void testConcurrentDeletes() {
    HighArray array = new HighArray(10_000, true);
    LongStream.rangeClosed(1L, 10_000L).forEach(i -> array.insert(i));
    assertThrows(
        ConcurrentModificationException.class,
        () -> LongStream.rangeClosed(1L, 10_000L).parallel().forEach(i -> array.delete(i)));
  }

  @Test
  @SuppressWarnings("checkstyle:magicnumber")
  void testSequentialDeletes() {
    HighArray array = new HighArray(10_000, true);
    LongStream.rangeClosed(1L, 10_000L).forEach(i -> array.insert(i));
    LongStream.rangeClosed(1L, 10_000L).forEach(i -> array.delete(i));
    assertEquals(0, array.count(), () -> "10,000 elements deleted: " + array.toString());
  }

  @Test
  @SuppressWarnings("checkstyle:magicnumber")
  void testConcurrentSyncDeletes() {
    HighArray array = new HighArray(100);
    LongStream.rangeClosed(1L, 100L).forEach(i -> array.insert(i));
    LongStream.rangeClosed(1L, 100L).parallel().forEach(i -> array.syncDelete(i));
    assertEquals(0, array.count(), () -> "100 elements deleted: " + array.toString());
  }

  @Test
  @SuppressWarnings("checkstyle:magicnumber")
  void testConcurrentInsertsDeletes() {
    HighArray array = new HighArray(10_000, true);
    assertThrows(
        ConcurrentModificationException.class,
        () -> {
          LongStream.rangeClosed(1L, 10_000L).parallel().forEach(i -> array.insert(i));
          LongStream.rangeClosed(1L, 10_000L).parallel().forEach(i -> array.delete(i));
        });
  }

  @SuppressWarnings("checkstyle:magicnumber")
  void testConcurrentSyncInsertsDeletes() {
    HighArray array = new HighArray(100);
    LongStream.rangeClosed(1L, 100L).parallel().forEach(i -> array.insert(i));
    LongStream.rangeClosed(1L, 100L).parallel().forEach(i -> array.syncDelete(i));
    assertEquals(0, array.count(), () -> "Elements cleared");
  }

  /** Added tests for code coverage completeness. */
  @Test
  void equalsContract() {
    EqualsVerifier.forClass(HighArray.class)
        .withIgnoredFields("modCount", "lock", "strict")
        .withRedefinedSuperclass()
        .withRedefinedSubclass(HighArrayExt.class)
        .withIgnoredAnnotations(NonNull.class)
        .verify();
  }

  @Test
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
