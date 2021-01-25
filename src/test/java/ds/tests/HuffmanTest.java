package ds.tests;

import static org.junit.jupiter.api.Assertions.*;

import ds.Huffman;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

@SuppressWarnings("PMD.LawOfDemeter")
@DisplayName("HuffmanTest")
@TestInstance(Lifecycle.PER_CLASS)
@Execution(ExecutionMode.SAME_THREAD)
class HuffmanTest {

  private static final String abraFile = "abra.txt";
  private static final String tinyTalesFile = "tinytales.txt";
  private static final String talesFile = "tale.txt";
  private static final String medTaleFile = "medtale.txt";

  private final String abra;
  private final String tinyTales;
  private final String tales;
  private final String medTale;

  public HuffmanTest() throws IOException {
    abra = readFile(abraFile);
    tinyTales = readFile(tinyTalesFile);
    tales = readFile(talesFile);
    medTale = readFile(medTaleFile);
  }

  private String readFile(String fileName) throws IOException {
    return new String(Files.readAllBytes(Paths.get(fileName)));
  }

  @Test
  @DisplayName("HuffmanTest.testAbraFileCompress")
  public void testAbraFileCompress() {
    Huffman hf = new Huffman(abra);
  }
}
