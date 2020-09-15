package ds;

import static ds.ArrayUtils.*;

import java.util.Arrays;

/***
 * <p>Deque implementation in Java.</p>
 ***/
@SuppressWarnings("PMD.SystemPrintln")
public class Deque implements IQueue, IStack, IDeque {
  private static final String QUEUE_UNDERFLOW = "Queue underflow.";
  long[] arr;
  int first;
  int last;

  public Deque(int size) {
    arr = new long[size];
    first = -1;
    last = -1;
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
    long val = peek();
    removeFirst();
    return val;
  }

  @Override
  public long peek() {
    return peekFirst();
  }

  @Override
  public boolean isFull() {
    return arr.length == 0 || first == 0 && last == arr.length - 1 || first == last + 1;
  }

  @Override
  public int size() {
    if (arr.length == 0) return 0;
    if (first == -1 && last == -1) return 0;
    return last >= first ? last - first + 1 : arr.length - first + last + 1;
  }

  @Override
  public boolean isEmpty() {
    return first == -1;
  }

  @Override
  public void addFirst(long key) {
    if (isFull()) doubleCapacity();
    if (first == -1) {
      first = 0;
      last = 0;
    } else if (first == 0) first = arr.length - 1;
    else first = first - 1;
    arr[first] = key;
  }

  @Override
  public void addLast(long key) {
    if (isFull()) doubleCapacity();
    if (first == -1) {
      first = 0;
      last = 0;
    } else if (last == arr.length - 1) last = 0;
    else last = last + 1;
    arr[last] = key;
  }

  @Override
  public void removeFirst() {
    if (isEmpty()) throw new IllegalStateException(QUEUE_UNDERFLOW);
    if (first == last) {
      first = -1;
      last = -1;
    } else if (first == arr.length - 1) first = 0;
    else first = first + 1;
  }

  @Override
  public void removeLast() {
    if (isEmpty()) throw new IllegalStateException(QUEUE_UNDERFLOW);
    if (first == last) {
      first = -1;
      last = -1;
    } else if (last == 0) last = arr.length - 1;
    else last = last - 1;
  }

  @Override
  public long peekFirst() {
    if (isEmpty()) throw new IllegalStateException(QUEUE_UNDERFLOW);
    return arr[first];
  }

  @Override
  public long peekLast() {
    if (isEmpty() || last < 0) throw new IllegalStateException(QUEUE_UNDERFLOW);
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
    int p = first;
    int r = n - p;
    long[] a = getDoubleCapacity(n);
    System.arraycopy(arr, p, a, 0, r);
    System.arraycopy(arr, 0, a, r, p);
    arr = a;
    first = 0;
    last = n - 1;
  }

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
