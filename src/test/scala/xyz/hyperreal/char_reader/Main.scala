package xyz.hyperreal.char_reader

object Main extends App with Testing {

  val s =
    "1\n2\n"
//    """
//      |1
//      |2
//      |""".trim.stripMargin

  println(indent(s))

}
