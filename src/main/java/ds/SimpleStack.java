package ds;

public class SimpleStack implements IStack {
  private final int maxSize;
  private long[] stackArray;
  private int top;

  public SimpleStack(int s) {
    maxSize = s;
    stackArray = new long[maxSize];
    top = -1;
  }

  @Override
  public void push(long j) {
    stackArray[top + 1] = j;
    ++top;
  }

  @Override
  public long pop() {
    return stackArray[top--];
  }

  @Override
  public long peek() {
    return stackArray[top];
  }

  @Override
  public boolean isEmpty() {
    return top == -1;
  }

  @Override
  public int size() {
    return top + 1;
  }

  @Override
  public boolean isFull() {
    return top == maxSize - 1;
  }
}
