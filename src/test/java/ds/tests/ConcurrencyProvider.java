package ds.tests;

import java.util.stream.Stream;

interface ConcurrencyProvider {
  default Stream<Integer> provideSyncArraySize() {
    Runtime rt = Runtime.getRuntime();
    int processors = rt.availableProcessors();
    if (processors <= 2) return Stream.of(100);
    return Stream.of(100);
  }

  default Stream<Integer> provideArraySize() {
    Runtime rt = Runtime.getRuntime();
    int processors = rt.availableProcessors();
    if (processors <= 2) return Stream.of(100_000);
    return Stream.of(10_000);
  }
}
