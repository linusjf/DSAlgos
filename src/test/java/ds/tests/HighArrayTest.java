package ds.tests;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import ds.HighArray;
import java.util.logging.Logger;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.BeforeAll;
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

  @BeforeAll
  @SuppressWarnings("checkstyle:magicnumber")
  public void initialise() {
    // create the array
    arr = new HighArray(100);
  }

  @Test
  @Order(1)
  @SuppressWarnings("checkstyle:magicnumber")
  public void testInsert() {
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
  @Order(4)
  @SuppressWarnings("checkstyle:magicnumber")
  public void testFindIndexFalse() {
    long searchKey = 35L;
    // search for item
    assertEquals(arr.findIndex(searchKey), -1, () -> "Key " + searchKey + " not available");
  }

  @Test
  @Order(4)
  @SuppressWarnings("checkstyle:magicnumber")
  public void testFindFalse() {
    long searchKey = 35L;
    // search for item
    assertFalse(arr.find(searchKey), () -> "Key " + searchKey + " not available");
  }

  @Test
  @Order(4)
  @SuppressWarnings("checkstyle:magicnumber")
  public void testFindIndexTrue() {
    long searchKey = 11L;
    // search for item
    assertTrue(arr.findIndex(searchKey) >= 0, () -> "Key " + searchKey + " available");
  }

  @Test
  @Order(4)
  @SuppressWarnings("checkstyle:magicnumber")
  public void testFindTrue() {
    long searchKey = 11L;
    // search for item
    assertTrue(arr.find(searchKey), () -> "Key " + searchKey + " available");
  }

  @Test
  @Order(2)
  @SuppressWarnings("checkstyle:magicnumber")
  public void testDeleteTrue() {
    LOGGER.info(() -> arr.toString());
    assertTrue(
        () -> arr.delete(00L) && arr.delete(55L) && arr.delete(99L),
        () -> "Elements 00, 55, 99 deleted");
  }

  @Test
  @Order(3)
  @SuppressWarnings("checkstyle:magicnumber")
  public void testDeleteFalse() {
    LOGGER.info(() -> arr.toString());
    assertFalse(
        () -> arr.delete(00L) || arr.delete(55L) || arr.delete(99L),
        () -> "Elements 00, 55, 99 deleted");
  }

  @Test
  @Order(5)
  @SuppressWarnings("checkstyle:magicnumber")
  public void testClear() {
    arr.clear();
    assertEquals(arr.count(), 0, () -> "Array cleared");
  }

  @Test
  @Order(6)
  @SuppressWarnings("checkstyle:magicnumber")
  public void testToString() {
    // insert 3 items
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
  public void testDisplay() {
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
  public void testException() {
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
  public void equalsContract() {
    EqualsVerifier.forClass(HighArray.class)
        .withRedefinedSuperclass()
        .withRedefinedSubclass(HighArrayExt.class)
        .verify();
  }

  @Test
  public void leafNodeEquals() {

    EqualsVerifier.forClass(HighArray.class)
        .withRedefinedSuperclass()
        .withRedefinedSubclass(HighArrayExt.class)
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
