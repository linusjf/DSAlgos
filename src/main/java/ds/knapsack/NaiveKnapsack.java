package ds.knapsack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

public class NaiveKnapsack extends AbstractKnapsack {

  final transient Map<WeightCountTuple, Integer> vals;

  public NaiveKnapsack(Item[] items, int capacity) {
    super(items, capacity);
    this.vals = new HashMap<>();
  }

  private void put(WeightCountTuple wct, Integer val) {
    vals.put(wct, val);
  }

  private boolean containsKey(WeightCountTuple wct) {
    return vals.containsKey(wct);
  }

  private Integer get(WeightCountTuple wct) {
    if (vals.containsKey(wct)) return vals.get(wct);
    throw new NoSuchElementException("No key : " + wct);
  }

  @Override
  public Solution solve() {
    List<Item> itemsSolution = new ArrayList<>(0);
    int maximalValue = knapsack(capacity, items.length, itemsSolution);
    return new Solution(itemsSolution, maximalValue);
  }

  private int knapsack(int totalWeight, int n, List<Item> solutionItems) {
    if (n == 0 || totalWeight == 0) return 0;

    WeightCountTuple wct = new WeightCountTuple(totalWeight, n - 1);
    if (containsKey(wct)) return get(wct);

    /**
     * If weight of nth item is more than capacity W, then this item cannot be included in the
     * optimal solution.
     */
    if (items[n - 1].weight > totalWeight) {
      solutionItems.remove(items[n - 1]);
      int val = knapsack(totalWeight, n - 1, solutionItems);
      put(wct, val);
      return val;
    }

    /** Return the maximum of two cases: (1) nth item included (2) not included * */
    int notIncludedVal = knapsack(totalWeight, n - 1, solutionItems);
    int newWeight = totalWeight - items[n - 1].weight;
    int remainder = knapsack(newWeight, n - 1, solutionItems);
    int includedVal = items[n - 1].value + remainder;
    boolean included = includedVal > notIncludedVal;
    if (included) {
      if (!solutionItems.contains(items[n - 1])) {
        solutionItems.add(items[n - 1]);
      }
      put(wct, includedVal);
      return includedVal;
    } else {
      solutionItems.remove(items[n - 1]);
      put(wct, notIncludedVal);
      return notIncludedVal;
    }
  }
}
