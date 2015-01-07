package net.moznion.capture.output.stream;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;

import org.junit.Test;

public class CapturerTest {
	@Test
	public void shouldPassForSeparated() {
		ByteArrayOutputStream parentStdoutStealer = new ByteArrayOutputStream();
		ByteArrayOutputStream parentStderrStealer = new ByteArrayOutputStream();
		ByteArrayOutputStream childStdoutStealer = new ByteArrayOutputStream();
		ByteArrayOutputStream childStderrStealer = new ByteArrayOutputStream();

		try (Capturer parentCapturer = new Capturer(parentStdoutStealer, parentStderrStealer)) {
			try (Capturer childCapturer = new Capturer(childStdoutStealer, childStderrStealer)) {
				System.out.print("goodbye");
				System.err.print("GOODBYE");
			}
			System.out.print("hello");
			System.err.print("HELLO");
		}

		assertEquals("hello", parentStdoutStealer.toString());
		assertEquals("HELLO", parentStderrStealer.toString());
		assertEquals("goodbye", childStdoutStealer.toString());
		assertEquals("GOODBYE", childStderrStealer.toString());
	}

	@Test
	public void shouldPassForMerged() {
		ByteArrayOutputStream parentMergedOut = new ByteArrayOutputStream();
		ByteArrayOutputStream childMergedOut = new ByteArrayOutputStream();

		try (Capturer parentCapturer = new Capturer(parentMergedOut)) {
			try (Capturer childCapturer = new Capturer(childMergedOut)) {
				System.out.print("goodbye");
				System.err.print("GOODBYE");
			}
			System.out.print("hello");
			System.err.print("HELLO");
		}

		assertEquals("helloHELLO", parentMergedOut.toString());
		assertEquals("goodbyeGOODBYE", childMergedOut.toString());
	}
}
