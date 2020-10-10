package ds;

import static java.util.Objects.*;

import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;

@SuppressWarnings({"nullness", "PMD.GodClass"})
public class CircularDoublyLinkedList<T> extends AbstractList<T> {

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

  @Override
  protected void linkFirst(T data) {
    throw new UnsupportedOperationException();
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
      head = new DoubleNode<>(data);
      head.setNext(head);
      head.setPrev(head);
      tail = head;
    } else {
      INode<T> newNode = new DoubleNode<>(data);
      tail.setNext(newNode);
      newNode.setPrev(tail);
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
  @SuppressWarnings({"PMD.LawOfDemeter", "nullness:argument.type.incompatible"})
  @Override
  public void addAtFirst(T data) {
    requireNonNull(data, DATA_NON_NULL);
    INode<T> newNode = new DoubleNode<>(data);
    if (isNull(this.head)) {
      this.head = newNode;
      this.tail = newNode;
      newNode.setNext(tail);
      newNode.setPrev(head);
    } else {
      tail.setNext(newNode);
      newNode.setPrev(tail);
      newNode.setNext(this.head);
      head.setPrev(newNode);
      this.head = newNode;
    }
    ++length;
  }

  @SuppressWarnings({"nullness:return.type.incompatible", "nullness:argument.type.incompatible"})
  @Override
  public INode<T> find(T data) {
    requireNonNull(data, DATA_NON_NULL);
    INode<T> node = new DoubleNode<>(data);
    if (head.equals(node)) return head;
    INode<T> startNode = head.getNext();
    while (!head.isSame(startNode)) {
      if (startNode.equals(node)) return startNode;
      startNode = next(startNode);
    }
    return null;
  }

  @SuppressWarnings({
    "nullness:assignment.type.incompatible",
    "PMD.NullAssignment",
    "PMD.LawOfDemeter",
    "nullness:argument.type.incompatible"
  })
  @Override
  public boolean delete(T data) {
    requireNonNull(data, DATA_NON_NULL);
    if (isNull(head)) return false;
    INode<T> node = new DoubleNode<>(data);
    if (head.equals(node)) {
      if (head.isSame(head.getNext())) head = tail = null;
      else {
        head = head.getNext();
        tail.setNext(head);
        head.setPrev(tail);
      }
      --length;
      return true;
    }
    INode<T> prevNode = head;
    INode<T> currNode = head.getNext();
    while (!head.isSame(currNode)) {
      if (currNode.equals(node)) {
        INode<T> nextNode = currNode.getNext();
        prevNode.setNext(nextNode);
        nextNode.setPrev(prevNode);
        --length;
        return true;
      }
      prevNode = currNode;
      currNode = currNode.getNext();
    }
    return false;
  }

  @Override
  public T deleteAt(int index) {
    return null;
  }

  @Override
  public INode<T> get(int index) {
    if (index < 0 || index > this.length - 1)
      throw new IndexOutOfBoundsException("Index not available: " + index);
    if (index == 0) return this.head;
    if (index == this.length - 1) return tail;
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
    return node.getNext();
  }

  private INode<T> prev(INode<T> node) {
    return node.getPrev();
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
  public Iterator<T> iterator() {
    return getIterator();
  }

  @Override
  public ListIterator<T> getIterator() {
    return new ListIter();
  }

  @Override
  public ListIterator<T> getIteratorFromIndex(int idx) {
    return new ListIter(idx);
  }

  @SuppressWarnings("PMD.AvoidFieldNameMatchingMethodName")
  final class ListIter implements ListIterator<T> {
    private INode<T> lastReturned;
    private INode<T> nextNode;
    private int nextIndex;

    ListIter() {
      this(0);
    }

    ListIter(int index) {
      checkIndex(index, length + 1);
      nextNode = (index == length) ? head : get(index);
      nextIndex = index == length ? 0 : index;
    }

    @Override
    public T next() {
      if (!hasNext()) throw new NoSuchElementException();
      lastReturned = nextNode = (nextNode == null) ? head : nextNode;
      nextNode = nextNode.getNext();
      ++nextIndex;
      if (nextIndex >= length) nextIndex -= length;
      return lastReturned.getData();
    }

    @Override
    public T previous() {
      if (!hasPrevious()) throw new NoSuchElementException();
      lastReturned = nextNode = nextNode.getPrev();
      --nextIndex;
      if (nextIndex < 0) nextIndex = length - 1;
      return lastReturned.getData();
    }

    @SuppressWarnings("PMD.NullAssignment")
    @Override
    public void add(T data) {
      lastReturned = null;
      if (isNull(nextNode)) linkLast(data);
      else linkBefore(data, nextNode);
      ++nextIndex;
    }

    @SuppressWarnings("PMD.NullAssignment")
    @Override
    public void remove() {
      if (isNull(lastReturned)) throw new IllegalStateException();
      INode<T> lastNext = lastReturned.getNext();
      if (lastReturned.isSame(head)) unlinkFirst();
      else unlink(lastReturned);
      if (lastReturned.isSame(nextNode)) nextNode = lastNext;
      else {
        --nextIndex;
        if (nextIndex < 0) nextIndex = length - 1;
      }
      lastReturned = null;
    }

    @Override
    public void set(T data) {
      if (lastReturned == null) throw new IllegalStateException();
      lastReturned.setData(data);
    }

    @Override
    public boolean hasNext() {
      return !isEmpty();
    }

    @Override
    public boolean hasPrevious() {
      return !isEmpty();
    }

    @Override
    public int nextIndex() {
      return nextIndex;
    }

    @Override
    public int previousIndex() {
      return nextIndex > 0 ? nextIndex - 1 : length - 1;
    }

    @Generated
    @Override
    public String toString() {
      StringBuilder sb = new StringBuilder();
      String lineSeparator = System.lineSeparator();
      sb.append("Last returned = ")
          .append(lastReturned)
          .append(lineSeparator)
          .append("Next node = ")
          .append(nextNode)
          .append(lineSeparator)
          .append("Next index = ")
          .append(nextIndex);
      return sb.toString();
    }
  }
}
