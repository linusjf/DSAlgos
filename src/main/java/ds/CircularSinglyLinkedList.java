package ds;

import java.util.Objects;

public class CircularSinglyLinkedList<T> {

  private static final String DATA_NON_NULL = "Data cannot be null.";
  private int length;

  @SuppressWarnings("initialization.fields.uninitialized")
  private INode<T> head;

  /**
   * Add element at end.
   *
   * @param data - data to be added to list.
   */
  @SuppressWarnings("PMD.LawOfDemeter")
  public void add(T data) {
    Objects.requireNonNull(data, DATA_NON_NULL);
    if (head == null) head = new SingleNode<>(data);
    else {
      INode<T> newNode = new SingleNode<>(data);
      INode<T> lastNode = getLast(head);
      lastNode.setNext(newNode);
      newNode.setNext(head);
    }
    ++length;
  }

  /**
   * Add the element at specified index.
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
      INode<T> newNode = new SingleNode<>(data);
      INode<T> leftNode = get(index - 1);
      INode<T> rightNode = get(index);
      newNode.setNext(rightNode);
      leftNode.setNext(newNode);
      ++length;
    } else throw new IndexOutOfBoundsException("Index not available.");
  }

  /**
   * Add element at first node. Set the newly created node as root node.
   *
   * @param data Add data node at beginning.
   */
  @SuppressWarnings("PMD.LawOfDemeter")
  public void addAtFirst(T data) {
    Objects.requireNonNull(data, DATA_NON_NULL);
    INode<T> newNode = new SingleNode<>(data);
    if (this.head == null) this.head = newNode;
    else {
      newNode.setNext(this.head);
      INode<T> last = getLast(head);
      this.head = newNode;
      last.setNext(head);
    }
    ++length;
  }

  @SuppressWarnings("nullness:return.type.incompatible")
  public INode<T> find(T data) {
    Objects.requireNonNull(data, DATA_NON_NULL);
    INode<T> node = new SingleNode<>(data);
    if (head.equals(node)) return head;
    INode<T> startNode = head.getNext();
    while (!head.distinctCompare(startNode)) {
      if (startNode.equals(node)) return startNode;
      startNode = next(startNode);
    }
    return null;
  }

  @SuppressWarnings({"nullness:assignment.type.incompatible", "PMD.NullAssignment"})
  public boolean delete(T data) {
    Objects.requireNonNull(data, DATA_NON_NULL);
    if (head == null) return false;
    INode<T> node = new SingleNode<>(data);
    if (head.equals(node)) {
      if (head.distinctCompare(head.getNext())) head = null;
      else head = head.getNext();
      --length;
      return true;
    }
    INode<T> prevNode = head;
    INode<T> currNode = head.getNext();
    while (!head.distinctCompare(currNode)) {
      if (currNode.equals(node)) {
        prevNode.setNext(currNode.getNext());
        --length;
        return true;
      }
      currNode = currNode.getNext();
    }
    return false;
  }

  private INode<T> get(int index) {
    if (index < 0 || index > this.length - 1)
      throw new IndexOutOfBoundsException("Index not available: " + index);
    if (index == 0) return this.head;
    if (index == this.length - 1) return getLast(this.head);
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

  @SuppressWarnings("PMD.LawOfDemeter")
  private INode<T> getLast(INode<T> node) {
    INode<T> lastNode = node;
    if (head.distinctCompare(lastNode.getNext())) return lastNode;
    return getLast(lastNode.getNext());
  }

  private INode<T> next(INode<T> node) {
    return node.getNext();
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
      if (head.distinctCompare(nextNode)) break;
      sb.append(',');
    }
    sb.append(']');
    return sb.toString();
  }
}
