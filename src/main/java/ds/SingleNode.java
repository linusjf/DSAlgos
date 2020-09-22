package ds;

import java.util.Objects;

class SingleNode<T> implements INode<T> {

  private T data;

  @SuppressWarnings("initialization.fields.uninitialized")
  private SingleNode<T> next;

  SingleNode(T data) {
    if (data == null) throw new NullPointerException("Data cannot be null.");
    this.data = data;
  }

  SingleNode<T> getNext() {
    return next;
  }

  void setNext(SingleNode<T> node) {
    this.next = node;
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
