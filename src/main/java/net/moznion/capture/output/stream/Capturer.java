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
   * <code>
   * ByteArrayOutputStream stdout = new ByteArrayOutputStream();
   * ByteArrayOutputStream stderr = new ByteArrayOutputStream();
   * 
   * try (Capturer capturer = new Capturer(stdout, stderr)) {
   *    System.out.print("hello");   // <= don't print anything
   *    System.err.print("goodbye"); // <= don't print anything
   * } // turn back to original stdout and stderr at here
   * 
   * System.out.print(stdout.toString()); // <= print "hello" on stdout
   * System.err.print(stderr.toString()); // <= print "goodbye" on stderr
   * </code>
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
   * <code>
   * ByteArrayOutputStream mergedOut = new ByteArrayOutputStream();
   * 
   * try (Capturer capturer = new Capturer(mergedOut)) {
   *    System.out.print("hello");   // <= don't print anything
   *    System.err.print("goodbye"); // <= don't print anything
   * } // turn back to original stdout and stderr at here
   * 
   * System.out.print(mergedOut.toString()); // <= print "hellogoodbye" on stdout
   * </code>
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
