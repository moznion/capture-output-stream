package net.moznion;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.junit.Test;

public class TeeTest {
	@Test
	public void shouldPassForSeparated() throws IOException {
		ByteArrayOutputStream stdoutStealer = new ByteArrayOutputStream();
		ByteArrayOutputStream stderrStealer = new ByteArrayOutputStream();

		ByteArrayOutputStream parentStdoutBranchStealer = new ByteArrayOutputStream();
		ByteArrayOutputStream parentStderrBranchStealer = new ByteArrayOutputStream();

		ByteArrayOutputStream childStdoutBranchStealer = new ByteArrayOutputStream();
		ByteArrayOutputStream childStderrBranchStealer = new ByteArrayOutputStream();

		try (StdoutCapturer stdoutCapturer = new StdoutCapturer(stdoutStealer)) {
			try (StderrCapturer stderrCapturer = new StderrCapturer(stderrStealer)) {
				try (Tee parentTee = new Tee(parentStdoutBranchStealer, parentStderrBranchStealer)) {
					try (Tee childTee = new Tee(childStdoutBranchStealer, childStderrBranchStealer)) {
						System.out.print("hello,");
						System.err.print("HELLO,");
					}
					System.out.print("yo,");
					System.err.print("YO,");
				}
				System.out.print("goodbye");
				System.err.print("GOODBYE");
			}
		}

		assertEquals("hello,yo,goodbye", stdoutStealer.toString());
		assertEquals("HELLO,YO,GOODBYE", stderrStealer.toString());

		assertEquals("hello,yo,", parentStdoutBranchStealer.toString());
		assertEquals("HELLO,YO,", parentStderrBranchStealer.toString());

		assertEquals("hello,", childStdoutBranchStealer.toString());
		assertEquals("HELLO,", childStderrBranchStealer.toString());
	}

	@Test
	public void shouldPassForMerger() throws IOException {
		ByteArrayOutputStream stdoutStealer = new ByteArrayOutputStream();
		ByteArrayOutputStream stderrStealer = new ByteArrayOutputStream();

		ByteArrayOutputStream parentMergedBranchStealer = new ByteArrayOutputStream();
		ByteArrayOutputStream childMergedBranchStealer = new ByteArrayOutputStream();

		try (StdoutCapturer stdoutCapturer = new StdoutCapturer(stdoutStealer)) {
			try (StderrCapturer stderrCapturer = new StderrCapturer(stderrStealer)) {
				try (Tee parentTee = new Tee(parentMergedBranchStealer)) {
					try (Tee childTee = new Tee(childMergedBranchStealer)) {
						System.out.print("hello,");
						System.err.print("HELLO,");
					}
					System.out.print("yo,");
					System.err.print("YO,");
				}
				System.out.print("goodbye");
				System.err.print("GOODBYE");
			}
		}

		assertEquals("hello,yo,goodbye", stdoutStealer.toString());
		assertEquals("HELLO,YO,GOODBYE", stderrStealer.toString());

		assertEquals("hello,HELLO,yo,YO,", parentMergedBranchStealer.toString());

		assertEquals("hello,HELLO,", childMergedBranchStealer.toString());
	}
}
