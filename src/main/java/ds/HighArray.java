package ds;

/**
 * Demonstrates array class with high-level interface.
 *
 * <p>To run this program: C&gt;java
 */
public class HighArray {
  @SuppressWarnings("all")
  private static final java.util.logging.Logger LOGGER =
      java.util.logging.Logger.getLogger(HighArray.class.getName());

  private long[] a;
  // ref to array a
  private int nElems;

  // number of data items
  // constructor
  // -----------------------------------------------------------
  public HighArray(int max) {
    a = new long[max];
    // create the array
    nElems = 0;
    // no items yet
  }

  // -----------------------------------------------------------
  public int findIndex(long searchKey) {
    // find specified value
    for (int j = 0; j < nElems; j++) if (a[j] == searchKey) return j;
    return -1;
  }

  // -----------------------------------------------------------
  public boolean find(long searchKey) {
    return findIndex(searchKey) >= 0;
  }

  // put element into array
  // -----------------------------------------------------------
  public void insert(long value) {
    // insert it
    a[nElems] = value;
    // increment size
    ++nElems;
  }

  // -----------------------------------------------------------
  public boolean delete(long value) {
    int j = findIndex(value);
    if (j == -1) return false;
    // move higher ones down
    for (int k = j; k < nElems; k++) a[k] = a[k + 1];
    // decrement size
    --nElems;
    return true;
  }

  // displays array contents
  // -----------------------------------------------------------
  @SuppressWarnings({"PMD.SystemPrintln", "PMD.LawOfDemeter"})
  public void display() {
    for (int j = 0; j < nElems; j++) System.out.print(a[j] + " ");
    System.out.println("");
  }
}
