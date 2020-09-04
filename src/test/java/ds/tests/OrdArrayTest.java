package ds.tests;

import static ds.ArrayUtils.*;
import static ds.tests.TestConstants.*;
import static ds.tests.TestData.*;
import static ds.tests.TestUtils.*;
import static org.joor.Reflect.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import ds.IArray;
import ds.OrdArray;
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
@DisplayName("OrdArrayTest")
class OrdArrayTest {

  @Nested
  class InsertTests {
    @ParameterizedTest
    @CsvSource(INIT_DATA)
    @DisplayName("OrdArrayTest.insertDuplicate")
    void insertDuplicate(@AggregateWith(OrdArrayArgumentsAggregator.class) IArray arr) {
      assertTrue(6 == arr.insert(66L) && isSorted(arr), "Index 6 expected");
    }

    @ParameterizedTest
    @CsvSource(INIT_DUPLICATE_DATA)
    @DisplayName("OrdArrayTest.insertDuplicateElements")
    void insertDuplicateElements(@AggregateWith(OrdArrayArgumentsAggregator.class) IArray arr) {
      assertTrue(21 == arr.count() && isSorted(arr), "21 sorted elements expected");
    }

    @ParameterizedTest
    @CsvSource(INIT_DATA)
    @DisplayName("OrdArrayTest.testInsert")
    void testInsert(@AggregateWith(OrdArrayArgumentsAggregator.class) IArray arr) {
      assertTrue(TEN == arr.count() && isSorted(arr), TEN + " elements inserted and sorted.");
    }

    @ParameterizedTest
    @CsvSource(INIT_DATA)
    @DisplayName("OrdArrayTest.testInsertAtStartExists")
    void testInsertAtStartExists(@AggregateWith(OrdArrayArgumentsAggregator.class) IArray arr) {
      int count = arr.count();
      long val = ZERO;
      int index = arr.findIndex(val);
      int insertIndex = arr.insert(val);
      assertTrue(
          insertIndex >= index
              && insertIndex <= index + 1
              && arr.count() == count + 1
              && isSorted(arr),
          "11 elements expected, indexes 0 or 1 expected.");
    }

    @ParameterizedTest
    @CsvSource(INIT_DATA)
    @DisplayName("OrdArrayTest.testInsertAtEndExists")
    void testInsertAtEndExists(@AggregateWith(OrdArrayArgumentsAggregator.class) IArray arr) {
      int count = arr.count();
      long val = 99L;
      int index = arr.findIndex(val);
      int insertIndex = arr.insert(val);
      assertTrue(
          insertIndex >= index
              && insertIndex <= index + 1
              && arr.count() == count + 1
              && isSorted(arr),
          "11 elements expected, indexes 9 or 10 expected.");
    }

    @ParameterizedTest
    @CsvSource(INIT_DATA)
    @DisplayName("OrdArrayTest.testInsertAtEnd")
    void testInsertAtEnd(@AggregateWith(OrdArrayArgumentsAggregator.class) IArray arr) {
      int count = arr.count();
      long val = HUNDRED;
      int insertIndex = arr.insert(val);
      assertTrue(
          insertIndex == count && arr.count() == count + 1 && isSorted(arr),
          () -> (count + 1) + " elements expected, index " + count + " expected.");
    }

    @ParameterizedTest
    @CsvSource(INIT_DATA)
    @DisplayName("OrdArrayTest.testInsertAtStart")
    void testInsertAtStart(@AggregateWith(OrdArrayArgumentsAggregator.class) IArray arr) {
      int count = arr.count();
      long val = -1L;
      int insertIndex = arr.insert(val);
      assertTrue(
          insertIndex == 0 && arr.count() == count + 1 && isSorted(arr),
          "11 elements expected, index 0 expected.");
    }

