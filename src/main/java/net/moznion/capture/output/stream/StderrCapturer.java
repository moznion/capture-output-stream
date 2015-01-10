package net.moznion.capture.output.stream;

import java.io.OutputStream;
import java.io.PrintStream;

/**
 * Capturer for STDERR.
 * 
 * @author moznion
 *
 */
public class StderrCapturer implements AutoCloseable {
  private final PrintStream originalStderr;

  /**
   * Capture STDERR after instantiating this.
   * 
   * @param alternativeStderr STDERR stream to capture. Captured STDERR contents can retrieve
   *        through this variable.
   */
  public StderrCapturer(OutputStream alternativeStderr) {
    originalStderr = System.err;
    System.setErr(new PrintStream(alternativeStderr));
  }

  @Override
  public void close() {
    System.setErr(originalStderr);
  }
}
