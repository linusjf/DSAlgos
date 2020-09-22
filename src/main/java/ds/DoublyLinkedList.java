package ds;

public class DoublyLinkedList<T extends Object> {

  private int length;

  @SuppressWarnings("initialization.fields.uninitialized")
  private DoubleNode<T> head;

  @SuppressWarnings("initialization.fields.uninitialized")
  private DoubleNode<T> tail;

  /**
   * Add element at end.
   *
   * @param data - data to be added to list.
   */
  public void add(T data) {
    if (data == null) return;
    if (head == null) {
      head = new DoubleNode<>(data);
      tail = head;
    } else {
      DoubleNode<T> newNode = new DoubleNode<>(data);
      tail.setNext(newNode);
      newNode.setPrev(tail);
      tail = newNode;
    }
    ++length;
  }

  /**
   * Add the element at specified index. Index starts from 0 to n-1 where n = length of linked list.
   * If index is negative, nothing will be added to linked list. if index = 0, element will be added
   * at head and element becomes the first node.
   *
   * @param data - data to be added at index.
   * @param index - index at which element to be added.
   */
  @SuppressWarnings("PMD.LawOfDemeter")
  public void add(T data, int index) {
    if (data == null) return;
    if (index == 0) {
      addAtFirst(data);
      return;
    }
    if (index == this.length) add(data);
    else if (index < this.length) {
      DoubleNode<T> newNode = new DoubleNode<>(data);
      DoubleNode<T> leftNode = getNode(index - 1);
      DoubleNode<T> rightNode = getNode(index);
      newNode.setNext(rightNode);
      newNode.setPrev(leftNode);
      leftNode.setNext(newNode);
      rightNode.setPrev(newNode);
      ++length;
    } else throw new IndexOutOfBoundsException("Index not available.");
  }

  /**
   * Add element at first node. Set the newly created node as root node.
   *
   * @param data Add data node at beginning.
   */
  public void addAtFirst(T data) {
    if (data == null) return;
    DoubleNode<T> newNode = new DoubleNode<>(data);
    if (this.head == null) this.head = this.tail = newNode;
    else {
      this.head.setPrev(newNode);
      newNode.setNext(this.head);
      this.head = newNode;
    }
    ++length;
  }

  private DoubleNode<T> getNode(int index) {
    if (index < 0 || index > this.length - 1)
      throw new IndexOutOfBoundsException("Index not available: " + index);
    if (index == 0) return this.head;
    if (index == this.length - 1) return this.tail;
    int midPoint = this.length >> 1;
    if (index < midPoint) return getNodeFromHead(index);
    else return getNodeFromTail(index);
  }

  private DoubleNode<T> getNodeFromHead(int index) {
    int pointer = 0;
    DoubleNode<T> pointerNode = this.head;
    while (pointer <= index) {
      if (pointer == index) break;
      else {
        pointerNode = next(pointerNode);
        ++pointer;
      }
    }
    return pointerNode;
  }

  private DoubleNode<T> getNodeFromTail(int index) {
    int pointer = length - 1;
    DoubleNode<T> pointerNode = this.tail;
    while (pointer >= 0) {
      if (pointer == index) break;
      else {
        pointerNode = prev(pointerNode);
        --pointer;
      }
    }
    return pointerNode;
  }

  private DoubleNode<T> next(DoubleNode<T> node) {
    return node.getNext();
  }

  private DoubleNode<T> prev(DoubleNode<T> node) {
    return node.getPrev();
  }

  public int size() {
    return this.length;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder(2);
    sb.append('[');
    DoubleNode<T> nextNode = this.head;
    while (nextNode != null) {
      sb.append(nextNode);
      nextNode = next(nextNode);
      if (nextNode != null) sb.append(',');
    }
    sb.append(']');
    return sb.toString();
  }
}