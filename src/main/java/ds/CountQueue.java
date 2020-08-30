package ds;

public class CountQueue implements IQueue {
  private final int maxSize;
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
    if (isFull()) throw new IllegalStateException("Queue is full.");
    if (rear == maxSize - 1) rear = -1;
    queArray[++rear] = j;
    nItems++;
  }

  @Override
  public long remove() {
    if (isEmpty()) throw new IllegalStateException("Queue is empty.");
    long temp = queArray[front++];
    if (front == maxSize) front = 0;
    nItems--;
    return temp;
  }

  @Override
  public long peek() {
    if (isEmpty()) throw new IllegalStateException("Empty queue!");
    return queArray[front];
  }

  @Override
  public boolean isEmpty() {
    return nItems == 0;
  }

  @Override
  public boolean isFull() {
    return nItems == maxSize;
  }

  @Override
  public int size() {
    return nItems;
  }
  
  @Override
  public String toString() {
    StringBuilder sb = new StringBuffer();
    String lineSeparator = System.lineSeparator();
    sb.append(getClass().getName())
      .append(lineSeparator)
      .append("maxSize = ")
      .append(maxSize)
      .append(lineSeparator)
      .append("nItems = ")
      .append(nItems)
      .append(lineSeparator)
      .append("front = ")
      .append(front)
      .append(lineSeparator)
      .append("rear = ")
      .append(rear)
      .append(lineSeparator);
  for (int i = 0; i < maxSize; i++)
  {
    sb.append(queArray[i])
      .append(" ");
      if ((i + 1) % 10 == 0)
        sb.append(lineSeparator);
  }
  return sb.toString();
  }
}
