package ds.tests;

import static org.joor.Reflect.*;

import ds.IArray;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;

class OrdArrayArgumentsAggregatorUnsorted extends OrdArrayArgumentsAggregator {

  @SuppressWarnings("PMD.LawOfDemeter")
  @Override
  public Object aggregateArguments(ArgumentsAccessor accessor, ParameterContext context) {
    IArray array = (IArray) super.aggregateArguments(accessor, context);
    Object[] args = accessor.toArray();
    long[] a = new long[array.get().length - 2];
    for (int i = 2; i < args.length; i++) a[i - 2] = accessor.get(i, Long.class);
    on(array).set("a", a);
    on(array).set("sorted", false);
    on(array).set("dirty", true);
    return array;
  }
}
