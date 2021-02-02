package ds;

interface ITreeNode<E> {
  void insert(E obj);

  ITreeNode<E> find(E obj);

  ITreeNode<E> remove(E obj);
}
