package net.moznion;

import java.io.OutputStream;
import java.io.PrintStream;

public class StderrCapturer implements AutoCloseable {
	private final PrintStream originalStderr;

	public StderrCapturer(OutputStream alternativeStderr) {
		originalStderr = System.err;
		System.setErr(new PrintStream(alternativeStderr));
	}

	@Override
	public void close() {
		System.setErr(originalStderr);
	}
}
