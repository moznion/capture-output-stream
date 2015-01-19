capture-output-stream [![Build Status](https://travis-ci.org/moznion/capture-output-stream.svg?branch=master)](https://travis-ci.org/moznion/capture-output-stream) [![javadoc.io](https://javadocio-badges.herokuapp.com/net.moznion/capture-output-stream/badge.svg)](https://javadocio-badges.herokuapp.com/net.moznion/capture-output-stream)
=============

Capture STDOUT and STDERR from Java with AutoCloseable

Synopsis
---

### Capturing

```java
import net.moznion.capture.output.stream.Capturer;

import java.io.ByteArrayOutputStream;

ByteArrayOutputStream stdout = new ByteArrayOutputStream();
ByteArrayOutputStream stderr = new ByteArrayOutputStream();

try (Capturer capturer = new Capturer(stdout, stderr)) {
    System.out.print("hello");   // <= don't print anything
    System.err.print("goodbye"); // <= don't print anything
} // turn back to original stdout and stderr at here

System.out.print(stdout.toString()); // <= print "hello" on stdout
System.err.print(stderr.toString()); // <= print "goodbye" on stderr
```

### Tee

```java
import net.moznion.capture.output.stream.Tee;

import java.io.ByteArrayOutputStream;

ByteArrayOutputStream stdoutBranch = new ByteArrayOutputStream();
ByteArrayOutputStream stderrBranch = new ByteArrayOutputStream();

try (Tee tee = new Tee(stdoutBranch, stderrBranch)) {
    System.out.print("hello");   // <= print "hello" and pass contents to stdoutBranch
    System.err.print("goodbye"); // <= print "goodbye" and pass contents to stderrBranch
} // don't pass contents to branch anymore if it reaches here

System.out.print(stdoutBranch.toString()); // <= print "hello" on stdout
System.err.print(stderrBranch.toString()); // <= print "goodbye" on stderr
```

Description
--

This package provides simple functions to capture or tee contents which is sent to STDERR or STDOUT.
It turns back to STDOUT or STDERR state which is before capturing or tee when escaping from try-with-resources statement.

This package is inspired by [Capture::Tiny](https://metacpan.org/pod/Capture::Tiny) from Perl.

Author
--

moznion (<moznion@gmail.com>)

License
--

```
The MIT License (MIT)
Copyright © 2014 moznion, http://moznion.net/ <moznion@gmail.com>

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the “Software”), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.
```

