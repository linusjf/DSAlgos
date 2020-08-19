package ds.tests;

import static ds.tests.TestConstants.*;
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
@DisplayName("HighArrayTest")
class HighArrayTest {
  @Nested
@DisplayName("HighArrayTest.ConstructorTests")
  class ConstructorTests {
    @SuppressWarnings("PMD.JUnitTestContainsTooManyAsserts")
    @Test
@DisplayName("HighArrayTest.ConstructorTests.testConstructorParameterNegative")
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
@DisplayName("HighArrayTest.ConstructorTests.testConstructorParameterOK")
    void testConstructorParameterOK() {
      IArray arr = new HighArray(10);
      assertEquals(10, arr.get().length, "Length 10 expected");
    }

    @Test
@DisplayName("HighArrayTest.ConstructorTests.testEmptyConstructor")
    void testEmptyConstructor() {
      IArray arr = new HighArray();
      boolean strict = (boolean) on(arr).get(STRICT);
      assertTrue(arr.get().length == 100 && !strict, "Length 100 and strict false expected");
    }

    @SuppressWarnings("PMD.JUnitTestContainsTooManyAsserts")
    @Test
@DisplayName("HighArrayTest.ConstructorTests.testConstructorParameterZero")
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
@DisplayName("HighArrayTest.InsertTests")
  class InsertTests {
    @ParameterizedTest
    @CsvSource(INIT_EXCEPTION_DATA)
@DisplayName("HighArrayTest.InsertTests.testException")
    void testException(@AggregateWith(HighArrayArgumentsAggregator.class) IArray highArray) {
      assertThrows(
          ArrayIndexOutOfBoundsException.class,
          () -> {
            highArray.insert(45L);
          });
    }

    @ParameterizedTest
    @CsvSource(INIT_DATA)
    @SuppressWarnings("PMD.JUnitTestContainsTooManyAsserts")
@DisplayName("HighArrayTest.InsertTests.testInsertAggregate")
    void testInsertAggregate(@AggregateWith(HighArrayArgumentsAggregator.class) IArray arr) {
      long[] a = {77, 99, 44, 55, 22, 88, 11, 00, 66, 33};
      long[] extent = arr.getExtentArray();
      assertEquals(arr.count(), 10, "10 elements not inserted.");
      assertArrayEquals(a, extent, "Elements must be equal.");
    }

    @Test
@DisplayName("HighArrayTest.InsertTests.testInsert")
    void testInsert() {
      HighArray arr = new HighArray(10);
      arr.insert(11L);
      int index = arr.insert(12L);
      assertEquals(1, index, "2 elements inserted.");
    }
  }

  @Nested
@DisplayName("HighArrayTest.ModCountTests")
  class ModCountTests {
    @Test
@DisplayName("HighArrayTest.ModCountTests.testInsertModCount")
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
@DisplayName("HighArrayTest.ModCountTests.testClearModCount")
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
@DisplayName("HighArrayTest.ModCountTests.testClearEmptyModCount")
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
@DisplayName("HighArrayTest.ModCountTests.testDeleteModCount")
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
@DisplayName("HighArrayTest.ModCountTests.testDeleteNotFoundModCount")
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
@DisplayName("HighArrayTest.DeleteTests")
  class DeleteTests {
    @ParameterizedTest
    @CsvSource(INIT_DATA)
    @SuppressWarnings({"PMD.DataflowAnomalyAnalysis", "PMD.JUnitTestContainsTooManyAsserts"})
@DisplayName("HighArrayTest.DeleteTests.testDeleteTrue")
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
@DisplayName("HighArrayTest.DeleteTests.testDeleteFalse")
    void testDeleteFalse(@AggregateWith(HighArrayArgumentsAggregator.class) IArray arr) {
      assertFalse(
          arr.delete(12L) || arr.delete(6L) || arr.delete(5L),
          "Elements 12, 6, 5 are not expected!");
    }

    @ParameterizedTest
    @CsvSource(INIT_DATA)
@DisplayName("HighArrayTest.DeleteTests.testDeleteEnd")
    void testDeleteEnd(@AggregateWith(HighArrayArgumentsAggregator.class) IArray arr) {
      int count = arr.count();
      long searchKey = 33L;
      assertTrue(
          arr.delete(searchKey) && arr.count() == count - 1,
          () -> String.format(NOT_AVAILABLE, searchKey));
    }

