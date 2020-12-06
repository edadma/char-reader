package xyz.hyperreal.char_reader

import Testing._

object Main extends App {

  val s =
//    "1\n a\n  b\n2"
    """
      |1
      | 2
      |""".trim.stripMargin

  println(indent(s))

}
