package xyz.hyperreal

import scala.scalajs.js
import js.Dynamic.{global => g}

package object char_reader {

  private val fs = g.require("fs")

  def readFile(file: String): String = fs.readFileSync(file).toString

}
