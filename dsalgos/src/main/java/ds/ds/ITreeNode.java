package ds;

public interface ITreeNode<E> extends Cloneable {
  void insert(E obj);

  ITreeNode<E> find(E obj);

  ITreeNode<E> remove(E obj);

  ITreeNode<E> left();

  void setLeft(ITreeNode<E> node);

  ITreeNode<E> right();

  void setRight(ITreeNode<E> node);

  E value();

  void setValue(E val);

  int size();

  void setSize(int size);

  void setHeight(int height);

  void setPriority(int priority);

  int height();

  int balanceFactor();

  int refCount();

  int priority();

  void incrementRefCount();

  void decrementRefCount();

  ITreeNode<E> clone();
}
