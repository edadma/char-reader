package xyz.hyperreal.char_reader

object Main extends App with Testing {

  val s =
    " 1"
//    """
//      |1
//      | 2
//      | #asdf
//      | 3
//      |""".trim.stripMargin

  println(indent(s))

}
