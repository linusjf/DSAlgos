package ds;

import java.util.Objects;

public class SinglyLinkedList<T> {

  private static final String DATA_NON_NULL = "Data cannot be null.";
  private int length;

  @SuppressWarnings("initialization.fields.uninitialized")
  private SingleNode<T> head;

  /**
   * Add element at specified index.
   *
   * @param data - data to be added at index.
   * @param index - index at which element to be added.
   */
  @SuppressWarnings({"PMD.LawOfDemeter", "nullness:argument.type.incompatible"})
  public void add(T data, int index) {
    Objects.requireNonNull(data, DATA_NON_NULL);
    if (index == 0) {
      addAtFirst(data);
      return;
    }
    if (index == this.length) add(data);
    else if (index < this.length) {
      SingleNode<T> newNode = new SingleNode<>(data);
      SingleNode<T> leftNode = get(index - 1);
      SingleNode<T> rightNode = get(index);
      newNode.setNext(rightNode);
      leftNode.setNext(newNode);
      ++length;
    } else throw new IndexOutOfBoundsException("Index not available.");
  }

  /**
   * Add element at end.
   *
   * @param data - data to be added to list.
   */
  @SuppressWarnings({"PMD.LawOfDemeter", "nullness:argument.type.incompatible"})
  public void add(T data) {
    Objects.requireNonNull(data, DATA_NON_NULL);
    if (head == null) head = new SingleNode<>(data);
    else {
      SingleNode<T> newNode = new SingleNode<>(data);
      SingleNode<T> lastNode = getLast(head);
      lastNode.setNext(newNode);
    }
    ++length;
  }

  @SuppressWarnings({"PMD.LawOfDemeter", "nullness:argument.type.incompatible"})
  public SingleNode<T> find(T data) {
    Objects.requireNonNull(data, DATA_NON_NULL);
    SingleNode<T> node = new SingleNode<>(data);
    if (head.equals(node)) return head;
    SingleNode<T> startNode = head.getNext();
    while (startNode != null) {
      if (startNode.equals(node)) return startNode;
      startNode = startNode.getNext();
    }
    return startNode;
  }

  @SuppressWarnings({"PMD.LawOfDemeter", "nullness:argument.type.incompatible"})
  public boolean delete(T data) {
    Objects.requireNonNull(data, DATA_NON_NULL);
    if (head == null) return false;
    SingleNode<T> node = new SingleNode<>(data);
    if (head.equals(node)) {
      head = head.getNext();
      --length;
      return true;
    }
    SingleNode<T> prevNode = head;
    SingleNode<T> currNode = head.getNext();
    while (currNode != null) {
      if (currNode.equals(node)) {
        prevNode.setNext(currNode.getNext());
        --length;
        return true;
      }
      currNode = currNode.getNext();
    }
    return false;
  }

  /**
   * Add element at first node. Set the newly created node as root node.
   *
   * @param data Add data node at beginning.
   */
  @SuppressWarnings({"PMD.LawOfDemeter", "nullness:argument.type.incompatible"})
  public void addAtFirst(T data) {
    Objects.requireNonNull(data, DATA_NON_NULL);
    SingleNode<T> newNode = new SingleNode<>(data);
    if (this.head == null) this.head = newNode;
    else {
      newNode.setNext(this.head);
      this.head = newNode;
    }
    ++length;
  }

  public SingleNode<T> get(int index) {
    if (index < 0 || index > this.length - 1)
      throw new IndexOutOfBoundsException("Index not available: " + index);
    if (index == 0) return this.head;
    if (index == this.length - 1) return getLast(this.head);
    int pointer = 0;
    SingleNode<T> pointerNode = this.head;
    while (pointer <= index) {
      if (pointer == index) break;
      else {
        pointerNode = next(pointerNode);
        ++pointer;
      }
    }
    return pointerNode;
  }

  @SuppressWarnings("PMD.LawOfDemeter")
  private SingleNode<T> getLast(SingleNode<T> node) {
    SingleNode<T> lastNode = node;
    if (lastNode.getNext() == null) return lastNode;
    return getLast(lastNode.getNext());
  }

  private SingleNode<T> next(SingleNode<T> node) {
    return node.getNext();
  }

  public int size() {
    return this.length;
  }

  public SingleNode<T> getHead() {
    return head;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder(2);
    sb.append('[');
    SingleNode<T> nextNode = this.head;
    while (nextNode != null) {
      sb.append(nextNode);
      nextNode = next(nextNode);
      if (nextNode != null) sb.append(',');
    }
    sb.append(']');
    return sb.toString();
  }
}
