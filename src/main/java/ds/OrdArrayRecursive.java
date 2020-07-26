package ds;

import java.util.Arrays;

/** Demonstrates array class with high-level interface. */
@SuppressWarnings("PMD.LawOfDemeter")
public class OrdArrayRecursive extends OrdArray {
  @SuppressWarnings("all")
  private static final java.util.logging.Logger LOGGER =
      java.util.logging.Logger.getLogger(OrdArrayRecursive.class.getName());

  public OrdArrayRecursive() {
    // empty constructor, implicitly calls super
  }

  public OrdArrayRecursive(int max) {
    super(max);
  }

  public OrdArrayRecursive(int max, boolean strict) {
    super(max, strict);
  }

  /**
   * Insert element into array.
   *
   * @param value element to insert
   * @return index of inserted element.
   */
  @Override
  public int insert(long value) {
    int length = nElems.intValue();
    if (length == a.length) throw new ArrayIndexOutOfBoundsException(length);
    if (dirty) checkSorted();
    if (sorted) return insert(value, length);
    sort(length);
    return insert(value, length);
  }

  protected int insert(long value, int length) {
    int expectedCount = modCount.intValue();
    int j = findIndex(value, length);
    j = j < 0 ? -1 * j - 1 : j;
    if (strict && expectedCount < modCount.intValue()) {
      dirty = true;
      return insert(value);
    }
    moveAndInsert(j, length, value);
    return j;
  }

  private void sort(int length) {
    Arrays.sort(a, 0, length);
    sorted = true;
  }

  @SuppressWarnings("PMD.DataflowAnomalyAnalysis")
  @Override
  public boolean delete(long value) {
    int length = nElems.intValue();
    if (dirty) checkSorted();
    if (sorted) return delete(value, length);
    return false;
    // sort(nElems.intValue());
    // return delete(value, nElems.intValue());
  }

  protected boolean delete(long value, int length) {
    int expectedCount = modCount.intValue();
    int j = findIndex(value, length);
    if (j < 0) return false;
    if (strict && expectedCount < modCount.intValue()) {
      dirty = true;
      return delete(value);
    }
    fastDelete(j, length);
    return true;
  }
}
