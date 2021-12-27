/**
 * @author : Linus Fernandes
 * @created : 2021-12-27
 */

/*
 * TreapData.java
 * Copyright (C) 2021  <@fernal73>
 *
 * Distributed under terms of the MIT license.
 */
package ds;

public class TreapData<T extends Comparable> implements ITreapData<T> {
  int priority;
  T data;

  public TreapData(T data, int priority) {
    this.data = data;
    this.priority = priority;
  }

  @Override
  public int priority() {
    return priority;
  }

  @Override
  public T data() {
    return data;
  }

  @Override
  public int compareTo(T other) {
    return data.compareTo(other);
  }
}
