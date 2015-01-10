package net.moznion.capture.output.stream;

import java.io.OutputStream;
import java.io.PrintStream;

/**
 * Capturer for STDOUT.
 * 
 * @author moznion
 *
 */
public class StdoutCapturer implements AutoCloseable {
  private final PrintStream originalStdout;

  /**
   * Capture STDOUT after instantiating this.
   * 
   * @param alternativeStdout STROUT stream to capture. Captured STDOUT contents can retrieve
   *        through this variable.
   */
  public StdoutCapturer(OutputStream alternativeStdout) {
    originalStdout = System.out;
    System.setOut(new PrintStream(alternativeStdout));
  }

  @Override
  public void close() {
    System.setOut(originalStdout);
  }
}
