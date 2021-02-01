package ds.tests;

import static org.junit.jupiter.api.Assertions.*;

import ds.BinaryInputStream;
import ds.BinaryOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

@SuppressWarnings("PMD.LawOfDemeter")
@DisplayName("BinaryStreamsTest")
@TestInstance(Lifecycle.PER_CLASS)
@Execution(ExecutionMode.SAME_THREAD)
class BinaryStreamsTest {
  private static final String ABRA_FILE = "abra.txt";

  @Test
  @DisplayName("BinaryStreamsTest.testAbraFile")
  public void testAbraFile() throws IOException {
    BinaryInputStream in = new BinaryInputStream(ABRA_FILE);
    BinaryOutputStream out = new BinaryOutputStream(ABRA_FILE + ".out");

    // read one 8-bit char at a time
    while (!in.isEmpty()) {
      char c = in.readChar();
      out.write(c);
    }
    out.flush();

    assertEquals(getFileSize(ABRA_FILE), getFileSize(ABRA_FILE + ".out"), "Files must be equal.");
  }

  private long getFileSize(String file) throws IOException {
    Path imageFilePath = Paths.get(file);
    FileChannel imageFileChannel = FileChannel.open(imageFilePath);
    return imageFileChannel.size();
  }
}
