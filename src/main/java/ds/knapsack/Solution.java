package ds.knapsack;

import ds.Generated;
import java.util.List;

public class Solution {

  // list of items to put in the bag to have the maximal value
  final List<Item> items;
  // maximal value possible
  final int value;

  public Solution(List<Item> items, int value) {
    this.items = items;
    this.value = value;
  }

  @Generated
  @SuppressWarnings("all")
  public void display() {
    System.out.println(this);
  }

  @Generated
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    String lineSeparator = System.lineSeparator();
    if (items != null && !items.isEmpty()) {
      sb.append(lineSeparator)
          .append("Knapsack solution")
          .append(lineSeparator)
          .append("Value = ")
          .append(value)
          .append(lineSeparator)
          .append("Items to pick :")
          .append(lineSeparator);
      for (Item item : items) sb.append("- ").append(item).append(lineSeparator);
    }
    return sb.toString();
  }

  public int getValue() {
    return value;
  }
}
