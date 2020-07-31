package ds.tests;

import java.util.stream.Stream;

interface ConcurrencyProvider {

  int CI_PROC_COUNT = 2;

  @SuppressWarnings("PMD.LawOfDemeter")
  default Stream<Integer> provideArraySize() {
    Runtime rt = Runtime.getRuntime();
    int processors = rt.availableProcessors();
    if (processors <= CI_PROC_COUNT) return Stream.of(100_000);
    return Stream.of(10_000);
  }
}
