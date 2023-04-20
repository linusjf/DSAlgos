package ds.knapsack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class FractionalKnapsack extends AbstractKnapsack<Solution<Double>> {

  public FractionalKnapsack(Item[] items, int capacity) {
    super(items, capacity);
  }

  @SuppressWarnings("PMD.LawOfDemeter")
  @Override
  public Solution<Double> solve() {
    List<Item> itemList = Arrays.asList(this.items.clone());
    Collections.sort(itemList);
    int n = itemList.size();
    double profit = 0.0f;
    int diff = capacity;
    int solutionWeight = 0;
    List<Item> solItems = new ArrayList<>();
    for (int i = 0; diff > 0 && i < n; i++) {
      Item item = itemList.get(i);
      solItems.add(item);
      if (item.weight > diff) {
        item.bounding = diff;
        profit += item.bounding / item.weight * item.value;
        solutionWeight = capacity;
      } else {
        item.bounding = item.weight;
        solutionWeight += item.bounding;
        profit += item.value;
      }
      diff = capacity - solutionWeight;
    }
    return new FractionalSolution<>(solItems, profit);
  }
}
