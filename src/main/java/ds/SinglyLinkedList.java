package ds;

import java.util.NoSuchElementException;
import java.util.Objects;

@SuppressWarnings("nullness")
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
      unlinkFirst(head);
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
  public INode<T> get(int index) {
    if (index < 0 || index > this.length - 1)
      throw new IndexOutOfBoundsException("Index not available: " + index);
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
    INode<T> last = getLast(head);
    if (last == null) head = node;
    else last.setNext(node);
    ++length;
  }

  @Override
  protected void link(INode<T> prev, T data, INode<T> next) {
    INode<T> node = new SingleNode<>(data, next);
    if (prev != null)
    prev.setNext(node);
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
  protected T unlinkFirst(INode<T> node) {
    final T data = node.getData();
    final INode<T> next = node.getNext();
    node.setNext(null);
    node.setData(null);
    head = next;
    --length;
    return data;
  }

  @Override
  protected T unlink(INode<T> node) {
    throw new UnsupportedOperationException("Operation not supported.");
  }

  @Override
  protected T unlink(INode<T> prev, INode<T> node) {
    final T data = node.getData();
    final INode<T> next = node.getNext();
    node.setNext(null);
    node.setData(null);
    prev.setNext(next);
    --length;
    return data;
  }

  @Override
  protected T unlinkLast(INode<T> node) {
    throw new UnsupportedOperationException("Operation not supported.");
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
  public Iterator<T> getIterator() {
    return new ListIterator(0);
  }

  final class ListIterator implements Iterator<T> {
    private INode<T> lastReturned = null;
    private INode<T> next;
    private int nextIndex;

    ListIterator(int index) {
      next = (index == length) ? null : get(index);
      nextIndex = index;
    }

    @Override
    public void reset() {
      nextIndex = 0;
      next = get(nextIndex);
    }

    @Override
    public T next() {
      if (!hasNext()) throw new NoSuchElementException();
      lastReturned = next;
      next = next.getNext();
      ++nextIndex;
      return lastReturned.getData();
    }

    @Override
    public boolean hasNext() {
      return nextIndex < length;
    }

    @Override
    public T previous() {
      if (!hasPrevious()) throw new NoSuchElementException("No previous element!");
      lastReturned = next = (next == null) ? get(length - 1) : get(nextIndex - 1);
      --nextIndex;
      return lastReturned.getData();
    }

    @Override
    public boolean hasPrevious() {
      return nextIndex > 0;
    }

    @Override
    public void insertAfter(T data) {
      if (next == null) 
        linkLast(data);
      else
        linkAfter(data,next); 
      ++nextIndex;
    }

    @Override
    public void insertBefore(T data) {}

    @Override
    public T remove() {
      return null;
    }

    @Override
    public void set(T data) {
      if (lastReturned == null) throw new IllegalStateException("Null element cannot be set.");
      lastReturned.setData(data);
    }
  }
}
