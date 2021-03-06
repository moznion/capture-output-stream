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
   * <pre><code>
   * ByteArrayOutputStream stdoutBranch = new ByteArrayOutputStream();
   * ByteArrayOutputStream stderrBranch = new ByteArrayOutputStream();
   * 
   * try (Tee tee = new Tee(stdoutBranch, stderrBranch)) {
   *   System.out.print(&quot;hello&quot;); // &lt;= print &quot;hello&quot; and pass contents to stdoutBranch
   *   System.err.print(&quot;goodbye&quot;); // &lt;= print &quot;goodbye&quot; and pass contents to stderrBranch
   * } // don't pass contents to branch anymore if it reaches here
   * 
   * System.out.print(stdoutBranch.toString()); // &lt;= print &quot;hello&quot; on stdout
   * System.err.print(stderrBranch.toString()); // &lt;= print &quot;goodbye&quot; on stderr
   * </code></pre>
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
   * <pre><code>
   * ByteArrayOutputStream mergedBranch = new ByteArrayOutputStream();
   * 
   * try (Tee tee = new Tee(mergedBranch)) {
   *   System.out.print(&quot;hello&quot;); // &lt;= print &quot;hello&quot; and pass contents to stdoutBranch
   *   System.err.print(&quot;goodbye&quot;); // &lt;= print &quot;goodbye&quot; and pass contents to stderrBranch
   * } // don't pass contents to branch anymore if it reaches here
   * 
   * System.out.print(mergedBranch.toString()); // &lt;= print &quot;hellogoodbye&quot; on stdout
   * </code></pre>
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
