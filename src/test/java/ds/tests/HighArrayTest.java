package ds.tests;

import static org.junit.jupiter.api.Assertions.*;

import ds.HighArray;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

@TestInstance(Lifecycle.PER_CLASS)
class HighArrayTest {
  HighArray arr;

  @BeforeAll
  @SuppressWarnings("checkstyle:magicnumber")
  public void initialise() {
    // create the array
    arr = new HighArray(100);
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
  }

  @Test
  @SuppressWarnings("checkstyle:magicnumber")
  public void testFindIndex() {

    int searchKey = 35;
    // search for item
    assertEquals(arr.findIndex(searchKey), -1, () -> "Key " + searchKey + " not available");
  }

  @Test
  @SuppressWarnings("checkstyle:magicnumber")
  public void testFind() {

    int searchKey = 35;
    // search for item
    assertFalse(arr.find(searchKey), () -> "Key " + searchKey + " not available");
  }

  @Test
  @SuppressWarnings("checkstyle:magicnumber")
  public void testDelete() {

    assertTrue(
        () -> arr.delete(00) && arr.delete(55) && arr.delete(99),
        () -> "Elements 00, 55, 99 deleted");
  }
}
