package ds;

import static java.util.Objects.*;

import java.util.Iterator;
import java.util.ListIterator;

@SuppressWarnings({"nullness", "PMD.LawOfDemeter", "PMD.GodClass"})
public class DoublyLinkedList<T> extends AbstractList<T> {

  private static final String DATA_NON_NULL = "Data cannot be null.";
  private int length;

  @SuppressWarnings("initialization.fields.uninitialized")
  private INode<T> head;

  @SuppressWarnings("initialization.fields.uninitialized")
  private INode<T> tail;

  @Override
  protected void linkBefore(T data, INode<T> next) {
    throw new UnsupportedOperationException();
  }

  /** Links e as first element. */
  @Override
  protected void linkFirst(T e) {
    final INode<T> f = head;
    final INode<T> newNode = new DoubleNode<>(e, f);
    head = newNode;
    if (isNull(f)) tail = newNode;
    else f.setPrev(newNode);
    ++length;
  }

  @Override
  protected void linkLast(T data) {
    throw new UnsupportedOperationException();
  }

  @Override
  protected T unlink(INode<T> node) {
    throw new UnsupportedOperationException();
  }

  @Override
  protected T unlinkFirst() {
    throw new UnsupportedOperationException();
  }

  @Override
  @SuppressWarnings("nullness:argument.type.incompatible")
  public INode<T> find(T data) {
    requireNonNull(data, DATA_NON_NULL);
    INode<T> node = new DoubleNode<>(data);
    if (head.equals(node)) return head;
    INode<T> startNode = next(head);
    while (!node.equals(startNode) && nonNull(startNode)) startNode = next(startNode);
    return startNode;
  }

  @SuppressWarnings({"PMD.LawOfDemeter", "nullness:argument.type.incompatible"})
  @Override
  public boolean delete(T data) {
    requireNonNull(data, DATA_NON_NULL);
    if (isEmpty()) return false;
    INode<T> node = new DoubleNode<>(data);
    if (head.equals(node)) {
      head = next(head);
      --length;
      return true;
    }
    INode<T> prevNode = head;
    INode<T> currNode = next(head);
    while (!node.equals(currNode) && nonNull(currNode)) {
      prevNode = currNode;
      currNode = next(currNode);
    }
    if (isNull(currNode)) return false;
    INode<T> nextNode = next(currNode);
    prevNode.setNext(nextNode);
    if (nonNull(nextNode)) nextNode.setPrev(prevNode);
    --length;
    return true;
  }

  @SuppressWarnings({"PMD.LawOfDemeter", "nullness:argument.type.incompatible"})
  @Override
  public T deleteAt(int index) {
    checkIndex(index, length);
    if (isEmpty()) return null;
    if (index == 0) return unlinkFirst();
    return unlink(get(index));
  }

  /**
   * Add element at end.
   *
   * @param data - data to be added to list.
   */
  @Override
  @SuppressWarnings("nullness:argument.type.incompatible")
  public void add(T data) {
    requireNonNull(data, DATA_NON_NULL);
    if (isNull(head)) {
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
  @Override
  @SuppressWarnings("nullness:argument.type.incompatible")
  public void addAtFirst(T data) {
    requireNonNull(data, DATA_NON_NULL);
    INode<T> newNode = new DoubleNode<>(data);
    if (isNull(this.head)) this.head = this.tail = newNode;
    else {
      this.head.setPrev(newNode);
      newNode.setNext(this.head);
      this.head = newNode;
    }
    ++length;
  }

  @Override
  public INode<T> get(int index) {
    checkIndex(index, this.length);
    if (index == 0) return this.head;
    if (index == this.length - 1) return this.tail;
    int midPoint = this.length >> 1;
    if (index < midPoint) return getFromHead(index);
    else return getFromTail(index);
  }

  private INode<T> getFromHead(int index) {
    int pointer = 0;
    INode<T> pointerNode = this.head;
    while (pointer != index) {
      pointerNode = next(pointerNode);
      ++pointer;
    }
    return pointerNode;
  }

  private INode<T> getFromTail(int index) {
    int pointer = length - 1;
    INode<T> pointerNode = this.tail;
    while (pointer != index) {
      pointerNode = prev(pointerNode);
      --pointer;
    }
    return pointerNode;
  }

  private INode<T> next(INode<T> node) {
    return isNull(node) ? null : node.getNext();
  }

  private INode<T> prev(INode<T> node) {
    return isNull(node) ? null : node.getPrev();
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
    while (nonNull(nextNode)) {
      sb.append(nextNode);
      nextNode = next(nextNode);
      if (nonNull(nextNode)) sb.append(',');
    }
    sb.append(']');
    return sb.toString();
  }

  @Override
  public Iterator<T> iterator() {
    return getIterator();
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
