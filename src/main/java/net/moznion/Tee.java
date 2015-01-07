package net.moznion;

import java.io.IOException;
import java.io.OutputStream;

public class Tee implements AutoCloseable {
	private final StdoutTee stdoutTee;
	private final StderrTee stderrTee;

	public Tee(OutputStream stdoutBranch, OutputStream stderrBranch) {
		stdoutTee = new StdoutTee(stdoutBranch);
		stderrTee = new StderrTee(stderrBranch);
	}

	public Tee(OutputStream mergedBranch) {
		stdoutTee = new StdoutTee(mergedBranch);
		stderrTee = new StderrTee(mergedBranch);
	}

	@Override
	public void close() throws IOException {
		stdoutTee.close();
		stderrTee.close();
	}
}
