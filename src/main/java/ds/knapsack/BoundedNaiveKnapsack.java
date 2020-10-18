package ds.knapsack;

import java.util.Arrays;
import java.util.List;

public class BoundedNaiveKnapsack extends NaiveKnapsack {

  public BoundedNaiveKnapsack(Item[] items, int capacity) {
    super(items, capacity);
  }

  @SuppressWarnings("PMD.LawOfDemeter")
  @Override
  public Solution solve() {
    List<Item> itemsList = Item.unpack(Arrays.asList(items));
    Item[] unpackedItems = itemsList.toArray(new Item[0]);
    // save original items
    Item[] origItems = this.items;
    // set items to unpacked items
    this.items = unpackedItems.clone();

    Solution solution = super.solve();

    itemsList = Item.pack(solution.getItems());

    // restore original items
    this.items = origItems;
    return new Solution(itemsList, solution.getValue());
  }
}
