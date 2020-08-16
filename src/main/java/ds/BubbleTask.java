package ds;

import java.util.concurrent.Callable;

final class BubbleTask implements Callable<Void> {
  long[] a;
  int i;
  BrickSortParallel sorter;

  @SuppressWarnings("PMD.ArrayIsStoredDirectly")
  BubbleTask(BrickSortParallel sorter, long[] a, int i) {
    this.sorter = sorter;
    this.a = a;
    this.i = i;
  }

  @Override
  public Void call() throws Exception {
    sorter.bubble(a, i);
    return null;
  }
}
