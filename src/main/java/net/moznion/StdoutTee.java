package net.moznion;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

import org.apache.commons.io.output.TeeOutputStream;

public class StdoutTee implements AutoCloseable {
	private final PrintStream originalStdout;
	private final OutputStream stdoutBranch;

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
