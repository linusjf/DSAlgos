package ds;

import static ds.ArrayUtils.*;
import static ds.MathUtils.*;

import java.util.Arrays;

/***
 * <p>Centred Deque implementation in Java.</p>
 ***/
public class CentredDeque implements IQueue, IStack, IDeque {
  private static final String QUEUE_UNDERFLOW = "Queue underflow.";
  long[] arr;
  int first;
  int last;

  public CentredDeque(int size) {
    arr = new long[size];
    first = last = -1;
  }

  @Override
  public void push(long j) {
    addFirst(j);
  }

  @Override
  public long pop() {
    return poll();
  }

  @Override
  public void insert(long j) {
    addLast(j);
  }

  @Override
  public long poll() {
    return pollFirst();
  }

  @Override
  public long peek() {
    return peekFirst();
  }

  @Override
  public boolean isFull() {
    return first == 0 || last == arr.length - 1;
  }

  private int getLeftBoundary() {
    if (isOdd(arr.length)) return ((arr.length + 1) >> 1) - 1;
    else return (arr.length >> 1) - 1;
  }

  private int getRightBoundary() {
    if (isOdd(arr.length)) return (arr.length + 1) >> 1;
    else return arr.length >> 1;
  }

  private int getLeftLength() {
    return first == -1 ? 0 : getLeftBoundary() - first + 1;
  }

  private int getRightLength() {
    return last == -1 ? 0 : arr.length - last;
  }

  @Override
  public int size() {
    if (arr.length == 0) return 0;
    return getLeftLength() + getRightLength();
  }

  @Override
  public boolean isEmpty() {
    return size() == 0;
  }

  private void reinitializeLeftPointer() {
    first = -1;
  }

  private void reinitializeRightPointer() {
    last = -1;
  }

  @Override
  public void addFirst(long key) {
    if (isFull()) doubleCapacity();
    if (first == -1) first = getLeftBoundary();
    else --first;
    arr[first] = key;
  }

  @Override
  public void addLast(long key) {
    if (isFull()) doubleCapacity();
    if (last == -1) last = getRightBoundary();
    else ++last;
    arr[last] = key;
  }

  @Override
  public long pollFirst() {
    if (isEmpty()) throw new IllegalStateException(QUEUE_UNDERFLOW);
    long val = arr[first];
    if (first == getLeftBoundary()) {
      reinitializeLeftPointer();
      return val;
    }
    ++first;
    return val;
  }

  @Override
  public long pollLast() {
    if (isEmpty()) throw new IllegalStateException(QUEUE_UNDERFLOW);
    long val = arr[last];
    if (last == getRightBoundary()) {
      reinitializeRightPointer();
      return val;
    }
    --last;
    return val;
  }

  @Override
  public long peekFirst() {
    if (isEmpty()) throw new IllegalStateException(QUEUE_UNDERFLOW);
    return arr[first];
  }

  @Override
  public long peekLast() {
    if (isEmpty()) throw new IllegalStateException(QUEUE_UNDERFLOW);
    return arr[last];
  }

  /***
   * <p> Double the capacity of this deque.
   * Call only when full, i.e.,
   * when head and tail have wrapped around to touch each other. </p>
   ***/
  private void doubleCapacity() {
    int n = arr.length;
    if (n == 0) throw new IllegalStateException("Initial capacity is zero. Cannot be doubled.");
    long[] a = getDoubleCapacity(n);
    if (first > 0) {
      System.arraycopy(arr, first, a, (a.length >> 1) - getLeftLength() - 1, getLeftLength());
      first = (a.length >> 1) - 1 - getLeftLength();
    }
    if (last > 0) {
      System.arraycopy(arr, getRightBoundary(), a, (a.length >> 1), getRightLength());
      last = (a.length >> 1) + getRightLength() - 1;
    }
    arr = a;
  }

  @Generated
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    String lineSeparator = System.lineSeparator();
    sb.append("First = ")
        .append(first)
        .append(lineSeparator)
        .append("Last = ")
        .append(last)
        .append(lineSeparator)
        .append(Arrays.toString(arr));
    return sb.toString();
  }
}
