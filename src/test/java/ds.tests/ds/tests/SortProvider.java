package ds.tests;

import java.util.stream.LongStream;

interface SortProvider {

  @SuppressWarnings("PMD.LawOfDemeter")
  default LongStream revRange(int from, int to) {
    return LongStream.rangeClosed(from, to).map(i -> to - i + from);
  }
}
