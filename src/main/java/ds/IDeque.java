package ds;

public interface IDeque {

  void addFirst(long key);

  void addLast(long key);

  long pollFirst();

  long pollLast();

  long peekFirst();

  long peekLast();

  boolean isEmpty();

  boolean isFull();

  int size();
}