    @SuppressWarnings("PMD.DataflowAnomalyAnalysis")
    @ParameterizedTest
    @CsvSource(INIT_SORTED_DATA)
    @DisplayName("OrdArrayTest.testInsertSorted")
    void testInsertSorted(@AggregateWith(OrdArrayArgumentsAggregator.class) IArray arr) {
      int count = arr.count();
      int res = arr.insert(99L);
      boolean sorted = isSorted(arr);
      assertTrue(
          res == 8 && arr.count() == count + 1 && sorted, "Sorted and insert at 8 expected.");
    }

    @SuppressWarnings("PMD.DataflowAnomalyAnalysis")
    @ParameterizedTest
    @CsvSource(INIT_ALL_SAME_DATA)
    @DisplayName("OrdArrayTest.testInsertAllSameSorted")
    void testInsertAllSameSorted(@AggregateWith(OrdArrayArgumentsAggregator.class) IArray arr) {
      int count = arr.count();
      int res = arr.insert(99L);
      boolean sorted = isSorted(arr);
      assertTrue(res == TEN && sorted && arr.count() == count + 1, "Insert must succeed.");
    }

    @ParameterizedTest
    @CsvSource(INIT_EXCEPTION_DATA)
    @DisplayName("OrdArrayTest.testException")
    void testException(@AggregateWith(OrdArrayArgumentsAggregator.class) IArray ordArray) {
      assertThrows(
          ArrayIndexOutOfBoundsException.class,
          () -> ordArray.insert(SCORE),
          "Array index out of bounds exception expected.");
    }
  }

  @Nested
  @DisplayName("OrdArrayTest.DeleteTests")
  class DeleteTests {

    @ParameterizedTest
    @CsvSource(INIT_DATA)
    @SuppressWarnings({"PMD.DataflowAnomalyAnalysis", "PMD.JUnitTestContainsTooManyAsserts"})
    @DisplayName("OrdArrayTest.DeleteTests.testDeleteTrue")
    void testDeleteTrue(@AggregateWith(OrdArrayArgumentsAggregator.class) IArray arr) {
      int count = arr.count();
      assertTrue(
          arr.delete(ZERO) && arr.delete(55L) && arr.delete(99L),
          "Elements 0, 55 and 99 must be deleted.");
      assertEquals(count - THREE, arr.count(), "Count must be " + (count - THREE) + ".");
      assertTrue(isSorted(arr), "Array must be sorted.");
    }

    @ParameterizedTest
    @CsvSource(INIT_DATA)
    @SuppressWarnings("PMD.DataflowAnomalyAnalysis")
    @DisplayName("OrdArrayTest.DeleteTests.testDeleteFalse")
    void testDeleteFalse(@AggregateWith(OrdArrayArgumentsAggregator.class) IArray arr) {
      int count = arr.count();
      assertFalse(
          arr.delete(12L) || arr.delete(6L) || arr.delete(5L) && arr.count() != count,
          "Elements 12, 6, 5 must not be found or deleted.");
    }

    @ParameterizedTest
    @CsvSource(INIT_DATA)
    @DisplayName("OrdArrayTest.DeleteTests.testDeleteStart")
    void testDeleteStart(@AggregateWith(OrdArrayArgumentsAggregator.class) IArray arr) {
      int count = arr.count();
      long searchKey = ZERO;
      assertTrue(
          arr.delete(searchKey) && arr.count() == count - 1 && isSorted(arr),
          () -> String.format(NOT_AVAILABLE, searchKey));
    }

    @ParameterizedTest
    @CsvSource(INIT_DATA)
    @DisplayName("OrdArrayTest.DeleteTests.testDeleteEnd")
    void testDeleteEnd(@AggregateWith(OrdArrayArgumentsAggregator.class) IArray arr) {
      int count = arr.count();
      long searchKey = 33L;
      assertTrue(
          arr.delete(searchKey) && arr.count() == count - 1 && isSorted(arr),
          () -> String.format(NOT_AVAILABLE, searchKey));
    }

    @ParameterizedTest
    @CsvSource(INIT_DATA)
    @DisplayName("OrdArrayTest.DeleteTests.testDeleteOverflow")
    void testDeleteOverflow(@AggregateWith(OrdArrayArgumentsAggregator.class) IArray arr) {
      long searchKey = ZERO;
      arr.delete(searchKey);
      int count = arr.count();
      assertFalse(
          arr.delete(searchKey) && arr.count() != count,
          () -> searchKey + " must not be available.");
    }

