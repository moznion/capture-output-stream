package net.moznion.capture.output.stream;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;

import org.junit.Test;

public class StderrCapturerTest {
	@Test
	public void shouldPass() {
		ByteArrayOutputStream parentStealer = new ByteArrayOutputStream();
		ByteArrayOutputStream childStealer = new ByteArrayOutputStream();

		try (StderrCapturer parentStderrCapturer = new StderrCapturer(parentStealer)) {
			try (StderrCapturer childStderrCapturer = new StderrCapturer(childStealer)) {
				System.err.print("goodbye");
			}
			System.err.print("hello");
		}

		assertEquals("hello", parentStealer.toString());
		assertEquals("goodbye", childStealer.toString());
	}
}
