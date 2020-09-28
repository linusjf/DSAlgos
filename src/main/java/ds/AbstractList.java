package ds;

public abstract class AbstractList<T> implements IList<T> {

  public abstract void add(T data, int index);

  public abstract void add(T data);

  public abstract void addAtFirst(T data);

  public abstract INode<T> find(T data);

  public abstract INode<T> get(int index);

  public abstract boolean delete(T data);

  public abstract int size();

  public abstract boolean isEmpty();

  public abstract Iterator<T> getIterator();

  public abstract void linkBefore(T data, INode<T> node);

  public abstract void linkAfter(T data, INode<T> node);

  public abstract void linkFirst(T data);

  public abstract void linkLast(T data);

  public abstract T unlink(INode<T> node);

  public abstract T unlinkFirst(INode<T> node);

  public abstract T unlinkLast(INode<T> node);
}
