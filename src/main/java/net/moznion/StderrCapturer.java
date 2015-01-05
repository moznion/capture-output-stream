package net.moznion;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class StderrCapturer implements AutoCloseable {
	private final PrintStream originalPrintStream;

	public StderrCapturer(ByteArrayOutputStream altByteArrayOutputStream) {
		originalPrintStream = System.err;
		System.setErr(new PrintStream(altByteArrayOutputStream));
	}

	@Override
	public void close() {
		System.setErr(originalPrintStream);
	}
}
