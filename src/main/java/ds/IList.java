package ds;

public interface IList<T> {
  void add(T data, int index);

  void add(T data);

  void addAtFirst(T data);

  INode<T> find(T data);

  INode<T> get(int index);

  T delete(int index);

  boolean delete(T data);

  int size();

  boolean isEmpty();

  Iterator<T> getIterator();
}
