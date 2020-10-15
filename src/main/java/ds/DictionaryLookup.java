package ds;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

@Generated
public final class DictionaryLookup {

  private static final String URL = "https://api.dictionaryapi.dev/api/v2/entries/en/";
  private static final String GET = "GET";
  private static final String USER_AGENT = "Mozilla/5.0";

  private DictionaryLookup() {
    throw new IllegalStateException(DictionaryLookup.class.getName() + ": Private constructor");
  }

  public static boolean isDictionaryWord(String word) throws IOException {
    return isWordAvailable(word);
  }

  /** To be used with caution. Rate limiting applies. Only for Proof of Concept(POC) purposes. */
  @SuppressWarnings("PMD.LawOfDemeter")
  private static boolean isWordAvailable(String word) throws IOException {
    String wordUrl = URL + word;
    URL obj = new URL(wordUrl);
    HttpURLConnection con = (HttpURLConnection) obj.openConnection();
    con.setRequestMethod(GET);
    con.setRequestProperty("User-Agent", USER_AGENT);
    return con.getResponseCode() == HttpURLConnection.HTTP_OK;
  }
}
