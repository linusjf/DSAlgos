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
    if (a[parent] < a[i]) {
      swap(i, parent);
      heapifyUp(parent);
    }
  }

  @Override
  protected void heapifyDown(int i) {
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
}
