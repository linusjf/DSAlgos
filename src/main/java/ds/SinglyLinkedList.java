package ds;

public class SinglyLinkedList<T extends Object> {

  private int size;
  private SingleNode<T> head;

  @SuppressWarnings("nullness")
  public SinglyLinkedList() {
    size = 0;
    head = null;
  }

  /**
   * Add element at end.
   *
   * @param data - data to be added to list.
   */
  public void add(T data) {
    if (data == null) return;
    if (head == null) head = new SingleNode<T>(data);
    else {
      SingleNode<T> newNode = new SingleNode<T>(data);
      SingleNode<T> lastNode = getLastNode(head);
      lastNode.setNext(newNode);
    }
    ++size;
  }

  /**
   * Add the element at specified index. Index start from 0 to n-1 where n=size of linked list. If
   * index is negative value, nothing will be added to linked list.
   *
   * <p>if index =0 , element will be added at head and element become the first node.
   *
   * @param data - data to be added at index.
   * @param index - index at which element to be added.
   */
  public void add(T data, int index) throws IndexOutOfBoundsException {
    if (data == null) return;
    // If index=0 , we should add the data at head
    if (index == 0) {
      addAtFirst(data);
      return;
    }
    // If index= size, we should add the data at last
    if (index == this.size) add(data);
    else if (index < this.size) {
      SingleNode<T> newNode = new SingleNode<T>(data);
      // get the node at (index) from linked list and mark as rightNode.
      // get the node at (index-1) from linked list and mark as leftNode.
      // set node of newly created node as right node.
      // set node of left node as newly created Node.
      SingleNode<T> leftNode = getNode(index - 1);
      SingleNode<T> rightNode = getNode(index);
      newNode.setNext(rightNode);
      leftNode.setNext(newNode);
      ++size;
    } else throw new IndexOutOfBoundsException("Index not available.");
  }

  /**
   * Add element at first node. Set the newly created node as root node.
   *
   * @param data Add data node at beginning.
   */
  public void addAtFirst(T data) {
    if (data == null) return;
    SingleNode<T> newNode = new SingleNode<T>(data);
    if (this.head != null) {
      newNode.setNext(this.head);
      this.head = newNode;
    } else this.head = newNode;
    ++size;
  }

  private SingleNode<T> getNode(int index) {
    if (index < 0 || index > this.size - 1)
      throw new IndexOutOfBoundsException("Index not available: " + index);
    if (index == 0) return this.head;
    if (index == this.size - 1) return getLastNode(this.head);
    int pointer = 0;
    SingleNode<T> pointerNode = this.head;
    while (pointer <= index) {
      if (pointer == index) break;
      else {
        pointerNode = next(pointerNode);
        pointer++;
      }
    }
    return pointerNode;
  }

  private SingleNode<T> getLastNode(SingleNode<T> node) {
    SingleNode<T> lastNode = node;
    if (lastNode.getNext() != null) return getLastNode(lastNode.getNext());
    else return lastNode;
  }

  private SingleNode<T> next(SingleNode<T> node) {
    return node.getNext();
  }

  public int size() {
    return this.size;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder(2);
    sb.append('[');
    SingleNode<T> nextNode = this.head;
    while (nextNode != null) {
      sb.append(nextNode);
      nextNode = next(nextNode);
      if (nextNode != null) 
        sb.append(',');
    }
    sb.append(']');
    return sb.toString();
  }
}
