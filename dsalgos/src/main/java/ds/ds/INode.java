package ds;

public interface INode<T> {

  T getData();

  void setData(T data);

  @SuppressWarnings({"not.interned", "PMD.CompareObjectsWithEquals"})
  default boolean isSame(INode<T> node) {
    return this == node;
  }

  INode<T> getPrev();

  INode<T> getNext();

  void setNext(INode<T> node);

  void setPrev(INode<T> node);
}
