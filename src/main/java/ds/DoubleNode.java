package ds;

import java.util.Objects;

class DoubleNode<T> implements INode<T> {

  private static final String NULL_STRING = "NULL";

  private T data;
  private DoubleNode<T> prev;
  private DoubleNode<T> next;

  @SuppressWarnings("nullness")
  DoubleNode(T data) {
    if (data == null) throw new NullPointerException("Data cannot be null.");
    this.data = data;
  }

  DoubleNode<T> getPrev() {
    return prev;
  }

  DoubleNode<T> getNext() {
    return next;
  }

  void setNext(DoubleNode<T> node) {
    this.next = node;
  }

  void setPrev(DoubleNode<T> node) {
    this.prev = node;
  }

  @Override
  public T getData() {
    return data;
  }

  @Override
  public void setData(T data) {
    this.data = data;
  }

  @Override
  @SuppressWarnings("fenum:argument.type.incompatible")
  public String toString() {
    return Objects.toString(data);
  }
}
