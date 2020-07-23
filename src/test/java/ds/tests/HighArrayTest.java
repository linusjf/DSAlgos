package ds.tests;

import static ds.tests.TestConstants.NOT_AVAILABLE;
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

@TestInstance(Lifecycle.PER_CLASS)
@Execution(ExecutionMode.SAME_THREAD)
@SuppressWarnings("PMD.LawOfDemeter")
class HighArrayTest {

  IArray insertElements() {
    IArray arr = new HighArray(100);
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
    return arr;
  }

  private void insertElements(IArray arr) {
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
  }

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

    @Test
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
    @Test
    void testException() {
      IArray highArray = new HighArray(3);
      highArray.insert(2L);
      highArray.insert(11L);
      highArray.insert(21L);
      assertThrows(
          ArrayIndexOutOfBoundsException.class,
          () -> {
            highArray.insert(45L);
          });
    }

    @Test
    void testInsert() {
      IArray arr = insertElements();
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
    @Test
    @SuppressWarnings("PMD.DataflowAnomalyAnalysis")
    void testDeleteTrue() {
      IArray arr = insertElements();
      int count = arr.count();
      assertTrue(
          arr.delete(00L)
              && arr.delete(55L)
              && arr.delete(99L),
              "Elements 0, 55 and 99 must be deleted.");
      assertFalse(arr.find(00L)
              || arr.find(55L)
              || arr.find(99L),
              "Elements 0, 55 and 99 must not be found.");
      assertEquals(count - 3, arr.count(),
          "Three elements should have been deleted.");
    }

    @Test
    @SuppressWarnings("PMD.DataflowAnomalyAnalysis")
    void testDeleteFalse() {
      IArray arr = insertElements();
      int count = arr.count();
      assertFalse(
          arr.delete(12L)
              || arr.delete(6L)
              || arr.delete(5L),
          "Elements 12, 6, 5 are not expected!");
    }

    @Test
    void testDeleteEnd() {
      IArray arr = insertElements();
      int count = arr.count();
      long searchKey = 33L;
      assertTrue(
          arr.delete(searchKey) && arr.count() == count - 1,
          () -> String.format(NOT_AVAILABLE, searchKey));
    }

    @Test
    void testDeleteEndArray() {
      IArray arr = new HighArray(10);
      insertElements(arr);
      int count = arr.count();
      long searchKey = 33L;
      assertTrue(
          arr.delete(searchKey) && arr.count() == count - 1,
          () -> String.format(NOT_AVAILABLE, searchKey));
    }

    @Test
    void testDeleteOverflow() {
      IArray arr = insertElements();
      int count = arr.count();
      long searchKey = 0L;
      arr.delete(searchKey);
      assertFalse(
          arr.delete(searchKey) && arr.count() != count - 1, () -> searchKey + " still available");
    }

    @Test
    void testDeleteStart() {
      IArray arr = insertElements();
      int count = arr.count();
      long searchKey = 77L;
      assertTrue(
          arr.delete(searchKey) && arr.count() == count - 1,
          () -> String.format(NOT_AVAILABLE, searchKey));
    }
  }

  @Nested
  class SyncTests {
    @Test
    @SuppressWarnings("PMD.DataflowAnomalyAnalysis")
    void testSyncDeleteTrue() {
      IArray arr = insertElements();
      int count = arr.count();
      assertTrue(
          arr.syncDelete(00L)
              && arr.syncDelete(55L)
              && arr.syncDelete(99L),
              "Elements 0, 55 and 99 must be deleted.");
      assertFalse(arr.find(00L)
              || arr.find(55L)
              || arr.find(99L),
              "Elements 0, 55 and 99 must not be found.");
      assertEquals(count - 3, arr.count(),
          "Three elements should have been deleted.");
    }

    @Test
    @SuppressWarnings("PMD.DataflowAnomalyAnalysis")
    void testSyncDeleteTrueIndividual() {
      IArray arr = insertElements();
      int count = arr.count();
      assertTrue(arr.syncDelete(00L) && arr.count() == count - 1, "Element 0 not found.");
    }

