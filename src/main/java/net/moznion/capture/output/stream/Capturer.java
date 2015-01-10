package net.moznion.capture.output.stream;

import java.io.OutputStream;

/**
 * Capturer for STDOUT and STDERR.
 * 
 * @author moznion
 * 
 */
public class Capturer implements AutoCloseable {
  private final StdoutCapturer stdoutCapturer;
  private final StderrCapturer stderrCapturer;

  /**
   * Capture STDOUT and STDERR independently after instantiating this.
   * 
   * @param alternativeStdout STROUT stream to capture. Captured STDOUT contents can retrieve
   *        through this variable.
   * @param alternativeStderr STDERR stream to capture. Captured STDERR contents can retrieve
   *        through this variable.
   */
  public Capturer(OutputStream alternativeStdout, OutputStream alternativeStderr) {
    stdoutCapturer = new StdoutCapturer(alternativeStdout);
    stderrCapturer = new StderrCapturer(alternativeStderr);
  }

  /**
   * Capture STDOUT and STDERR which are merged after instantiating this.
   * 
   * @param mergedOut STDOUT and STDERR stream to capture. Captured merged contents can retrieve
   *        through this variable.
   */
  public Capturer(OutputStream mergedOut) {
    stdoutCapturer = new StdoutCapturer(mergedOut);
    stderrCapturer = new StderrCapturer(mergedOut);
  }

  @Override
  public void close() {
    stdoutCapturer.close();
    stderrCapturer.close();
  }
}
