package xyz.hyperreal

package object char_reader {

  def readFile(file: String): String = util.Using(io.Source.fromFile(file))(source => source.mkString).get

}