    @Test
    @SuppressWarnings("PMD.DataflowAnomalyAnalysis")
    void testSyncDeleteFalseIndividual() {
      IArray arr = insertElements();
      int count = arr.count();
      assertFalse(arr.syncDelete(12L) && arr.count() == count, "Elements 12 found and deleted");
    }

    @Test
    @SuppressWarnings("PMD.DataflowAnomalyAnalysis")
    void testSyncDeleteFalse() {
      IArray arr = insertElements();
      int count = arr.count();
      assertFalse(
          arr.syncDelete(12L)
              || arr.syncDelete(6L)
              || arr.syncDelete(5L),
          "Elements 12, 6, 5 not expected!");
    }
  }

  @Nested
  class FindTests {
    @Test
    void testFindIndexFalse() {
      IArray arr = insertElements();
      long searchKey = 35L;
      assertEquals(-1, arr.findIndex(searchKey), () -> searchKey + " available");
    }

    @Test
    void testFindFalse() {
      IArray arr = insertElements();
      long searchKey = 35L;
      assertFalse(arr.find(searchKey), () -> searchKey + " available");
    }

    @Test
    void testFindIndex() {
      IArray arr = insertElements();
      long searchKey = 11L;
      assertEquals(6, arr.findIndex(searchKey), () -> String.format(NOT_AVAILABLE, searchKey));
    }

    @Test
    void testFindIndexTrue() {
      IArray arr = insertElements();
      long searchKey = 11L;
      assertTrue(
          arr.find(searchKey) && arr.findIndex(searchKey) == 6,
          () -> String.format(NOT_AVAILABLE, searchKey));
    }

    @Test
    void testFindIndexStart() {
      IArray arr = insertElements();
      long searchKey = 77L;
      assertEquals(0, arr.findIndex(searchKey), () -> String.format(NOT_AVAILABLE, searchKey));
    }

    @Test
    void testFindIndexStartTrue() {
      IArray arr = insertElements();
      long searchKey = 77L;
      assertTrue(
          arr.find(searchKey) && arr.findIndex(searchKey) == 0,
          () -> String.format(NOT_AVAILABLE, searchKey));
    }

    @Test
    void testFindIndexEnd() {
      IArray arr = insertElements();
      long searchKey = 33L;
      assertEquals(9, arr.findIndex(searchKey), () -> String.format(NOT_AVAILABLE, searchKey));
    }

    @Test
    void testFindIndexEndTrue() {
      IArray arr = insertElements();
      long searchKey = 33L;
      assertTrue(
          arr.find(searchKey) && arr.findIndex(searchKey) == 9,
          () -> String.format(NOT_AVAILABLE, searchKey));
    }

    @Test
    void testFindIndexOverflow() {
      IArray arr = insertElements();
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

    @Test
    void testFindTrue() {
      IArray arr = insertElements();
      long searchKey = 11L;
      assertTrue(
          arr.find(searchKey) && arr.findIndex(searchKey) == 6,
          () -> String.format(NOT_AVAILABLE, searchKey));
    }
  }

  @Nested
  class MiscTests {
    @Test
    void testGet() {
      IArray arr = insertElements();
      long[] vals = arr.get();
      assertTrue(vals != null && vals.length == 100, "Null array or length incorrect");
    }

    @Test
    void testCountZero() {
      IArray arr = new HighArray(10, true);
      assertEquals(0, arr.count(), "Count must be zero!");
    }

    @Test
    void testCountPositive() {
      IArray arr = insertElements();
      assertEquals(10, arr.count(), "Count must be 10!");
    }

    @Test
    void testClear() {
      IArray arr = insertElements();
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
    @Test
    void testToString() {
      IArray arr = new HighArray(10);
      arr.insert(77L);
      arr.insert(99L);
      arr.insert(44L);
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

    @Test
    void testToStringEmpty() {
      IArray arr = insertElements();
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

    @Test
    void testDisplay() {
      IArray arr = insertElements();
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
