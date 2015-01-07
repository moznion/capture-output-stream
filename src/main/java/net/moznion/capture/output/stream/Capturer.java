package net.moznion.capture.output.stream;

import java.io.OutputStream;

public class Capturer implements AutoCloseable {
	private final StdoutCapturer stdoutCapturer;
	private final StderrCapturer stderrCapturer;

	public Capturer(OutputStream alternativeStdout, OutputStream alternativeStderr) {
		stdoutCapturer = new StdoutCapturer(alternativeStdout);
		stderrCapturer = new StderrCapturer(alternativeStderr);
	}

	public Capturer(OutputStream mergedOut) {
		stdoutCapturer = new StdoutCapturer(mergedOut);
		stderrCapturer = new StderrCapturer(mergedOut);
	}

	@Override
	public void close() {
		stdoutCapturer.close();
		stderrCapturer.close();
	}
}