    @ParameterizedTest
    @CsvSource(INIT_FULL_DATA)
@DisplayName("HighArrayTest.DeleteTests.testDeleteEndArray")
    void testDeleteEndArray(@AggregateWith(HighArrayArgumentsAggregator.class) IArray arr) {
      int count = arr.count();
      long searchKey = 33L;
      assertTrue(
          arr.delete(searchKey) && arr.count() == count - 1,
          () -> String.format(NOT_AVAILABLE, searchKey));
    }

    @ParameterizedTest
    @CsvSource(INIT_DATA)
@DisplayName("HighArrayTest.DeleteTests.testDeleteOverflow")
    void testDeleteOverflow(@AggregateWith(HighArrayArgumentsAggregator.class) IArray arr) {
      int count = arr.count();
      long searchKey = 0L;
      arr.delete(searchKey);
      assertFalse(
          arr.delete(searchKey) && arr.count() != count - 1, () -> searchKey + " still available");
    }

    @ParameterizedTest
    @CsvSource(INIT_DATA)
@DisplayName("HighArrayTest.DeleteTests.testDeleteStart")
    void testDeleteStart(@AggregateWith(HighArrayArgumentsAggregator.class) IArray arr) {
      int count = arr.count();
      long searchKey = 77L;
      assertTrue(
          arr.delete(searchKey) && arr.count() == count - 1,
          () -> String.format(NOT_AVAILABLE, searchKey));
    }
  }

  @Nested
@DisplayName("HighArrayTest.SyncTests")
  class SyncTests {
    @ParameterizedTest
    @CsvSource(INIT_DATA)
    @SuppressWarnings({"PMD.DataflowAnomalyAnalysis", "PMD.JUnitTestContainsTooManyAsserts"})
@DisplayName("HighArrayTest.SyncTests.testSyncDeleteTrue")
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
@DisplayName("HighArrayTest.SyncTests.testSyncDeleteTrueIndividual")
    void testSyncDeleteTrueIndividual(
        @AggregateWith(HighArrayArgumentsAggregator.class) IArray arr) {
      int count = arr.count();
      assertTrue(arr.syncDelete(00L) && arr.count() == count - 1, "Element 0 not found.");
    }

    @ParameterizedTest
    @CsvSource(INIT_DATA)
    @SuppressWarnings("PMD.DataflowAnomalyAnalysis")
@DisplayName("HighArrayTest.SyncTests.testSyncDeleteFalseIndividual")
    void testSyncDeleteFalseIndividual(
        @AggregateWith(HighArrayArgumentsAggregator.class) IArray arr) {
      int count = arr.count();
      assertFalse(arr.syncDelete(12L) && arr.count() == count, "Elements 12 found and deleted");
    }

    @ParameterizedTest
    @CsvSource(INIT_DATA)
    @SuppressWarnings("PMD.DataflowAnomalyAnalysis")
@DisplayName("HighArrayTest.SyncTests.testSyncDeleteFalse")
    void testSyncDeleteFalse(@AggregateWith(HighArrayArgumentsAggregator.class) IArray arr) {
      assertFalse(
          arr.syncDelete(12L) || arr.syncDelete(6L) || arr.syncDelete(5L),
          "Elements 12, 6, 5 not expected!");
    }
  }

  @Nested
@DisplayName("HighArrayTest.FindTests")
  class FindTests {
    @ParameterizedTest
    @CsvSource(INIT_DATA)
@DisplayName("HighArrayTest.FindTests.testFindIndexFalse")
    void testFindIndexFalse(@AggregateWith(HighArrayArgumentsAggregator.class) IArray arr) {
      long searchKey = 35L;
      assertEquals(-1, arr.findIndex(searchKey), () -> searchKey + " available");
    }

    @ParameterizedTest
    @CsvSource(INIT_DATA)
@DisplayName("HighArrayTest.FindTests.testFindFalse")
    void testFindFalse(@AggregateWith(HighArrayArgumentsAggregator.class) IArray arr) {
      long searchKey = 35L;
      assertFalse(arr.find(searchKey), () -> searchKey + " available");
    }

