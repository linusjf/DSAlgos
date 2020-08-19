package ds.tests;

import static ds.ArrayUtils.*;
import static ds.tests.TestConstants.*;
import static ds.tests.TestData.*;
import static ds.tests.TestUtils.*;
import static org.joor.Reflect.*;
import static org.junit.jupiter.api.Assertions.*;

import ds.IArray;
import ds.OrdArrayLock;
import java.util.Optional;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.AggregateWith;
import org.junit.jupiter.params.provider.CsvSource;

@TestInstance(Lifecycle.PER_CLASS)
@Execution(ExecutionMode.SAME_THREAD)
@SuppressWarnings("PMD.LawOfDemeter")
@DisplayName("OrdArrayLockTest")
class OrdArrayLockTest {
  @Nested
@DisplayName("OrdArrayLockTest.ConstructorTests")
  class ConstructorTests {
    @SuppressWarnings("PMD.JUnitTestContainsTooManyAsserts")
    @Test
@DisplayName("OrdArrayLockTest.ConstructorTests.testConstructorParameterNegative")
    void testConstructorParameterNegative() {
      IllegalArgumentException iae =
          assertThrows(
              IllegalArgumentException.class,
              () -> new OrdArrayLock(-1),
              "IllegalArgumentException expected for " + -1);
      Optional<String> msg = Optional.ofNullable(iae.getMessage());
      String val = msg.orElse("");
      assertTrue(val.contains("-1"), "Parameter -1 expected");
    }

    @Test
@DisplayName("OrdArrayLockTest.ConstructorTests.testConstructorParameterOK")
    void testConstructorParameterOK() {
      IArray arr = new OrdArrayLock(10);
      assertEquals(10, arr.get().length, "Length 10 expected");
    }

    @Test
@DisplayName("OrdArrayLockTest.ConstructorTests.testEmptyConstructor")
    void testEmptyConstructor() {
      IArray arr = new OrdArrayLock();
      boolean strict = (boolean) on(arr).get(STRICT);
      assertTrue(arr.get().length == 100 && !strict, "Length 100 and strict false expected");
    }

    @SuppressWarnings("PMD.JUnitTestContainsTooManyAsserts")
    @Test
@DisplayName("OrdArrayLockTest.ConstructorTests.testConstructorParameterZero")
    void testConstructorParameterZero() {
      IllegalArgumentException iae =
          assertThrows(
              IllegalArgumentException.class,
              () -> new OrdArrayLock(0),
              "IllegalArgumentException expected for " + 0);
      Optional<String> msg = Optional.ofNullable(iae.getMessage());
      String val = msg.orElse("");
      assertTrue(val.contains("0"), "Parameter 0 expected");
    }
  }

  @Nested
@DisplayName("OrdArrayLockTest.InsertTests")
  class InsertTests {
    @ParameterizedTest
    @CsvSource(INIT_DATA)
@DisplayName("OrdArrayLockTest.InsertTests.insertDuplicate")
    void insertDuplicate(@AggregateWith(OrdArrayLockArgumentsAggregator.class) IArray arr) {
      assertTrue(6 == arr.syncInsert(66L) && isSorted(arr), "Index 6 expected");
    }

    @ParameterizedTest
    @CsvSource(INIT_DUPLICATE_DATA)
@DisplayName("OrdArrayLockTest.InsertTests.insertDuplicateElements")
    void insertDuplicateElements(@AggregateWith(OrdArrayLockArgumentsAggregator.class) IArray arr) {
      assertTrue(21 == arr.count() && isSorted(arr), "21 elements expected");
    }

    @ParameterizedTest
    @CsvSource(INIT_DATA)
@DisplayName("OrdArrayLockTest.InsertTests.testInsert")
    void testInsert(@AggregateWith(OrdArrayLockArgumentsAggregator.class) IArray arr) {
      assertTrue(10 == arr.count() && isSorted(arr), "10 elements not inserted.");
    }

