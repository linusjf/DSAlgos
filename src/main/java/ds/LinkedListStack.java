package ds;

public class LinkedListStack {

  StackNode root;

  public boolean isEmpty() {
    return root == null;
  }

  public void push(long data) {
    StackNode newNode = new StackNode(data);
    if (root == null) root = newNode;
    else {
      StackNode temp = root;
      root = newNode;
      newNode.next = temp;
    }
  }

  public long pop() {
    if (root == null) return Long.MIN_VALUE;
    long popped = root.data;
    root = root.next;
    return popped;
  }

  public long peek() {
    return root == null ? Long.MIN_VALUE : root.data;
  }

  static class StackNode {
    long data;
    StackNode next;

    StackNode(long data) {
      this.data = data;
    }
  }
}