    @ParameterizedTest
    @CsvSource(INIT_FULL_DATA)
    @DisplayName("OrdArrayTest.DeleteTests.testDeleteEndArray")
    void testDeleteEndArray(@AggregateWith(OrdArrayArgumentsAggregator.class) IArray arr) {
      int count = arr.count();
      long searchKey = 33L;
      assertTrue(
          arr.delete(searchKey) && arr.count() == count - 1 && isSorted(arr),
          () -> String.format(NOT_AVAILABLE, searchKey));
    }
  }

  @Nested
  @DisplayName("OrdArrayTest.SyncTests")
  class SyncTests {
    @ParameterizedTest
    @CsvSource(INIT_DATA)
    @SuppressWarnings({"PMD.DataflowAnomalyAnalysis", "PMD.JUnitTestContainsTooManyAsserts"})
    @DisplayName("OrdArrayTest.SyncTests.testSyncDeleteTrue")
    void testSyncDeleteTrue(@AggregateWith(OrdArrayArgumentsAggregator.class) IArray arr) {
      int count = arr.count();
      assertTrue(
          arr.syncDelete(ZERO) && arr.syncDelete(55L) && arr.syncDelete(99L),
          "Elements 0, 55 and 99 must be deleted.");
      assertEquals(count - THREE, arr.count(), "Count must be " + (count - THREE) + ".");
      assertTrue(isSorted(arr), "Array must be sorted.");
    }

    @ParameterizedTest
    @CsvSource(INIT_DATA)
    @SuppressWarnings("PMD.DataflowAnomalyAnalysis")
    @DisplayName("OrdArrayTest.SyncTests.testSyncDeleteTrueIndividual")
    void testSyncDeleteTrueIndividual(
        @AggregateWith(OrdArrayArgumentsAggregator.class) IArray arr) {
      int count = arr.count();
      assertTrue(
          arr.syncDelete(ZERO) && arr.count() == count - 1 && isSorted(arr),
          "Element 0 must not be found.");
    }

    @ParameterizedTest
    @CsvSource(INIT_DATA)
    @SuppressWarnings("PMD.DataflowAnomalyAnalysis")
    @DisplayName("OrdArrayTest.SyncTests.testSyncDeleteFalse")
    void testSyncDeleteFalse(@AggregateWith(OrdArrayArgumentsAggregator.class) IArray arr) {
      int count = arr.count();
      assertFalse(
          arr.syncDelete(12L) || arr.syncDelete(6L) || arr.syncDelete(5L) && arr.count() != count,
          "Elements 12, 6, 5 must not be found or deleted.");
    }

    @ParameterizedTest
    @CsvSource(INIT_DATA)
    @SuppressWarnings("PMD.DataflowAnomalyAnalysis")
    @DisplayName("OrdArrayTest.SyncTests.testSyncDeleteFalseIndividual")
    void testSyncDeleteFalseIndividual(
        @AggregateWith(OrdArrayArgumentsAggregator.class) IArray arr) {
      int count = arr.count();
      assertFalse(arr.syncDelete(12L) && arr.count() != count, "Elements 12 not found or deleted.");
    }

    @ParameterizedTest
    @CsvSource(INIT_DATA)
    @DisplayName("OrdArrayTest.SyncTests.insertSyncDuplicate")
    void insertSyncDuplicate(@AggregateWith(OrdArrayArgumentsAggregator.class) IArray arr) {
      assertTrue(6 == arr.syncInsert(66L) && isSorted(arr), "7 elements expected.");
    }
  }