    @ParameterizedTest
    @CsvSource(INIT_DATA)
@DisplayName("OrdArrayLockTest.InsertTests.testInsertAtStartExists")
    void testInsertAtStartExists(@AggregateWith(OrdArrayLockArgumentsAggregator.class) IArray arr) {
      int count = arr.count();
      long val = 0L;
      int index = arr.findIndex(val);
      int insertIndex = arr.syncInsert(val);
      assertTrue(
          insertIndex >= index
              && insertIndex <= index + 1
              && arr.count() == count + 1
              && isSorted(arr),
          "11 elements expected, indexes 0 or 1 expected.");
    }

    @ParameterizedTest
    @CsvSource(INIT_DATA)
@DisplayName("OrdArrayLockTest.InsertTests.testInsertAtEndExists")
    void testInsertAtEndExists(@AggregateWith(OrdArrayLockArgumentsAggregator.class) IArray arr) {
      int count = arr.count();
      long val = 99L;
      int index = arr.findIndex(val);
      int insertIndex = arr.syncInsert(val);
      assertTrue(
          insertIndex >= index
              && insertIndex <= index + 1
              && arr.count() == count + 1
              && isSorted(arr),
          "11 elements expected, indexes 9 or 10 expected.");
    }

    @ParameterizedTest
    @CsvSource(INIT_DATA)
@DisplayName("OrdArrayLockTest.InsertTests.testInsertAtEnd")
    void testInsertAtEnd(@AggregateWith(OrdArrayLockArgumentsAggregator.class) IArray arr) {
      int count = arr.count();
      long val = 100L;
      int insertIndex = arr.syncInsert(val);
      assertTrue(
          insertIndex == count && arr.count() == count + 1 && isSorted(arr),
          () -> (count + 1) + " elements expected, index " + count + " expected.");
    }

    @ParameterizedTest
    @CsvSource(INIT_DATA)
@DisplayName("OrdArrayLockTest.InsertTests.testInsertAtStart")
    void testInsertAtStart(@AggregateWith(OrdArrayLockArgumentsAggregator.class) IArray arr) {
      int count = arr.count();
      long val = -1L;
      int insertIndex = arr.syncInsert(val);
      assertTrue(
          insertIndex == 0 && arr.count() == count + 1 && isSorted(arr),
          "11 elements expected, index 0 expected.");
    }

    @SuppressWarnings("PMD.DataflowAnomalyAnalysis")
    @ParameterizedTest
    @CsvSource(INIT_SORTED_DATA)
@DisplayName("OrdArrayLockTest.InsertTests.testInsertSorted")
    void testInsertSorted(@AggregateWith(OrdArrayLockArgumentsAggregator.class) IArray arr) {
      int count = arr.count();
      int res = arr.syncInsert(99L);
      assertTrue(res == 8 && arr.count() == count + 1, "Sorted and insert at 8 expected.");
    }

    @SuppressWarnings("PMD.DataflowAnomalyAnalysis")
    @ParameterizedTest
    @CsvSource(INIT_ALL_SAME_DATA)
@DisplayName("OrdArrayLockTest.InsertTests.testInsertAllSameSorted")
    void testInsertAllSameSorted(@AggregateWith(OrdArrayLockArgumentsAggregator.class) IArray arr) {
      int count = arr.count();
      int res = arr.syncInsert(99L);
      assertTrue(res == 10 && arr.count() == count + 1, "Insert must succeed.");
    }

    @ParameterizedTest
    @CsvSource(INIT_EXCEPTION_DATA)
@DisplayName("OrdArrayLockTest.InsertTests.testException")
    void testException(@AggregateWith(OrdArrayLockArgumentsAggregator.class) IArray ordArray) {
      assertThrows(
          ArrayIndexOutOfBoundsException.class,
          () -> {
            ordArray.syncInsert(45L);
          });
    }
  }

