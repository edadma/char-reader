package xyz.hyperreal.char_reader

trait Testing {

  def noindent(s: String): String = noindent(CharReader.fromString(s, indentation = None))

  def noindent(r: CharReader): String = r.iterator map charline mkString "\n"

  def charline(r: CharReader): String =
    r.longErrorText(s"'${r.ch match {
      case CharReader.INDENT => "INDENT"
      case CharReader.DEDENT => "DEDENT"
      case CharReader.EOI    => "EOI"
      case '\n'              => "\\n"
      case c                 => c.toString
    }}'")

  def indent(s: String): String =
    CharReader.fromString(s, indentation = Some(("#", "", ""))).iterator map charline mkString "\n"

  def text(s: String): String = {
    var first = false

    CharReader.fromString(s, indentation = Some(("#", "", ""))).iterator map { r =>
      if (!first && r.ch == CharReader.INDENT) {
        r.textUntilDedent()
        first = true
      }

      charline(r)
    } mkString "\n"
  }

}
