package ds;

public interface IArray {

  long[] get();

  long[] getExtentArray();

  int count();

  void clear();

  int findIndex(long searchKey);

  boolean find(long searchKey);

  int insert(long value);

  int syncInsert(long value);

  boolean delete(long value);

  boolean syncDelete(long value);

  IArray copy();

  IArray sort(ISort sorter);
}
