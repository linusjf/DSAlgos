package ds;

public interface Iterator<T> {
  void reset();

  T next();

  boolean hasNext();

  T previous();

  boolean hasPrevious();

  void add(T data);

  int nextIndex();

  int previousIndex();

  T remove();

  void set(T data);
}
