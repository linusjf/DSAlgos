package ds.tests;

import ds.IArray;
import ds.HighArray;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.aggregator.ArgumentsAggregationException;
import org.junit.jupiter.params.aggregator.ArgumentsAggregator;

class HighArrayArgumentsAggregator implements ArgumentsAggregator {

  public Object aggregateArguments(ArgumentsAccessor accessor, ParameterContext context)
      throws ArgumentsAggregationException {
    IArray array = new HighArray();
    return array;
  }
}
