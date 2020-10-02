package ds;

import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.Objects;

@SuppressWarnings({"nullness", "PMD.GodClass", "PMD.LawOfDemeter", "PMD.NullAssignment"})
public class SinglyLinkedList<T> extends AbstractList<T> {

  private static final String DATA_NON_NULL = "Data cannot be null.";
  private int length;

  @SuppressWarnings("initialization.fields.uninitialized")
  private INode<T> head;

  /**
   * Add element at first node. Set the newly created node as root node.
   *
   * @param data Add data node at beginning.
   */
  @SuppressWarnings({"PMD.LawOfDemeter", "nullness:argument.type.incompatible"})
  @Override
  public void addAtFirst(T data) {
    Objects.requireNonNull(data, DATA_NON_NULL);
    linkFirst(data);
  }

  /**
   * Add element at specified index.
   *
   * @param data - data to be added at index.
   * @param index - index at which element to be added.
   */
  @Override
  @SuppressWarnings({"PMD.LawOfDemeter", "nullness:argument.type.incompatible"})
  public void add(T data, int index) {
    Objects.requireNonNull(data, DATA_NON_NULL);
    if (index == 0) {
      addAtFirst(data);
      return;
    }
    if (index == this.length) add(data);
    else if (index < this.length) {
      INode<T> leftNode = get(index - 1);
      INode<T> rightNode = leftNode.getNext();
      link(leftNode, data, rightNode);
    } else throw new IndexOutOfBoundsException("Index not available.");
  }

  /**
   * Add element at end.
   *
   * @param data - data to be added to list.
   */
  @Override
  @SuppressWarnings({"PMD.LawOfDemeter", "nullness:argument.type.incompatible"})
  public void add(T data) {
    Objects.requireNonNull(data, DATA_NON_NULL);
    linkLast(data);
  }

  @SuppressWarnings({"PMD.LawOfDemeter", "nullness:argument.type.incompatible"})
  @Override
  public INode<T> find(T data) {
    Objects.requireNonNull(data, DATA_NON_NULL);
    INode<T> node = new SingleNode<>(data);
    if (head.equals(node)) return head;
    INode<T> startNode = next(head);
    while (startNode != null) {
      if (startNode.equals(node)) return startNode;
      startNode = next(startNode);
    }
    return startNode;
  }

  @SuppressWarnings({"PMD.LawOfDemeter", "nullness:argument.type.incompatible"})
  @Override
  public boolean delete(T data) {
    Objects.requireNonNull(data, DATA_NON_NULL);
    if (head == null) return false;
    INode<T> node = new SingleNode<>(data);
    if (head.equals(node)) {
      unlinkFirst();
      return true;
    }
    INode<T> prevNode = head;
    INode<T> currNode = next(head);
    while (currNode != null) {
      if (currNode.equals(node)) {
        unlink(prevNode, currNode);
        return true;
      }
      prevNode = currNode;
      currNode = next(currNode);
    }
    return false;
  }

  @Override
  public T deleteAt(int index) {
    Objects.checkIndex(index, length);
    T data;
    if (index == 0) data = unlinkFirst();
    else {
      INode<T> prev = get(index - 1);
      INode<T> curr = prev.getNext();
      data = unlink(prev, curr);
    }
    return data;
  }

  @Override
  public INode<T> get(int index) {
    Objects.checkIndex(index, length);
    if (index == 0) return this.head;
    if (index == this.length - 1) return getLast(this.head);
    int pointer = 1;
    INode<T> pointerNode = next(this.head);
    while (pointer != index) {
      pointerNode = next(pointerNode);
      ++pointer;
    }
    return pointerNode;
  }

  @SuppressWarnings("PMD.LawOfDemeter")
  private INode<T> getLast(INode<T> node) {
    if (node == null) return null;
    INode<T> lastNode = node;
    INode<T> nextNode = next(lastNode);
    if (nextNode == null) return lastNode;
    return getLast(nextNode);
  }