  @Nested
  @DisplayName("OrdArrayTest.ConstructorTests")
  class ConstructorTests {
    @SuppressWarnings("PMD.JUnitTestContainsTooManyAsserts")
    @Test
    @DisplayName("OrdArrayTest.ConstructorTests.testConstructorParameterNegative")
    void testConstructorParameterNegative() {
      IllegalArgumentException iae =
          assertThrows(
              IllegalArgumentException.class,
              () -> new OrdArray(-1),
              "IllegalArgumentException expected for " + -1);
      Optional<String> msg = Optional.ofNullable(iae.getMessage());
      String val = msg.orElse("");
      assertTrue(val.contains("-1"), "Parameter -1 expected");
    }

    @Test
    @DisplayName("OrdArrayTest.ConstructorTests.testConstructorParameterOK")
    void testConstructorParameterOK() {
      IArray arr = new OrdArray(TEN);
      assertEquals(TEN, arr.get().length, "Length " + TEN + " expected.");
    }

    @Test
    @DisplayName("OrdArrayTest.ConstructorTests.testEmptyConstructor")
    void testEmptyConstructor() {
      IArray arr = new OrdArray();
      boolean strict = (boolean) on(arr).get(STRICT);
      assertTrue(
          arr.get().length == HUNDRED && !strict,
          "Length " + HUNDRED + " and strict false expected.");
    }

    @SuppressWarnings("PMD.JUnitTestContainsTooManyAsserts")
    @Test
    @DisplayName("OrdArrayTest.ConstructorTests.testConstructorParameterZero")
    void testConstructorParameterZero() {
      IllegalArgumentException iae =
          assertThrows(
              IllegalArgumentException.class,
              () -> new OrdArray(0),
              "IllegalArgumentException expected for " + 0);
      Optional<String> msg = Optional.ofNullable(iae.getMessage());
      String val = msg.orElse("");
      assertTrue(val.contains("0"), "Parameter 0 expected");
    }
  }

  @Nested
  @DisplayName("OrdArrayTest.ModCountTests")
  class ModCountTests {
    @Test
    @DisplayName("OrdArrayTest.ModCountTests.testInsertModCount")
    void testInsertModCount() {
      IArray arr = new OrdArray(HUNDRED);
      int count = arr.count();
      int modCount = getModCount(arr);
      arr.insert(TEN);
      int newModCount = getModCount(arr);
      assertTrue(
          modCount < newModCount && newModCount == 1 && arr.count() == count + 1,
          "modCount must be  incremented.");
    }

    @Test
    @DisplayName("OrdArrayTest.ModCountTests.testClearModCount")
    void testClearModCount() {
      IArray arr = new OrdArray(HUNDRED);
      arr.insert(TEN);
      int modCount = getModCount(arr);
      arr.clear();
      int newModCount = getModCount(arr);
      assertTrue(
          modCount < newModCount && newModCount == 2 && arr.count() == 0,
          "modCount must be  incremented.");
    }

    @Test
    @DisplayName("OrdArrayTest.ModCountTests.testClearEmptyModCount")
    void testClearEmptyModCount() {
      IArray arr = new OrdArray(HUNDRED);
      int modCount = getModCount(arr);
      arr.clear();
      int newModCount = getModCount(arr);
      assertTrue(
          modCount == newModCount && modCount == 0 && arr.count() == 0,
          "modCount must not be incremented.");
    }

    @Test
    @DisplayName("OrdArrayTest.ModCountTests.testDeleteModCount")
    void testDeleteModCount() {
      IArray arr = new OrdArray(HUNDRED);
      arr.insert(TEN);
      int modCount = getModCount(arr);
      arr.delete(TEN);
      int newModCount = getModCount(arr);
      assertTrue(
          modCount < newModCount && newModCount == 2 && arr.count() == 0,
          "modCount must be  incremented.");
    }

    @Test
    @DisplayName("OrdArrayTest.ModCountTests.testDeleteNotFoundModCount")
    void testDeleteNotFoundModCount() {
      IArray arr = new OrdArray(HUNDRED);
      int count = arr.count();
      int modCount = getModCount(arr);
      arr.delete(TEN);
      int newModCount = getModCount(arr);
      assertTrue(
          modCount == newModCount && modCount == 0 && arr.count() == count,
          "modCount must not be incremented.");
    }
  }

