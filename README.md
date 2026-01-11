# char-reader

![Maven Central](https://img.shields.io/maven-central/v/io.github.edadma/char-reader_3)
![GitHub](https://img.shields.io/github/license/edadma/char-reader)
![Scala Version](https://img.shields.io/badge/Scala-3.7.4-blue.svg)
![ScalaJS Version](https://img.shields.io/badge/Scala.js-1.20.1-blue.svg)
![Scala Native Version](https://img.shields.io/badge/Scala_Native-0.5.9-blue.svg)

A Scala library for intelligent character-by-character reading with automatic indentation tracking.

## Overview

`char-reader` provides a powerful abstraction for parsing text with significant whitespace. It automatically generates `INDENT` and `DEDENT` tokens when indentation levels change, making it ideal for parsing languages like Python, YAML, or any custom DSL that uses indentation for structure.

Key features include:
- **Automatic indentation tracking** with configurable indentation styles
- **Precise position tracking** (line and column numbers)
- **Cross-platform support** (JVM, JavaScript via Scala.js, and Native)
- **Rich error reporting** with contextual information
- **Flexible iteration** over characters with lookahead capabilities
- **Comment line detection** and handling

## Installation

Add the dependency to your `build.sbt`:

```scala
libraryDependencies += "io.github.edadma" %%% "char_reader" % "0.1.24"
```

For cross-platform projects, use `%%%` to automatically select the appropriate artifact.

## Basic Usage

### Simple Character Reading

```scala
import io.github.edadma.char_reader.CharReader

// Read from string without indentation tracking
val reader = CharReader.fromString("Hello\nWorld")
while (!reader.eoi) {
  println(s"Char: '${reader.ch}' at line ${reader.line}, column ${reader.col}")
  reader = reader.next
}
```

### Indentation-Aware Reading

```scala
import io.github.edadma.char_reader.CharReader

val text = """
|1
| a
|  b
| c
|2
""".stripMargin

// Configure comment syntax (prefix, middle, suffix)
val reader = CharReader.fromString(text, indentation = Some(("#", "", "")))

reader.iterator.foreach { r =>
  r.ch match {
    case CharReader.INDENT => println("Indentation increased")
    case CharReader.DEDENT => println("Indentation decreased")
    case CharReader.EOI    => println("End of input")
    case '\n'              => println("Newline")
    case c                 => println(s"Character: '$c'")
  }
}
```

### Reading from File

```scala
val reader = CharReader.fromFile("input.txt", indentation = Some(("#", "", "")))
```

## Advanced Features

### Pattern Matching

```scala
val reader = CharReader.fromString("function hello() {")

reader.matches("function") match {
  case Some(nextReader) => println("Found 'function' keyword")
  case None => println("Pattern not found")
}
```

### Consuming Text

```scala
// Consume until whitespace
val (consumed, rest) = reader.consume(_.ch.isWhitespace)
println(s"Consumed: '$consumed'")

// Consume past a delimiter
reader.consumePastDelimiter("*/") match {
  case Some((content, rest)) => println(s"Comment content: '$content'")
  case None => println("Delimiter not found")
}
```

### Error Reporting

```scala
val reader = CharReader.fromString("error here")
reader.error("Unexpected token") // Throws with context
```

Output:
```
Unexpected token (line 1, column 1):
error here
^
```

## Special Characters

- `CharReader.EOI`: End of Input
- `CharReader.INDENT`: Indentation level increased
- `CharReader.DEDENT`: Indentation level decreased

## Configuration

### Tab Width

```scala
CharReader.fromString(text, tabs = 4) // Default tab width
```

### Indentation Settings

```scala
// Configure comment syntax: (prefix, middle, suffix)
val pythonStyle = Some(("#", "", ""))
val cStyle = Some(("/*", "", "*/"))
val reader = CharReader.fromString(text, indentation = pythonStyle)
```

## Building

This project uses SBT with cross-compilation:

```bash
# Test all platforms
sbt test

# Test specific platform
sbt charReaderJVM/test
sbt charReaderJS/test
sbt charReaderNative/test

# Publish
sbt publishSigned
```

## Contributing

This project welcomes contributions from the community. Contributions are accepted using GitHub pull requests.

For a good pull request, please provide:

1. **Clear description**: Include the "what" and "why" of your changes
2. **Passing tests**: Ensure existing tests pass and add new tests for new features
3. **Test coverage**: Use `sbt coverage test` to generate coverage reports
4. **Documentation**: Update README if adding new features
5. **Code style**: Run `scalafmt` to maintain consistent formatting

## Testing

Run the test suite:

```bash
sbt clean test
```

Generate coverage report:

```bash
sbt clean coverage test coverageReport
```

## License

This project is licensed under the [ISC License](https://opensource.org/licenses/ISC).
