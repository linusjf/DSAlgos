package ds;

public interface Iterator<T> {
  void reset();

  void next();

  boolean atEnd();

  INode<T> current();

  void insertAfter(T data);

  void insertBefore(T data);

  T deleteCurrent();
}
