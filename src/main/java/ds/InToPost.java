package ds;

import java.util.Stack;

/***
 * <p>Infix to PostFix conversion.</p>
 ***/
public class InToPost {
  private Stack<Character> theStack;
  private String input;
  private String output = "";

  public InToPost(String in) {
    input = in;
    theStack = new Stack<>();
  }

  private void displayStack(String s) {
    System.out.println(s + " : ");
    System.out.println(theStack.toString());
  }

  /***
   * <p>Translate to postfix.</p>
   ***/
  public String translate() {
    for (int j = 0; j < input.length(); j++) {
      char ch = input.charAt(j);
      displayStack("For " + ch + " ");
      switch (ch) {
        case '+':
        case '-':
          handleOperator(ch, 1);
          break;
        case '*':
        case '/':
          handleOperator(ch, 2);
          break;
        case '(':
          theStack.push(ch);
          break;
        case ')':
          handleParenthesis(ch);
          break;
        default:
          output = output + ch;
          break;
      }
    }
    while (!theStack.isEmpty()) {
      displayStack("While ");
      output = output + theStack.pop();
    }
    displayStack("End ");
    return output;
  }

  public void handleParenthesis(char ch) {
    while (!theStack.isEmpty()) {
      char chx = theStack.pop();

      if (chx == '(') break;
      else output = output + chx;
    }
  }

  public void handleOperator(char opThis, int prec1) {
    while (!theStack.isEmpty()) {
      char opTop = theStack.pop();
      if (opTop == '(') {
        theStack.push(opTop);
        break;
      } else {
        int prec2;
        if (opTop == '+' || opTop == '-') prec2 = 1;
        else prec2 = 2;
        if (prec2 < prec1) {
          theStack.push(opTop);
          break;
        } else output = output + opTop;
      }
    }
    theStack.push(opThis);
  }
}
