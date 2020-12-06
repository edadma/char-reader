package xyz.hyperreal.ncr

object Testing {

//  def indent(s: String): String =
//    CharReader.fromString(s, indentation = Some((Some("#"), None))).toList map (r =>
//      r.longErrorText(s"<${r.ch match {
//        case '\n' => "\\n"
//        case c    => c.toString
//      }}>")) mkString "\n"
//
//  def text(s: String): String =
//    CharReader.fromString(s, indentation = Some((Some("#"), None))).toListIndentedText map (r =>
//      r.longErrorText(s"<${r.ch match {
//        case '\n' => "\\n"
//        case c    => c.toString
//      }}>")) mkString "\n"

  def noindent(s: String): String =
    CharReader.fromString(s, indentation = None).toList map (r =>
      r.longErrorText(s"'${r.ch match {
        case CharReader.INDENT => "INDENT"
        case CharReader.DEDENT => "DEDENT"
        case CharReader.EOI    => "EOI"
        case '\n'              => "\\n"
        case c                 => c.toString
      }}'")) mkString "\n"

  def indent(s: String): String =
    CharReader.fromString(s, indentation = Some(("#", "", ""))).toList map (r =>
      r.longErrorText(s"'${r.ch match {
        case CharReader.INDENT => "INDENT"
        case CharReader.DEDENT => "DEDENT"
        case CharReader.EOI    => "EOI"
        case '\n'              => "\\n"
        case c                 => c.toString
      }}'")) mkString "\n"

}
