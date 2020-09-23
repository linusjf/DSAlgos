package ds;

public interface INode<T> {

  T getData();

  void setData(T data);

  boolean distinctCompare(INode<T> node);

  INode<T> getPrev();

  INode<T> getNext();

  void setNext(INode<T> node);

  void setPrev(INode<T> node);
}
