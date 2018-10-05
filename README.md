# File Content Transformator

[![](https://jitpack.io/v/ondrej-nemec/filetransformator.svg)](https://jitpack.io/#ondrej-nemec/filetransformator)
[![MIT License](http://img.shields.io/badge/license-MIT-green.svg) ](https://github.com/ondrej-nemec/filetransformator/blob/master/LICENSE)

* [Description](#description)
* [Get library](#how-to-install)
* [Usage](#usage)
	* [From terminal](#from-terminal)
	* [As library](#as-library)
	* [LineTransformator interface](#linetransformator-interface)

## Description
Package which allow you very quickly change content of file. This package read line from original file, make all given transform and save line to new file.
## How to install
### Download:

<a href="https://ondrej-nemec.github.io/download/file-transformator-0.2.jar" target=_blank>Download jar</a>

### Maven:

After `build` tag:
```xml
<repositories>
	<repository>
	    <id>jitpack.io</id>
	    <url>https://jitpack.io</url>
	</repository>
</repositories>
```
And to `dependencies`:
```xml
<dependency>
  <groupId>com.github.ondrej-nemec</groupId>
  <artifactId>filetransformator</artifactId>
  <version>v0.2-alpha</version>
</dependency>
```

## Usage
### From terminal
This library is java runnable file, too. So you can use it from terminal. Of course, options are limited.
```shell
# windows
start javaw -jar ./file-transformator-<version>.jar
# linux
java -jar ./file-transformator-<version>.jar
```
After jar file is:
`<originFile> <resultFile> [-co <originCharset>] [-cr <resultCharset>] [-do] [-dr] [-t <time>]/[-tu <time> <H/M/S/N>] [-m <replaceWhat> <repaceWith>]`
#### Options
**originFile:** from which file will be read

**resultFile:** to which file will be appended, if no file exist, will be created

**-co <originCharset>:** if you wish specified charset of origin file - some specials symbols etc.

**-cr <resultCharset>:** similar to previous

**-do:** if you use this option, original file will be deleted at the end

**-dr:** with this option, program delete result file before transformation if exists

**-t <time>/-tu <time> <H/M/S/N>:** For *.srt subtitle file transformation. If text is soon or late to voice, you could use this. Require this format of time: 'hh:mm:ss,sss --> hh:mm:ss,sss'. Given number is default miliseconds, but you can specify time unit (H - hours, M - minutes, S - second, N - miliseconds). If subtitles are soon that voice, number is positive, if subtitles are late that voice, number is negative.

**-m <replaceWhat> <repaceWith>:** Replace first given string with second given string. This option you could use more time.

### As library
There are two ways how to use this package as library. First way uses the parametrized constructor of `ContentTransformator` class. When you create new class, everything is done.
```java
public ContentTransformator(
			String originPath, String originCharset,
			boolean deleteOrigin,
			String resultPath, String resultCharset,
			boolean deleteExistingResult,
			List<LineTransformator> transformators);
```
The parameters are very similar to options for bash command. [See options](#options). `LineTransformator` interface is explained below. [See LineTransformator](#linetransformator-interface).

The second way is using `public void transform(BufferedReader br, BufferedWriter bw, List<LineTransformator> transformators) throws IOException` method. For this way has `LineTransformator` class non-parametrized constructor, too. **Remember: everythings is doing per line - read line, applying every LineTransformator, save line - so add append option to BufferWriter.**

### LineTransformator interface
With this library, you can modify files as you wish. Just create new implementation of `LineTransformator` interface. This interface has only one method:
```java
String updateLine(String line);
```
Returned value of this method is used as input to next `LineTransformator` or is written to file. In this library two implemetations are prepared.
#### SubtitleTimeTransformator
For *.srt subtitle file transformation. Require this format of time: 'hh:mm:ss,sss --> hh:mm:ss,sss'. If subtitles are soon that voice, shift is positive, if subtitles are late that voice, shift is negative. Shift is given in constructor.

#### ReplaceStringTransformator
Use `java.util.Map`. The 'key' is what you want to replace, 'value' is new value. Map is given in constructor. Or you can specified that 'keys' are regexs.
