package ds;

public class NoCountQueue implements IQueue {
  private final int maxSize;
  private long[] queArray;
  private int front;
  private int rear;

  public NoCountQueue(int s) {
    maxSize = s + 1;
    queArray = new long[maxSize];
    front = 0;
    rear = -1;
  }

  @Override
  public void insert(long j) {
    if (rear == maxSize - 1) rear = -1;
    queArray[++rear] = j;
  }

  @Override
  public long remove() {
    long temp = queArray[front++];
    if (front == maxSize) front = 0;
    return temp;
  }

  @Override
  public long peek() {
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
    return rear >= front ? rear - front + 1 : maxSize + rear - front + 1;
  }
}
