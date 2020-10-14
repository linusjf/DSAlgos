package ds;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;

public final class DictionaryLookup {

  private static final String URL = "https://api.dictionaryapi.dev/api/v2/entries/en/";
  private static final String GET = "GET";
  private static final String USER_AGENT = "Mozilla/5.0";
  private static final String NO_RESULT_FOUND = "No definitions found";

  private DictionaryLookup() {
    throw new IllegalStateException(DictionaryLookup.class.getName() + ": Private constructor");
  }

  public static boolean isDictionaryWord(String word) throws IOException {
    return isWordAvailable(word);
  }

  @SuppressWarnings("PMD.LawOfDemeter")
  private static boolean isWordAvailable(String word) throws IOException {
    String wordUrl = URL + word;
    URL obj = new URL(wordUrl);
    HttpURLConnection con = (HttpURLConnection) obj.openConnection();
    con.setRequestMethod(GET);
    con.setRequestProperty("User-Agent", USER_AGENT);
    int responseCode = con.getResponseCode();
    if (responseCode == HttpURLConnection.HTTP_OK) {
      try (BufferedReader in =
          new BufferedReader(
              new InputStreamReader(con.getInputStream(), Charset.forName("UTF-8")))) {
        String inputLine;
        StringBuilder response = new StringBuilder();

        while ((inputLine = in.readLine()) != null) response.append(inputLine);
        String result = response.toString();
        return !result.contains(NO_RESULT_FOUND);
      }
    }
    throw new IOException("Error connecting to dictionary service API...");
  }
}
