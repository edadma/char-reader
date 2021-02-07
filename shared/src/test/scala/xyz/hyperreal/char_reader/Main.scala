package xyz.hyperreal.char_reader

object Main extends App with Testing {

  val s = "1\n2\n"

  println(noindent(s))
  println(noindent(CharReader.fromFile("test")))

}