  @Nested
@DisplayName("OrdArrayLockTest.DeleteTests")
  class DeleteTests {

    @ParameterizedTest
    @CsvSource(INIT_DATA)
    @SuppressWarnings({"PMD.DataflowAnomalyAnalysis", "PMD.JUnitTestContainsTooManyAsserts"})
@DisplayName("OrdArrayLockTest.DeleteTests.testDeleteTrue")
    void testDeleteTrue(@AggregateWith(OrdArrayLockArgumentsAggregator.class) IArray arr) {
      int count = arr.count();
      assertTrue(
          arr.syncDelete(00L) && arr.syncDelete(55L) && arr.syncDelete(99L),
          "Elements 0, 55 and 99 must be deleted");
      assertEquals(count - 3, arr.count(), "Count must be " + (count - 3));
      assertTrue(isSorted(arr), "Array must be sorted");
    }

    @ParameterizedTest
    @CsvSource(INIT_DATA)
    @SuppressWarnings("PMD.DataflowAnomalyAnalysis")
@DisplayName("OrdArrayLockTest.DeleteTests.testDeleteFalse")
    void testDeleteFalse(@AggregateWith(OrdArrayLockArgumentsAggregator.class) IArray arr) {
      int count = arr.count();
      assertFalse(
          arr.syncDelete(12L) || arr.syncDelete(6L) || arr.syncDelete(5L) && arr.count() != count,
          "Elements 12, 6, 5 found and deleted");
    }

    @ParameterizedTest
    @CsvSource(INIT_DATA)
@DisplayName("OrdArrayLockTest.DeleteTests.testDeleteStart")
    void testDeleteStart(@AggregateWith(OrdArrayLockArgumentsAggregator.class) IArray arr) {
      int count = arr.count();
      long searchKey = 00L;
      assertTrue(
          arr.syncDelete(searchKey) && arr.count() == count - 1 && isSorted(arr),
          () -> String.format(NOT_AVAILABLE, searchKey));
    }

    @ParameterizedTest
    @CsvSource(INIT_DATA)
@DisplayName("OrdArrayLockTest.DeleteTests.testDeleteEnd")
    void testDeleteEnd(@AggregateWith(OrdArrayLockArgumentsAggregator.class) IArray arr) {
      int count = arr.count();
      long searchKey = 33L;
      assertTrue(
          arr.syncDelete(searchKey) && arr.count() == count - 1 && isSorted(arr),
          () -> String.format(NOT_AVAILABLE, searchKey));
    }

    @ParameterizedTest
    @CsvSource(INIT_DATA)
@DisplayName("OrdArrayLockTest.DeleteTests.testDeleteOverflow")
    void testDeleteOverflow(@AggregateWith(OrdArrayLockArgumentsAggregator.class) IArray arr) {
      long searchKey = 0L;
      arr.syncDelete(searchKey);
      int count = arr.count();
      assertFalse(
          arr.syncDelete(searchKey) && arr.count() != count, () -> searchKey + " still available");
    }

    @ParameterizedTest
    @CsvSource(INIT_FULL_DATA)
@DisplayName("OrdArrayLockTest.DeleteTests.testDeleteEndArray")
    void testDeleteEndArray(@AggregateWith(OrdArrayLockArgumentsAggregator.class) IArray arr) {
      int count = arr.count();
      long searchKey = 33L;
      assertTrue(
          arr.syncDelete(searchKey) && arr.count() == count - 1 && isSorted(arr),
          () -> String.format(NOT_AVAILABLE, searchKey));
    }
  }

  @Nested
@DisplayName("OrdArrayLockTest.ModCountTests")
  class ModCountTests {
    @Test
@DisplayName("OrdArrayLockTest.ModCountTests.testInsertModCount")
    void testInsertModCount() {
      IArray arr = new OrdArrayLock(100);
      int count = arr.count();
      int modCount = getModCount(arr);
      arr.syncInsert(10L);
      int newModCount = getModCount(arr);
      assertTrue(
          modCount < newModCount && newModCount == 1 && arr.count() == count + 1,
          "modcount not incremented.");
    }

