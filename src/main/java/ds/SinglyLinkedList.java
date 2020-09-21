package ds;

public class SinglyLinkedList {

  class Node<T> {

    private T data;

    private Node<T> node;

    Node(T data) {
      this.data = data;
    }

    Node<T> getNode() {
      return node;
    }

    void setNode(Node<T> node) {
      this.node = node;
    }

    T getData() {
      return data;
    }

    void setData(T data) {
      this.data = data;
    }

    @Override
    public String toString() {
      return data.toString();
    }

    @Override
    public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result + ((data == null) ? 0 : data.hashCode());
      result = prime * result + ((node == null) ? 0 : node.hashCode());
      return result;
    }

    @Override
    public boolean equals(Object obj) {
      if (this == obj) return true;
      if (obj == null) return false;
      if (!(obj instanceof Node)) return false;
      Node other = (Node) obj;
      if (data == null) {
        if (other.data != null) return false;
      } else if (!data.equals(other.data)) return false;
      if (node == null) {
        if (other.node != null) return false;
      } else if (!node.equals(other.node)) return false;
      return true;
    }
  }
}
