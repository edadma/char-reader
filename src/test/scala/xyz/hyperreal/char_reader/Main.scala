package xyz.hyperreal.char_reader

object Main extends App with Testing {

  val s =
//    " 1"
    """
      |123
      |""".trim.stripMargin

  println(indent(s))

}
