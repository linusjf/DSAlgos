package ds.knapsack;

import java.util.ArrayList;
import java.util.List;

public class UnboundedKnapsack extends AbstractKnapsack<Solution<Integer>> {

  public UnboundedKnapsack(Item[] items, int capacity) {
    super(items, capacity);
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
    itemsList.add(items[chosenItem]);
    path(items, capacity - items[chosenItem].weight, dp, itemsList);
  }

  @SuppressWarnings("PMD.UnusedLocalVariable")
  @Override
  public Solution<Integer> solve() {
    int[] dp = new int[capacity + 1];
    List<Item> itemsList = new ArrayList<>(items.length);
    int i = 0;
    for (int ignored : dp) {
      for (Item item : items) {
        if (item.weight <= i) {
          int includedVal = dp[i - item.weight] + item.value;
          dp[i] = Math.max(includedVal, dp[i]);
        }
      }
      ++i;
    }
    path(items, capacity, dp, itemsList);
    itemsList = Item.pack(itemsList);
    return new Solution<>(itemsList, dp[capacity]);
  }
}
