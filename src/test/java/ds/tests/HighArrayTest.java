package ds.tests;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import ds.HighArray;
import java.util.logging.Logger;
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
    arr.insert(77);
    arr.insert(99);
    arr.insert(44);
    arr.insert(55);
    arr.insert(22);
    arr.insert(88);
    arr.insert(11);
    arr.insert(00);
    arr.insert(66);
    arr.insert(33);
    assertEquals(arr.count(), 10, "10 elements inserted.");
  }

  @Test
  @Order(4)
  @SuppressWarnings("checkstyle:magicnumber")
  public void testFindIndexFalse() {
    int searchKey = 35;
    // search for item
    assertEquals(arr.findIndex(searchKey), -1, () -> "Key " + searchKey + " not available");
  }

  @Test
  @Order(4)
  @SuppressWarnings("checkstyle:magicnumber")
  public void testFindFalse() {
    int searchKey = 35;
    // search for item
    assertFalse(arr.find(searchKey), () -> "Key " + searchKey + " not available");
  }

  @Test
  @Order(4)
  @SuppressWarnings("checkstyle:magicnumber")
  public void testFindIndexTrue() {
    int searchKey = 11;
    // search for item
    assertTrue(arr.findIndex(searchKey) >= 0, () -> "Key " + searchKey + " available");
  }

  @Test
  @Order(4)
  @SuppressWarnings("checkstyle:magicnumber")
  public void testFindTrue() {
    int searchKey = 11;
    // search for item
    assertTrue(arr.find(searchKey), () -> "Key " + searchKey + " available");
  }

  @Test
  @Order(2)
  @SuppressWarnings("checkstyle:magicnumber")
  public void testDeleteTrue() {
    LOGGER.info(() -> arr.toString());
    assertTrue(
        () -> arr.delete(00) && arr.delete(55) && arr.delete(99),
        () -> "Elements 00, 55, 99 deleted");
  }

  @Test
  @Order(3)
  @SuppressWarnings("checkstyle:magicnumber")
  public void testDeleteFalse() {
    LOGGER.info(() -> arr.toString());
    assertFalse(
        () -> arr.delete(00) || arr.delete(55) || arr.delete(99),
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
    arr.insert(77);
    arr.insert(99);
    arr.insert(44);
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
}
