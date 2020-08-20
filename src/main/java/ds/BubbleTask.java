package ds;

import java.util.concurrent.Callable;

final class BubbleTask implements Callable<Void> {
  long[] a;
  int i;
  AbstractBrickSort sorter;

  @SuppressWarnings("PMD.ArrayIsStoredDirectly")
  BubbleTask(AbstractBrickSort sorter, long[] a, int i) {
    this.sorter = sorter;
    this.a = a;
    this.i = i;
  }

  @Override
  public Void call() {
    sorter.bubble(a, i);
    return null;
  }
}