  @Nested
  @DisplayName("OrdArrayTest.MiscTests")
  class MiscTests {
    @ParameterizedTest
    @CsvSource(INIT_DATA)
    @DisplayName("OrdArrayTest.MiscTests.testClear")
    void testClear(@AggregateWith(OrdArrayArgumentsAggregator.class) IArray arr) {
      int origCount = arr.count();
      arr.clear();
      long[] copy = new long[origCount];
      long[] origTrunc = Arrays.copyOf(arr.get(), origCount);
      assertTrue(
          0 == arr.count() && Arrays.equals(copy, origTrunc), () -> "Array must be cleared.");
    }

    @Test
    @DisplayName("OrdArrayTest.MiscTests.testClearEmpty")
    void testClearEmpty() {
      IArray arr = new OrdArray(HUNDRED);
      arr.clear();
      long[] copy = new long[HUNDRED];
      assertTrue(0 == arr.count() && Arrays.equals(arr.get(), copy), () -> "Array must be clear.");
    }

    @ParameterizedTest
    @CsvSource(INIT_DATA)
    @DisplayName("OrdArrayTest.MiscTests.testGet")
    void testGet(@AggregateWith(OrdArrayArgumentsAggregator.class) IArray arr) {
      long[] vals = arr.get();
      assertTrue(
          vals != null && vals.length == HUNDRED, "Non-Null array and length " + HUNDRED + ".");
    }

    @ParameterizedTest
    @CsvSource(INIT_DATA)
    @DisplayName("OrdArrayTest.MiscTests.testExtentArray")
    void testExtentArray(@AggregateWith(OrdArrayArgumentsAggregator.class) IArray arr) {
      long[] vals = arr.getExtentArray();
      assertTrue(vals != null && vals.length == TEN, "Non-Null array and length " + TEN + ".");
    }

    @Test
    @DisplayName("OrdArrayTest.MiscTests.testExtentArrayEmpty")
    void testExtentArrayEmpty() {
      IArray arr = new OrdArray();
      long[] vals = arr.getExtentArray();
      assertTrue(vals != null && vals.length == 0, "Non-Null array and length zero.");
    }

    @ParameterizedTest
    @CsvSource(INIT_DATA)
    @DisplayName("OrdArrayTest.MiscTests.testCountZero")
    void testCountZero(@AggregateWith(OrdArrayArgumentsAggregator.class) IArray arr) {
      arr.clear();
      assertEquals(0, arr.count(), "Count must be zero!");
    }

    @ParameterizedTest
    @CsvSource(INIT_DATA)
    @DisplayName("OrdArrayTest.MiscTests.testCountPositive")
    void testCountPositive(@AggregateWith(OrdArrayArgumentsAggregator.class) IArray arr) {
      assertEquals(TEN, arr.count(), "Count must be 10!");
    }
  }

  @Nested
  @DisplayName("OrdArrayTest.FindTests")
  class FindTests {

    @ParameterizedTest
    @CsvSource(INIT_DATA)
    @DisplayName("OrdArrayTest.FindTests.testFindIndexFalse")
    void testFindIndexFalse(@AggregateWith(OrdArrayArgumentsAggregator.class) IArray arr) {
      long searchKey = 35L;
      assertEquals(-4, arr.findIndex(searchKey) + 1, () -> searchKey + " not available.");
    }

    @ParameterizedTest
    @CsvSource(INIT_DATA)
    @DisplayName("OrdArrayTest.FindTests.testFindFalse")
    void testFindFalse(@AggregateWith(OrdArrayArgumentsAggregator.class) IArray arr) {
      long searchKey = 35L;
      assertFalse(arr.find(searchKey), () -> searchKey + " must not be available.");
    }

    @ParameterizedTest
    @CsvSource(INIT_DATA)
    @DisplayName("OrdArrayTest.FindTests.testFindIndexTrue")
    void testFindIndexTrue(@AggregateWith(OrdArrayArgumentsAggregator.class) IArray arr) {
      long searchKey = 11L;
      assertEquals(1, arr.findIndex(searchKey), () -> String.format(NOT_AVAILABLE, searchKey));
    }

