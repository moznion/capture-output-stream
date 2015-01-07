package net.moznion;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.junit.Test;

public class StdoutTeeTest {
	@Test
	public void shouldPass() throws IOException {
		ByteArrayOutputStream stdoutStealer = new ByteArrayOutputStream();

		ByteArrayOutputStream parentBranchStealer = new ByteArrayOutputStream();
		ByteArrayOutputStream childBranchStealer = new ByteArrayOutputStream();
		ByteArrayOutputStream grandChildBranchStealer = new ByteArrayOutputStream();

		try (StdoutCapturer parentStdoutCapturer = new StdoutCapturer(stdoutStealer)) {
			try (StdoutTee parentStdoutTee = new StdoutTee(parentBranchStealer)) {
				System.out.print("hello,");
				try (StdoutTee childStdoutTee = new StdoutTee(childBranchStealer)) {
					System.out.print("thank you,");
					try (StdoutTee grandChildStdoutTee = new StdoutTee(grandChildBranchStealer)) {
						System.out.print("yo,");
					}
				}
			}
			System.out.print("goodbye");
		}

		assertEquals("hello,thank you,yo,", parentBranchStealer.toString());
		assertEquals("thank you,yo,", childBranchStealer.toString());
		assertEquals("yo,", grandChildBranchStealer.toString());
		assertEquals("hello,thank you,yo,goodbye", stdoutStealer.toString());
	}
}
