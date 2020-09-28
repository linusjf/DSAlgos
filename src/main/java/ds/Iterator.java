package ds;

public interface Iterator<T> {
  void reset();

  T next();

  boolean hasNext();

  T previous();

  boolean hasPrevious();

  void insertAfter(T data);

  void insertBefore(T data);

  T remove();

  void set(T data);
}
