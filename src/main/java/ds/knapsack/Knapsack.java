package ds.knapsack;

import ds.Generated;
import java.util.ArrayList;
import java.util.List;

public class Knapsack {

  final Item[] items;
  final int capacity;

  public Knapsack(Item[] items, int capacity) {
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
      for (Item item : items) sb.append("- ").append(item).append(lineSeparator);
    }
    return sb.toString();
  }

  @Generated
  @SuppressWarnings("all")
  public void display() {
    System.out.println(this);
  }

  // we write the solve algorithm
  protected static Solution solveFor(Item[] items, int capacity) {
    int nbItems = items.length;
    // we use a matrix to store the max value at each n-th item
    int[][] matrix = new int[nbItems + 1][capacity + 1];

    // first line is initialized to 0
    for (int i = 0; i <= capacity; i++) matrix[0][i] = 0;

    // we iterate on items
    for (int i = 1; i <= nbItems; i++) {
      // we iterate on each capacity
      for (int j = 0; j <= capacity; j++) {
        if (items[i - 1].weight > j) matrix[i][j] = matrix[i - 1][j];
        else
          // we maximize value at this rank in the matrix
          matrix[i][j] =
              Math.max(
                  matrix[i - 1][j], matrix[i - 1][j - items[i - 1].weight] + items[i - 1].value);
      }
    }

    int res = matrix[nbItems][capacity];
    int w = capacity;
    List<Item> itemsSolution = new ArrayList<>();

    for (int i = nbItems; i > 0 && res > 0; i--) {
      if (res != matrix[i - 1][w]) {
        itemsSolution.add(items[i - 1]);
        // we remove item's value and weight
        res -= items[i - 1].value;
        w -= items[i - 1].weight;
      }
    }
    return new Solution(itemsSolution, matrix[nbItems][capacity]);
  }

  public Solution solve() {
    return solveFor(this.items, this.capacity);
  }
}
