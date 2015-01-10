package net.moznion.capture.output.stream;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

import org.apache.commons.io.output.TeeOutputStream;

/**
 * Tee-er for STDERR.
 * 
 * <p>
 * The tee works just like capture, except that output is captured as well as passed on to the
 * original STDERR.
 * </p>
 * 
 * @author moznion
 *
 */
public class StderrTee implements AutoCloseable {
  private final PrintStream originalStderr;
  private final OutputStream stderrBranch;

  /**
   * Tee STDERR after instantiating this.
   * 
   * @param stderrBranch STRERR stream to capture. Captured STDERR contents can retrieve through
   *        this variable. Original STDERR is also available.
   */
  public StderrTee(OutputStream stderrBranch) {
    originalStderr = System.err;
    this.stderrBranch = stderrBranch;

    TeeOutputStream teeOutputStream = new TeeOutputStream(originalStderr, this.stderrBranch);
    PrintStream stderr = new PrintStream(teeOutputStream);
    System.setErr(stderr);
  }

  @Override
  public void close() throws IOException {
    stderrBranch.close();
    System.setErr(originalStderr);
  }
}
