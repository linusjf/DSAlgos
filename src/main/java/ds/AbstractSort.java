package ds;

public class AbstractSort implements ISort {

  @Override
  public abstract IArray sort(
      IArray array);

  protected abstract void swap(
      int first, 
      int second); 

}
