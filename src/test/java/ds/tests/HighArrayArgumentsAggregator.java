package ds.tests;

import ds.HighArray;
import ds.IArray;
import java.util.Optional;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.aggregator.ArgumentsAggregator;

class HighArrayArgumentsAggregator implements ArgumentsAggregator {

  @SuppressWarnings("PMD.LawOfDemeter")
  @Override
  public Object aggregateArguments(ArgumentsAccessor accessor, ParameterContext context) {
    Optional<Integer> sizeOptional = Optional.ofNullable(accessor.get(0, Integer.class));
    int size = sizeOptional.orElse(100);
    Optional<Boolean> strictOptional = Optional.ofNullable(accessor.get(1, Boolean.class));
    boolean strict = strictOptional.orElse(Boolean.FALSE);
    Object[] args = accessor.toArray();
    IArray array = new HighArray(size, strict);
    for (int i = 2; i < args.length; i++) array.insert(accessor.get(i, Long.class));
    return array;
  }
}
