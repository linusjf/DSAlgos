package ds;

public interface ISortInterruptible extends ISort {
  IArray sortInterruptibly(IArray array) throws Exception;
}