    @ParameterizedTest
    @CsvSource(INIT_DATA)
    @DisplayName("OrdArrayTest.FindTests.testFindIndexStart")
    void testFindIndexStart(@AggregateWith(OrdArrayArgumentsAggregator.class) IArray arr) {
      long searchKey = ZERO;
      assertEquals(0, arr.findIndex(searchKey), () -> String.format(NOT_AVAILABLE, searchKey));
    }

    @ParameterizedTest
    @CsvSource(INIT_DATA)
    @DisplayName("OrdArrayTest.FindTests.testFindIndexStartTrue")
    void testFindIndexStartTrue(@AggregateWith(OrdArrayArgumentsAggregator.class) IArray arr) {
      long searchKey = ZERO;
      assertTrue(
          arr.find(searchKey) && arr.findIndex(searchKey) == 0,
          () -> String.format(NOT_AVAILABLE, searchKey));
    }

    @ParameterizedTest
    @CsvSource(INIT_DATA)
    @DisplayName("OrdArrayTest.FindTests.testFindIndexEndTrue")
    void testFindIndexEndTrue(@AggregateWith(OrdArrayArgumentsAggregator.class) IArray arr) {
      long searchKey = 99L;
      assertTrue(
          arr.find(searchKey) && arr.findIndex(searchKey) == 9,
          () -> String.format(NOT_AVAILABLE, searchKey));
    }

    @ParameterizedTest
    @CsvSource(INIT_DATA)
    @DisplayName("OrdArrayTest.FindTests.testFindIndexEnd")
    void testFindIndexEnd(@AggregateWith(OrdArrayArgumentsAggregator.class) IArray arr) {
      long searchKey = 99L;
      assertEquals(9, arr.findIndex(searchKey), () -> String.format(NOT_AVAILABLE, searchKey));
    }

    @ParameterizedTest
    @CsvSource(INIT_DATA)
    @DisplayName("OrdArrayTest.FindTests.testFindTrue")
    void testFindTrue(@AggregateWith(OrdArrayArgumentsAggregator.class) IArray arr) {
      long searchKey = 11L;
      assertTrue(arr.find(searchKey), () -> String.format(NOT_AVAILABLE, searchKey));
    }

    @ParameterizedTest
    @CsvSource(INIT_SEQ_DATA)
    @DisplayName("OrdArrayTest.FindTests.testFindSeqBefore")
    void testFindSeqBefore(@AggregateWith(OrdArrayArgumentsAggregator.class) IArray arr) {
      long searchKey = 14L;
      assertTrue(
          arr.find(searchKey) && arr.findIndex(searchKey) == THREE,
          () -> String.format(NOT_AVAILABLE, searchKey));
    }

    @ParameterizedTest
    @CsvSource(INIT_SEQ_DATA)
    @DisplayName("OrdArrayTest.FindTests.testFindSeqAfter")
    void testFindSeqAfter(@AggregateWith(OrdArrayArgumentsAggregator.class) IArray arr) {
      long searchKey = 16L;
      assertTrue(
          arr.find(searchKey) && arr.findIndex(searchKey) == 5,
          () -> String.format(NOT_AVAILABLE, searchKey));
    }

    @ParameterizedTest
    @CsvSource(INIT_SEQ_DATA)
    @DisplayName("OrdArrayTest.FindTests.testFindIndexOverflow")
    void testFindIndexOverflow(@AggregateWith(OrdArrayArgumentsAggregator.class) IArray arr) {
      long searchKey = ZERO;
      arr.delete(searchKey);
      assertEquals(-1, arr.findIndex(searchKey), () -> searchKey + " must not be available.");
    }

    @Test
    @DisplayName("OrdArrayTest.FindTests.testFindEmpty")
    void testFindEmpty() {
      IArray arr = new OrdArray(TEN);
      long searchKey = ZERO;
      assertEquals(-1, arr.findIndex(searchKey), () -> searchKey + " must not be available.");
    }
  }

