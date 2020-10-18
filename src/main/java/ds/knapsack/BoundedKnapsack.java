package ds.knapsack;

import java.util.Arrays;
import java.util.List;

public class BoundedKnapsack extends Knapsack {

  public BoundedKnapsack(Item[] items, int capacity) {
    super(items, capacity);
  }

  @SuppressWarnings("PMD.LawOfDemeter")
  @Override
  public Solution solve() {
    List<Item> itemsList = Item.unpack(Arrays.asList(items));
    Item[] items = itemsList.toArray(new Item[0]);

    Solution solution = solve(items, capacity);

    itemsList = Item.pack(solution.getItems());

    return new Solution(itemsList, solution.getValue());
  }
}
