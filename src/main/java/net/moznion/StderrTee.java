package net.moznion;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

import org.apache.commons.io.output.TeeOutputStream;

public class StderrTee implements AutoCloseable {
	private final PrintStream originalStderr;
	private final OutputStream stderrBranch;

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
