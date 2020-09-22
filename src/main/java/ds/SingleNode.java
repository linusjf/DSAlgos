package ds;

import java.util.Objects;

class SingleNode<T extends Object> {

  private static final String NULL_STRING = "NULL";
  private T data;

  private SingleNode<T> next;

  @SuppressWarnings("nullness")
  SingleNode(T data) {
    this.data = data;
  }

  SingleNode<T> getNext() {
    return next;
  }

  void setNext(SingleNode<T> node) {
    this.next = node;
  }

  T getData() {
    return data;
  }

  void setData(T data) {
    this.data = data;
  }

  @Override
  public String toString() {
    return data == null ? NULL_STRING : Objects.toString(data);
  }
}
