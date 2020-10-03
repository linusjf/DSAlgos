package ds;

import static java.util.Objects.*;

import java.util.ListIterator;

@SuppressWarnings("nullness")
public class CircularSinglyLinkedList<T> extends AbstractList<T> {

  private static final String DATA_NON_NULL = "Data cannot be null.";
  private int length;

  @SuppressWarnings("initialization.fields.uninitialized")
  private INode<T> head;

  @SuppressWarnings("initialization.fields.uninitialized")
  private INode<T> tail;

  /**
   * Add element at end.
   *
   * @param data - data to be added to list.
   */
  @SuppressWarnings({"PMD.LawOfDemeter", "nullness:argument.type.incompatible"})
  @Override
  public void add(T data) {
    requireNonNull(data, DATA_NON_NULL);
    if (isNull(head)) {
      head = new SingleNode<>(data);
      head.setNext(head);
      tail = head;
    } else {
      INode<T> newNode = new SingleNode<>(data);
      tail.setNext(newNode);
      newNode.setNext(head);
      tail = newNode;
    }
    ++length;
  }

  /**
   * Add the element at specified index.
   *
   * @param data - data to be added at index.
   * @param index - index at which element to be added.
   */
  @SuppressWarnings({"PMD.LawOfDemeter", "nullness:argument.type.incompatible"})
  @Override
  public void add(T data, int index) {
    requireNonNull(data, DATA_NON_NULL);
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
  @SuppressWarnings({"PMD.LawOfDemeter", "nullness:argument.type.incompatible"})
  @Override
  public void addAtFirst(T data) {
    requireNonNull(data, DATA_NON_NULL);
    INode<T> newNode = new SingleNode<>(data);
    if (isNull(this.head)) {
      this.head = newNode;
      this.tail = newNode;
      newNode.setNext(head);
    } else {
      newNode.setNext(this.head);
      this.head = newNode;
      tail.setNext(head);
    }
    ++length;
  }

  @SuppressWarnings({"nullness:return.type.incompatible", "nullness:argument.type.incompatible"})
  @Override
  public INode<T> find(T data) {
    requireNonNull(data, DATA_NON_NULL);
    INode<T> node = new SingleNode<>(data);
    if (head.equals(node)) return head;
    INode<T> startNode = head.getNext();
    while (!head.isSame(startNode)) {
      if (startNode.equals(node)) return startNode;
      startNode = next(startNode);
    }
    return null;
  }

  @Override
  public T deleteAt(int index) {
    return null;
  }

  @SuppressWarnings({
    "nullness:assignment.type.incompatible",
    "PMD.NullAssignment",
    "nullness:argument.type.incompatible"
  })
  @Override
  public boolean delete(T data) {
    requireNonNull(data, DATA_NON_NULL);
    if (isNull(head)) return false;
    INode<T> node = new SingleNode<>(data);
    if (head.equals(node)) {
      if (head.isSame(head.getNext())) {
        head = tail = null;
      } else head = head.getNext();
      --length;
      return true;
    }
    INode<T> prevNode = head;
    INode<T> currNode = head.getNext();
    while (!head.isSame(currNode)) {
      if (currNode.equals(node)) {
        prevNode.setNext(currNode.getNext());
        --length;
        return true;
      }
      prevNode = currNode;
      currNode = currNode.getNext();
    }
    return false;
  }

  @Override
  public INode<T> get(int index) {
    if (index < 0 || index > this.length - 1)
      throw new IndexOutOfBoundsException("Index not available: " + index);
    if (index == 0) return this.head;
    if (index == this.length - 1) return tail;
    int pointer = 0;
    INode<T> pointerNode = this.head;
    while (pointer != index) {
      pointerNode = next(pointerNode);
      ++pointer;
    }
    return pointerNode;
  }

  private INode<T> next(INode<T> node) {
    return node.getNext();
  }

  @Override
  public int size() {
    return this.length;
  }

  @Override
  public boolean isEmpty() {
    return this.length == 0;
  }

  public INode<T> getHead() {
    return head;
  }

  public INode<T> getTail() {
    return tail;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder(2);
    sb.append('[');
    INode<T> nextNode = this.head;
    while (nonNull(head)) {
      sb.append(nextNode);
      nextNode = next(nextNode);
      if (head.isSame(nextNode)) break;
      sb.append(',');
    }
    sb.append(']');
    return sb.toString();
  }

  @Override
  public ListIterator<T> getIterator() {
    return getIteratorFromIndex(0);
  }
  
  @Override
  public ListIterator<T> getIteratorFromIndex(int idx) {
    return null;
  }
}
