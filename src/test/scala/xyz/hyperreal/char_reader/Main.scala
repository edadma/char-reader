package xyz.hyperreal.ncr

import Testing._

object Main extends App {

  val s = "1\n a\n2"
//    """
//      |1
//      |  a
//      |    b
//      |2
//      |""".trim.stripMargin

  println(indent(s))

}
