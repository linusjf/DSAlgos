package ds;

public interface IDeque {

  void addFirst(long key);

  void addLast(long key);

  void removeFirst();

  void removeLast();

  long peekFirst();

  long peekLast();

  boolean isEmpty();

  boolean isFull();

  int size();
}
