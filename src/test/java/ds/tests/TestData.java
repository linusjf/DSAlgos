package ds.tests;

class TestData {
  public static final String INIT_DATA = "100, false, 77L,99L,44L,55L,22L,88L,11L,00L,66L,33L";

  public static final String INIT_STRICT_DATA =
      "100, true, 77L,99L,44L,55L,22L,88L,11L,00L,66L,33L";

  public static final String INIT_TOSTRING_DATA = "100, false, 77L,99L,44L";

  public static final String INIT_EXCEPTION_DATA = "3, false, 77L,99L,44L";

  public static final String INIT_SEQ_DATA = "100, false, 11L,12L,13L,14L,15L,16L,17L,18L,19L,20L";

  public static final String INIT_DUPLICATE_DATA =
      "100, false, "
          + "77L,77L,99L,77L,99L,44L,55L,22L,22L,88L,88L,11L,11L,11L,00L,00L,00L,00L,66L,33L,33L";

  public static final String INIT_UNSORTED_DATA =
      "100, false, 77L,99L,44L,55L,22L,88L,11L,00L,66L,33L";

  public static final String INIT_ALL_SAME_DATA =
      "100, false, 43L,43L,43L,43L,43L,43L,43L,43L,43L,43L";
}
