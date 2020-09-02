package ds;

public interface IHeap {
void insert(long val);
long findMin();
long findMax();
boolean deleteMax();
boolean deleteMin();
boolean isEmpty();
int size();
}
