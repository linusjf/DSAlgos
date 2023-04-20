package ds.tests;

import static org.junit.jupiter.api.Assertions.*;

import ds.QuickSelect;
import java.util.Arrays;
import java.util.Random;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

@SuppressWarnings("PMD.LawOfDemeter")
@DisplayName("QuickSelectTest")
@TestInstance(Lifecycle.PER_CLASS)
@Execution(ExecutionMode.SAME_THREAD)
class QuickSelectTest {

  private static final String EXPECTED = " expected.";

  @Test
  @DisplayName("QuickSelectTest.testEmptyArray")
  void testEmptyArray() {
    QuickSelect qs = new QuickSelect(new long[0]);
    assertThrows(
        IndexOutOfBoundsException.class,
        () -> qs.find(0),
        () -> "Index out of bounds exception" + EXPECTED);
  }

  @Test
  @DisplayName("QuickSelectTest.testSingleElementArray")
  void testSingleElementArray() {
    long[] arr = {23};
    QuickSelect qs = new QuickSelect(arr);
    assertEquals(23, qs.find(0), () -> 23 + EXPECTED);
  }

  @Test
  @DisplayName("QuickSelectTest.testTwoElementArray")
  void testTwoElementArray() {
    long[] arr = {24, 23};
    QuickSelect qs = new QuickSelect(arr);
    assertEquals(23, qs.find(0), () -> 23 + EXPECTED);
    assertEquals(24, qs.find(1), () -> 24 + EXPECTED);
  }

  @Test
  @DisplayName("QuickSelectTest.testThreeElementArray")
  void testThreeElementArray() {
    long[] arr = {23, 21, 24};
    QuickSelect qs = new QuickSelect(arr);
    assertEquals(23, qs.find(1), () -> 23 + EXPECTED);
  }

  @Test
  @DisplayName("QuickSelectTest.testEven")
  void testEven() {
    long[] arr = {23, 21, 20, 18, 17, 16, 0, 10, 9, 10};
    QuickSelect qs = new QuickSelect(arr.clone());
    Arrays.sort(arr);
    assertEquals(arr[5], qs.find(5), () -> arr[5] + EXPECTED);
  }

  @Test
  @DisplayName("QuickSelectTest.testOdd")
  void testOdd() {
    long[] arr = {23, 21, 20, 18, 18, 17, 16, 0, 10, 9, 10};
    QuickSelect qs = new QuickSelect(arr.clone());
    Arrays.sort(arr);
    assertEquals(arr[5], qs.find(5), () -> arr[5] + EXPECTED);
  }

  @Test
  @DisplayName("QuickSelectTest.testRandomEven")
  void testRandomEven() {
    long[] arr = new long[10_000];
    Random random = new Random();
    for (int i = 0; i < 10_000; i++) arr[i] = random.nextInt();
    QuickSelect qs = new QuickSelect(arr.clone());
    Arrays.sort(arr);
    long qsVal = arr[4999];
    assertEquals(qsVal, qs.find(4999), () -> qsVal + EXPECTED);
  }

  @Test
  @DisplayName("QuickSelectTest.testRandomOdd")
  void testRandomOdd() {
    long[] arr = new long[10_001];
    Random random = new Random();
    for (int i = 0; i < 10_001; i++) arr[i] = random.nextInt();
    QuickSelect qs = new QuickSelect(arr.clone());
    Arrays.sort(arr);
    long qsVal = arr[5000];
    assertEquals(qsVal, qs.find(5000), () -> qsVal + EXPECTED);
  }
}
