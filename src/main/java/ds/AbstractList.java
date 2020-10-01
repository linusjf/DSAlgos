package ds;

import java.util.ListIterator;

public abstract class AbstractList<T> implements IList<T> {

  @Override
  public abstract void add(T data, int index);

  @Override
  public abstract void add(T data);

  @Override
  public abstract void addAtFirst(T data);

  @Override
  public abstract INode<T> find(T data);

  @Override
  public abstract INode<T> get(int index);

  @Override
  public abstract boolean delete(T data);

  @Override
  public abstract T deleteAt(int index);

  @Override
  public abstract int size();

  @Override
  public abstract boolean isEmpty();

  @Override
  public abstract ListIterator<T> getIterator();

  protected void link(INode<T> prev, T data, INode<T> next) {
    throw new UnsupportedOperationException();
  }

  protected void linkFirst(T data) {
    throw new UnsupportedOperationException();
  }

  protected void linkLast(T data) {
    throw new UnsupportedOperationException();
  }

  protected void linkAfter(T data, INode<T> node) {
    throw new UnsupportedOperationException();
  }

  protected T unlink(INode<T> prev, INode<T> node) {
    throw new UnsupportedOperationException();
  }

  protected T unlink(INode<T> node) {
    throw new UnsupportedOperationException();
  }

  protected T unlinkFirst() {
    throw new UnsupportedOperationException();
  }

  protected T unlinkLast(INode<T> node) {
    throw new UnsupportedOperationException();
  }

  protected T unlinkLast() {
    throw new UnsupportedOperationException();
  }
}
