package ds;

class DoubleNode<T extends Object> {

  private static final String NULL_STRING = "NULL";

  private T data;
  private DoubleNode<T> prev;
  private DoubleNode<T> next;

  @SuppressWarnings("nullness")
  DoubleNode(T data) {
    this.data = data;
    this.next = null;
    this.prev = null;
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

  T getData() {
    return data;
  }

  void setData(T data) {
    this.data = data;
  }

  @Override
  public String toString() {
    return data == null ? NULL_STRING : data + "";
  }
}
