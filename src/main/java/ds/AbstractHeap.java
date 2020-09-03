package ds;

public abstract class AbstractHeap implements IQueue {

  protected long[] a;

  protected int nElems;

  public AbstractHeap(int maxsize) {
    a = new long[maxsize];
    nElems = 0;
  }

  protected abstract void heapifyUp(int i);

  protected abstract void heapifyDown(int i);

  /** Function to return the position of the parent for the node currently at pos. */
  protected int parent(int pos) {
    return (pos == 0) ? 0 : (pos - 1) >> 1;
  }

  /** Return position of left child for node currently at pos. */
  protected int leftChild(int pos) {
    return 2 * pos + 1;
  }

  /** Return position of right child for node currently at pos. */
  protected int rightChild(int pos) {
    return (pos << 1) + 2;
  }

  /** Function to swap two nodes of the heap. */
  protected void swap(int fpos, int spos) {
    ArrayUtils.swap(a, fpos, spos);
  }

  /** Function to insert a node into the heap. */
  @Override
  public void insert(long element) {
    if (isFull()) throw new IllegalStateException("Queue full: number of elements is " + nElems);
    a[nElems++] = element;
    heapifyUp(nElems - 1);
  }

  @Override
  public boolean isEmpty() {
    return nElems == 0;
  }

  @Override
  public boolean isFull() {
    return nElems == a.length;
  }

  @Override
  public int size() {
    return nElems;
  }

  @Override
  public long peek() {
    if (isEmpty()) throw new IllegalStateException("Queue empty: " + nElems + " elements.");
    return a[0];
  }

  /** Function to remove and return the minimum element from the heap. */
  @Override
  public long poll() {
    if (isEmpty()) throw new IllegalStateException("Queue empty: " + nElems + " elements.");
    long root = a[0];
    a[0] = a[--nElems];
    heapifyDown(0);
    return root;
  }
}
