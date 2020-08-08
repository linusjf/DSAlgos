package ds.tests;

final class TestData {
  public static final String INIT_DATA = "100, false, 77,99,44,55,22,88,11,00,66,33";

  public static final String INIT_FULL_DATA = "10, false, 77,99,44,55,22,88,11,00,66,33";

  public static final String INIT_STRICT_DATA = "100, true, 77,99,44,55,22,88,11,00,66,33";

  public static final String INIT_TOSTRING_DATA = "100, false, 77,99,44";

  public static final String INIT_EXCEPTION_DATA = "3, false, 77,99,44";

  public static final String INIT_SEQ_DATA = "100, false, 11,12,13,14,15,16,17,18,19,20";

  public static final String INIT_DUPLICATE_DATA =
      "100, false, " + "77,77,99,77,99,44,55,22,22,88,88,11,11,11,00,00,00,00,66,33,33";

  public static final String INIT_UNSORTED_DATA = "100, false, 77,99,44,55,22,88,11,00,66,33";

  public static final String INIT_SORTED_DATA = "100, false, 43,61,61,69,72,75,87,92,101,102";

  public static final String INIT_ALL_SAME_DATA = "100, false, 43,43,43,43,43,43,43,43,43,43";

  public static final String INIT_SELECTION_SORT_DATA =
      "100, false, 14, 33, 27, 10, 35, 19, 42, 44";

  public static final String INIT_INSERTION_SORT_DATA = "100, false, 4, 3, 2, 10, 12, 1, 5, 6";

  public static final String INIT_BUBBLE_SORT_DATA = "100, false, 14, 33, 27, 35, 10";

  public static final String INIT_BRICK_SORT_DATA = "100, false, 3, 2, 3, 8, 5, 6, 4, 1";

  private TestData() {
    // empty constructor
  }
}
