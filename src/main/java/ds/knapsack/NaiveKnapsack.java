package ds.knapsack;

import ds.Generated;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NaiveKnapsack {

  Item[] items;
  final int capacity;
  final transient Map<WeightCountTuple, Integer> vals;

  public NaiveKnapsack(Item[] items, int capacity) {
    this.items = items.clone();
    this.capacity = capacity;
    this.vals = new HashMap<>();
  }

  @Generated
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    String lineSeparator = System.lineSeparator();
    if (items != null && items.length > 0) {
      sb.append("Naive Knapsack problem: ")
          .append(lineSeparator)
          .append("Capacity : ")
          .append(capacity)
          .append(lineSeparator)
          .append("Items :")
          .append(lineSeparator);
      for (Item item : items) sb.append("- ").append(item).append(lineSeparator);
    }
    return sb.toString();
  }

  @Generated
  @SuppressWarnings("all")
  public void display() {
    System.out.println(this);
  }

  private void put(WeightCountTuple wct, Integer val) {
    vals.put(wct, val);
  }

  private boolean containsKey(WeightCountTuple wct) {
    return vals.containsKey(wct);
  }

  @SuppressWarnings("nullable")
  private Integer get(WeightCountTuple wct) {
    return vals.get(wct);
  }

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
