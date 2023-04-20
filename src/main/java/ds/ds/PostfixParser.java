package ds;

import java.util.Stack;

public class PostfixParser {

  private final Stack<Integer> theStack;
  private final String input;

  public PostfixParser(String s) {
    input = s;
    theStack = new Stack<>();
  }

  public int parse() {
    int interAns = 0;
    for (int j = 0; j < input.length(); j++) {
      char ch = input.charAt(j);
      if (ch >= '0' && ch <= '9') {
        theStack.push((int) (ch - '0'));
        continue;
      }
      int num2 = theStack.pop();
      int num1 = theStack.pop();
      switch (ch) {
        case '+':
          interAns = num1 + num2;
          break;
        case '-':
          interAns = num1 - num2;
          break;
        case '*':
          interAns = num1 * num2;
          break;
        case '/':
          interAns = num1 / num2;
          break;
        case '%':
          interAns = num1 % num2;
          break;
        default:
          interAns = 0;
          break;
      }
      theStack.push(interAns);
    }
    if (!theStack.isEmpty()) interAns = theStack.pop();
    return interAns;
  }
}
