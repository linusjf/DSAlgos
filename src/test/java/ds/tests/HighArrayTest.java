package ds.tests;

import static ds.tests.TestConstants.NOT_AVAILABLE;
import static ds.tests.TestData.*;
import static ds.tests.TestUtils.getModCount;
import static org.joor.Reflect.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import ds.HighArray;
import ds.IArray;
import java.util.Arrays;
import java.util.Optional;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.checkerframework.checker.nullness.qual.NonNull;
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
class HighArrayTest {
  @Nested
  class ConstructorTests {
    @SuppressWarnings("PMD.JUnitTestContainsTooManyAsserts")
    @Test
    void testConstructorParameterNegative() {
      IllegalArgumentException iae =
          assertThrows(
              IllegalArgumentException.class,
              () -> new HighArray(-1),
              "IllegalArgumentException expected for " + -1);
      Optional<String> msg = Optional.ofNullable(iae.getMessage());
      String val = msg.orElse("");
      assertTrue(val.contains("-1"), "Parameter -1 expected");
    }

    @Test
    void testConstructorParameterOK() {
      IArray arr = new HighArray(10);
      assertEquals(10, arr.get().length, "Length 10 expected");
    }

    void testEmptyConstructor() {
      IArray arr = new HighArray();
      boolean strict = (boolean) on(arr).get("strict");
      assertTrue(arr.get().length == 100 && !strict, "Length 100 and strict false expected");
    }

    @SuppressWarnings("PMD.JUnitTestContainsTooManyAsserts")
    @Test
    void testConstructorParameterZero() {
      IllegalArgumentException iae =
          assertThrows(
              IllegalArgumentException.class,
              () -> new HighArray(0),
              "IllegalArgumentException expected for " + 0);
      Optional<String> msg = Optional.ofNullable(iae.getMessage());
      String val = msg.orElse("");
      assertTrue(val.contains("0"), "Parameter 0 expected");
    }
  }

  @Nested
  class InsertTests {
    @ParameterizedTest
    @CsvSource(INIT_EXCEPTION_DATA)
    void testException(@AggregateWith(HighArrayArgumentsAggregator.class) IArray highArray) {
      assertThrows(
          ArrayIndexOutOfBoundsException.class,
          () -> {
            highArray.insert(45L);
          });
    }

    @ParameterizedTest
    @CsvSource(INIT_DATA)
    void testInsert(@AggregateWith(HighArrayArgumentsAggregator.class) IArray arr) {
      assertEquals(arr.count(), 10, "10 elements not inserted.");
    }
  }

  @Nested
  class ModCountTests {
    @Test
    void testInsertModCount() {
      IArray arr = new HighArray(100);
      int count = arr.count();
      int modCount = getModCount(arr);
      arr.insert(10L);
      int newModCount = getModCount(arr);
      assertTrue(
          modCount < newModCount && newModCount == 1 && arr.count() == count + 1,
          "modcount not incremented.");
    }

    @Test
    void testClearModCount() {
      IArray arr = new HighArray(100);
      arr.insert(10L);
      int modCount = getModCount(arr);
      arr.clear();
      int newModCount = getModCount(arr);
      assertTrue(
          modCount < newModCount && newModCount == 2 && arr.count() == 0,
          "modcount not incremented.");
    }

    @Test
    void testClearEmptyModCount() {
      IArray arr = new HighArray(100);
      int modCount = getModCount(arr);
      arr.clear();
      int newModCount = getModCount(arr);
      assertTrue(
          modCount == newModCount && modCount == 0 && arr.count() == 0,
          "modcount must not be incremented.");
    }

    @Test
    void testDeleteModCount() {
      IArray arr = new HighArray(100);
      arr.insert(10L);
      int modCount = getModCount(arr);
      arr.delete(10L);
      int newModCount = getModCount(arr);
      assertTrue(
          modCount < newModCount && newModCount == 2 && arr.count() == 0,
          "modcount not incremented.");
    }

