package ds;

public class BracketChecker {
  private final String input;

  public BracketChecker(String in) {
    input = in;
  }

  private boolean handleClosingBracket(IStack theStack, char ch) {
    boolean matchFound = true;
    if (theStack.isEmpty()) {
      matchFound = false;
    } else {
      char chx = (char) theStack.pop();
      if (!doesBracketMatch(chx, ch)) {
        matchFound = false;
      }
    }
    return matchFound;
  }

  public boolean check() {
    boolean matchFound = true;
    IStack theStack = new LinkedListStack();
    for (int j = 0; j < input.length() && matchFound; j++) {
      char ch = input.charAt(j);
      switch (ch) {
        case '{':
        case '[':
        case '(':
          theStack.push(ch);
          break;

        case '}':
        case ']':
        case ')':
          matchFound = handleClosingBracket(theStack, ch);
          break;

        default:
      }
    }
    if (!theStack.isEmpty()) {
      matchFound = false;
    }
    return matchFound;
  }

  private boolean doesBracketMatch(char start, char end) {
    return start == '{' && end == '}' || start == '[' && end == ']' || start == '(' && end == ')';
  }
}
