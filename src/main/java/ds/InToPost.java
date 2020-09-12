package ds;

import java.util.Stack;

/***
 * <p>Infix to PostFix conversion.</p>
 ***/
public class InToPost {
  private static final char PLUS_OPERATOR = '+';
  private static final char MINUS_OPERATOR = '-';
  private static final char MULT_OPERATOR = '*';
  private static final char DIV_OPERATOR = '/';
  private static final char LEFT_PARENTHESIS = '(';
  private static final char RIGHT_PARENTHESIS = ')';
  private final Stack<Character> theStack;
  private final String input;

  public InToPost(String in) {
    input = in;
    theStack = new Stack<>();
  }

  @SuppressWarnings("PMD.SystemPrintln")
  private void displayStack(String s) {
    System.out.println(s + " : ");
    System.out.println(theStack.toString());
  }

  /***
   * <p>Translate to postfix.</p>
   ***/
  public String translate() {
    int length = input.length();
    StringBuilder output = new StringBuilder(length);
    for (int j = 0; j < length; j++) {
      char ch = input.charAt(j);
      displayStack("For " + ch + " ");
      switch (ch) {
        case PLUS_OPERATOR:
        case MINUS_OPERATOR:
          handleOperator(ch, 1, output);
          break;
        case MULT_OPERATOR:
        case DIV_OPERATOR:
          handleOperator(ch, 2, output);
          break;
        case LEFT_PARENTHESIS:
          theStack.push(ch);
          break;
        case RIGHT_PARENTHESIS:
          handleParenthesis(ch, output);
          break;
        default:
          output.append(ch);
          break;
      }
    }
    while (!theStack.isEmpty()) {
      displayStack("While ");
      output.append(theStack.pop());
    }
    displayStack("End ");
    return output.toString();
  }

  public void handleParenthesis(char ch, StringBuilder output) {
    while (!theStack.isEmpty()) {
      char chx = theStack.pop();
      if (chx == LEFT_PARENTHESIS) break;
      else output.append(chx);
    }
  }

  public void handleOperator(char opThis, int prec1, StringBuilder output) {
    while (!theStack.isEmpty()) {
      char opTop = theStack.pop();
      if (opTop == LEFT_PARENTHESIS) {
        theStack.push(opTop);
        break;
      } else {
        int prec2;
        if (opTop == PLUS_OPERATOR || opTop == MINUS_OPERATOR) prec2 = 1;
        else prec2 = 2;
        if (prec2 < prec1) {
          theStack.push(opTop);
          break;
        } else output.append(opTop);
      }
    }
    theStack.push(opThis);
  }
}