    @Test
    void testDeleteNotFoundModCount() {
      IArray arr = new HighArray(100);
      int count = arr.count();
      int modCount = getModCount(arr);
      arr.delete(10L);
      int newModCount = getModCount(arr);
      assertTrue(
          modCount == newModCount && modCount == 0 && arr.count() == count,
          "modcount must not be incremented.");
    }
  }

  @Nested
  class DeleteTests {
    @ParameterizedTest
    @CsvSource(INIT_DATA)
    @SuppressWarnings({"PMD.DataflowAnomalyAnalysis", "PMD.JUnitTestContainsTooManyAsserts"})
    void testDeleteTrue(@AggregateWith(HighArrayArgumentsAggregator.class) IArray arr) {
      int count = arr.count();
      assertTrue(
          arr.delete(00L) && arr.delete(55L) && arr.delete(99L),
          "Elements 0, 55 and 99 must be deleted.");
      assertFalse(
          arr.find(00L) || arr.find(55L) || arr.find(99L),
          "Elements 0, 55 and 99 must not be found.");
      assertEquals(count - 3, arr.count(), "Three elements should have been deleted.");
    }

    @ParameterizedTest
    @CsvSource(INIT_DATA)
    @SuppressWarnings("PMD.DataflowAnomalyAnalysis")
    void testDeleteFalse(@AggregateWith(HighArrayArgumentsAggregator.class) IArray arr) {
      assertFalse(
          arr.delete(12L) || arr.delete(6L) || arr.delete(5L),
          "Elements 12, 6, 5 are not expected!");
    }

    @ParameterizedTest
    @CsvSource(INIT_DATA)
    void testDeleteEnd(@AggregateWith(HighArrayArgumentsAggregator.class) IArray arr) {
      int count = arr.count();
      long searchKey = 33L;
      assertTrue(
          arr.delete(searchKey) && arr.count() == count - 1,
          () -> String.format(NOT_AVAILABLE, searchKey));
    }

    @ParameterizedTest
    @CsvSource(INIT_FULL_DATA)
    void testDeleteEndArray(@AggregateWith(HighArrayArgumentsAggregator.class) IArray arr) {
      int count = arr.count();
      long searchKey = 33L;
      assertTrue(
          arr.delete(searchKey) && arr.count() == count - 1,
          () -> String.format(NOT_AVAILABLE, searchKey));
    }

    @ParameterizedTest
    @CsvSource(INIT_DATA)
    void testDeleteOverflow(@AggregateWith(HighArrayArgumentsAggregator.class) IArray arr) {
      int count = arr.count();
      long searchKey = 0L;
      arr.delete(searchKey);
      assertFalse(
          arr.delete(searchKey) && arr.count() != count - 1, () -> searchKey + " still available");
    }

    @ParameterizedTest
    @CsvSource(INIT_DATA)
    void testDeleteStart(@AggregateWith(HighArrayArgumentsAggregator.class) IArray arr) {
      int count = arr.count();
      long searchKey = 77L;
      assertTrue(
          arr.delete(searchKey) && arr.count() == count - 1,
          () -> String.format(NOT_AVAILABLE, searchKey));
    }
  }

  @Nested
  class SyncTests {
    @ParameterizedTest
    @CsvSource(INIT_DATA)
    @SuppressWarnings({"PMD.DataflowAnomalyAnalysis", "PMD.JUnitTestContainsTooManyAsserts"})
    void testSyncDeleteTrue(@AggregateWith(HighArrayArgumentsAggregator.class) IArray arr) {
      int count = arr.count();
      assertTrue(
          arr.syncDelete(00L) && arr.syncDelete(55L) && arr.syncDelete(99L),
          "Elements 0, 55 and 99 must be deleted.");
      assertFalse(
          arr.find(00L) || arr.find(55L) || arr.find(99L),
          "Elements 0, 55 and 99 must not be found.");
      assertEquals(count - 3, arr.count(), "Three elements should have been deleted.");
    }

