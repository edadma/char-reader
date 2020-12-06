package xyz.hyperreal.char_reader

object CharReader {
  val EOI = '\u001A'
  val INDENT = '\uE000'
  val DEDENT = '\uE001'

  def fromString(s: String, tabs: Int = 4, indentation: Option[(String, String, String)] = None) =
    new CharReader(s to LazyList, tabs, indentation)
}

class CharReader private (input: LazyList[Char],
                          start: LazyList[Char],
                          line: Int,
                          col: Int,
                          tabs: Int,
                          val prev: Option[Char],
                          indentation: Option[(String, String, String)],
                          indent: Int,
                          level: Int,
                          private var _textUntilDedent: Boolean) {

  import CharReader.{EOI, INDENT, DEDENT}

  private type Input = LazyList[Char]

  def this(in: LazyList[Char], tabs: Int, indentation: Option[(String, String, String)]) =
    this(in, in, 1, 1, tabs, None, indentation, 0, 0, false)

  def textUntilDedent(): Unit = _textUntilDedent = true

  def more: Boolean = input.nonEmpty

  def eoi: Boolean = input.isEmpty

  def ch: Char = if (more) input.head else EOI

  def soi: Boolean = prev.isEmpty

  def sol: Boolean = soi || prev.get == '\n' || prev.get == INDENT || prev.get == DEDENT

  @scala.annotation.tailrec
  private def matches(in: Input, s: String, idx: Int = 0): Boolean =
    if (s.isEmpty) false
    else if (idx == s.length) true
    else if (in.isEmpty || in.head != s.charAt(idx)) false
    else matches(in.tail, s, idx + 1)

  @scala.annotation.tailrec
  private def skipToEol(in: Input, count: Int = 0): (Input, Int) =
    if (in.isEmpty || in.head == '\n') (in, count)
    else skipToEol(in.tail, count + 1)

  @scala.annotation.tailrec
  private def skipSpace(in: Input, count: Int = 0): Either[(Input, Int), (Input, Int)] =
    if (in.isEmpty || in.head == '\n') Left(in, count)
    else if (in.head == ' ' && (!_textUntilDedent || count < level)) skipSpace(in.tail, count + 1)
    else if (!_textUntilDedent && matches(in, indentation.get._1)) {
      val (r, c) = skipToEol(in)

      Left(r, c + count)
    } else Right(in, count)

  def next: CharReader =
    if (ch == EOI)
      error("end of input")
    else if (soi && ch == ' ') {
      skipSpace(input) match {
        case Left((rest, count)) => newLine(rest, count + 1)
        case Right((rest, count)) =>
          new CharReader(INDENT +: rest, input, 1, count + 1, tabs, None, indentation, count, count, false)
      }
    } else if (ch == '\n')
      if (indentation.isDefined)
        skipSpace(input.tail) match {
          case Left((rest, count)) =>
            if (rest.isEmpty && indent > 0)
              newLine(Seq.fill((level - count) / indent)(DEDENT) ++: rest, count + 1, indent, 0)
            else newLine(rest, count + 1)
          case Right((rest, count)) =>
            if (level == 0)
              newLine(INDENT +: rest, count + 1, count, count)
            else if (count % indent != 0 || count > level + indent)
              newLine(rest, count + 1)
                .error(s"expected indentation to be ${if (count > level) level + indent
                else level - indent} spaces, not $count spaces")
            else {
              val in =
                if (count > level) INDENT +: rest
                else if (count < level) {
                  _textUntilDedent = false
                  Seq.fill((level - count) / indent)(DEDENT) ++: rest
                } else rest

              newLine(in, count + 1, indent, count)
            }
        } else newLine(input.tail)
    else if (indentation.isDefined && input.tail.isEmpty && level > 0)
      nextChar(LazyList.fill(level / indent)(DEDENT), 0)
    else
      nextChar(input.tail)

  private def nextChar(in: Input, _level: Int = level): CharReader =
    new CharReader(in,
                   start,
                   line,
                   if (ch == INDENT || ch == DEDENT) col else col + 1,
                   tabs,
                   Some(ch),
                   indentation,
                   indent,
                   _level,
                   _textUntilDedent)

  private def newLine(in: Input, _col: Int = 1, _indent: Int = indent, _level: Int = level): CharReader =
    new CharReader(in, input.tail, line + 1, _col, tabs, Some(ch), indentation, _indent, _level, _textUntilDedent)

  def iterator: Iterator[CharReader] =
    new Iterator[CharReader] {
      private var done = false
      private var r: CharReader = _

      def hasNext: Boolean = !done

      def next(): CharReader = {
        if (done)
          throw new NoSuchElementException("no more characters")
        else if (r eq null)
          r = CharReader.this
        else
          r = r.next

        if (r.ch == EOI)
          done = true

        r
      }
    }

  def lineString: String = {
    val buf = new StringBuilder
    var l: Input = this.start

    while (l.nonEmpty && l.head != '\n') {
      if (l.head != INDENT && l.head != DEDENT)
        buf += l.head

      l = l.tail
    }

    buf.toString
  }

  def lineText: String = {
    val buf = new StringBuilder
    var zcol = 0

    lineString foreach {
      case '\t' =>
        val len = tabs - zcol % tabs

        buf ++= " " * len
        zcol += len
      case c =>
        buf += c
        zcol += 1
    }

    buf.toString
  }

  def errorText: String = lineText + '\n' + (" " * (col - 1)) + "^\n"

  def longErrorText(msg: String): String = s"$msg (line $line, column $col):\n" + errorText

  def error(msg: String): Nothing = sys.error(longErrorText(msg))

  override def toString =
    s"<$line, $col, ${ch match {
      case INDENT                      => "INDENT"
      case DEDENT                      => "DEDENT"
      case EOI                         => "EOI"
      case '\n'                        => "\\n"
      case ' '                         => "' '"
      case _ if ch >= ' ' && ch <= '~' => ch.toString
      case _                           => "?"
    }}>"
}
