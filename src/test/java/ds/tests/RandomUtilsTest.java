package ds.tests;

import static ds.RandomUtils.randomInRange;
import static ds.tests.TestConstants.SCORE;
import static org.joor.Reflect.*;
import static org.junit.jupiter.api.Assertions.*;

import ds.RandomUtils;
import org.joor.ReflectException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

@DisplayName("RandomUtilsTest")
@TestInstance(Lifecycle.PER_CLASS)
@Execution(ExecutionMode.SAME_THREAD)
@SuppressWarnings({"PMD.LawOfDemeter", "PMD.DataflowAnomalyAnalysis"})
class RandomUtilsTest {

  @Test
  @DisplayName("RandomUtilsTest.testPrivateConstructor")
  void testPrivateConstructor() {
    assertThrows(
        ReflectException.class,
        () -> on(RandomUtils.class).create(),
        "Private constructor throws exception.");
  }

  @Test
  @DisplayName("RandomUtilsTest.testRandomInRange")
  void testRandomInRange() {
    int random = randomInRange(1, SCORE);
    assertTrue(1 <= random && random < SCORE, "Private constructor throws exception.");
  }

  @Test
  @DisplayName("RandomUtilsTest.testRandomInRangeSizeZero")
  void testRandomInRangeSizeZero() {
    assertThrows(
        IllegalArgumentException.class,
        () -> randomInRange(1, 1),
        "Range must be of minimal size 1.");
  }

  @Test
  @DisplayName("RandomUtilsTest.testRandomInRangeSizeOne")
  void testRandomInRangeSizeOne() {
    int random = randomInRange(0, 1);
    assertEquals(0, random, "Value must be zero.");
  }
}
