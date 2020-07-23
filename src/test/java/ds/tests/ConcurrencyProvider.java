package ds.tests;

import java.util.stream.Stream;

interface ConcurrencyProvider {
  default Stream<Integer> provideArraySize() {
    return Stream.of(30_000);
  }
}
