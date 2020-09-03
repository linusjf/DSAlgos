package ds;

public interface IQueue {
  void insert(long j);

  long poll();

  long peek();

  boolean isEmpty();

  boolean isFull();

  int size();
}
