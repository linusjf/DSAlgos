package ds;

import static ds.ArrayUtils.swap;

public class MinHeap extends AbstractHeap {

  public MinHeap(int maxSize) {
    super(maxSize);
  }

  @Override
  protected void heapifyUp(int i) {
    if (i == 0) return;
    int parent = parent(i);
    if (a[parent] > a[i]) {
      swap(i, parent);
      heapifyUp(parent);
    }
  }

  @Override
  protected void heapifyDown(int i) {
    int left = leftChild(i);
    int right = rightChild(i);
    int smallest = i;
    if (left < nElems && a[left] < a[i]) smallest = left;
    if (right < nElems && a[right] < a[smallest]) smallest = right;
    if (smallest != i) {
      // swap with child having lesser value
      swap(i, smallest);
      // call heapify-down on the child
      heapifyDown(smallest);
    }
  }
}
