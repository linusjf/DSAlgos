package ds;

public abstract class AbstractSort implements ISort {

  @Override
  protected abstract void sort(long[] array, int length);

  @Override
  public abstract IArray sort(IArray array);

  protected void swap(long[] a, int first, int second) {
    long temp = a[first];
    a[first] = a[second];
    a[second] = temp;
  }
}
