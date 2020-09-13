package ds;

/***
 * <p>Deque implementation in Java.</p>
 ***/
@SuppressWarnings("PMD.SystemPrintln")
public class Deque {
  long[] arr;
  int front;
  int rear;

  public Deque(int size) {
    arr = new long[size];
    front = -1;
    rear = 0;
  }

  public boolean isFull() {
    return front == 0 && rear == size - 1 || front == rear + 1;
  }

  public boolean isEmpty() {
    return front == -1;
  }

  public void insertFront(long key) {
    if (isFull()) {
      System.out.println("Overflow");
      return;
    }

    if (front == -1) {
      front = 0;
      rear = 0;
    } else if (front == 0) front = size - 1;
    else front = front - 1;

    arr[front] = key;
  }

  public void insertRear(long key) {
    if (isFull()) {
      System.out.println(" Overflow ");
      return;
    }

    if (front == -1) {
      front = 0;
      rear = 0;
    } else if (rear == size - 1) rear = 0;
    else rear = rear + 1;

    arr[rear] = key;
  }

  public void deleteFront() {
    if (isEmpty()) {
      System.out.println("Queue Underflow\n");
      return;
    }

    // Deque has only one element
    if (front == rear) {
      front = -1;
      rear = -1;
    } else if (front == size - 1) front = 0;
    else front = front + 1;
  }

  public void deleteRear() {
    if (isEmpty()) {
      System.out.println(" Underflow");
      return;
    }

    if (front == rear) {
      front = -1;
      rear = -1;
    } else if (rear == 0) rear = size - 1;
    else rear = rear - 1;
  }

  public long getFront() {
    if (isEmpty()) {
      System.out.println(" Underflow");
      return -1;
    }
    return arr[front];
  }

  public long getRear() {
    if (isEmpty() || rear < 0) {
      System.out.println(" Underflow\n");
      return -1;
    }
    return arr[rear];
  }
}
