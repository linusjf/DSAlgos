package ds;

import java.util.Objects;

public class DoubleNode<T> implements INode<T> {
  private T data;
  private DoubleNode<T> prev;
  private DoubleNode<T> next;

  @SuppressWarnings("nullness")
  DoubleNode(T data) {
    Objects.requireNonNull(data, "Data cannot be null.");
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

  @Override
  @SuppressWarnings("all")
  public boolean equals(final Object o) {
    if (o == this) return true;
    if (!(o instanceof DoubleNode)) return false;
    final DoubleNode<?> other = (DoubleNode<?>) o;
    if (!other.canEqual((Object) this)) return false;
    final Object this$data = this.getData();
    final Object other$data = other.getData();
    if (this$data == null ? other$data != null : !this$data.equals(other$data)) return false;
    return true;
  }

  @SuppressWarnings("all")
  protected boolean canEqual(final Object other) {
    return other instanceof DoubleNode;
  }

  @Override
  @SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    final Object $data = this.getData();
    result = result * PRIME + ($data == null ? 43 : $data.hashCode());
    return result;
  }

  @Override
  @SuppressWarnings("not.interned")
  public boolean distinctCompare(INode<T> node) {
    return this == node;
  }
}
