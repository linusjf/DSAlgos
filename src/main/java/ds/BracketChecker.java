package ds;

public class BracketChecker {
  private final String input;

  public BracketChecker(String in) {
    input = in;
  }

  public void check() {
    LinkedListStack theStack = new LinkedListStack();
    for (int j = 0; j < input.length(); j++) {
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
            if ((ch == '}' && chx != '{') || (ch == ']' && chx != '[') || (ch == ')' && chx != '('))
              System.out.println("Error: " + ch + " at " + j);
          } else 
            System.out.println("Error: " + ch + " at " + j);
          break;
            
        default:
      }
    }
    if (!theStack.isEmpty()) 
      System.out.println("Error: missing right delimiter");
  }
}
