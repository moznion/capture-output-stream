package net.moznion;

import java.io.OutputStream;
import java.io.PrintStream;

public class StdoutCapturer implements AutoCloseable {
	private final PrintStream originalStdout;

	public StdoutCapturer(OutputStream alternativeStdout) {
		originalStdout = System.out;
		System.setOut(new PrintStream(alternativeStdout));
	}

	@Override
	public void close() {
		System.setOut(originalStdout);
	}
}
