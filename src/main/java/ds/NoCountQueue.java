package ds;

public class NoCountQueue implements IQueue {
  private final int maxSize;
  private long[] queArray;
  private int front;
  private int rear;

  public NoCountQueue(int s) {
    if (s < 0)
      throw new IllegalArgumentException("Queue size cannot be negative.");
    maxSize = s + 1;
    queArray = new long[maxSize];
    initialisePointers();
  }

  private void initialisePointers() {
    front = 0;
    rear = -1;
  }

  @Override
  public void insert(long j) {
    if (isFull()) throw new IllegalStateException("Queue is full.");
    queArray[++rear] = j;
  }

  private boolean isSingleElementQueue() {
    return front == rear;
  }

  @Override
  public long remove() {
    if (isEmpty()) throw new IllegalStateException("Queue is empty.");
    long temp = queArray[front];
    if (isSingleElementQueue())  
      initialisePointers();
    else 
      ++front;
    return temp;
  }

  @Override
  public long peek() {
    if (isEmpty()) throw new IllegalStateException("Empty queue!");
    return queArray[front];
  }

  @Override
  public boolean isEmpty() {
    return (rear + 1) % maxSize == front;
  }

  @Override
  public boolean isFull() {
    return (rear + 2) % maxSize == front;
  }

  @Override
  public int size() {
        return rear - front + 1;
  }
}
