package ds.tests;

import java.util.stream.Stream;

public class BaseConcurrencyTest {
  static Stream<Integer> provideArraySize() {
    return Stream.of(20_000);
  }
}
