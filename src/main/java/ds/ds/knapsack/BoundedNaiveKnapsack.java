package ds.knapsack;

import ds.Generated;
import java.util.Arrays;
import java.util.List;

public class BoundedNaiveKnapsack extends NaiveKnapsack {
  final Item[] origItems;

  public BoundedNaiveKnapsack(Item[] items, int capacity) {
    super(unpack(items), capacity);
    this.origItems = items.clone();
  }

  private static Item[] unpack(Item... items) {
    List<Item> itemsList = Item.unpack(Arrays.asList(items.clone()));
    return itemsList.toArray(new Item[0]);
  }

  @SuppressWarnings("PMD.LawOfDemeter")
  @Override
  public Solution<Integer> solve() {
    Solution<Integer> solution = super.solve();
    List<Item> itemsList = Item.pack(solution.getItems());
    return new Solution<>(itemsList, solution.getValue());
  }

  @SuppressWarnings("all")
  @Generated
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    String lineSeparator = System.lineSeparator();
    if (origItems != null && origItems.length > 0) {
      sb.append(getClass().getSimpleName())
          .append(" problem: ")
          .append(lineSeparator)
          .append("Capacity : ")
          .append(capacity)
          .append(lineSeparator)
          .append("Items :")
          .append(lineSeparator);
      for (Item item : origItems) sb.append("- ").append(item).append(lineSeparator);
    }
    return sb.toString();
  }
}
