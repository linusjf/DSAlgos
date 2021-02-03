package ds;

interface ITreeNode<E> {
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

  int height();
}
