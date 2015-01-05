package net.moznion;

import java.io.ByteArrayOutputStream;

public class Capturer implements AutoCloseable {
	private final StdoutCapturer stdoutCapturer;
	private final StderrCapturer stderrCapturer;

	public Capturer(ByteArrayOutputStream altStdout, ByteArrayOutputStream altStderr) {
		stdoutCapturer = new StdoutCapturer(altStdout);
		stderrCapturer = new StderrCapturer(altStderr);
	}

	public Capturer(ByteArrayOutputStream mergedOut) {
		stdoutCapturer = new StdoutCapturer(mergedOut);
		stderrCapturer = new StderrCapturer(mergedOut);
	}

	@Override
	public void close() {
		stdoutCapturer.close();
		stderrCapturer.close();
	}
}
