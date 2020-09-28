package ds;

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
  public abstract int size();

  @Override
  public abstract boolean isEmpty();

  @Override
  public abstract Iterator<T> getIterator();

  protected abstract void link(INode<T> prev, T data, INode<T> next);

  protected abstract void linkFirst(T data);

  protected abstract void linkLast(T data);

  protected abstract T unlink(INode<T> node);

  protected abstract T unlinkFirst(INode<T> node);

  protected abstract T unlinkLast(INode<T> node);

  protected abstract T unlink(INode<T> prev, INode<T> node);
}
