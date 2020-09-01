package ds.tests;

import static org.junit.jupiter.api.Assertions.*;

import ds.IArray;
import ds.OrdArray;
import java.util.stream.LongStream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.RepetitionInfo;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.junit.jupiter.api.parallel.ResourceAccessMode;
import org.junit.jupiter.api.parallel.ResourceLock;

@TestInstance(Lifecycle.PER_CLASS)
@Execution(ExecutionMode.CONCURRENT)
@SuppressWarnings("PMD.LawOfDemeter")
@DisplayName("OrdArrayRepeatTest")
class OrdArrayRepeatTest {
  IArray array;

  OrdArrayRepeatTest() {
    array = new OrdArray(1000, true);
    LongStream nos = LongStream.rangeClosed(1L, 1000L).unordered();
    nos.forEach(i -> array.insert(i));
    nos.close();
  }

  @RepeatedTest(1000)
  @ResourceLock(value = "hello", mode = ResourceAccessMode.READ_WRITE)
  @DisplayName("OrdArrayRepeatTest.repeatedTestWithRepetitionInfo")
  void repeatedTestWithRepetitionInfo(RepetitionInfo repetitionInfo) {
    int current = repetitionInfo.getCurrentRepetition();
    if (!array.delete(current)) fail(() -> "Element " + current + " not found.");
  }
}
