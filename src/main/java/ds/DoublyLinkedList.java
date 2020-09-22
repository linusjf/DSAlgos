package ds;

public class DoublyLinkedList<T extends Object> {

  private int size;
  private DoubleNode<T> head;
  private DoubleNode<T> tail;

  @SuppressWarnings("nullness")
  public DoublyLinkedList() {
    size = 0;
    head = null;
    tail = null;
  }

  /**
   * Add element at end.
   *
   * @param data - data to be added to list.
   */
  @SuppressWarnings("nullness")
  public void add(T data) {
    if (data == null) return;
    if (head == null) {
      head = new DoubleNode<T>(data);
      tail = head;
      head.setNext(null);
      head.setPrev(null);
    } else {
      DoubleNode<T> newNode = new DoubleNode<T>(data);
      DoubleNode<T> lastNode = tail;
      lastNode.setNext(newNode);
      newNode.setPrev(lastNode);
      newNode.setNext(null);
      tail = newNode;
    }
    ++size;
  }

  /**
   * Add the element at specified index. Index start from 0 to n-1 where n=size of linked list. If
   * index is negative value, nothing will be added to linked list.
   *
   * <p>if index =0 , element will be added at head and element become the first node.
   *
   * @param data - data to be added at index.
   * @param index - index at which element to be added.
   */
  public void add(T data, int index) throws IndexOutOfBoundsException {
    if (data == null) return;
    // If index=0 , we should add the data at head
    if (index == 0) {
      addAtFirst(data);
      return;
    }
    // If index= size, we should add the data at last
    if (index == this.size) add(data);
    else if (index < this.size) {
      DoubleNode<T> newNode = new DoubleNode<T>(data);
      // get the node at (index) from linked list and mark as rightNode.
      // get the node at (index-1) from linked list and mark as leftNode.
      // set node of newly created node as right node.
      // set node of left node as newly created Node.
      DoubleNode<T> leftNode = getNode(index - 1);
      DoubleNode<T> rightNode = getNode(index);
      newNode.setNext(rightNode);
      newNode.setPrev(leftNode);
      leftNode.setNext(newNode);
      rightNode.setPrev(newNode);
      ++size;
    } else throw new IndexOutOfBoundsException("Index not available.");
  }

  /**
   * Add element at first node. Set the newly created node as root node.
   *
   * @param data Add data node at beginning.
   */
  public void addAtFirst(T data) {
    if (data == null) return;
    DoubleNode<T> newNode = new DoubleNode<T>(data);
    if (this.head != null) {
      newNode.setNext(this.head);
      this.head.setPrev(newNode);
      this.head = newNode;
    } else this.head = newNode;
    ++size;
  }

  private DoubleNode<T> getNode(int index) {
    if (index < 0 || index > this.size - 1)
      throw new IndexOutOfBoundsException("Index not available: " + index);
    if (index == 0) return this.head;
    if (index == this.size - 1) return this.tail;
    int midPoint = this.size >> 1;
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
    int pointer = size - 1;
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
    return this.size;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder(2);
    sb.append('[');
    DoubleNode<T> nextNode = this.head;
    while (nextNode != null) {
      represent = represent + nextNode.toString();
      nextNode = next(nextNode);
      if (nextNode != null) sb.append(',');
    }
    sb.append(']');
    return sb.toString();
  }
}
