package ds.tests;

import static org.junit.jupiter.api.Assertions.*;

import ds.HuffmanCompressor;
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

  private static final String ABRA_FILE = "abra.txt";
  private static final String TINY_TALES_FILE = "tinytales.txt";
  private static final String TALES_FILE = "tale.txt";
  private static final String MED_TALE_FILE = "medtale.txt";

  private final String abra;
  private final String tinyTales;
  private final String tales;
  private final String medTale;

  public HuffmanTest() throws IOException {
    abra = readFile(ABRA_FILE);
    tinyTales = readFile(TINY_TALES_FILE);
    tales = readFile(TALES_FILE);
    medTale = readFile(MED_TALE_FILE);
  }

  private String readFile(String fileName) throws IOException {
    return new String(Files.readAllBytes(Paths.get(fileName)));
  }

  @Test
  @DisplayName("HuffmanTest.testAbraFile")
  public void testAbraFile() {
    HuffmanCompressor hfc = new HuffmanCompressor(abra);
  }
}
