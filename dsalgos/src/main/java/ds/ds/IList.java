package ds;

import java.util.ListIterator;

public interface IList<T> extends Iterable<T> {
  void add(T data, int index);

  void add(T data);

  void addAtFirst(T data);

  INode<T> find(T data);

  INode<T> get(int index);

  T deleteAt(int index);

  boolean delete(T data);

  int size();

  boolean isEmpty();

  ListIterator<T> getIterator();

  ListIterator<T> getIteratorFromIndex(int index);

  INode<T> getHead();

  INode<T> getTail();
}