    @ParameterizedTest
    @CsvSource(INIT_DATA)
@DisplayName("HighArrayTest.FindTests.testFindIndex")
    void testFindIndex(@AggregateWith(HighArrayArgumentsAggregator.class) IArray arr) {
      long searchKey = 11L;
      assertEquals(6, arr.findIndex(searchKey), () -> String.format(NOT_AVAILABLE, searchKey));
    }

    @ParameterizedTest
    @CsvSource(INIT_DATA)
@DisplayName("HighArrayTest.FindTests.testFindIndexTrue")
    void testFindIndexTrue(@AggregateWith(HighArrayArgumentsAggregator.class) IArray arr) {
      long searchKey = 11L;
      assertTrue(
          arr.find(searchKey) && arr.findIndex(searchKey) == 6,
          () -> String.format(NOT_AVAILABLE, searchKey));
    }

    @ParameterizedTest
    @CsvSource(INIT_DATA)
@DisplayName("HighArrayTest.FindTests.testFindIndexStart")
    void testFindIndexStart(@AggregateWith(HighArrayArgumentsAggregator.class) IArray arr) {
      long searchKey = 77L;
      assertEquals(0, arr.findIndex(searchKey), () -> String.format(NOT_AVAILABLE, searchKey));
    }

    @ParameterizedTest
    @CsvSource(INIT_DATA)
@DisplayName("HighArrayTest.FindTests.testFindIndexStartTrue")
    void testFindIndexStartTrue(@AggregateWith(HighArrayArgumentsAggregator.class) IArray arr) {
      long searchKey = 77L;
      assertTrue(
          arr.find(searchKey) && arr.findIndex(searchKey) == 0,
          () -> String.format(NOT_AVAILABLE, searchKey));
    }

    @ParameterizedTest
    @CsvSource(INIT_DATA)
@DisplayName("HighArrayTest.FindTests.testFindIndexEnd")
    void testFindIndexEnd(@AggregateWith(HighArrayArgumentsAggregator.class) IArray arr) {
      long searchKey = 33L;
      assertEquals(9, arr.findIndex(searchKey), () -> String.format(NOT_AVAILABLE, searchKey));
    }

    @ParameterizedTest
    @CsvSource(INIT_DATA)
@DisplayName("HighArrayTest.FindTests.testFindIndexEndTrue")
    void testFindIndexEndTrue(@AggregateWith(HighArrayArgumentsAggregator.class) IArray arr) {
      long searchKey = 33L;
      assertTrue(
          arr.find(searchKey) && arr.findIndex(searchKey) == 9,
          () -> String.format(NOT_AVAILABLE, searchKey));
    }

    @ParameterizedTest
    @CsvSource(INIT_DATA)
@DisplayName("HighArrayTest.FindTests.testFindIndexOverflow")
    void testFindIndexOverflow(@AggregateWith(HighArrayArgumentsAggregator.class) IArray arr) {
      long searchKey = 0L;
      arr.delete(0L);
      assertEquals(-1, arr.findIndex(searchKey), () -> searchKey + " still available");
    }

    @Test
@DisplayName("HighArrayTest.FindTests.testFindEmpty")
    void testFindEmpty() {
      IArray arr = new HighArray(10);
      long searchKey = 0L;
      assertEquals(-1, arr.findIndex(searchKey), () -> searchKey + " available");
    }

    @ParameterizedTest
    @CsvSource(INIT_DATA)
@DisplayName("HighArrayTest.FindTests.testFindTrue")
    void testFindTrue(@AggregateWith(HighArrayArgumentsAggregator.class) IArray arr) {
      long searchKey = 11L;
      assertTrue(
          arr.find(searchKey) && arr.findIndex(searchKey) == 6,
          () -> String.format(NOT_AVAILABLE, searchKey));
    }
  }

  @Nested
@DisplayName("HighArrayTest.MiscTests")
  class MiscTests {
    @ParameterizedTest
    @CsvSource(INIT_DATA)
@DisplayName("HighArrayTest.MiscTests.testGet")
    void testGet(@AggregateWith(HighArrayArgumentsAggregator.class) IArray arr) {
      long[] vals = arr.get();
      assertTrue(vals != null && vals.length == 100, "Null array or length incorrect");
    }

