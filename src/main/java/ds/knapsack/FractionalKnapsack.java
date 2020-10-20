package ds.knapsack;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class FractionalKnapsack extends AbstractKnapsack {

  public FractionalKnapsack(Item[] items, int capacity) {
    super(items, capacity);
  }

  @Override
  public Solution<Double> solve() {
    List<Item> itemList = Arrays.asList(this.items.clone());
    Collections.sort(itemList);
    int solutionWeight = 0;
    int n = itemList.size();
    double profit = 0.0f;
    int diff = capacity - solutionWeight;
    for (int i = 0; diff > 0 && i < n; i++) {
      Item item = itemList.get(i);
      diff = capacity - solutionWeight;
      if (item.weight >= diff) {
        item.bounding = diff;
        solutionWeight = capacity;
        profit += item.bounding / item.weight * item.value;
        break;
      } else {
        item.bounding = item.weight;
        solutionWeight += item.bounding;
        profit += item.value;
      }
    }
    return new Solution<>(Arrays.asList(items), profit);
  }
}
