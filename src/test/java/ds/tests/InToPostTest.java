package ds.tests;

import static org.junit.jupiter.api.Assertions.*;

import ds.InToPost;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

@DisplayName("InToPostTest")
@TestInstance(Lifecycle.PER_CLASS)
@Execution(ExecutionMode.SAME_THREAD)
class InToPostTest {

  @Test
  @DisplayName("InToPostTest.testEmptyString")
  void testEmptyString() {
    String empty = "";
    InToPost r = new InToPost(empty);
    assertEquals("", r.translate(), "Empty string expected.");
  }

  @DisplayName("InToPostTest.testSingleLetter")
  @Test
  void testSingleLetter() {
    String letter = "M";
    InToPost r = new InToPost(letter);
    assertEquals(letter, r.translate(), "Letter expected.");
  }
}