    @ParameterizedTest
    @CsvSource(INIT_DATA)
    @SuppressWarnings("PMD.DataflowAnomalyAnalysis")
    void testSyncDeleteTrueIndividual(
        @AggregateWith(HighArrayArgumentsAggregator.class) IArray arr) {
      int count = arr.count();
      assertTrue(arr.syncDelete(00L) && arr.count() == count - 1, "Element 0 not found.");
    }

    @ParameterizedTest
    @CsvSource(INIT_DATA)
    @SuppressWarnings("PMD.DataflowAnomalyAnalysis")
    void testSyncDeleteFalseIndividual(
        @AggregateWith(HighArrayArgumentsAggregator.class) IArray arr) {
      int count = arr.count();
      assertFalse(arr.syncDelete(12L) && arr.count() == count, "Elements 12 found and deleted");
    }

    @ParameterizedTest
    @CsvSource(INIT_DATA)
    @SuppressWarnings("PMD.DataflowAnomalyAnalysis")
    void testSyncDeleteFalse(@AggregateWith(HighArrayArgumentsAggregator.class) IArray arr) {
      assertFalse(
          arr.syncDelete(12L) || arr.syncDelete(6L) || arr.syncDelete(5L),
          "Elements 12, 6, 5 not expected!");
    }
  }

  @Nested
  class FindTests {
    @ParameterizedTest
    @CsvSource(INIT_DATA)
    void testFindIndexFalse(@AggregateWith(HighArrayArgumentsAggregator.class) IArray arr) {
      long searchKey = 35L;
      assertEquals(-1, arr.findIndex(searchKey), () -> searchKey + " available");
    }

    @ParameterizedTest
    @CsvSource(INIT_DATA)
    void testFindFalse(@AggregateWith(HighArrayArgumentsAggregator.class) IArray arr) {
      long searchKey = 35L;
      assertFalse(arr.find(searchKey), () -> searchKey + " available");
    }

    @ParameterizedTest
    @CsvSource(INIT_DATA)
    void testFindIndex(@AggregateWith(HighArrayArgumentsAggregator.class) IArray arr) {
      long searchKey = 11L;
      assertEquals(6, arr.findIndex(searchKey), () -> String.format(NOT_AVAILABLE, searchKey));
    }

    @ParameterizedTest
    @CsvSource(INIT_DATA)
    void testFindIndexTrue(@AggregateWith(HighArrayArgumentsAggregator.class) IArray arr) {
      long searchKey = 11L;
      assertTrue(
          arr.find(searchKey) && arr.findIndex(searchKey) == 6,
          () -> String.format(NOT_AVAILABLE, searchKey));
    }

    @ParameterizedTest
    @CsvSource(INIT_DATA)
    void testFindIndexStart(@AggregateWith(HighArrayArgumentsAggregator.class) IArray arr) {
      long searchKey = 77L;
      assertEquals(0, arr.findIndex(searchKey), () -> String.format(NOT_AVAILABLE, searchKey));
    }

    @ParameterizedTest
    @CsvSource(INIT_DATA)
    void testFindIndexStartTrue(@AggregateWith(HighArrayArgumentsAggregator.class) IArray arr) {
      long searchKey = 77L;
      assertTrue(
          arr.find(searchKey) && arr.findIndex(searchKey) == 0,
          () -> String.format(NOT_AVAILABLE, searchKey));
    }

    @ParameterizedTest
    @CsvSource(INIT_DATA)
    void testFindIndexEnd(@AggregateWith(HighArrayArgumentsAggregator.class) IArray arr) {
      long searchKey = 33L;
      assertEquals(9, arr.findIndex(searchKey), () -> String.format(NOT_AVAILABLE, searchKey));
    }

    @ParameterizedTest
    @CsvSource(INIT_DATA)
    void testFindIndexEndTrue(@AggregateWith(HighArrayArgumentsAggregator.class) IArray arr) {
      long searchKey = 33L;
      assertTrue(
          arr.find(searchKey) && arr.findIndex(searchKey) == 9,
          () -> String.format(NOT_AVAILABLE, searchKey));
    }

