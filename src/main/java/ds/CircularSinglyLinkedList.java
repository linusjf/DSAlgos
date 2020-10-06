package ds;

import static java.util.Objects.*;

import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;

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

  @Override
  protected T unlinkFirst() {
    final T data = head.getData();
    head.setData(null);
    if (head.isSame(head.getNext())) {
      head.setNext(null);
      head = tail = null;
    } else {
      INode<T> next = head.getNext();
      head.setNext(null);
      head = next;
      tail.setNext(head);
    }
    --length;
    return data;
  }

  @Override
  protected T unlink(INode<T> prevNode, INode<T> node) {
    prevNode.setNext(node.getNext());
    final T data = node.getData();
    node.setNext(null);
    node.setData(null);
    --length;
    return data;
  }

  @Override
  protected void link(INode<T> prevNode, T data, INode<T> nextNode) {
    INode<T> node = new SingleNode<>(data, nextNode);
    prevNode.setNext(node);
    ++length;
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
      unlinkFirst();
      return true;
    }
    INode<T> prevNode = head;
    INode<T> currNode = head.getNext();
    while (!head.isSame(currNode)) {
      if (currNode.equals(node)) {
        unlink(prevNode, currNode);
        return true;
      }
      prevNode = currNode;
      currNode = currNode.getNext();
    }
    return false;
  }

  @Override
  public INode<T> get(int index) {
    checkIndex(index, length);
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

  @SuppressWarnings("PMD.LawOfDemeter")
  private INode<T> getPrevious(INode<T> node) {
    if (head.isSame(node)) return head;
    INode<T> prevNode = head;
    INode<T> currNode = head.getNext();
    while (nonNull(currNode)) {
      if (node.isSame(currNode)) return prevNode;
      prevNode = currNode;
      currNode = currNode.getNext();
    }
    return currNode;
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
  public Iterator<T> iterator() {
    return getIterator();
  }

  @Override
  public ListIterator<T> getIterator() {
    return new ListIter();
  }

  private boolean isTail(INode<T> node) {
    return node == tail;
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
      nextIndex = index % length;
    }

    @Override
    public T next() {
      if (!hasNext()) throw new NoSuchElementException();
      lastReturned = nextNode;
      nextNode = nextNode.getNext();
      nextIndex = ++nextIndex % length;
      return lastReturned.getData();
    }

    @Override
    public T previous() {
      if (!hasPrevious()) throw new NoSuchElementException();
      lastReturned = nextNode = isTail(nextNode) ? tail : getPrevious(nextNode);
      nextIndex--;
      if (nextIndex < 0) nextIndex = length - 1;
      return lastReturned.getData();
    }

    @Override
    public void add(T data) {
      lastReturned = null;
      if (isTail(nextNode)) linkLast(data);
      else linkBefore(data, nextNode);
      nextIndex++;
    }

    @Override
    public void remove() {
      if (isNull(lastReturned)) throw new IllegalStateException();
      INode<T> lastNext = lastReturned.getNext();
      unlink(lastReturned);
      if (lastReturned.isSame(nextNode)) nextNode = lastNext;
      else {
        nextIndex--;
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
