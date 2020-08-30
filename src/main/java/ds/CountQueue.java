package ds;

public class CountQueue implements IQueue {
  private int maxSize;
  private long[] queArray;
  private int front;
  private int rear;
  private int nItems;

  public CountQueue(int s) {
    maxSize = s;
    queArray = new long[maxSize];
    front = 0;
    rear = -1;
    nItems = 0;
  }

  @Override
  public void insert(long j) {
    if (rear == maxSize - 1) rear = -1;
    queArray[++rear] = j;
    nItems++;
  }

  @Override
  public long remove() {
    long temp = queArray[front++];
    if (front == maxSize) front = 0;
    nItems--;
    return temp;
  }

  @Override
  public long peek() {
    return queArray[front];
  }

  @Override
  public boolean isEmpty() {
    return (nItems == 0);
  }

  @Override
  public boolean isFull() {
    return (nItems == maxSize);
  }

  @Override
  public int size() {
    return nItems;
  }
}