  @Override
  protected void linkFirst(T data) {
    final INode<T> f = head;
    INode<T> node = new SingleNode<>(data, f);
    head = node;
    ++length;
  }

  @Override
  protected void linkLast(T data) {
    INode<T> node = new SingleNode<>(data);
    final INode<T> f = head;
    INode<T> last = getLast(f);
    if (last == null) head = node;
    else last.setNext(node);
    ++length;
  }

  @Override
  protected void link(INode<T> prev, T data, INode<T> next) {
    INode<T> node = new SingleNode<>(data, next);
    if (prev != null) prev.setNext(node);
    ++length;
  }

  @Override
  protected void linkAfter(T data, INode<T> node) {
    INode<T> newNode = new SingleNode<>(data);
    newNode.setNext(node.getNext());
    node.setNext(newNode);
    ++length;
  }

  @Override
  protected T unlinkFirst() {
    INode<T> node = head;
    if (node == null) return null;
    final T data = node.getData();
    final INode<T> next = node.getNext();
    node.setNext(null);
    node.setData(null);
    head = next;
    --length;
    return data;
  }

  @Override
  protected T unlink(INode<T> prev, INode<T> node) {
    final T data = node.getData();
    final INode<T> next = node.getNext();
    node.setNext(null);
    node.setData(null);
    if (prev != null) prev.setNext(next);
    --length;
    return data;
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

  @Override
  public ListIterator<T> getIterator() {
    return new Iterator(0);
  }

  @SuppressWarnings("PMD.AvoidFieldNameMatchingMethodName")
  final class Iterator implements ListIterator<T> {
    private INode<T> prevNode;
    private INode<T> lastReturned;
    private INode<T> nextNode;
    private int nextIndex;

    Iterator(int index) {
      Objects.checkIndex(index, length + 1);
      prevNode = index > 0 ? get(index - 1) : null;
      nextNode = (index == length) ? null : get(index);
      nextIndex = index;
    }

    @Override
    public T next() {
      if (nextNode == null) throw new NoSuchElementException("No more elements!");
      prevNode = lastReturned;
      lastReturned = nextNode;
      nextNode = nextNode.getNext();
      ++nextIndex;
      return lastReturned.getData();
    }

    @Override
    public boolean hasNext() {
      return nextIndex < length;
    }

    @Override
    public T previous() {
      if (lastReturned == null) throw new NoSuchElementException("No more elements.");
      T data = lastReturned.getData();
      nextNode = lastReturned;
      lastReturned = prevNode;
      --nextIndex;
      int prevIndex = previousIndex();
      prevNode = prevIndex >= 1 ? get(prevIndex - 1) : null;
      return data;
    }

    @Override
    public boolean hasPrevious() {
      return nextIndex > 0;
    }

    @Override
    public void add(T data) {
      if (lastReturned == null) throw new IllegalStateException("Next not invoked!");
      if (nextNode == null) {
        linkFirst(data);
      } else {
        link(lastReturned, data, nextNode);
        nextNode = lastReturned.getNext();
      }
      lastReturned = null;
      ++nextIndex;
    }

    @Override
    public void remove() {
      if (lastReturned == null)
        throw new IllegalStateException("Remove already invoked or next not invoked!");
      unlink(prevNode, lastReturned);
      lastReturned = null;
      --nextIndex;
    }

    @Override
    public void set(T data) {
      if (lastReturned == null) throw new IllegalStateException("Null element cannot be set.");
      lastReturned.setData(data);
    }

    @Override
    public int nextIndex() {
      return nextIndex;
    }

    @Override
    public int previousIndex() {
      return nextIndex - 1;
    }

    @Override
    public String toString() {
      StringBuilder sb = new StringBuilder();
      String lineSeparator = System.lineSeparator();
      sb.append("Prev node = ")
          .append(prevNode)
          .append(lineSeparator)
          .append("Last returned = ")
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