    @ParameterizedTest
    @CsvSource(INIT_DATA)
    void testFindIndexOverflow(@AggregateWith(HighArrayArgumentsAggregator.class) IArray arr) {
      long searchKey = 0L;
      arr.delete(0L);
      assertEquals(-1, arr.findIndex(searchKey), () -> searchKey + " still available");
    }

    @Test
    void testFindEmpty() {
      IArray arr = new HighArray(10);
      long searchKey = 0L;
      assertEquals(-1, arr.findIndex(searchKey), () -> searchKey + " available");
    }

    @ParameterizedTest
    @CsvSource(INIT_DATA)
    void testFindTrue(@AggregateWith(HighArrayArgumentsAggregator.class) IArray arr) {
      long searchKey = 11L;
      assertTrue(
          arr.find(searchKey) && arr.findIndex(searchKey) == 6,
          () -> String.format(NOT_AVAILABLE, searchKey));
    }
  }

  @Nested
  class MiscTests {
    @ParameterizedTest
    @CsvSource(INIT_DATA)
    void testGet(@AggregateWith(HighArrayArgumentsAggregator.class) IArray arr) {
      long[] vals = arr.get();
      assertTrue(vals != null && vals.length == 100, "Null array or length incorrect");
    }

    @Test
    void testCountZero() {
      IArray arr = new HighArray(10, true);
      assertEquals(0, arr.count(), "Count must be zero!");
    }

    @ParameterizedTest
    @CsvSource(INIT_DATA)
    void testCountPositive(@AggregateWith(HighArrayArgumentsAggregator.class) IArray arr) {
      assertEquals(10, arr.count(), "Count must be 10!");
    }

    @ParameterizedTest
    @CsvSource(INIT_DATA)
    void testClear(@AggregateWith(HighArrayArgumentsAggregator.class) IArray arr) {
      int origCount = arr.count();
      arr.clear();
      long[] copy = new long[origCount];
      long[] origTrunc = Arrays.copyOf(arr.get(), origCount);
      assertTrue(0 == arr.count() && Arrays.equals(copy, origTrunc), () -> "Array not cleared");
    }

    @Test
    void testClearEmpty() {
      IArray arr = new HighArray(100);
      arr.clear();
      long[] copy = new long[100];
      assertTrue(0 == arr.count() && Arrays.equals(arr.get(), copy), () -> "Array not cleared");
    }
  }

  @Nested
  class ToStringTests {
    @ParameterizedTest
    @CsvSource(INIT_TOSTRING_DATA)
    void testToString(@AggregateWith(HighArrayArgumentsAggregator.class) IArray arr) {
      String lineSeparator = System.lineSeparator();
      StringBuilder sb = new StringBuilder();
      sb.append(HighArray.class.getName())
          .append(lineSeparator)
          .append("nElems = ")
          .append(3)
          .append(lineSeparator)
          .append("77 99 44 ");
      assertEquals(sb.toString(), arr.toString(), "Strings not equal.");
    }

    @ParameterizedTest
    @CsvSource(INIT_DATA)
    void testToStringEmpty(@AggregateWith(HighArrayArgumentsAggregator.class) IArray arr) {
      arr.clear();
      String lineSeparator = System.lineSeparator();
      StringBuilder sb = new StringBuilder();
      sb.append(HighArray.class.getName())
          .append(lineSeparator)
          .append("nElems = ")
          .append(0)
          .append(System.lineSeparator());
      assertEquals(sb.toString(), arr.toString(), "Strings not equal.");
    }

    @ParameterizedTest
    @CsvSource(INIT_DATA)
    void testDisplay(@AggregateWith(HighArrayArgumentsAggregator.class) IArray arr) {
      IArray highArray = spy(arr);

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
  }

  @Nested
  class EqualsVerifierTests {
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
