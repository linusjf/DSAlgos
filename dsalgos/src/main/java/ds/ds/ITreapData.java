/**
 * @author : home
 * @created : 2021-12-27
 */

/*
 * ITreapData.java
 * Copyright (C) 2021  <@localhost>
 *
 * Distributed under terms of the MIT license.
 */
package ds;

public interface ITreapData<T> extends Comparable<T> {
  T data();

  int priority();
}
