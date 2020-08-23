package ds;

public class BracketChecker {
  private final String input;

  public BracketChecker(String in) {
    input = in;
  }

  public boolean check() {
    boolean matchFound = true;
    LinkedListStack theStack = new LinkedListStack();
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
          if (!theStack.isEmpty()) {
            char chx = (char) theStack.pop();
            if (!doesBracketMatch(chx, ch)) {
              System.out.println("Error: " + ch + " at " + j);
              matchFound = false;
            }
          } else {
            System.out.println("Error: " + ch + " at " + j);
            matchFound = false;
          }
          break;

        default:
      }
    }
    if (!theStack.isEmpty()) {
      System.out.println("Error: missing right delimiter");
      matchFound = false;
    }
    return matchFound;
  }

  private boolean doesBracketMatch(char start, char end) {
    if (start == '{' && end == '}') return true;
    if (start == '[' && end == ']') return true;
    if (start == '(' && end == ')') return true;
    return false;
  }
}
