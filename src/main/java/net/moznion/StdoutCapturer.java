package net.moznion;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class StdoutCapturer implements AutoCloseable {
	private final PrintStream originalPrintStream;

	public StdoutCapturer(ByteArrayOutputStream altByteArrayOutputStream) {
		originalPrintStream = System.out;
		System.setOut(new PrintStream(altByteArrayOutputStream));
	}

	@Override
	public void close() {
		System.setOut(originalPrintStream);
	}
}
