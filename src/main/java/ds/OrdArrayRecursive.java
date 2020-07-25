package ds;

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

  protected int insert(long value, int length) {
    int expectedCount = modCount.intValue();
    int j = findIndex(value, length);
    j = j < 0 ? -1 * j - 1 : j;
    if (strict && expectedCount < modCount.intValue()) return insert(value);
    moveAndInsert(j, length, value);
    return j;
  }

  protected boolean delete(long value, int length) {
    int expectedCount = modCount.intValue();
    int j = findIndex(value, length);
    if (j < 0) return false;
    if (strict && expectedCount < modCount.intValue()) return delete(value);
    fastDelete(j, length);
    return true;
  }
}
