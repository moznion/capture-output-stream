package net.moznion;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.junit.Test;

public class StderrTeeTest {
	@Test
	public void shouldPass() throws IOException {
		ByteArrayOutputStream stderrStealer = new ByteArrayOutputStream();

		ByteArrayOutputStream parentBranchStealer = new ByteArrayOutputStream();
		ByteArrayOutputStream childBranchStealer = new ByteArrayOutputStream();
		ByteArrayOutputStream grandChildBranchStealer = new ByteArrayOutputStream();

		try (StderrCapturer parentStderrCapturer = new StderrCapturer(stderrStealer)) {
			try (StderrTee parentStderrTee = new StderrTee(parentBranchStealer)) {
				System.err.print("hello,");
				try (StderrTee childStderrTee = new StderrTee(childBranchStealer)) {
					System.err.print("thank you,");
					try (StderrTee grandChildStderrTee = new StderrTee(grandChildBranchStealer)) {
						System.err.print("yo,");
					}
				}
			}
			System.err.print("goodbye");
		}

		assertEquals("hello,thank you,yo,", parentBranchStealer.toString());
		assertEquals("thank you,yo,", childBranchStealer.toString());
		assertEquals("yo,", grandChildBranchStealer.toString());
		assertEquals("hello,thank you,yo,goodbye", stderrStealer.toString());
	}
}
