package ds;

public class Reverser {
  private final String input;

  public Reverser(String in) {
    input = in;
  }

  public String doRev() {
    int stackSize = input.length();
    SimpleStack theStack = new SimpleStack(stackSize);
    for (int j = 0; j < stackSize; j++) {
      char ch = input.charAt(j);
      theStack.push(ch);
    }
    StringBuilder sb = new StringBuilder();
    while (!theStack.isEmpty()) {
      char ch = (char) theStack.pop();
      sb.append(ch);
    }
    return sb.toString();
  }
}
