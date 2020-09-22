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

  @Override
  @SuppressWarnings("all")
  public boolean equals(final Object o) {
    if (o == this) return true;
    if (!(o instanceof SingleNode)) return false;
    final SingleNode<?> other = (SingleNode<?>) o;
    if (!other.canEqual((Object) this)) return false;
    final Object this$data = this.getData();
    final Object other$data = other.getData();
    if (this$data == null ? other$data != null : !this$data.equals(other$data)) return false;
    return true;
  }

  @SuppressWarnings("all")
  protected boolean canEqual(final Object other) {
    return other instanceof SingleNode;
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
