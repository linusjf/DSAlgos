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

  protected abstract void linkBefore(T data, INode<T> next);

  protected abstract void linkFirst(T data);

  protected abstract void linkLast(T data);

  protected abstract T unlink(INode<T> node);

  protected abstract T unlinkFirst();
}
