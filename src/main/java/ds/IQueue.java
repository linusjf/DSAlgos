package ds;

public interface IQueue {
  void insert(long j);

  long remove();

  long peek();

  boolean isEmpty();

  boolean isFull();

  int size();
}
