package ds.tests;

import static ds.AssertionUtils.*;
import static ds.tests.TestConstants.*;
import static org.joor.Reflect.*;
import static org.junit.jupiter.api.Assertions.*;

import ds.AssertionUtils;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.joor.ReflectException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

@DisplayName("AssertionUtilsTest")
@TestInstance(Lifecycle.PER_CLASS)
@Execution(ExecutionMode.SAME_THREAD)
@SuppressWarnings("PMD.LawOfDemeter")
class AssertionUtilsTest {

  @Test
  @DisplayName("AssertionUtilsTest.testPrivateConstructor")
  void testPrivateConstructor() {
    assertThrows(
        ReflectException.class,
        () -> on(AssertionUtils.class).create(),
        "Private constructor throws exception.");
  }

  @Test
  @DisplayName("AssertionUtilsTest.testInequality")
  void testInequality() {
    assertThrows(
        AssertionError.class, () -> assertEquality(SCORE, MYRIAD), "Inequality throws exception.");
  }

  @Test
  @DisplayName("AssertionUtilsTest.testServiceTerminated")
  void testServiceTerminated() {
    ExecutorService service =
        Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() + 1);
    assertThrows(
        AssertionError.class,
        () -> assertServiceTerminated(service),
        "Service not terminated throws exception.");
  }
}
