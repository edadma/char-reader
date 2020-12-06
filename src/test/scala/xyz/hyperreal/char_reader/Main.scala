package xyz.hyperreal.char_reader

import Testing._

object Main extends App {

  val s =
//    "1\n \n"
    """
      |1
      |  #a
      |  2
      | #b
      |  3
      |""".trim.stripMargin

  println(indent(s))

}
