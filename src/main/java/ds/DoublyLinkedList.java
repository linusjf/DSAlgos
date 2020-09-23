package ds;

import java.util.Objects;

public class DoublyLinkedList<T extends Object> {

  private static final String DATA_NON_NULL = "Data cannot be null.";
  private int length;

  @SuppressWarnings("initialization.fields.uninitialized")
  private INode<T> head;

  @SuppressWarnings("initialization.fields.uninitialized")
  private INode<T> tail;

  public INode<T> find(T data) {
    Objects.requireNonNull(data, DATA_NON_NULL);
    INode<T> node = new DoubleNode<>(data);
    if (head.equals(node)) return head;
    INode<T> startNode = next(head);
    while (startNode != null) {
      if (startNode.equals(node)) return startNode;
      startNode = next(startNode);
    }
    return startNode;
  }

  @SuppressWarnings("PMD.LawOfDemeter")
  public boolean delete(T data) {
    Objects.requireNonNull(data, DATA_NON_NULL);
    if (head == null) return false;
    INode<T> node = new DoubleNode<>(data);
    if (head.equals(node)) {
      head = next(head);
      --length;
      return true;
    }
    INode<T> prevNode = head;
    INode<T> currNode = next(head);
    while (currNode != null) {
      if (currNode.equals(node)) {
        INode<T> nextNode = next(currNode);
        prevNode.setNext(nextNode);
        if (nextNode != null) nextNode.setPrev(prevNode);
        --length;
        return true;
      }
      currNode = next(currNode);
    }
    return false;
  }

  /**
   * Add element at end.
   *
   * @param data - data to be added to list.
   */
  public void add(T data) {
    Objects.requireNonNull(data, DATA_NON_NULL);
    if (head == null) {
      head = new DoubleNode<>(data);
      tail = head;
    } else {
      INode<T> newNode = new DoubleNode<>(data);
      tail.setNext(newNode);
      newNode.setPrev(tail);
      tail = newNode;
    }
    ++length;
  }

  /**
   * Add element at specified index.
   *
   * @param data - data to be added at index.
   * @param index - index at which element to be added.
   */
  @SuppressWarnings("PMD.LawOfDemeter")
  public void add(T data, int index) {
    Objects.requireNonNull(data, DATA_NON_NULL);
    if (index == 0) {
      addAtFirst(data);
      return;
    }
    if (index == this.length) add(data);
    else if (index < this.length) {
      INode<T> newNode = new DoubleNode<>(data);
      INode<T> leftNode = get(index - 1);
      INode<T> rightNode = get(index);
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
    Objects.requireNonNull(data, DATA_NON_NULL);
    INode<T> newNode = new DoubleNode<>(data);
    if (this.head == null) this.head = this.tail = newNode;
    else {
      this.head.setPrev(newNode);
      newNode.setNext(this.head);
      this.head = newNode;
    }
    ++length;
  }

  public INode<T> get(int index) {
    if (index < 0 || index > this.length - 1)
      throw new IndexOutOfBoundsException("Index not available: " + index);
    if (index == 0) return this.head;
    if (index == this.length - 1) return this.tail;
    int midPoint = this.length >> 1;
    if (index < midPoint) return getFromHead(index);
    else return getFromTail(index);
  }

  private INode<T> getFromHead(int index) {
    int pointer = 0;
    INode<T> pointerNode = this.head;
    while (pointer <= index) {
      if (pointer == index) break;
      else {
        pointerNode = next(pointerNode);
        ++pointer;
      }
    }
    return pointerNode;
  }

  private INode<T> getFromTail(int index) {
    int pointer = length - 1;
    INode<T> pointerNode = this.tail;
    while (pointer >= 0) {
      if (pointer == index) break;
      else {
        pointerNode = prev(pointerNode);
        --pointer;
      }
    }
    return pointerNode;
  }

  private INode<T> next(INode<T> node) {
    return node.getNext();
  }

  private INode<T> prev(INode<T> node) {
    return node.getPrev();
  }

  public int size() {
    return this.length;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder(2);
    sb.append('[');
    INode<T> nextNode = this.head;
    while (nextNode != null) {
      sb.append(nextNode);
      nextNode = next(nextNode);
      if (nextNode != null) sb.append(',');
    }
    sb.append(']');
    return sb.toString();
  }

  public INode<T> getHead() {
    return head;
  }

  public INode<T> getTail() {
    return tail;
  }
}
