package ds.tests;

import java.util.stream.Stream;

public abstract class BaseConcurrencyTest {
  static Stream<Integer> provideArraySize() {
    return Stream.of(30_000);
  }
}
