package ds;

import java.util.Stack;

public class PostfixParser {

  private Stack<Integer> theStack;
  private String input;

  public PostfixParser(String s) {
    input = s;
  }

  private void displayStack(String s) {
    System.out.println(s + " : ");
    System.out.println(theStack);
  }

  public int parse() {
    theStack = new Stack<>();
    char ch;
    int j;
    int num1, num2, interAns;
    for (j = 0; j < input.length(); j++) {
      ch = input.charAt(j);
      displayStack("" + ch + " ");
      if (ch >= '0' && ch <= '9') theStack.push((int) (ch - '0'));
      else {
        num2 = theStack.pop();
        num1 = theStack.pop();
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
          default:
            interAns = 0;
        }
        theStack.push(interAns);
      }
    }
    interAns = theStack.pop();
    return interAns;
  }
}
