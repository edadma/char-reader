package xyz.hyperreal.char_reader

import java.io.{FileInputStream, InputStream}

import scala.collection.mutable.ListBuffer

object CharReader {
  val EOI = '\u001A'
  val INDENT = '\uE000'
  val DEDENT = '\uE001'

  def fromString(s: String, tabs: Int = 4, indentation: Option[(String, String, String)] = None) =
    new CharReader(s to LazyList, tabs, indentation)
}

class CharReader private (val input: LazyList[Char],
                          start: LazyList[Char],
                          val line: Int,
                          val col: Int,
                          val tabs: Int,
                          val prev: Option[Char],
                          val indentation: Option[(String, String, String)],
                          indent: Int,
                          level: Int,
                          var textUntilDedent: Boolean) {

  import CharReader.{EOI, INDENT, DEDENT}

  private type Input = LazyList[Char]

  def this(chars: LazyList[Char], tabs: Int, indentation: Option[(String, String, String)]) =
    this(chars, chars, 1, 1, tabs, None, indentation, 0, 0, false)

  def some: Boolean = input.nonEmpty

  def none: Boolean = input.isEmpty

  def ch: Char = if (some) input.head else EOI

  def sol: Boolean = prev.isEmpty || prev.get == '\n' || prev.get == INDENT || prev.get == DEDENT

  @scala.annotation.tailrec
  private def matches(in: Input, s: String, idx: Int = 0): Boolean =
    if (s.isEmpty)
      false
    else if (idx == s.length)
      true
    else if (in.isEmpty || in.head != s.charAt(idx))
      false
    else
      matches(in.tail, s, idx + 1)

  @scala.annotation.tailrec
  private def skipSpace(in: Input, count: Int = 0): Either[(Input, Int), (Input, Int)] =
    if (in.isEmpty || in.head == '\n')
      Left(in, count)
    else if (in.head == ' ')
      skipSpace(in.tail, count + 1)
    else
      Right(in, count)

  def next: CharReader =
    if (ch == EOI)
      error("end of input")
    else if (ch == '\n')
      if (indentation.isDefined)
        skipSpace(input.tail) match {
          case Left((rest, count)) =>
            if (rest.isEmpty) newLine(Seq.fill((indent * level - count) / indent)(DEDENT) ++: rest, count + 1)
            else newLine(rest, count + 1)
          case Right((rest, count)) =>
            val cur = indent * level

            if (level == 0)
              newLine(INDENT +: rest, count + 1, count, 1)
            else if (count % indent != 0 || count > cur + indent)
              newLine(rest, count + 1)
                .error(s"expected indentation to be ${if (count > cur) cur + indent
                else cur - indent} spaces, not $count spaces")
            else {
              val in =
                if (count > cur) INDENT +: rest
                else Seq.fill((cur - count) / indent)(DEDENT) ++: rest

              newLine(in, count + 1, indent, count / indent)
            }
        } else newLine(input.tail)
    else
      nextChar

  private def nextChar: CharReader =
    new CharReader(input.tail,
                   start,
                   line,
                   if (ch == INDENT || ch == DEDENT) col else col + 1,
                   tabs,
                   Some(ch),
                   indentation,
                   indent,
                   level,
                   textUntilDedent)

  private def newLine(in: Input, _col: Int = 1, _indent: Int = indent, _level: Int = level): CharReader =
    new CharReader(in, input.tail, line + 1, _col, tabs, Some(ch), indentation, _indent, _level, textUntilDedent)

  def toList: List[CharReader] = {
    val buf = new ListBuffer[CharReader]
    var r: CharReader = this

    @scala.annotation.tailrec
    def list(): Unit = {
      buf += r

      if (r.ch != EOI) {
        r = r.next
        list()
      }
    }

    list()
    buf.toList
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
