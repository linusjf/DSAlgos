package ds.knapsack;

import ds.Generated;
import ds.ISolve;
import java.util.ArrayList;
import java.util.List;

public class UnboundedKnapsack implements ISolve<Solution> {

  final Item[] items;
  final int capacity;

  public UnboundedKnapsack(Item[] items, int capacity) {
    this.items = items.clone();
    this.capacity = capacity;
  }

  private static void path(Item[] items, int capacity, int[] dp, List<Item> itemsList) {
    if (capacity == 0) return;
    int ans = 0;
    int chosenItem = -1;
    for (int j = 0; j < items.length; j++) {
      int index = capacity - items[j].weight;
      if (index >= 0) {
        int newAns = dp[index] + items[j].value;
        if (newAns > ans) {
          ans = newAns;
          chosenItem = j;
        }
      }
    }
    if (chosenItem != -1) {
      itemsList.add(items[chosenItem]);
      path(items, capacity - items[chosenItem].weight, dp, itemsList);
    }
  }

  @SuppressWarnings("PMD.UnusedLocalVariable")
  @Override
  public Solution solve() {
    int[] dp = new int[capacity + 1];

    List<Item> itemsList = new ArrayList<>(items.length);
    int i = 0;
    for (int dpVal : dp) {
      int j = 0;
      for (Item item : items) {
        if (item.weight <= i) {
          int includedVal = dp[i - item.weight] + item.value;
          if (includedVal > dp[i]) dp[i] = includedVal;
        }
        ++j;
      }
      ++i;
    }
    path(items, capacity, dp, itemsList);
    itemsList = Item.pack(itemsList);
    return new Solution(itemsList, dp[capacity]);
  }

  @Generated
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    String lineSeparator = System.lineSeparator();
    if (items != null && items.length > 0) {
      sb.append("Unbounded Knapsack problem: ")
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
}
