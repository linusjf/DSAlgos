package ds;

import java.util.concurrent.Callable;

final class BubbleTask implements Cloneable, Callable<Void> {
  enum TaskType {
    START_ODD,
    START_EVEN,
    BUBBLE
  }

  long[] a;
  int i;
  AbstractBrickSort sorter;
  TaskType type;

  @SuppressWarnings("PMD.ArrayIsStoredDirectly")
  BubbleTask(AbstractBrickSort sorter, long[] a, int i, TaskType type) {
    this.sorter = sorter;
    this.a = a;
    this.i = i;
    this.type = type;
  }

  @Generated
  @SuppressWarnings("checkstyle:NoClone")
  @Override
  public BubbleTask clone() {
    try {
      return (BubbleTask) super.clone();
    } catch (CloneNotSupportedException cnse) {
      throw new AssertionError("Shouldn't get here.." + cnse.getMessage(), cnse);
    }
  }

  @Override
  public Void call() {
    switch (type) {
      case START_ODD:
        sorter.bubbleStartOdd(a);
        break;

      case START_EVEN:
        sorter.bubbleStartEven(a);
        break;

      case BUBBLE:
        sorter.bubble(a, i);
        break;
    }
    return null;
  }

  public static BubbleTask createCopy(BubbleTask bt) {
    return bt.clone();
  }
}
