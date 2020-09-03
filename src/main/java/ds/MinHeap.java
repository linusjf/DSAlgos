package ds;

public class MinHeap implements IQueue {

  private long[] a;

  private int nElems;

  public MinHeap(int maxsize) {
    a = new long[maxsize];
    nElems = 0;
  }

  private void heapifyUp(int i) {
    if (i == 0) return;
    int parent = parent(i);
    if (a[parent] < a[i]) {
      swap(i, parent);
      heapifyUp(parent);
      parent = parent(i);
    }
  }

  private void heapifyDown(int i) {
    int left = leftChild(i);
    int right = rightChild(i);
    int largest = i;
    if (left < nElems && a[left] > a[i]) largest = left;
    if (right < nElems && a[right] > a[largest]) largest = right;
    if (largest != i) {
      // swap with child having greater value
      swap(i, largest);
      // call heapify-down on the child
      heapifyDown(largest);
    }
  }

  /** Function to return the position of the parent for the node currently at pos. */
  private int parent(int pos) {
    return (pos == 0) ? 0 : pos / 2;
  }

  /** Function to return the position of the left child for the node currently at pos. */
  private int leftChild(int pos) {
    return 2 * pos + 1;
  }

  /** Function to return the position of the right child for the node currently at pos. */
  private int rightChild(int pos) {
    return (2 * pos) + 2;
  }

  /** Function to swap two nodes of the heap. */
  private void swap(int fpos, int spos) {
    long tmp = a[fpos];
    a[fpos] = a[spos];
    a[spos] = tmp;
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
    a[0] = a[nElems - 1];
    --nElems;
    heapifyDown(0);
    return root;
  }
}
