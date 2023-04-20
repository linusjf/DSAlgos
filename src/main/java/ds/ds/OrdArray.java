package ds;

/** Demonstrates array class with high-level interface. */
public class OrdArray extends AbstractOrdArray {

  public OrdArray() {
    // empty constructor
  }

  public OrdArray(int max) {
    this(max, false);
  }

  public OrdArray(int max, boolean strict) {
    super(max, strict);
  }

  public OrdArray(OrdArray array) {
    super(array);
  }

  @Override
  public IArray copy() {
    return new OrdArray(this);
  }

  @Override
  @SuppressWarnings("all")
  public boolean equals(final Object o) {
    if (o == this) return true;
    if (!(o instanceof OrdArray)) return false;
    final OrdArray other = (OrdArray) o;
    if (!other.canEqual((Object) this)) return false;
    if (!super.equals(o)) return false;
    return true;
  }

  @SuppressWarnings("all")
  protected boolean canEqual(final Object other) {
    return other instanceof OrdArray;
  }

  @Override
  @SuppressWarnings("all")
  public int hashCode() {
    final int result = super.hashCode();
    return result;
  }
}
