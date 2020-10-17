package ds.tests;

import static org.junit.jupiter.api.Assertions.*;

import ds.knapsack.Item;
import ds.knapsack.Knapsack;
import ds.knapsack.NaiveKnapsack;
import ds.knapsack.Solution;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

@SuppressWarnings("PMD.LawOfDemeter")
@DisplayName("KnapsackTest")
@TestInstance(Lifecycle.PER_CLASS)
@Execution(ExecutionMode.SAME_THREAD)
class KnapsackTest {
  private static final String VALUES_EQUAL = "Values must be equal.";
  private final Item[] items;

  KnapsackTest() {
    items =
        new Item[] {
          new Item("Elt1", 4, 12),
          new Item("Elt2", 2, 1),
          new Item("Elt3", 2, 2),
          new Item("Elt4", 1, 1),
          new Item("Elt5", 10, 4)
        };
  }

  @Test
  @DisplayName("KnapsackTest.testKnapsack")
  void testKnapsack() {
    Knapsack knapsack = new Knapsack(items, 15);
    knapsack.display();
    Solution solution = knapsack.solve();
    solution.display();
    assertEquals(15, solution.getValue(), VALUES_EQUAL);
  }

  @Test
  @DisplayName("KnapsackTest.testKnapsackNoItems")
  void testKnapsackNoItems() {
    Knapsack knapsack = new Knapsack(new Item[0], 15);
    knapsack.display();
    Solution solution = knapsack.solve();
    solution.display();
    assertEquals(0, solution.getValue(), VALUES_EQUAL);
  }

  @Test
  @DisplayName("KnapsackTest.testKnapsackOneItem")
  void testKnapsackOneItem() {
    Item[] oneItems = {new Item("Elt1", 4, 12)};
    Knapsack knapsack = new Knapsack(oneItems, 12);
    knapsack.display();
    Solution solution = knapsack.solve();
    solution.display();
    assertEquals(4, solution.getValue(), VALUES_EQUAL);
  }

  @Test
  @DisplayName("KnapsackTest.testKnapsackWeightZero")
  void testKnapsackWeightZero() {
    Knapsack knapsack = new Knapsack(items, 0);
    knapsack.display();
    Solution solution = knapsack.solve();
    solution.display();
    assertEquals(0, solution.getValue(), VALUES_EQUAL);
  }

  @Test
  @DisplayName("KnapsackTest.testNaiveKnapsack")
  void testNaiveKnapsack() {
    NaiveKnapsack knapsack = new NaiveKnapsack(items, 15);
    knapsack.display();
    Solution solution = knapsack.solve();
    solution.display();
    assertEquals(15, solution.getValue(), VALUES_EQUAL);
  }

  @Test
  @DisplayName("KnapsackTest.testNaiveKnapsackWeightZero")
  void testNaiveKnapsackWeightZero() {
    NaiveKnapsack knapsack = new NaiveKnapsack(items, 0);
    knapsack.display();
    Solution solution = knapsack.solve();
    solution.display();
    assertEquals(0, solution.getValue(), VALUES_EQUAL);
  }

  @Test
  @DisplayName("KnapsackTest.testNaiveKnapsackNoItems")
  void testNaiveKnapsackNoItems() {
    NaiveKnapsack knapsack = new NaiveKnapsack(new Item[0], 15);
    knapsack.display();
    Solution solution = knapsack.solve();
    solution.display();
    assertEquals(0, solution.getValue(), VALUES_EQUAL);
  }
}
