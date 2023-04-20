package ds.knapsack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NaiveKnapsack extends AbstractKnapsack<Solution<Integer>> {

  public NaiveKnapsack(Item[] items, int capacity) {
    super(items, capacity);
  }

  private void put(Map<WeightCountTuple, Integer> vals, WeightCountTuple wct, Integer val) {
    vals.put(wct, val);
  }

  private boolean containsKey(Map<WeightCountTuple, Integer> vals, WeightCountTuple wct) {
    return vals.containsKey(wct);
  }

  @SuppressWarnings("all")
  private Integer get(Map<WeightCountTuple, Integer> vals, WeightCountTuple wct) {
    return vals.get(wct);
  }

  @SuppressWarnings("PMD.UseConcurrentHashMap")
  @Override
  public Solution<Integer> solve() {
    List<Item> itemsSolution = new ArrayList<>(0);
    Map<WeightCountTuple, Integer> vals = new HashMap<>();
    int maximalValue = knapsack(capacity, items.length, itemsSolution, vals);
    return new Solution<>(itemsSolution, maximalValue);
  }

  private int knapsack(
      int totalWeight, int n, List<Item> solutionItems, Map<WeightCountTuple, Integer> vals) {
    if (n == 0 || totalWeight == 0) return 0;

    WeightCountTuple wct = new WeightCountTuple(totalWeight, n - 1);
    if (containsKey(vals, wct)) return get(vals, wct);

    /**
     * If weight of nth item is more than capacity W, then this item cannot be included in the
     * optimal solution.
     */
    if (items[n - 1].weight > totalWeight) {
      //    solutionItems.remove(items[n - 1]);
      int val = knapsack(totalWeight, n - 1, solutionItems, vals);
      put(vals, wct, val);
      return val;
    }

    /** Return the maximum of two cases: (1) nth item included (2) not included * */
    int notIncludedVal = knapsack(totalWeight, n - 1, solutionItems, vals);
    int newWeight = totalWeight - items[n - 1].weight;
    int remainder = knapsack(newWeight, n - 1, solutionItems, vals);
    int includedVal = items[n - 1].value + remainder;
    boolean included = includedVal > notIncludedVal;
    if (included) {
      if (!solutionItems.contains(items[n - 1])) {
        solutionItems.add(items[n - 1]);
      }
      put(vals, wct, includedVal);
      return includedVal;
    } else {
      solutionItems.remove(items[n - 1]);
      put(vals, wct, notIncludedVal);
      return notIncludedVal;
    }
  }
}
