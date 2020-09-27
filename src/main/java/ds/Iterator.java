package ds;

public interface Iterator<T> {
  void reset();

  INode<T> next();

  boolean hasNext();

  void insertAfter(T data);

  void insertBefore(T data);

  T remove();
}
