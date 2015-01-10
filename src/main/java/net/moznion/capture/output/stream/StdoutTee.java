package net.moznion.capture.output.stream;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

import org.apache.commons.io.output.TeeOutputStream;

/**
 * Tee-er for STDOUT.
 * 
 * <p>
 * The tee works just like capture, except that output is captured as well as passed on to the
 * original STDOUT.
 * </p>
 * 
 * @author moznion
 *
 */
public class StdoutTee implements AutoCloseable {
  private final PrintStream originalStdout;
  private final OutputStream stdoutBranch;

  /**
   * Tee STROUT after instantiating this.
   * 
   * <code>
   * ByteArrayOutputStream stdoutBranch = new ByteArrayOutputStream();
   * 
   * try (StdoutTee tee = new StdoutTee(stdoutBranch)) {
   *   System.out.print("hello");   // <= print "hello" and pass contents to stdoutBranch
   * } // don't pass contents to branch anymore if it reaches here
   * 
   * System.out.print(stdoutBranch.toString()); // <= print "hello" on stdout
   * </code>
   * 
   * @param stdoutBranch STROUT stream to capture. Captured STDOUT contents can retrieve through
   *        this variable. Original STDOUT is also available.
   */
  public StdoutTee(OutputStream stdoutBranch) {
    originalStdout = System.out;
    this.stdoutBranch = stdoutBranch;

    TeeOutputStream teeOutputStream = new TeeOutputStream(originalStdout, this.stdoutBranch);
    PrintStream stdout = new PrintStream(teeOutputStream);
    System.setOut(stdout);
  }

  @Override
  public void close() throws IOException {
    stdoutBranch.close();
    System.setOut(originalStdout);
  }
}
