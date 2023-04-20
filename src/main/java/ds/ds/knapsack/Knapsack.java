package ds.knapsack;

import java.util.ArrayList;
import java.util.List;

public class Knapsack extends AbstractKnapsack<Solution<Integer>> {

  public Knapsack(Item[] items, int capacity) {
    super(items, capacity);
  }

  // we write the solve algorithm
  protected static Solution<Integer> solveFor(Item[] items, int capacity) {
    int nbItems = items.length;
    // we use a matrix to store the max value at each n-th item
    int[][] matrix = new int[nbItems + 1][capacity + 1];

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
      if (res == matrix[i - 1][w]) continue;
      itemsSolution.add(items[i - 1]);
      // we remove item's value and weight
      res -= items[i - 1].value;
      w -= items[i - 1].weight;
    }
    return new Solution<>(itemsSolution, matrix[nbItems][capacity]);
  }

  @Override
  public Solution<Integer> solve() {
    return solveFor(this.items, this.capacity);
  }
}
