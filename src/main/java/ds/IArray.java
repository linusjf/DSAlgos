package ds;

public interface IArray {

  long[] get();

  int count();

  void clear();

  int findIndex(long searchKey);

  boolean find(long searchKey);

  int insert(long value);

  int syncInsert(long value);

  boolean delete(long value);

  boolean syncDelete(long value);

  void display();
}
