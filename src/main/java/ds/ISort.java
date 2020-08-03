package ds;

public interface ISort {
  IArray sort(IArray array);

  int getComparisonCount();

  int getSwapCount();

  int getCopyCount();

  int getTimeComplexity();
}