    @Test
@DisplayName("OrdArrayLockTest.ModCountTests.testClearModCount")
    void testClearModCount() {
      IArray arr = new OrdArrayLock(100);
      arr.syncInsert(10L);
      int modCount = getModCount(arr);
      arr.clear();
      int newModCount = getModCount(arr);
      assertTrue(
          modCount < newModCount && newModCount == 2 && arr.count() == 0,
          "modcount not incremented.");
    }

    @Test
@DisplayName("OrdArrayLockTest.ModCountTests.testClearEmptyModCount")
    void testClearEmptyModCount() {
      IArray arr = new OrdArrayLock(100);
      int modCount = getModCount(arr);
      arr.clear();
      int newModCount = getModCount(arr);
      assertTrue(
          modCount == newModCount && modCount == 0 && arr.count() == 0,
          "modcount must not be incremented.");
    }

    @Test
@DisplayName("OrdArrayLockTest.ModCountTests.testDeleteModCount")
    void testDeleteModCount() {
      IArray arr = new OrdArrayLock(100);
      arr.syncInsert(10L);
      int modCount = getModCount(arr);
      arr.syncDelete(10L);
      int newModCount = getModCount(arr);
      assertTrue(
          modCount < newModCount && newModCount == 2 && arr.count() == 0,
          "modcount not incremented.");
    }

    @Test
@DisplayName("OrdArrayLockTest.ModCountTests.testDeleteNotFoundModCount")
    void testDeleteNotFoundModCount() {
      IArray arr = new OrdArrayLock(100);
      int count = arr.count();
      int modCount = getModCount(arr);
      arr.syncDelete(10L);
      int newModCount = getModCount(arr);
      assertTrue(
          modCount == newModCount && modCount == 0 && arr.count() == count,
          "modcount must not be incremented.");
    }
  }

  @Nested
@DisplayName("OrdArrayLockTest.EqualsVerifierTests")
  class EqualsVerifierTests {
    /** Added tests for code coverage completeness. */
    @Test
    @SuppressWarnings("PMD.JUnitTestsShouldIncludeAssert")
@DisplayName("OrdArrayLockTest.EqualsVerifierTests.equalsContract")
    void equalsContract() {
      EqualsVerifier.forClass(OrdArrayLock.class)
          .withIgnoredFields(MOD_COUNT, LOCK, STRICT, WRITE)
          .withRedefinedSuperclass()
          .withRedefinedSubclass(OrdArrayLockExt.class)
          .withPrefabValues(
              Lock.class,
              new ReentrantReadWriteLock().writeLock(),
              new ReentrantReadWriteLock().readLock())
          .withIgnoredAnnotations(NonNull.class)
          .verify();
    }

    @Test
    @SuppressWarnings("PMD.JUnitTestsShouldIncludeAssert")
@DisplayName("OrdArrayLockTest.EqualsVerifierTests.leafNodeEquals")
    void leafNodeEquals() {
      EqualsVerifier.forClass(OrdArrayLock.class)
          .withIgnoredFields(MOD_COUNT, LOCK, STRICT, WRITE)
          .withRedefinedSuperclass()
          .withRedefinedSubclass(OrdArrayLockExt.class)
          .withPrefabValues(
              Lock.class,
              new ReentrantReadWriteLock().writeLock(),
              new ReentrantReadWriteLock().readLock())
          .withIgnoredAnnotations(NonNull.class)
          .verify();
    }
  }

  static class OrdArrayLockExt extends OrdArrayLock {
    OrdArrayLockExt(int size) {
      super(size);
    }

    @Override
    public boolean canEqual(Object obj) {
      return obj instanceof OrdArrayLockExt;
    }
  }
}
