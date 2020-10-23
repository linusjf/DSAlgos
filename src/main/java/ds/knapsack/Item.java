package ds.knapsack;

import static java.util.Objects.*;
import static ds.MathUtils.*;

import ds.Generated;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SuppressWarnings("PMD.ShortClassName")
public class Item implements Comparable {
  final String name;
  final int value;
  final int weight;
  transient double bounding = 1;

  public Item(String name, int value, int weight) {
    requireNonNull(name);
    requireGreaterThan(0, value);
    requireGreaterThan(0, weight);
    this.name = name;
    this.value = value;
    this.weight = weight;
  }

  public Item(String name, int value, int weight, int bounding) {
    this(name, value, weight);
    this.bounding = bounding;
  }

  public Item(Item src) {
    this(src.name, src.value, src.weight);
  }

  @Override
  public String toString() {
    return name + " [value = " + value + ", weight = " + weight + ", bounding = " + bounding + "]";
  }

  public static List<Item> pack(Collection<Item> items) {
    Set<Item> set = new HashSet<>(items);
    for (Item item : set) item.bounding = Collections.frequency(items, item);
    return List.of(set.toArray(new Item[0]));
  }

  public static List<Item> unpack(Collection<Item> items) {
    List<Item> unpackedItems = new ArrayList<>(items.size());
    for (Item item : items) {
      double bounding = item.bounding;
      for (int i = 0; i < bounding; i++) unpackedItems.add(new Item(item));
    }
    return unpackedItems;
  }

  @Override
  public int compareTo(Object item) {
    Item i2 = (Item) item;
    double rate1 = (double) value / (double) weight;
    double rate2 = (double) i2.value / (double) i2.weight;
    return rate1 > rate2 ? -1 : rate1 < rate2 ? 1 : 0;
  }

  @Generated
  @Override
  @SuppressWarnings("all")
  public boolean equals(final Object o) {
    if (o == this) return true;
    if (!(o instanceof Item)) return false;
    final Item other = (Item) o;
    if (!other.canEqual((Object) this)) return false;
    if (this.value != other.value) return false;
    if (this.weight != other.weight) return false;
    final Object this$name = this.name;
    final Object other$name = other.name;
    if (this$name == null ? other$name != null : !this$name.equals(other$name)) return false;
    return true;
  }

  @Generated
  @SuppressWarnings("all")
  protected boolean canEqual(final Object other) {
    return other instanceof Item;
  }

  @Generated
  @Override
  @SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.value;
    result = result * PRIME + this.weight;
    final Object $name = this.name;
    result = result * PRIME + ($name == null ? 43 : $name.hashCode());
    return result;
  }
}
