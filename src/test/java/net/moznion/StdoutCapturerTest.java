package net.moznion;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;

import org.junit.Test;

public class StdoutCapturerTest {
	@Test
	public void shouldPass() {
		ByteArrayOutputStream parentStealer = new ByteArrayOutputStream();
		ByteArrayOutputStream childStealer = new ByteArrayOutputStream();

		try (StdoutCapturer parentStdoutCapturer = new StdoutCapturer(parentStealer)) {
			try (StdoutCapturer childStdoutCapturer = new StdoutCapturer(childStealer)) {
				System.out.print("goodbye");
			}
			System.out.print("hello");
		}

		assertEquals("hello", parentStealer.toString());
		assertEquals("goodbye", childStealer.toString());
	}
}
