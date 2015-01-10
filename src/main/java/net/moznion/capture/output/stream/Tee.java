package net.moznion.capture.output.stream;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Tee-er for STDOUT and STDERR.
 * 
 * <p>
 * The tee works just like capture, except that output is captured as well as passed on to the
 * original STDOUT and STDERR.
 * </p>
 * 
 * @author moznion
 *
 */
public class Tee implements AutoCloseable {
  private final StdoutTee stdoutTee;
  private final StderrTee stderrTee;

  /**
   * Tee STDOUT and STDERR independently after instantiating this.
   * 
   * @param stdoutBranch STROUT stream to capture. Captured STDOUT contents can retrieve through
   *        this variable. Original STDOUT is also available.
   * @param stderrBranch STRERR stream to capture. Captured STDERR contents can retrieve through
   *        this variable. Original STDERR is also available.
   */
  public Tee(OutputStream stdoutBranch, OutputStream stderrBranch) {
    stdoutTee = new StdoutTee(stdoutBranch);
    stderrTee = new StderrTee(stderrBranch);
  }

  /**
   * Tee STDOUT and STDERR which are merged after instantiating this.
   * 
   * @param mergedBranch STDOUT and STDERR stream to capture. Captured merged contents can retrieve
   *        through this variable. Original STDOUT and STDERR are available for each independently.
   */
  public Tee(OutputStream mergedBranch) {
    stdoutTee = new StdoutTee(mergedBranch);
    stderrTee = new StderrTee(mergedBranch);
  }

  @Override
  public void close() throws IOException {
    stdoutTee.close();
    stderrTee.close();
  }
}