    @ParameterizedTest
    @CsvSource(INIT_DATA)
@DisplayName("HighArrayTest.MiscTests.testExtentArray")
    void testExtentArray(@AggregateWith(HighArrayArgumentsAggregator.class) IArray arr) {
      long[] vals = arr.getExtentArray();
      assertTrue(vals != null && vals.length == 10, "Null array or length incorrect");
    }

    @Test
@DisplayName("HighArrayTest.MiscTests.testExtentArrayEmpty")
    void testExtentArrayEmpty() {
      IArray arr = new HighArray();
      long[] vals = arr.getExtentArray();
      assertTrue(vals != null && vals.length == 0, "Null array or length incorrect");
    }

    @Test
@DisplayName("HighArrayTest.MiscTests.testCountZero")
    void testCountZero() {
      IArray arr = new HighArray(10, true);
      assertEquals(0, arr.count(), "Count must be zero!");
    }

    @ParameterizedTest
    @CsvSource(INIT_DATA)
@DisplayName("HighArrayTest.MiscTests.testCountPositive")
    void testCountPositive(@AggregateWith(HighArrayArgumentsAggregator.class) IArray arr) {
      assertEquals(10, arr.count(), "Count must be 10!");
    }

    @ParameterizedTest
    @CsvSource(INIT_DATA)
@DisplayName("HighArrayTest.MiscTests.testClear")
    void testClear(@AggregateWith(HighArrayArgumentsAggregator.class) IArray arr) {
      int origCount = arr.count();
      arr.clear();
      long[] copy = new long[origCount];
      long[] origTrunc = Arrays.copyOf(arr.get(), origCount);
      assertTrue(0 == arr.count() && Arrays.equals(copy, origTrunc), () -> "Array not cleared");
    }

    @Test
@DisplayName("HighArrayTest.MiscTests.testClearEmpty")
    void testClearEmpty() {
      IArray arr = new HighArray(100);
      arr.clear();
      long[] copy = new long[100];
      assertTrue(0 == arr.count() && Arrays.equals(arr.get(), copy), () -> "Array not cleared");
    }
  }

  @Nested
@DisplayName("HighArrayTest.ToStringTests")
  class ToStringTests {
    @ParameterizedTest
    @CsvSource(INIT_TOSTRING_DATA)
@DisplayName("HighArrayTest.ToStringTests.testToString")
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
@DisplayName("HighArrayTest.ToStringTests.testToStringSpan")
    void testToStringSpan(@AggregateWith(HighArrayArgumentsAggregator.class) IArray arr) {
      String lineSeparator = System.lineSeparator();
      StringBuilder sb = new StringBuilder();
      sb.append(HighArray.class.getName())
          .append(lineSeparator)
          .append("nElems = ")
          .append(10)
          .append(lineSeparator)
          .append("77 99 44 55 22 88 11 0 66 33 ")
          .append(lineSeparator);
      assertEquals(sb.toString(), arr.toString(), "Strings not equal.");
    }

    @ParameterizedTest
    @CsvSource(INIT_DATA)
@DisplayName("HighArrayTest.ToStringTests.testToStringEmpty")
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
@DisplayName("HighArrayTest.ToStringTests.testDisplay")
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
@DisplayName("HighArrayTest.EqualsVerifierTests")
  class EqualsVerifierTests {
    /** Added tests for code coverage completeness. */
    @Test
    @SuppressWarnings("PMD.JUnitTestsShouldIncludeAssert")
@DisplayName("HighArrayTest.EqualsVerifierTests.equalsContract")
    void equalsContract() {
      EqualsVerifier.forClass(HighArray.class)
          .withIgnoredFields(MOD_COUNT, LOCK, STRICT)
          .withRedefinedSuperclass()
          .withRedefinedSubclass(HighArrayExt.class)
          .withIgnoredAnnotations(NonNull.class)
          .verify();
    }

    @Test
    @SuppressWarnings("PMD.JUnitTestsShouldIncludeAssert")
@DisplayName("HighArrayTest.EqualsVerifierTests.leafNodeEquals")
    void leafNodeEquals() {
      EqualsVerifier.forClass(HighArray.class)
          .withIgnoredFields(MOD_COUNT, LOCK, STRICT)
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