  @Nested
  @DisplayName("OrdArrayTest.ToStringTests")
  class ToStringTests {
    @ParameterizedTest
    @CsvSource(INIT_TOSTRING_DATA)
    @DisplayName("OrdArrayTest.ToStringTests.testToString")
    void testToString(@AggregateWith(OrdArrayArgumentsAggregator.class) IArray arr) {
      String lineSeparator = System.lineSeparator();
      StringBuilder sb = new StringBuilder();
      sb.append(OrdArray.class.getName())
          .append(lineSeparator)
          .append("nElems = ")
          .append(THREE)
          .append(lineSeparator)
          .append("44 77 99 ");
      assertEquals(sb.toString(), arr.toString(), "Strings must be equal.");
    }

    @ParameterizedTest
    @CsvSource(INIT_DATA)
    @DisplayName("OrdArrayTest.ToStringTests.testToStringSpan")
    void testToStringSpan(@AggregateWith(OrdArrayArgumentsAggregator.class) IArray arr) {
      String lineSeparator = System.lineSeparator();
      StringBuilder sb = new StringBuilder();
      sb.append(OrdArray.class.getName())
          .append(lineSeparator)
          .append("nElems = ")
          .append(TEN)
          .append(lineSeparator)
          .append("0 11 22 33 44 55 66 77 88 99 ")
          .append(lineSeparator);
      assertEquals(sb.toString(), arr.toString(), "Strings must be equal.");
    }

    @Test
    @DisplayName("OrdArrayTest.ToStringTests.testToStringEmpty")
    void testToStringEmpty() {
      IArray arr = new OrdArray(TEN);
      String lineSeparator = System.lineSeparator();
      StringBuilder sb = new StringBuilder();
      sb.append(OrdArray.class.getName())
          .append(lineSeparator)
          .append("nElems = ")
          .append(0)
          .append(lineSeparator);
      assertEquals(sb.toString(), arr.toString(), "Strings must be equal.");
    }

    @ParameterizedTest
    @CsvSource(INIT_TOSTRING_DATA)
    @DisplayName("OrdArrayTest.ToStringTests.testDisplay")
    void testDisplay(@AggregateWith(OrdArrayArgumentsAggregator.class) IArray arr) {
      IArray ordArray = spy(arr);

      doAnswer(
              i -> {
                i.callRealMethod();
                return null;
              })
          .when(ordArray)
          .display();
      ordArray.display();
      doCallRealMethod().when(ordArray).display();
      ordArray.display();
      verify(ordArray, times(2)).display();
    }
  }

  @Nested
  @DisplayName("OrdArrayTest.EqualsVerifierTests")
  class EqualsVerifierTests {
    /** Added tests for code coverage completeness. */
    @Test
    @SuppressWarnings("PMD.JUnitTestsShouldIncludeAssert")
    @DisplayName("OrdArrayTest.EqualsVerifierTests.equalsContract")
    void equalsContract() {
      EqualsVerifier.forClass(OrdArray.class)
          .withIgnoredFields(MOD_COUNT, LOCK, STRICT)
          .withRedefinedSuperclass()
          .withRedefinedSubclass(OrdArrayExt.class)
          .withIgnoredAnnotations(NonNull.class)
          .verify();
    }

    @Test
    @SuppressWarnings("PMD.JUnitTestsShouldIncludeAssert")
    @DisplayName("OrdArrayTest.EqualsVerifierTests.leafNodeEquals")
    void leafNodeEquals() {
      EqualsVerifier.forClass(OrdArray.class)
          .withIgnoredFields(MOD_COUNT, LOCK, STRICT)
          .withRedefinedSuperclass()
          .withRedefinedSubclass(OrdArrayExt.class)
          .withIgnoredAnnotations(NonNull.class)
          .verify();
    }
  }

  static class OrdArrayExt extends OrdArray {
    OrdArrayExt(int size) {
      super(size);
    }

    @Override
    public boolean canEqual(Object obj) {
      return obj instanceof OrdArrayExt;
    }
  }
}
