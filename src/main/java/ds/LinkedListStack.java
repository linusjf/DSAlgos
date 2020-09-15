package ds;

public class LinkedListStack implements IStack {

  StackNode[] root = new StackNode[0];
  int nElems;

  @Override
  public boolean isEmpty() {
    return root.length == 0;
  }

  @Override
  public void push(long data) {
    StackNode newNode = new StackNode(data);
    if (isEmpty()) {
      root = new StackNode[1];
      root[0] = newNode;
    } else {
      StackNode temp = root[0];
      root[0] = newNode;
      newNode.next = new StackNode[1];
      newNode.next[0] = temp;
    }
    ++nElems;
  }

  @Override
  public long pop() {
    StackNode node = root[0];
    long popped = node.data;
    root = node.next;
    --nElems;
    return popped;
  }

  @Override
  public long peek() {
    return root[0].data;
  }

  @Override
  public boolean isFull() {
    return false;
  }

  @Override
  public int size() {
    return nElems;
  }

  static class StackNode {
    long data;
    StackNode[] next = new StackNode[0];

    StackNode(long data) {
      this.data = data;
    }
  }
}
