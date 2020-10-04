package ds;

import static java.util.Objects.*;

import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;

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
    requireNonNull(data, DATA_NON_NULL);
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
    requireNonNull(data, DATA_NON_NULL);
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
    requireNonNull(data, DATA_NON_NULL);
    linkLast(data);
  }

  @SuppressWarnings({"PMD.LawOfDemeter", "nullness:argument.type.incompatible"})
  @Override
  public INode<T> find(T data) {
    requireNonNull(data, DATA_NON_NULL);
    INode<T> node = new SingleNode<>(data);
    if (head.equals(node)) return head;
    INode<T> startNode = next(head);
    while (!node.equals(startNode) && nonNull(startNode)) startNode = next(startNode);
    return startNode;
  }

  @SuppressWarnings({
    "PMD.LawOfDemeter",
    "nullness:argument.type.incompatible",
    "fenum:argument.type.incompatible"
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
    INode<T> currNode = next(head);
    while (!node.equals(currNode) && nonNull(currNode)) {
      prevNode = currNode;
      currNode = next(currNode);
    }
    return isNull(unlink(prevNode, currNode)) ? false : true;
  }

  @Override
  public T deleteAt(int index) {
    checkIndex(index, length);
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
    checkIndex(index, length);
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
    INode<T> nextNode = next(node);
    return isNull(nextNode) ? node : getLast(nextNode);
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
    if (isNull(head)) head = node;
    else {
      INode<T> last = getLast(head);
      last.setNext(node);
    }
    ++length;
  }

  @Override
  protected void link(INode<T> prev, T data, INode<T> next) {
    INode<T> node = new SingleNode<>(data, next);
    if (nonNull(prev)) prev.setNext(node);
    ++length;
  }

  @Override
  protected T unlinkFirst() {
    INode<T> node = head;
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
    if (isNull(node)) return null;
    final T data = node.getData();
    final INode<T> next = node.getNext();
    node.setNext(null);
    node.setData(null);
    prev.setNext(next);
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
    return new ListIter();
  }

  @Override
  public ListIterator<T> getIteratorFromIndex(int index) {
    return new ListIter(index);
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
      nextNode = (index == length) ? null : get(index);
      lastReturned = index > 0 ? get(index - 1) : null;
      nextIndex = index;
    }

    @Override
    public T next() {
      if (isNull(nextNode)) throw new NoSuchElementException("No more elements!");
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
      if (isNull(lastReturned)) throw new NoSuchElementException("No more elements.");
      nextNode = lastReturned;
      lastReturned = get(nextIndex - 1);
      --nextIndex;
      return lastReturned.getData();
    }

    @Override
    public boolean hasPrevious() {
      return nextIndex > 0;
    }

    @Override
    public void add(T data) {
      if (isNull(nextNode)) {
        linkFirst(data);
      } else {
        lastReturned =
            lastReturned == null ? hasPrevious() ? get(nextIndex - 1) : null : lastReturned;
        link(lastReturned, data, nextNode);
      }
      ++nextIndex;
    }

    @Override
    public void remove() {
      if (isNull(lastReturned))
        throw new IllegalStateException("Remove already invoked or next not invoked!");
      unlink(get(nextIndex - 1), lastReturned);
      lastReturned = null;
      --nextIndex;
    }

    @Override
    public void set(T data) {
      if (isNull(lastReturned)) throw new IllegalStateException("Null element cannot be set.");
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
