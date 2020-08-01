package ds;

public abstract class AbstractSort implements ISort {

  protected abstract void sort(long[] array, int length);

  @Override
  public IArray sort(IArray array) {
    IArray copy = array.copy();
    sort(copy.get(), copy.count());
    return copy;
  }

  protected void swap(long[] a, int first, int second) {
    long temp = a[first];
    a[first] = a[second];
    a[second] = temp;
  }
}
