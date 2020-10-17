package ds.knapsack;

import ds.Generated;
import java.util.ArrayList;
import java.util.List;

public class NaiveKnapsack {

  final Item[] items;
  final int capacity;

  public NaiveKnapsack(Item[] items, int capacity) {
    this.items = items.clone();
    this.capacity = capacity;
  }

  @Generated
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    String lineSeparator = System.lineSeparator();
    if (items != null && items.length > 0) {
      sb.append("Knapsack problem: ")
          .append(lineSeparator)
          .append("Capacity : ")
          .append(capacity)
          .append(lineSeparator)
          .append("Items :")
          .append(lineSeparator);
      for (Item item : items) sb.append("- ").append(item);
    }
    return sb.toString();
  }

  @Generated
  @SuppressWarnings("all")
  public void display() {
    System.out.println(this);
  }

  public Solution solve() {
    List<Item> itemsSolution = new ArrayList<>(0);
    int maximalValue = knapsack(capacity, items.length, itemsSolution);
    return new Solution(itemsSolution, maximalValue);
  }

  private int knapsack(int totalWeight, int n, List<Item> solutionItems) {
    if (n == 0 || totalWeight == 0) return 0;

    // If weight of the nth item is more
    // than Knapsack capacity W, then
    // this item cannot be included in the optimal solution
    if (items[n - 1].weight > totalWeight) return knapsack(totalWeight, n - 1, solutionItems);
    // Return the maximum of two cases:
    // (1) nth item included
    // (2) not included
    else {
      int includedVal =
          items[n - 1].value + knapsack(totalWeight - items[n - 1].weight, n - 1, solutionItems);
      int notIncludedVal = knapsack(totalWeight, n - 1, solutionItems);
      if (includedVal > notIncludedVal) {
        solutionItems.add(items[n - 1]);
        return includedVal;
      } else return notIncludedVal;
    }
  }
}
